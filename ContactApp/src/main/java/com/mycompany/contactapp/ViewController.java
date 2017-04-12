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
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.scijava.Context;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;


/**
 *
 * @author florian
**/
@Plugin(type = ViewControllerInterface.class)
public class ViewController extends Application implements ViewControllerInterface{
    @Parameter
    static private Context context;
    static private ContactMainView listView;
    
    
    @Parameter
    ContactViewServiceInterface contactViewService;

    public ViewController() {
    }
    
    @Override
    public void init() {
        
        // method left blank, not really useful
       
    }
    @Override
    public void refresh() {
        listView.refresh();
    }
    

    @Override
    public void start(Stage primaryStage) throws Exception {
        // creating our pane and injecting things
        listView = new ContactMainView();
        
        context.inject(listView);
        
        listView.init();
        
        // creating the scene
        Scene scene = new Scene(listView);

        // setting the scene to the stage
        primaryStage.setScene(scene);
        
        final ChangeListener<Number> windowSizeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
               
                if (newValue.intValue() > 900){
                    try {
                        listView.openLargeWindow();
                    } catch (IOException ex) {
                        Logger.getLogger(ViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if (newValue.intValue() < 850){
                    try {
                        listView.openLittleWindow();
                    } catch (IOException ex) {
                        Logger.getLogger(ViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        primaryStage.widthProperty().addListener(windowSizeListener);
        // showing the stage
        primaryStage.show();
       
    }
    
    @Override
    public void show() {
        launch();
    }

    @Override
    public void addContact() {
        
    }

    @Override
    public void openContact() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void newContactNotification(String name) {
        listView.newContactNotification(name);
    }
    
    
   
}
