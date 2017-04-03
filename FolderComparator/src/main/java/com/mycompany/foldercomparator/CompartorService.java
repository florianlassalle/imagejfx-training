/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foldercomparator;

import java.io.File;
import org.scijava.Prioritized;
import org.scijava.event.EventService;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.service.AbstractService;
import org.scijava.service.SciJavaService;

/**
 *
 * @author florian
 */
@Plugin(type = SciJavaService.class,priority = 10)
public class CompartorService extends AbstractService implements ComparatorServiceInterface{
    @Parameter
    EventService eventService;
    
    private File sourceDirectory;
    private File targetDirectory;

    @Override
    public void setFolder(File directory, FieldType type) {
        if (type.equals(FieldType.SOURCE)) {
            this.sourceDirectory = directory;
            
        }
        else if (type.equals(FieldType.TARGET)){
            this.targetDirectory = directory;
        }
        
        eventService.publish(new FolderChangesEvent(directory,type));
    }
        
    
}
