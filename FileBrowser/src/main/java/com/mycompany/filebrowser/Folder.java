/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.filebrowser;

import java.nio.file.attribute.FileTime;
import javafx.scene.image.Image;

/**
 *
 * @author florian
 */
public class Folder extends ItemFile{
    /**
     * Object for the folders
     */
    private static Image icon =  new Image("file:folder.png",32,0,false,false);
    
    public Folder(String name, String path,boolean isSelected) {
        super(name, path,isSelected);
    }

    public Image getIcon() {
        return icon;
    }

    @Override
    long getSize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FileTime getDateModification() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
   
    
}
