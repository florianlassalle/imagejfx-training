/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.filebrowser;

import java.io.IOException;
import java.util.List;
import javafx.collections.ObservableList;
import org.scijava.service.SciJavaService;

/**
 *
 * @author florian
 */
public interface FileService extends SciJavaService {
    
    void openFolder();
    void sortItems();
    void searching();
    public void selection(ObservableList<String> names);
    public List<ItemFile> getItemList();
    public String getCurrentRepository();
    public void open();
    
}