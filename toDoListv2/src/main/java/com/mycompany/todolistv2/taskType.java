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
public enum taskType {
    basic(new basicTask()),
    important(new importantTask()),
    green(new greenTask());
    
    private taskStyle style = null;
    
    taskType(taskStyle style){
        this.style=style;
    }

    public taskStyle getStyle() {
        return style;
    }
    
}

