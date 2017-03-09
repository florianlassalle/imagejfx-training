/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolistv2;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author florian
 */
public class FXMLController extends AnchorPane {
    /*
    classe controleur, elles est lancée par le main.
    elle doit extends l'elément root de la fenètre, ici AnchorPane.
    
    On doit aussi declarer avec un @FXML les variables correspondants aux boutons, 
    on doit ici leur atribuer le meme nom que leur id dans le fichier FXML.
    */
    
    @FXML
    private TextField txttask;
    
    @FXML
    private ListView<Task> listView;
    @FXML
    private RadioButton importantTask;
    @FXML
    private RadioButton greenTask;
    @FXML
    private RadioButton basicTask;
    @FXML
    final ToggleGroup taskGroup = new ToggleGroup();
    
    
    public FXMLController() throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
        
        listView.getItems().add(new Task("Finir le cour javaFX", true));
        
        listView.setCellFactory(this::createCell);
        importantTask.setToggleGroup(taskGroup);
        greenTask.setToggleGroup(taskGroup);
        basicTask.setToggleGroup(taskGroup);
        taskGroup.selectToggle(basicTask);

        
        
    }
    
    private ListCell<Task> createCell (ListView<Task> task){
        return new TaskListCell();
    }
    
    @FXML
    private void selectAll(){
        listView.getItems().forEach(task ->task.setSelected(true));
    }
    
    @FXML
    private void addTask(){
        String newTask = txttask.getText();
        
        if (importantTask.isSelected()){
            listView.getItems().add(new Task(newTask, true, TaskType.important));
        }
        else if (greenTask.isSelected()){
            listView.getItems().add(new Task(newTask, true, TaskType.green));
        }
        else{
            listView.getItems().add(new Task(newTask, true));
        }
        //listView.getItems().add(new Task(newTask, true));
        txttask.clear();
    }
    
    @FXML
    private void deleteTask(){
        List<Task> boxes = listView
                    .getItems()
                    .stream()
                    .filter(ch-> ch.isSelected())
                    .collect(Collectors.toList());
            
            listView.getItems().removeAll(boxes);
    }

    
    
    private class TaskListCell extends ListCell<Task> {
        
        CheckBox checkbox = new CheckBox();

        public TaskListCell() {
            super();
            itemProperty().addListener(this::onItemChanged);
            
        }

        private void onItemChanged(ObservableValue obs, Task oldValue, Task newValue) {
            
            if(oldValue != null){
                oldValue.getSelectedProperty().unbindBidirectional(checkbox.selectedProperty());
            }
            
            if(newValue == null){
                setGraphic(null);
            }
            
            else {
                setGraphic(checkbox);
                
                newValue.getSelectedProperty().bindBidirectional(checkbox.selectedProperty());
                checkbox.setText(newValue.getName());
                checkbox.setTextFill(newValue.getColor());
                checkbox.setFont(newValue.getFont());
            }
            
            
        }
        
        
    }
    
}
