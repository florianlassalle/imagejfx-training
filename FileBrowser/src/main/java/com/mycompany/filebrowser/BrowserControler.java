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
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
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
    /*
    @Parameter
    Sorting sorting;
    */
     @Parameter
        Context context;
    
    @FXML
    private ListView<ItemFile> listView;
    @FXML
    private MenuButton sortMenu;
    @FXML
    private TextField textField;
    @FXML
    private ContextMenu contextMenu;
    

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
        RightClickMenu rightClick = new RightClickMenu(context);
        fileService.openFolder(); // at the initialization we display the content of home directory of the user
    }

     private ListCell<ItemFile> createCell (ListView<ItemFile> item){
        return new FileListCell();
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
    
    @FXML
    public void click(MouseEvent mouseEvent){
        
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2){
                
                fileService.open();
            }
            else  {
                fileService.selection(listView.getSelectionModel().getSelectedItems());
            }
        }
           

            
    }
    
    @FXML
    public void open () throws IOException{
        fileService.open();
    }
    @FXML
    public void properties () throws IOException{
        Alert alert = new Alert(Alert.AlertType.INFORMATION, fileService.informations());
        alert.setResizable(true);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
         alert.showAndWait();
    }
    
    @EventHandler
    public void onProgressEvent(ListUpdateEvent event){
        /*
        When the list of items is updated, we refresh the view
        */
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
        HBox hbox; // each cell is a box contaning a checbox and an image
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
                
                CheckBox checkbox = new CheckBox();
  
                hbox = new HBox();  
  
                Pane pane = new Pane();  
                pane.setMinWidth(5);  

                hbox.getChildren().addAll(new ImageView(newValue.getIcon()), pane, checkbox);  // we add to the box the elements that we want to display
                newValue.selectedProperty().bindBidirectional(checkbox.selectedProperty());
                checkbox.setText(newValue.getName());
    
                setGraphic(hbox);  
              
            }
        }
    }
    
    public class SortingToolBar extends MenuButton{
        /*
        This class define the contenu of the menuButton, here we fill it with the plugins of type sorting
        */
        
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
                MenuItem sortButton = new MenuItem(pluginInfo.getLabel());
                
                Sorting plugin = pluginInfo.createInstance();
                context.inject(plugin); // we need the service in the plugin, so we inject the context
                
                sortButton.setOnAction(ActionEvent -> applyPlugin(plugin));
                sortMenu.getItems().add(sortButton);
            } catch (InstantiableException ex) {
                Logger.getLogger(BrowserControler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void applyPlugin(Sorting plugin){
            plugin.sort();
        }
    }
    
    public class RightClickMenu extends ContextMenu{
        /*
        Same as SortingToolbar, but for the contextual menu ... yes it is useless ...
        */
        @Parameter
        PluginService pluginService;
        
        public RightClickMenu (Context context){
            context.inject(this);
            pluginService
                    .getPluginsOfType(Sorting.class)
                    .forEach(this::addPlugin);
        }
        public void addPlugin(PluginInfo<Sorting> pluginInfo) {

            try {
                MenuItem sortButton = new MenuItem(pluginInfo.getLabel());
                
                Sorting plugin = pluginInfo.createInstance();
                context.inject(plugin);
                
                sortButton.setOnAction(ActionEvent -> applyPlugin(plugin));
                contextMenu.getItems().add(sortButton);
            } catch (InstantiableException ex) {
                Logger.getLogger(BrowserControler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void applyPlugin(Sorting plugin){
            plugin.sort();
        }
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
