/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.contactapp;

import javafx.application.Application;
import javafx.stage.Stage;
import org.scijava.SciJava;

/**
 *
 * @author florian
 */
public class ContactAppMain extends Application{

    /**
     * @param args the command line arguments
     */
    
    public ContactAppMain() throws Exception {
        SciJava scijava = new SciJava();
        scijava.getContext().inject(this);
        scijava.get(ContactViewServiceInterface.class).showView();
        
    }
    
    public static void main(String[] args) throws Exception {
        new ContactAppMain();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     
}
