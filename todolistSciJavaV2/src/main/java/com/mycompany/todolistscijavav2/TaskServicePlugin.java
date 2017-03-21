/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolistscijavav2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import org.scijava.event.EventService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.service.AbstractService;
import org.scijava.service.SciJavaService;
/**
 *
 * @author florian
 */
@Plugin(type = SciJavaService.class,priority = 10)
public class TaskServicePlugin extends AbstractService implements TaskService{
    
     
    @Parameter
    EventService eventService;
 
    List<Task> taskList = new ArrayList<>();

    public TaskServicePlugin() {
        super();
    }
    
    
    
    public void addTask(TextField txttask, ToggleGroup taskGroup){
        String taskName = txttask.getText();
        RadioButton rb = (RadioButton) taskGroup.getSelectedToggle();
        String type = rb.getId().toString();
        
        Task newTask = makingTask(taskName, type);
        
        taskList.add(newTask);
        txttask.clear();
        System.out.println(this.taskList);
        
        eventService.publish(new TaskAddedEvent(newTask));
    }
    
    private Task makingTask (String taskName, String type){
        TaskType taskType = TaskType.BASIC;
        if (type.equals("importantTask")){
            taskType = TaskType.IMPORTANT;
        }
        else if (type.equals("greenTask")){
            taskType = TaskType.GREEN;
        }
        
        Task newTask = new Task(taskName, true, taskType);
        return newTask;

    }
    
    @Override
    public List<Task> getTaskList(){
        return this.taskList;
    }
    
    public void setTaskList(List<Task> tasks){
        this.taskList = tasks;
    }
    
    @Override
    public void removeTask(ListView<Task> listView){
        List<Task> tasks = listView
                .getItems()
                .stream()
                .filter(ch-> ch.isSelected())
                .collect(Collectors.toList());
            
        taskList.removeAll(tasks);
        eventService.publish(new TaskDeletedEvent(tasks));

    }
    
    public void selectAll(Button btnSelectAll){
        String valueDefault = "All task done";
        String valueSelected = "Unselect all";
        
        if (btnSelectAll.getText().equals(valueDefault)){
            taskList.forEach(task ->task.setSelected(true));
            eventService.publish(new SelectAllEvent(valueSelected));
            
        }
        else if (btnSelectAll.getText().equals(valueSelected)){
            taskList.forEach(task ->task.setSelected(false));
            eventService.publish(new SelectAllEvent(valueDefault));
        }
    }
}
