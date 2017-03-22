/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.filebrowser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
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
public class FileServicePlugin extends AbstractService implements FileService{
    @Parameter
    EventService eventService;
    
    
    
    List<ItemFile> itemList = new ArrayList<>();
    //String currentRepository = System.getProperty("user.dir");
    String currentRepository = "/home/florian";
    
    
    @Override
    public void openFolder() {
        File folder = new File(currentRepository);
        File[] list = folder.listFiles();
        for (File item : list){
            if (item.isFile()){
                itemList.add(new ImageFile(item.getName(), item.getAbsolutePath(),false));
            }
            else if (item.isDirectory())itemList.add(new Folder(item.getName(), item.getAbsolutePath(),false));
        }
        System.out.println(this.itemList);
        eventService.publish(new ListUpdateEvent(this.itemList));
    }

    @Override
    public void sortItems() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void searching() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void open(){
        // Rien ne s'ouvre, parceque la liste est vide.
        System.out.println("coucou");
         List<ItemFile> liste =itemList
                .stream()
                .filter(ch -> ch.isSelected())
                .collect(Collectors.toList());
         System.out.println(liste);
         liste.forEach((ItemFile item) -> {
              System.out.println(item.getPath());
             try {
                
                 Runtime.getRuntime().exec("eog " + item.getPath());
             } catch (IOException ex) {
                 System.out.println(item.getPath());
                 Logger.getLogger(FileServicePlugin.class.getName()).log(Level.SEVERE, null, ex);
             }
        });
        
    }
    public void selection(ObservableList<String> names){
        List<ItemFile> liste =itemList
                .stream()
                .filter(ch -> ch.getName().equals(names))
                .collect(Collectors.toList());
        liste.forEach((ItemFile item) -> {
            item.setSelected(!item.isSelected());
        });
    }

    public List<ItemFile> getItemList() {
        return itemList;
    }

    public String getCurrentRepository() {
        return currentRepository;
    }
    
    
}
