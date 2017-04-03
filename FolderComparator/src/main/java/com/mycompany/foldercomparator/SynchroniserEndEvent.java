/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foldercomparator;

import javafx.concurrent.Task;
import org.scijava.event.SciJavaEvent;

/**
 *
 * @author florian
 */
public class SynchroniserEndEvent extends SciJavaEvent{
    private Task<Void> synchroniser;

    public SynchroniserEndEvent(Task<Void> synchroniser) {
        this.synchroniser = synchroniser;
    }

    public Task<Void> getSynchroniser() {
        return synchroniser;
    }

    public void setSynchroniser(Task<Void> synchroniser) {
        this.synchroniser = synchroniser;
    }
}
