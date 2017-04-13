/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.contactapp;

import java.util.List;
import org.scijava.plugin.SciJavaPlugin;

/**
 *
 * @author florian
 */
public interface ViewControllerInterface extends SciJavaPlugin {
    public void refresh();
    void init();

    void show();
    public void addContact();
    public Contact openContact();
    public void newContactNotification(String name);
    
}
