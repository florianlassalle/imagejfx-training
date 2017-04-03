/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foldercomparator;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
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
    
    private ObservableValue<File>  sourceDirectory;
    private ObservableValue<File> targetDirectory;
    private Task<Void> synchroniser;

    @Override
    public void setFolder(ObservableValue<File> directory, FieldType type) {
        if (type.equals(FieldType.SOURCE)) {
            this.sourceDirectory = directory;
            
        }
        else if (type.equals(FieldType.TARGET)){
            this.targetDirectory = directory;
        }
        
        eventService.publish(new FieldEnabling(true));
    }
    
    @Override
    public void synchroniseFolders() {
        
        this.synchroniser = new FolderSynchroniser(this.sourceDirectory.getValue(),this.targetDirectory.getValue());
        
        eventService.publish(new SynchroniserProgressEvent(synchroniser));
        
        new Thread(synchroniser).start();
       
        synchroniser.setOnSucceeded((WorkerStateEvent event) -> {
            eventService.publish(new SynchroniserEndEvent(synchroniser));
            
          });
    }
    @Override
    public void cancelTask(){
        this.synchroniser.cancel();
    }
    
}
