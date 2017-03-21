/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolistscijavav2;

import java.util.List;
import org.scijava.event.SciJavaEvent;

/**
 *
 * @author florian
 */
public class TaskDeletedEvent extends SciJavaEvent{
    public final List<Task> tasks;
    
    public TaskDeletedEvent(List<Task> tasks){
        this.tasks = tasks;
    }
    public List<Task> getTask(){
        return this.tasks;
    }
}
