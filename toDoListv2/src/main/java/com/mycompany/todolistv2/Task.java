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
public class Task {
    
    String name;
    BooleanProperty selectedProperty = new SimpleBooleanProperty();
    TaskType tasktype;
    protected TaskStyle style = new BasicTask();

    public Task(String name, boolean isSelected) {
        this.name = name;
        selectedProperty.setValue(isSelected);
        
        selectedProperty.addListener((obs,oldValue,newValue)->{
            System.out.println("propety changed");
        
        });
    }

    public Task(String name,boolean isSelected, TaskType tasktype) {
        this.name = name;
        selectedProperty.setValue(isSelected);
        selectedProperty.addListener((obs,oldValue,newValue)->{
            System.out.println("propety changed");
            });
        this.tasktype = tasktype;
        this.style = tasktype.getStyle();
        
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setSelected(Boolean selected) {
        selectedProperty.setValue(selected);
    }

    public void setType(TaskType type) {
        this.tasktype = type;
        this.style = type.getStyle();
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
