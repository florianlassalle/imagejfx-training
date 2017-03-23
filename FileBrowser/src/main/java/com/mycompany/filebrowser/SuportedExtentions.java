/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.filebrowser;

/**
 *
 * @author florian
 */
public enum SuportedExtentions {
    PNG(".png"),
    JPEG (".jpg"),
    TIFF (".tiff"),
    ;
    
    String extention;
    
    private SuportedExtentions(String extention) {
        this.extention = extention;
    }

    public String getExtention() {
        return extention;
    }
    
    
    
}
