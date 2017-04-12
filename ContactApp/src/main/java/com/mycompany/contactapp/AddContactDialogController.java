/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.contactapp;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author florian
 */
public class AddContactDialogController extends GridPane{
    @FXML
    TextField name;
    @FXML
    TextField email;
    @FXML
    TextField image;
    @FXML
    TextField description;
    
    private String stringName;
    private String stringEmail;
    private String stringDescription;
    private String stringImage;
    
    public AddContactDialogController() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/AddContact.fxml")); // c'est toujour galere de trouver le bon chemin, penser a mettre le fxml dans le bon dossier comme ici
        loader.setRoot(this);       //root est la base de la page, ici anchorPane
        loader.setController(this); //definition du controlleur
        loader.load(); // generation de la fenetre.
        
    }
    
    public String getName(){
        return this.stringName;
    }
    public String getEmail(){
        return this.stringEmail;
    }
    public String getDescription(){
        return this.stringDescription;
    }
    public String getImage(){
        return this.stringImage;
    }
    
    @FXML
    public void setName(){
        this.stringName = this.name.getText();
    }
    @FXML
    public void setEmail(){
        this.stringEmail = this.email.getText();
    }
    @FXML
    public void setDescription(){
        this.stringDescription = this.description.getText();
    }
    @FXML
    public void setImage(){
        this.stringImage = this.image.getText();
    }
}
