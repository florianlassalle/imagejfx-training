/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.contactapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.scijava.Context;
import org.scijava.plugin.Parameter;

/**
 *
 * @author florian
 */

public class ContactMainView extends AnchorPane implements ViewControllerInterface{

    @FXML
    GridPane grid;
    @Parameter
    ContactViewServiceInterface contactViewService;
    @Parameter
    Context context;
    @Parameter
    Stage stage;
    
    private ViewControllerInterface currentNode ;
     
    public ContactMainView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Scene.fxml")); // c'est toujour galere de trouver le bon chemin, penser a mettre le fxml dans le bon dossier comme ici
        loader.setRoot(this);       //root est la base de la page, ici anchorPane
        loader.setController(this); //definition du controlleur
        loader.load(); // generation de la fenetre.
        
    }
    

    @Override
    public void init() {
        if (this.currentNode == null){
            try {
                openLittleWindow();
            } catch (IOException ex) {
                Logger.getLogger(ContactMainView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        refresh();
    }

    @Override
    public void show() {
    }

    @Override
    public void refresh() {
        
        this.currentNode.refresh();
    }

    @Override
    public void addContact() {
        List<String> newContact = createDialog();
        contactViewService.addContact(newContact, this);
    }
    
    public List<String> createDialog(){
        Node addContactNode = null;
        // Create the custom dialog.
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("Add Contact");
        dialog.setHeaderText("Fill the informations for the new contact");
        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        try {
            addContactNode = new AddContactDialogController();
        } catch (IOException ex) {
            Logger.getLogger(ContactMainView.class.getName()).log(Level.SEVERE, null, ex);
        }
        dialog.getDialogPane().setContent(addContactNode);
        
        // Convert the result to a username-password-pair when the login button is clicked.
        AddContactDialogController controller = (AddContactDialogController) addContactNode;
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                List<String> list = new ArrayList<String>();
                list.add(controller.getName());
                list.add(controller.getEmail());
                list.add( controller.getDescription());
                list.add(controller.getImage());
                return list;
            }
            return null;
        });
        Optional<List<String>> result = dialog.showAndWait();
        return result.get();
    }

    @Override
    public void openContact() {
        this.currentNode.openContact();
    }
    
    public void openLittleWindow() throws IOException{
        Node contactListView = new ListViewController();
        context.inject(contactListView);
        grid.getChildren().remove(this.currentNode);
        
        grid.add(contactListView,0,2);
        this.currentNode = (ViewControllerInterface) contactListView;
        refresh();
    }
    public void openLargeWindow() throws IOException{
        Node contactTileView = new TileViewController();
        context.inject(contactTileView);
        grid.getChildren().remove(this.currentNode);        
        grid.add(contactTileView,0,2);
        this.currentNode = (ViewControllerInterface) contactTileView;
        refresh();
    }

    @Override
    public void newContactNotification(String name) {
        Label message = new Label("New contact added : " +name);
        Popup popup = new Popup();
        popup.getContent().add(message);
        popup.setAutoFix(true);
        popup.setHideOnEscape(true);
        
        popup.show(this.grid,this.grid.getLayoutX(),this.grid.getLayoutY());
    }
   
    
}
