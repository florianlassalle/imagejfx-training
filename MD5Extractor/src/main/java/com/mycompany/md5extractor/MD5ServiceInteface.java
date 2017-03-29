/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.md5extractor;

import java.io.File;
import org.scijava.service.SciJavaService;

/**
 *
 * @author florian
 */
public interface MD5ServiceInteface extends SciJavaService {
    public void addFolder(File file);
    public void compileMD5();
    public String getDirectory();
    public void cancelTask();
}
