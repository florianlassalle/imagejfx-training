/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.md5extractor;

import org.scijava.event.SciJavaEvent;

/**
 *
 * @author florian
 */
public class RunningEvent extends SciJavaEvent{
    private String message;
    public RunningEvent(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    
}
