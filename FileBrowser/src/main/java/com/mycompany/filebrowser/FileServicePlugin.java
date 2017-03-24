/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.filebrowser;

import java.io.File;
import java.util.regex.Pattern;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
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
 * 
 * 
 */
@Plugin(type = SciJavaService.class,priority = 10)
public class FileServicePlugin extends AbstractService implements FileService{
    @Parameter
    EventService eventService;
    
    List<ItemFile> itemList = new ArrayList<>();
    String currentRepository;
    
    
    @Override
    public void openFolder() {
        /*
        *Default constructor, launch openFolder, with the default user home directory.
        */
        
        openFolder(System.getProperty("user.home"));
    }
    
    public void openFolder(String path) {
        /*
        *Open the folder on the given path.
        *lauch the function createFile for each file in the folder.
        *publish an event of type ListUpdateEvent when done.
        
        */
        this.currentRepository = path;
        File folder = new File(path);
        File[] list = folder.listFiles();
        this.itemList.clear();
        for (File item : list){
            createFile(item);
        }
       
        eventService.publish(new ListUpdateEvent(this.itemList));
    }
    
    private void createFile(File item){
        /*
        *Create an object ItemFile in fuction of the type of the files in the folder.
        *It add in the itemList only the image files and folders, and only non hidden elements.
        */
        if (item.isFile()&& (!item.isHidden())){
            
            if (isImage(item)){
                    try {
                    this.itemList.add(new ImageFile(item.getName(), item.getAbsolutePath(),false,Files.size(item.toPath()),Files.getLastModifiedTime(item.toPath())));
                } catch (IOException ex) {
                    Logger.getLogger(FileServicePlugin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else if (item.isDirectory()&& (!item.isHidden()))this.itemList.add(new Folder(item.getName(), item.getAbsolutePath(),false));
    }
    
    private boolean isImage(File file){
        /*
        * Return true if the file is an image.
        */
        String fileType = "";
        try {
            fileType = Files.probeContentType(file.toPath());
        } catch (IOException ex) {
            Logger.getLogger(FileServicePlugin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        fileType = fileType.split("/")[0];
        if (fileType.equals("image")) return true;
        else return false;
    }

    @Override
    public void sortItems() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void searching(String entry) {
        /**
         * When called, we use a regular expression on the name of the items in the list
         * this method use the regex library of java
         * the research is case insansitive
         * if there is nothing in the string in parameter, the whole folder is displayed
         */
        final List<ItemFile> found = new ArrayList<>();
        if (!entry.equals("")){
            Pattern pattern = Pattern.compile(entry,Pattern.CASE_INSENSITIVE);
            this.itemList.stream()
                .forEach((ItemFile item) -> {
                    Matcher matcher = pattern.matcher(item.getName());
                    if (matcher.find()) {
                        found.add(item);
                    }
            });
            eventService.publish(new ListUpdateEvent(found));
        }
        else eventService.publish(new ListUpdateEvent(this.itemList));
        
        
    }
    
    @Override
    public void open(){
        /**
        * Method to be cald by the Controller
        * First it make a list of all selected items (on the view and so in the model)
        * Then it check if the item is a folder or an image.
        * If it's a folder, it launch the openFolder method, with the demanded path
        * if multiple folders are selected, the last one is opened
        * if it's an image, it call the linux program "eog" to open the image.
        **/
         List<ItemFile> liste =itemList
                .stream()
                .filter(ch -> ch.isSelected())
                .collect(Collectors.toList());
         liste.forEach((ItemFile item) -> {
             
             if (item.getClass().equals(ImageFile.class)){
                
                 try {
                    Runtime.getRuntime().exec("eog " + item.getPath());
                } catch (IOException ex) {
                    Logger.getLogger(FileServicePlugin.class.getName()).log(Level.SEVERE, null, ex);
                }
             }
             else {
                 openFolder(item.getPath());
             }
             
        });
        
    }
    
    public void selection(ObservableList<ItemFile> names){
       
        names.forEach((ItemFile item) -> {
            item.setSelected(!item.isSelected());
        });
    }

    public void up(){
        /*
        * Lauch openFolder, with the parent directory as parameter.
        */
        File folder = new File(this.currentRepository);
        
        openFolder(folder.getParent());
    }

    public List<ItemFile> getItemList() {
        return itemList;
    }

    public String getCurrentRepository() {
        return currentRepository;
    }

   

   
    @Override
    public String informations() {
        String info = " ";
    
        List<ItemFile> selectedList= itemList
                .stream()
                .filter(ch -> ch.isSelected())
                .collect(Collectors.toList());
        for(ItemFile item : itemList) {
            if (item.getClass().equals(ImageFile.class)){
                info = info + " Name : " + item.getName() + " size : " + item.getSize() + " last Modification : " + item.getDateModification().toString()+ "\n";
            }
        
        }
    return info;
    }
    
    
}
