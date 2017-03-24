/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.filebrowser;

import java.nio.file.attribute.FileTime;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;

/**
 *
 * @author florian
 */
public abstract class ItemFile implements Comparable<ItemFile>{
    /**
     * Super class of the elements found in the folder
     * 
     */
    
    private String name;
    private String path;
    private BooleanProperty selectedProperty = new SimpleBooleanProperty();
    private long size;

    public ItemFile(String name, String path,boolean isSelected) {
        this.name = name;
        this.path = path;
        
        selectedProperty.setValue(isSelected);
        selectedProperty.addListener((obs,oldValue,newValue)->{
           
        
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
        /**
         * Implementation of the interface Comparable
         * it permite to compare Items by thier names
         */
        return this.getName().compareTo(o.getName());
    }
    abstract Image getIcon();
    abstract long getSize();
    abstract public FileTime getDateModification();
    
        
    
    
    
}
