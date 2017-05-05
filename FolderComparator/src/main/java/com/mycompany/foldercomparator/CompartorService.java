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
    
    private File  sourceDirectory;
    private File targetDirectory;
    private Task<Void> synchroniser;

    @Override
    public void setFolder(File directory, FieldType type) {
        if (type.equals(FieldType.SOURCE)) {
            this.sourceDirectory = directory;
            
        }
        else if (type.equals(FieldType.TARGET)){
            this.targetDirectory = directory;
        }
        
        eventService.publish(new FieldEnabling(true));
        eventService.publish(new FolderChangesEvent(sourceDirectory,type));
    }
    
    @Override
    public void synchroniseFolders() {
        
        this.synchroniser = new FolderSynchroniser(this.sourceDirectory,this.targetDirectory);
        
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
    
    @Override
    public void switchFolders(){
        File  sourceDirectoryTmp = this.sourceDirectory;
        this.sourceDirectory = this.targetDirectory;
        this.targetDirectory = sourceDirectoryTmp;
        
        eventService.publish(new FolderChangesEvent(sourceDirectory,FieldType.SOURCE));
        eventService.publish(new FolderChangesEvent(targetDirectory,FieldType.TARGET));
        
        
    }
}
