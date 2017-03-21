/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolistscijavav2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.scijava.event.EventService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 *
 * @author florian
 */
@Plugin(type = TaskSaving.class, label = "loading tasks")

public class LoadingPlugin implements TaskSaving{
    @Parameter
    TaskService taskService;
    @Parameter
    EventService eventService;
    
    public void loadTasks() {
        ObjectMapper mapper = new ObjectMapper();
        List<Task> tasks = new ArrayList<>();
        try {
            tasks = mapper.readValue(new File("/home/florian/Documents/taskSaving.json"),  new TypeReference<List<Task>>(){});
            //tasks = Arrays.asList(mapper.readValue(new File("/home/florian/Documents/taskSaving.json"), Task[].class));
            //tasks = Arrays.asList(mapper.readValue(new File("/home/florian/Documents/taskSaving.json"), Task[].class));
        } catch (IOException ex) {
            Logger.getLogger(TaskSavingPlugin.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(tasks);
        taskService.setTaskList(tasks);
        eventService.publish(new LoadedEvent(tasks));
    }
/*
    @Override
    public void saveTasks() {
        System.out.println("prout");
    }
*/
    public void saveCalling (){
        loadTasks();
        
    }
}
