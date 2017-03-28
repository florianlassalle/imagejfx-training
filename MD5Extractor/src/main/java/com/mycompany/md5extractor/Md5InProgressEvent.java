/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.md5extractor;

import javafx.concurrent.Task;
import org.scijava.event.SciJavaEvent;

/**
 *
 * @author florian
 */
public class Md5InProgressEvent extends SciJavaEvent{
    private Task<Void> md5;
    
     
    public Md5InProgressEvent(Task<Void> md5){
        this.md5 = md5;
    }
    public Task<Void>  getTask(){
        return this.md5;
    }
}
