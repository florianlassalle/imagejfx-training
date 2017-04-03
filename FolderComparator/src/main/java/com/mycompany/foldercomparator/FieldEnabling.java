/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foldercomparator;

import org.scijava.event.SciJavaEvent;

/**
 *
 * @author florian
 */
public class FieldEnabling extends SciJavaEvent{
    private boolean enabling;

    public FieldEnabling(boolean enabling) {
        this.enabling = enabling;
    }
    
    public boolean getEnabled (){
        return enabling;
    }
    
    
}
