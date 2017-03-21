/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolistscijavav2;

import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import org.scijava.service.SciJavaService;
/**
 *
 * @author florian
 */
public interface TaskService extends SciJavaService {
    void addTask(TextField txttask, ToggleGroup taskGroup);
    List<Task> getTaskList();
    void removeTask(ListView<Task> listView);
    public void selectAll(Button btnSelectAll);
    public void setTaskList(List<Task> tasks);
}
