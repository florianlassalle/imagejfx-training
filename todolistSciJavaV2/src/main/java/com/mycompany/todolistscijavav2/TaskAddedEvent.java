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
public class TaskAddedEvent extends SciJavaEvent{
    public final Task task;
    
    public TaskAddedEvent(Task task){
        this.task = task;
    }
    public Task getTask(){
        return this.task;
    }
}
