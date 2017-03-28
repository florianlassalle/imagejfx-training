/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.md5extractor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import org.scijava.event.EventService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.service.AbstractService;
import org.scijava.service.SciJavaService;

/**
 *
 * @author florian
 */
@Plugin(type = SciJavaService.class,priority = 10)

public class MD5Service extends AbstractService implements MD5ServiceInteface{
    @Parameter
    EventService eventService;
   
    private File directory;
        
    @Override
    public void addFolder(File file) {
        if (file.isDirectory()) this.directory = file;
        System.out.println("adding folder");
    }

    @Override
    public void compileMD5() {
        List<File> files = getFilesFromFolder();
        Task<Void> md5 = new MD5Calculator(files);
        
        eventService.publish(new Md5InProgressEvent(md5));
        
        
        new Thread(md5).start();
        md5.setOnRunning((WorkerStateEvent event) -> {
            eventService.publish(new RunningEvent(md5.getMessage()));
        });
        //md5.messageProperty().addListener(listener);
        md5.setOnSucceeded((WorkerStateEvent event) -> {
            eventService.publish(new MD5EndingEvent());
            
          });
    }
    
    
    public List<File> getFilesFromFolder (){
        File[] list = this.directory.listFiles();
        List<File> folderList = new ArrayList<>();
        for (File file : list){
            if (file.isFile()){
                if (isImage(file)) {
                    folderList.add(file);
                }
            }
                
        }
        return folderList;
    }
    
    private boolean isImage(File file){
        /*
        * Return true if the file is an image.
        */
        String fileType = "";
        try {
            fileType = Files.probeContentType(file.toPath());
        } catch (IOException ex) {
            Logger.getLogger(MD5Service.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        fileType = fileType.split("/")[0];
        if (fileType.equals("image")) return true;
        else return false;
    }

    public String getDirectory() {
        return directory.getAbsolutePath();
    }
   
    
}
