/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foldercomparator;

import java.io.File;
import org.scijava.service.SciJavaService;

/**
 *
 * @author florian
 */
public interface ComparatorServiceInterface extends SciJavaService{
    public void setFolder(File directory,FieldType tpe);
    
}
