/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.contactapp;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.scijava.Context;
import org.scijava.plugin.Parameter;

/**
 *
 * @author florian
 */
public class ListViewController extends ListView<Contact> implements ViewControllerInterface{
    @Parameter
    ContactViewServiceInterface contactViewService;
    @Parameter
    Context context;
    
    public ListViewController() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/ListView.fxml")); // c'est toujour galere de trouver le bon chemin, penser a mettre le fxml dans le bon dossier comme ici
        loader.setRoot(this);       //root est la base de la page, ici anchorPane
        loader.setController(this); //definition du controlleur
        loader.load(); // generation de la fenetre.
        // setting the cell factory
        this.setCellFactory(this::createCell);
        
    }
    private ListCell<Contact> createCell(ListView<Contact> listView) {
        ContactListCell todoTaskCell = new ContactListCell();
        ContactListCell contactListCell = new ContactListCell();
        //cellList.add(todoTaskCell);
        return todoTaskCell;
    }
    
    @Override
    public void refresh(){
        List<Contact> contactList = contactViewService.getContactList();
        this.getItems().clear();
        this.getItems().addAll(contactList);
    }
    @Override
    public void init() {
        
    }

    @Override
    public void show() {
    }

    @Override
    public void addContact() {
    }

    @Override
    public Contact openContact() {
        return this.getSelectionModel().getSelectedItem();
    }

    @Override
    public void newContactNotification(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    private class ContactListCell extends ListCell<Contact> {
        /*
        Ici on definis comment la vue doit afficher les tache, 
        */
        HBox hbox; // each cell is a box contaning a checbox and an image
        //CheckBox checkbox = new CheckBox();
        VBox vbox;

        public ContactListCell() {
            super();
            itemProperty().addListener(this::onItemChanged);
            
        }

        private void onItemChanged(ObservableValue obs, Contact oldValue, Contact newValue) {
            
            if(oldValue != null){
                //oldValue.selectedProperty().unbindBidirectional(checkbox.selectedProperty());
            }
            
            if(newValue == null){
                setGraphic(null);
            }
            else {
                
                Label nameLabel = new Label();
                Label emailLabel = new Label();
                
                hbox = new HBox();  
  
                hbox.setSpacing(20);
                
                vbox = new VBox();
                vbox.getChildren().addAll(nameLabel, emailLabel);
                
                hbox.getChildren().addAll(new ImageView(newValue.getIcon()),vbox); 
                nameLabel.setText(newValue.getName());
                emailLabel.setText(newValue.getEmail());
    
                setGraphic(hbox);  
              
            }
        }
    }
}
