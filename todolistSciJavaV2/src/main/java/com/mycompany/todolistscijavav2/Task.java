/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolistscijavav2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import jdk.nashorn.internal.objects.Global;

/**
 *
 * @author florian
 */
public class Task {
    
    private String name;
    private BooleanProperty selectedProperty = new SimpleBooleanProperty();
    private TaskType tasktype;
    protected TaskStyler style = new BasicTask();

    public Task() {
        this.name = null;
        selectedProperty.setValue(false);
    }
    

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

    public BooleanProperty selectedProperty() {
        return selectedProperty;
    }
    @JsonIgnore
    public Color getColor() {
        return style.getColor();
    }
    @JsonIgnore
    public Font getFont() {
        return style.getFont();
    }

    public TaskType getTasktype() {
        return tasktype;
    }


 
    
    
    
}

