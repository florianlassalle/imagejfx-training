/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foldercomparator;

/**
 *
 * @author florian
 */
public enum FieldType {
    SOURCE("Source"),
    TARGET("Target");
    
    private String name =  "";

    private FieldType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
