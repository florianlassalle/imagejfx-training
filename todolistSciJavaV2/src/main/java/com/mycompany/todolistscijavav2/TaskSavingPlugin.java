/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolistscijavav2;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 *
 * @author florian
 */
@Plugin(type = TaskSaving.class, label = "Saving tasks")
public class TaskSavingPlugin implements TaskSaving{
    
    @Parameter
    TaskService taskService;
    

    public void saveTasks() {
        List<Task> tasks = taskService.getTaskList();
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("/home/florian/Documents/taskSaving.json"), tasks);
        } catch (IOException ex) {
            Logger.getLogger(TaskSavingPlugin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/*
    public void loadTasks() {
        System.out.println("prout");
    }
    */
    public void saveCalling (){
        saveTasks();
        
    }

    
    
}
