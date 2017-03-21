/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolistscijavav2;
/**
 *
 * @author florian
 */
public enum TaskType {
    BASIC(new BasicTask()),
    IMPORTANT(new ImportantTask()),
    GREEN(new GreenTask());
    
    private TaskStyler style = null;
    
    TaskType(TaskStyler style){
        this.style=style;
    }

    public TaskStyler getStyle() {
        return style;
    }
    
}

