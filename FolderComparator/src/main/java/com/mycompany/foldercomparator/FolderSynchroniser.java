/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foldercomparator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.concurrent.Task;

/**
 *
 * @author florian
 */
public class FolderSynchroniser extends Task<Void>{

    private File source;
    private File target;
    
    public FolderSynchroniser(File source, File target) {
        this.source = source;
        this.target = target;
    }

    
    @Override
    protected Void call() throws Exception {
        List<File>  filesSource = Arrays.asList(this.source.listFiles());
        List<File>  filesTarget = Arrays.asList(this.target.listFiles());
        
        int iterator = 0;
        
        for (File file : filesSource){
            
            updateProgress(iterator,filesSource.size()-1);
            updateMessage("traitement de : "+file.getName());
            Thread.sleep(1000);
            if (isCancelled()) {
                break;
            }
            iterator++;
        }
       
        return null;
    }
    
}
