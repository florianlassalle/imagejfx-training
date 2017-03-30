/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foldercomparator;

import java.io.File;
import java.io.IOException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.scijava.SciJava;
import org.scijava.plugin.Parameter;

/**
 *
 * @author florian
 */
public class ComparatorController extends GridPane{
    
    
    @Parameter
    ComparatorServiceInterface comparatorServiceInterface;
    
    @Parameter
    Stage stage;
    
    @FXML
    ProgressBar progressBar;
    @FXML
    Button select;
    @FXML
    Button start;
    @FXML
    Label label;
    
    private final ObjectProperty<File> sourceFolder = new SimpleObjectProperty<>();
    private final ObjectProperty<File> targetFolder = new SimpleObjectProperty<>();

    public ComparatorController() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Scene.fxml")); // c'est toujour galere de trouver le bon chemin, penser a mettre le fxml dans le bon dossier comme ici
        loader.setRoot(this);       //root est la base de la page, ici anchorPane
        loader.setController(this); //definition du controlleur
        loader.load(); // generation de la fenetre.
        
        // Comme dit plus haut, on injecte scijava ici plutot que dans le main, ce qui permet qu'il soit injecté avant de lancer les fonctions d'initialisation
        
        SciJava scijava = new SciJava();
        scijava.context().inject(this);
        
         
    }
    
    
}
