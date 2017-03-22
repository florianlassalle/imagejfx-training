/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.filebrowser;

import java.util.List;
import org.scijava.event.SciJavaEvent;

/**
 *
 * @author florian
 */
public class ListUpdateEvent extends SciJavaEvent{
     public final List<ItemFile> itemList ;
    
    public ListUpdateEvent(List<ItemFile> itemList ){
        this.itemList = itemList;
    }
    public List<ItemFile>  getItemList(){
        return this.itemList;
    }
}
