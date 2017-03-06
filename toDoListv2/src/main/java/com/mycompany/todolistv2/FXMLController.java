/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolistv2;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import static javafx.scene.text.Font.font;

/**
 *
 * @author florian
 */
public class FXMLController implements Initializable {
    
     @FXML
    private Button btnadd;
     @FXML
    private Button btndelete;
     @FXML
    private Button btnsetdone;
    @FXML
    private TextField txttask;
    @FXML
    private GridPane grid;
    @FXML
    private ListView<CheckBox> list_view;
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        String newTask;
        
        if (event.getSource()==btnadd || event.getSource()==txttask)
        {
            newTask = txttask.getText();
            addTask(newTask);
        }
        
        else if (event.getSource()== btndelete){
            List<CheckBox> boxes = list_view
                    .getItems()
                    .stream()
                    .filter(ch-> ch.isSelected())
                    .collect(Collectors.toList());
            
            list_view.getItems().removeAll(boxes);
        }
        else if(event.getSource()== btnsetdone){
            setDone();
        }
        
        
    }
    
    private void addTask(String newTask)
    {
        final CheckBox newBox = new CheckBox(newTask);
        Font police = font(20);
        newBox.setFont(police);
        list_view.getItems().add(newBox);
        txttask.clear();
    }
    
    private void setDone()
    {
        List<CheckBox> boxes = list_view
            .getItems()
            .stream()
            .filter(ch -> !ch.isSelected())
            .collect(Collectors.toList());
        boxes.forEach(ch -> ch.setSelected(true));
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
