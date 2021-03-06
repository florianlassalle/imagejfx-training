/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.filebrowser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.scijava.event.EventService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 *
 * @author florian
 */
@Plugin(type = Sorting.class, label = "Sort Alphabeticaly")
public class AlphabeticSortingPlugin implements Sorting{
    @Parameter
    FileService fileService;
    @Parameter
    EventService eventService;

    @Override
    public void sort() {
        /**
         * Sorting the itemFiles by thier names.
         * We use here the Collection.sort method thanks to the implementation of the Comparable interface in the ItemFile class
         * 
         * Folders are sorted first, then the images, like this, folders apeares first in the view
         */
        
        List<ItemFile> itemList = fileService.getItemList();
        List<ItemFile> list =itemList
                .stream()
                .filter(ch -> ch.getClass().equals(Folder.class))
                .collect(Collectors.toList());
        Collections.sort(list);
        List<ItemFile> imagelist =itemList
                .stream()
                .filter(ch -> ch.getClass().equals(ImageFile.class))
                .collect(Collectors.toList());
        Collections.sort(imagelist);
        
        list.addAll(imagelist);
        
        eventService.publish(new ListUpdateEvent(list));
    }
    
}
