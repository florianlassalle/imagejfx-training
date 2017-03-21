/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolistscijavav2;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.scijava.Context;
import org.scijava.InstantiableException;
import org.scijava.SciJava;
import org.scijava.event.EventHandler;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.PluginInfo;
import org.scijava.plugin.PluginService;

/**
 *
 * @author florian
 */
public class ToDoListController extends AnchorPane {
    /*
    classe controleur, elles est lancée par le main.
    elle doit extends l'elément root de la fenètre, ici AnchorPane.
    
    On doit aussi declarer avec un @FXML les variables correspondants aux boutons, 
    on doit ici leur atribuer le meme nom que leur id dans le fichier FXML.
    */
    
    @Parameter
    TaskService taskService;
    @Parameter
    TaskSaving taskSaving;
    @Parameter
    PluginService pluginService;
    
    @FXML
    AnchorPane anchorPane;
    @FXML
    BorderPane borderPane;
    @FXML
    private TextField txttask;
    @FXML
    private Button btnsetdone;
    @FXML
    private ListView<Task> listView;
    @FXML
    private RadioButton importantTask;
    @FXML
    private RadioButton greenTask;
    @FXML
    private RadioButton basicTask;
    @FXML
    final ToggleGroup taskGroup = new ToggleGroup();
    @FXML
    private ToolBar toolBar;
    @FXML
    private Stage stage;
    
    @Parameter
        Context context;
    
    public ToDoListController() throws IOException {
        /*
        Le constructeur du controleur, on initialise ici la vue.
        On choisis ici le fichier FXML a ouvrir.
        j'ai choisi d'injecter ici scijava, parceque on a besoin qu'il soit injecté
        avant de lancer l'initialisation du plugin "saveBar" qui  est initialisée dans le constructeur.
        
        
        */
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Scene.fxml")); // c'est toujour galere de trouver le bon chemin, penser a mettre le fxml dans le bon dossier comme ici
        loader.setRoot(this);       //root est la base de la page, ici anchorPane
        loader.setController(this); //definition du controlleur
        loader.load(); // generation de la fenetre.
        
        // Comme dit plus haut, on injecte scijava ici plutot que dans le main, ce qui permet qu'il soit injecté avant de lancer les fonctions d'initialisation
         SciJava scijava = new SciJava();
        scijava.context().inject(this);
        
        listView.getItems().add(new Task("Finir le cour javaFX", true));
        
        listView.setCellFactory(this::createCell); // Bon la je saia pas trop, mais a priori on creer des cellules qui remplissent la listview mais sont invisibles.
        importantTask.setToggleGroup(taskGroup); // Creation du groupe de boutons qui contient les differents types de taches
        greenTask.setToggleGroup(taskGroup);
        basicTask.setToggleGroup(taskGroup);
        taskGroup.selectToggle(basicTask);
        
        // Comme dit en haut, on creer ici une toolBar, on va y injecter automatiquement des boutons en fonctions des plugins qui extendent l'interface TaskSaving
        SavingToolBar savingBar = new SavingToolBar(context);
           
        
    }
    
    
    private ListCell<Task> createCell (ListView<Task> task){
        return new TaskListCell();
    }
    
    @FXML
    public void openTask() throws IOException{
        /*
        Fonction permettant douvrir une nouvelle fenetre contenant le détail d'une tache
        */
         List<Task> tasks = listView
                .getItems()
                .stream()
                .filter(ch-> ch.isSelected())
                .collect(Collectors.toList());
        Task task = tasks.get(0);
        Parent children = new TaskViewController(task);
        //anchorPane.getChildren().setAll(children);
        
        Scene newScene = new Scene (children);
        Stage stage = new Stage();
        stage.setScene(newScene);
        
        stage.show();
        
        
        /*
        FXMLLoader loader = new FXMLLoader();
        TaskViewController taskControler = new TaskViewController(task);
        loader.setLocation(getClass().getResource("/fxml/TaskView.fxml"));
        loader.setRoot(taskControler);
        loader.setController(taskControler);
        loader.load();
        
        Node node = FXMLLoader.load(ToDoListController.class.getResource("/fxml/TaskView.fxml"));
        
        this.getChildren().setAll(nodes);
        */
    }
    
    /*
    La gestion des tache se fait par un service scijava, donc les fonctions appelles par les boutons ne font
    que appeler les fonctions du service
    */
    @FXML
    private void selectAll(){
        taskService.selectAll(btnsetdone);
    }
    @FXML
    private void addTask(){
        taskService.addTask(txttask, taskGroup);
    }
    @FXML
    private void deleteTask(){
        taskService.removeTask( listView);
    }
    
    /*
    Ces fonctions recuperent les evennements emis par les services et plugins pour mettre la vue a jour en fonction des changements dans le modele.
    */
    @EventHandler
    public void onTaskaddedEvent(TaskAddedEvent event){
        Platform.runLater( () ->
            listView.getItems().add(event.getTask())
        );
    }
    
    @EventHandler
    public void onDeletedEvent(TaskDeletedEvent event){
        Platform.runLater( () ->
            listView.getItems().removeAll(event.getTask())
        );
    }
    @EventHandler
    public void onSelectAllEvent(SelectAllEvent event){
        Platform.runLater( () ->
            btnsetdone.setText(event.getValue())
        );
    }
    @EventHandler
    public void onLoadedEvent(LoadedEvent event){
        Platform.runLater( () ->
                event.getTasks()
                    .stream()
                    .forEach((task) -> {
                    listView.getItems().add(task);
        }));
    }

    
    
    private class TaskListCell extends ListCell<Task> {
        /*
        Ici on definis comment la vue doit afficher les tache, 
        */
        
        CheckBox checkbox = new CheckBox();

        public TaskListCell() {
            super();
            itemProperty().addListener(this::onItemChanged);
            
        }

        private void onItemChanged(ObservableValue obs, Task oldValue, Task newValue) {
            
            if(oldValue != null){
                oldValue.selectedProperty().unbindBidirectional(checkbox.selectedProperty());
            }
            
            if(newValue == null){
                setGraphic(null);
            }
            else {
                setGraphic(checkbox);
                newValue.selectedProperty().bindBidirectional(checkbox.selectedProperty());
                checkbox.setText(newValue.getName());
                checkbox.setTextFill(newValue.getColor());
                checkbox.setFont(newValue.getFont());
            }
        }
    }
        
    
    
    public class SavingToolBar extends ToolBar{
        
        @Parameter
        PluginService pluginService;
        
        public SavingToolBar (Context context){
            context.inject(this);
            pluginService
                    .getPluginsOfType(TaskSaving.class)
                    .forEach(this::addPlugin);
        }
    
        public void addPlugin(PluginInfo<TaskSaving> pluginInfo) {

            try {
                Button saveButton = new Button(pluginInfo.getLabel());
                
                TaskSaving plugin = pluginInfo.createInstance();
                context.inject(plugin);
                
                saveButton.setOnAction(ActionEvent -> applyPlugin(plugin));
                toolBar.getItems().add(saveButton);
            } catch (InstantiableException ex) {
                Logger.getLogger(ToDoListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void applyPlugin(TaskSaving plugin){
            plugin.saveCalling();
        }
    }
}
