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
public class ImageFile extends ItemFile{
    /**
     * Images objects
     * the atributes are the size (long) and the last modification date (FileTime)
     */
    private static Image icon =  new Image("image.png");
    private long size;
    private FileTime dateModification;

    public long getSize() {
        return size;
    }

    public FileTime getDateModification() {
        return dateModification;
    }

    public String getPropeties() {
        return propeties;
    }
    private String propeties;
    
    
    public ImageFile(String name, String path,boolean isSelected) {
        super(name, path,isSelected);
    }

    public ImageFile( String name, String path, boolean isSelected,long size, FileTime dateModification) {
        super(name, path, isSelected);
        this.size = size;
        this.dateModification = dateModification;
    }
    

    public Image getIcon() {
        return icon;
    }
    
    
    
}
