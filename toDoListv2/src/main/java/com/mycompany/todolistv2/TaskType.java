/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolistv2;

/**
 *
 * @author florian
 */
public enum TaskType {
    basic(new BasicTask()),
    important(new ImportantTask()),
    green(new GreenTask());
    
    private TaskStyle style = null;
    
    TaskType(TaskStyle style){
        this.style=style;
    }

    public TaskStyle getStyle() {
        return style;
    }
    
}

