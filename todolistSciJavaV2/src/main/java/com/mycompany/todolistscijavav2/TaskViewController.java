/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolistscijavav2;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.scijava.SciJava;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 *
 * @author florian
 */



public class TaskViewController extends BorderPane{
    @FXML
    private Label taskTitle;
    @FXML 
    private TextArea taskDescription;
    @FXML
    private Button backMainPage;
    @FXML
    private RadioButton basicTask;
    @FXML
    private RadioButton importantTask;
    @FXML
    private RadioButton greenTask;
    @FXML
    final ToggleGroup taskGroup = new ToggleGroup();
    @FXML
    AnchorPane anchorPane;
    Task task;
    
     public TaskViewController(Task task) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/TaskView.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
        
        this.task = task;
        importantTask.setToggleGroup(taskGroup);
        greenTask.setToggleGroup(taskGroup);
        basicTask.setToggleGroup(taskGroup);
        initialization();
        
               
    }
     
    public void initialization(){
         String name = task.getName();
         taskTitle.setText(name);
         TaskType taskType = task.getTasktype();
         System.out.println(taskType);
         detectType();
         
         
     }
     
    public void back(){
        
        // get a handle to the stage
        Stage stage = (Stage) backMainPage.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    
    public void detectType(){
        TaskType taskType = task.getTasktype();
        if (taskType.equals(TaskType.BASIC)){
            taskGroup.selectToggle(basicTask);
        }
        if (taskType.equals(TaskType.IMPORTANT)){
            taskGroup.selectToggle(importantTask);
        }
        else if (taskType.equals(TaskType.GREEN)){
            taskGroup.selectToggle(greenTask);
        }
    }
     
    
}
