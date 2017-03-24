/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.filebrowser;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.scijava.event.EventService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 *
 * @author florian
 */
@Plugin(type = Sorting.class, label = "Sort by size")
public class SizeSortingPlugin implements Sorting{
    /**
     * sorting items by size
     * using the size property of the object
     * 
     */
    @Parameter
    FileService fileService;
    @Parameter
    EventService eventService;

    @Override
    public void sort() {
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
        // here we use Comparator, i don't really understand how it work, but at the end my objects are well sorted
        Comparator<ItemFile> comparator = new Comparator<ItemFile>() {
            @Override
            public int compare(ItemFile left, ItemFile right) {
                long result = left.getSize()- right.getSize();
                return (int) result;
            }
        };
        Collections.sort(imagelist,comparator);
        
        list.addAll(imagelist);
        
        eventService.publish(new ListUpdateEvent(list));
    }
    
    
    
}
