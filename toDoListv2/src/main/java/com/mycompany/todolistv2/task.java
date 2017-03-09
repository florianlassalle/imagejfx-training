/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolistv2;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author florian
 */
public class task {
    
    String name;
    BooleanProperty selectedProperty = new SimpleBooleanProperty();
    taskType tasktype;
    protected taskStyle style = new basicTask();

    public task(String name, boolean isSelected) {
        this.name = name;
        selectedProperty.setValue(isSelected);
        
        selectedProperty.addListener((obs,oldValue,newValue)->{
            System.out.println("propety changed");
        
        });
    }

    public task(String name,boolean isSelected, taskType tasktype) {
        this.name = name;
        selectedProperty.setValue(isSelected);
        selectedProperty.addListener((obs,oldValue,newValue)->{
            System.out.println("propety changed");
            });
        this.tasktype = tasktype;
        if (tasktype == taskType.important){
            this.style = new importantTask();
        }
        else if (tasktype == taskType.green){
            this.style = new greenTask();
        }
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setSelected(Boolean selected) {
        selectedProperty.setValue(selected);
    }

    public void setStyle(taskStyle style) {
        this.style = style;
    }
    
    public boolean isSelected (){
        return selectedProperty.getValue();
    }

    public String getName() {
        return name;
    }

    public BooleanProperty getSelectedProperty() {
        return selectedProperty;
    }
    public Color getColor() {
        return style.getColor();
    }
    public Font getFont() {
        return style.getFont();
    }

    
    
}
