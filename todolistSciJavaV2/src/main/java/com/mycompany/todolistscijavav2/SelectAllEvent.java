/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolistscijavav2;

import org.scijava.event.SciJavaEvent;

/**
 *
 * @author florian
 */
public class SelectAllEvent extends SciJavaEvent{
     public final String valueButton;
    
    public SelectAllEvent(String valueButton){
        this.valueButton = valueButton;
    }
    public String getValue(){
        return this.valueButton ;
    }
}
