/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.filebrowser;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;

/**
 *
 * @author florian
 */
public abstract class ItemFile implements Comparable<ItemFile>{
    
    private String name;
    private String path;
    private BooleanProperty selectedProperty = new SimpleBooleanProperty();

    public ItemFile(String name, String path,boolean isSelected) {
        this.name = name;
        this.path = path;
        
        selectedProperty.setValue(isSelected);
        selectedProperty.addListener((obs,oldValue,newValue)->{
            System.out.println("propety changed ");
            System.out.println(obs.getValue());
        
        });
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
    
    public BooleanProperty selectedProperty() {
        return selectedProperty;
    }
    public void setSelected(Boolean selected) {
        selectedProperty.setValue(selected);
    }

    public BooleanProperty getSelectedProperty() {
        return selectedProperty;
    }
    public Boolean isSelected() {
        return selectedProperty.getValue();
    }

    @Override
    public int compareTo(ItemFile o) {
        return this.getName().compareTo(o.getName());
    }
    abstract Image getIcon();
        
    
    
    
}
