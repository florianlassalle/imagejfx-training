/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.filebrowser;

import javafx.scene.image.Image;

/**
 *
 * @author florian
 */
public class ImageFile extends ItemFile{
    private Image icon =  new Image("file:image.png",32,32,false,false);
    
    public ImageFile(String name, String path,boolean isSelected) {
        super(name, path,isSelected);
    }

    public Image getIcon() {
        return icon;
    }
    
    
    
}
