/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.filebrowser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        System.out.println("Sorting ^^");
        /*
        List<ItemFile> itemList = fileService.getItemList();
        List<String> nameList = new ArrayList();
        itemList.stream().forEach((item) -> {
            nameList.add(item.getName());
        });
        Collections.sort(nameList);
*/
        List<ItemFile> itemList = fileService.getItemList();
        Collections.sort(itemList);
        eventService.publish(new ListUpdateEvent(itemList));
    }
    
}
