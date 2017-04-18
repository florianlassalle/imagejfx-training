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
 * 
 * This is the main view controller, it control the views, the buttons etc ...
 * it call the two others controllers for the display of the contacts, the list view or the tilePane
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
    private Node viewList;
    private Node tilePane;
     
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
                 this.viewList = new ListViewController();
                context.inject(viewList);
                this.tilePane= new TileViewController();
                context.inject(this.tilePane);
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
        /*
        Dialog window creation, display it and get the result as an array list
        */
        Node addContactNode = null;
        // Create the custom dialog.
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("Add Contact");
        dialog.setHeaderText("Fill the informations for the new contact");
        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Save changes", ButtonData.OK_DONE);
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
    
    public void ContactViewMaker(Contact contact){
        Node addContactNode = null;
        // Create the custom dialog.
        Dialog dialog = new Dialog();
        dialog.setTitle(contact.getName());
        // Set the button types.
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        try {
            addContactNode = new ContactViewController(contact);
        } catch (IOException ex) {
            Logger.getLogger(ContactMainView.class.getName()).log(Level.SEVERE, null, ex);
        }
        dialog.getDialogPane().setContent(addContactNode);
        
        dialog.showAndWait();
    }

    @Override
    public Contact openContact() {
        Contact contact = this.currentNode.openContact();
        ContactViewMaker(contact);
        return contact;
    }
    
    public void openLittleWindow() throws IOException{
        
        grid.getChildren().remove(this.currentNode);
        
        grid.add(this.viewList,0,2);
        this.currentNode = (ViewControllerInterface) this.viewList;
        refresh();
    }
    public void openLargeWindow() throws IOException{
       
        grid.getChildren().remove(this.currentNode);        
        grid.add(this.tilePane,0,2);
        this.currentNode = (ViewControllerInterface) this.tilePane;
        refresh();
    }

    @Override
    public void newContactNotification(String name) {
        /*
        trying to display a notification when a contact is added
        don't work very well, the notification apear in the top right corner of the screen, not in the window
        */
        Label message = new Label("New contact added : " +name);
        Popup popup = new Popup();
        popup.getContent().add(message);
        popup.setAutoFix(true);
        popup.setHideOnEscape(true);
        
        popup.show(this.grid,this.grid.getLayoutX(),this.grid.getLayoutY());
    }
   
    
}
