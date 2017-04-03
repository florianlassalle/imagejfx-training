/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foldercomparator;

import java.io.File;
import org.scijava.event.SciJavaEvent;

/**
 *
 * @author florian
 */
public class FolderChangesEvent extends SciJavaEvent{
    private File folder;
    private FieldType type;

    public FolderChangesEvent(File folder, FieldType type) {
        this.folder = folder;
        this.type = type;
    }

    public File getFolder() {
        return folder;
    }

    public FieldType getType() {
        return type;
    }
    
    
}
