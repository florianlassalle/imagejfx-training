/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolistscijavav2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.scijava.SciJava;

/**
 *
 * @author florian
 */
public class MainToDoList extends Application{
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = new ToDoListController();
        /*
        SciJava scijava = new SciJava();

        // when injecting the context,
        // all the attributes annoted with
        // @Parameter will be pointed
        // to the corresponding service
        scijava.context().inject(root);
*/
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
