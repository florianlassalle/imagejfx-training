/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.contactapp;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.scijava.Context;
import org.scijava.plugin.Parameter;

/**
 *
 * @author florian
 */
public class TileViewController extends TilePane implements ViewControllerInterface{
    
    
    @Parameter
    ContactViewServiceInterface contactViewService;
    @Parameter
    Context context;
    
    Contact currentContact;
    
    public TileViewController() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/TilePane.fxml")); // c'est toujour galere de trouver le bon chemin, penser a mettre le fxml dans le bon dossier comme ici
        loader.setRoot(this);       //root est la base de la page, ici anchorPane
        loader.setController(this); //definition du controlleur
        loader.load(); // generation de la fenetre.
    }
    
    
    

    @Override
    public void refresh() {
        contactViewService.getContactList()
                .stream()
                .forEach((contact) -> {
            this.getChildren().add(createItem(contact));
        });
    }


    @Override
    public void init() {
    }

    @Override
    public void show() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public VBox createItem (Contact contact){
        VBox vBox = new VBox();
        ImageView icon = new ImageView(contact.getIcon());
        icon.setFitWidth(64);
        icon.setPreserveRatio(true);
        icon.setSmooth(true);
        icon.setCache(true);
        vBox.getChildren().addAll(new ImageView(contact.getIcon()),new Label(contact.getName()), new Label(contact.getEmail())); 
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
        vBox.setOnMouseClicked(ActionEvent -> openThisContact(contact));
        return vBox;
    }
    public void openThisContact (Contact contact){
        this.currentContact = contact;
        
    }
    @Override
    public void addContact() {
        
    }

    @Override
    public Contact openContact() {
        return this.currentContact;

    }

    @Override
    public void newContactNotification(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
