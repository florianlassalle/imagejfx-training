/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.md5extractor;

import java.io.File;
import java.io.IOException;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.scijava.SciJava;
import org.scijava.event.EventHandler;
import org.scijava.plugin.Parameter;

/**
 *
 * @author florian
 */
public class MD5ExtractorController extends GridPane{
    @Parameter
    MD5ServiceInteface md5ServiceInteface;
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

    public MD5ExtractorController() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Scene.fxml")); // c'est toujour galere de trouver le bon chemin, penser a mettre le fxml dans le bon dossier comme ici
        loader.setRoot(this);       //root est la base de la page, ici anchorPane
        loader.setController(this); //definition du controlleur
        loader.load(); // generation de la fenetre.
        
        // Comme dit plus haut, on injecte scijava ici plutot que dans le main, ce qui permet qu'il soit injecté avant de lancer les fonctions d'initialisation
        
        SciJava scijava = new SciJava();
        scijava.context().inject(this);
        
         
    }
    
    @FXML
    public void selectFolder(){
        DirectoryChooser folderChooser = new DirectoryChooser();
        folderChooser.setTitle("Open Resource File");
        File file = folderChooser.showDialog(stage);
        if (file != null) {
            md5ServiceInteface.addFolder(file);
            select.setText(md5ServiceInteface.getDirectory());
        } else {
        }
        
        
    }
    @FXML
    public void start(){
        
        md5ServiceInteface.compileMD5();
        select.setDisable(true);
        start.setDisable(true);
        
        
    }
    
    @EventHandler
    public void onProgressEvent(Md5InProgressEvent event){
        /*
        When the list of items is updated, we refresh the view
        */
        progressBar.setVisible(true);
        System.out.println(event.getTask().getMessage());
        
       Platform.runLater( () ->
            progressBar.progressProperty().bind(event.getTask().progressProperty()        
            ));
       
    }
    @EventHandler
    public void onEnddingEvent(MD5EndingEvent event){
        /*
        When the list of items is updated, we refresh the view
        */
        Platform.runLater( () ->
        refresh()
        );
    }
    @EventHandler
    public void onRunningEvent(RunningEvent event){
        /*
        When the list of items is updated, we refresh the view
        */
        System.out.println("message recut");
        label.setVisible(true);
        Platform.runLater( () ->
        label.setText(event.getMessage())
        );
    }
    
    public void refresh(){
        label.setVisible(true);
        select.setDisable(false);
        start.setDisable(false);
        label.setText("Done");
    }
    
}
