/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.filebrowser;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.WindowEvent;
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
public class BrowserControler extends AnchorPane{
    @Parameter
    FileService fileService;
    @Parameter
    Sorting sorting;
     @Parameter
        Context context;
    
    @FXML
    private ListView<ItemFile> listView;
    @FXML
    private ToolBar toolBar;
    @FXML
    private TextField textField;
    

    public BrowserControler() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Scene.fxml")); // c'est toujour galere de trouver le bon chemin, penser a mettre le fxml dans le bon dossier comme ici
        loader.setRoot(this);       //root est la base de la page, ici anchorPane
        loader.setController(this); //definition du controlleur
        loader.load(); // generation de la fenetre.
        
        // Comme dit plus haut, on injecte scijava ici plutot que dans le main, ce qui permet qu'il soit injecté avant de lancer les fonctions d'initialisation
        SciJava scijava = new SciJava();
        scijava.context().inject(this);
        
        //Initialization
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView.setCellFactory(this::createCell);
        SortingToolBar sortingBar = new SortingToolBar(context);
        fileService.openFolder();
    }

     private ListCell<ItemFile> createCell (ListView<ItemFile> item){
        return new FileListCell();
    }
    
    
    public void browseFile(){
        // appel au service
        // affichage sur la listView
    }
    @FXML
    public void searchFile(){
        fileService.searching(textField.getText());
        //textField.clear();
    }
    @FXML
    public void up(){
        fileService.up();
    }
    /*
    @FXML
    public void click(){
        fileService.selection(listView.getSelectionModel().getSelectedItems());
    }
    */
    @FXML
    public void open () throws IOException{
        System.out.println("prout");
        fileService.open();
    }
    
    @EventHandler
    public void onUpdateEvent(ListUpdateEvent event){
        Platform.runLater( () ->
        listView.getItems().removeAll(listView.getItems())
        );
        Platform.runLater( () ->
            
            event.getItemList()
                    .stream()
                    .forEach((file) -> {
                    
                    listView.getItems().add(file);
                    }));
    }
    
    
      
    private class FileListCell extends ListCell<ItemFile> {
        /*
        Ici on definis comment la vue doit afficher les tache, 
        */
        HBox hbox;
        CheckBox checkbox = new CheckBox();
       

        public FileListCell() {
            super();
            itemProperty().addListener(this::onItemChanged);
            
        }

        private void onItemChanged(ObservableValue obs, ItemFile oldValue, ItemFile newValue) {
            
            if(oldValue != null){
                oldValue.selectedProperty().unbindBidirectional(checkbox.selectedProperty());
            }
            
            if(newValue == null){
                setGraphic(null);
            }
            else {
                
                //Image depIcon =  new Image("file:folder.png",true);  
                CheckBox checkbox = new CheckBox();
  
                hbox = new HBox();  
  
  
                Pane pane = new Pane();  
                pane.setMinWidth(5);  


                hbox.getChildren().addAll(new ImageView(newValue.getIcon()), pane, checkbox);  
                newValue.selectedProperty().bindBidirectional(checkbox.selectedProperty());
                checkbox.setText(newValue.getName());
                setGraphic(hbox);  
              
            }
        }
    }
    
    public class SortingToolBar extends ToolBar{
        
        @Parameter
        PluginService pluginService;
        
        public SortingToolBar (Context context){
            context.inject(this);
            pluginService
                    .getPluginsOfType(Sorting.class)
                    .forEach(this::addPlugin);
        }
    
        public void addPlugin(PluginInfo<Sorting> pluginInfo) {

            try {
                Button sortButton = new Button(pluginInfo.getLabel());
                
                Sorting plugin = pluginInfo.createInstance();
                context.inject(plugin);
                
                sortButton.setOnAction(ActionEvent -> applyPlugin(plugin));
                toolBar.getItems().add(sortButton);
            } catch (InstantiableException ex) {
                Logger.getLogger(BrowserControler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void applyPlugin(Sorting plugin){
            plugin.sort();
        }
    }
    
    public class RightClickMenu extends ListView<ItemFile>{
        ContextMenu contextMenu = new ContextMenu();
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
