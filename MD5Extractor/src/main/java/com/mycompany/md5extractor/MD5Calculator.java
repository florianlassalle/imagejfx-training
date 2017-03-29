/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.md5extractor;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import org.scijava.event.EventService;
import org.scijava.plugin.Parameter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author florian
 */
public class MD5Calculator extends Task<Void>{
    @Parameter
    EventService eventService;
    
     private BiConsumer<Integer, Integer> progressUpdate ;
     
     private List<File> files;

     public MD5Calculator(List<File> files){
         this.files = files;
     }
     
    @Override
    protected Void call() throws Exception {
        int iterator = 0;
        int total = this.files.size();
        for (File file : this.files) {
            iterator++;
            
            updateProgress(iterator,total);
            updateMessage(file.getName());
            if (isCancelled()) {
                break;
            }
            BufferedImage buffImg = ImageIO.read(file);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(buffImg, "png", outputStream);
            byte[] data = outputStream.toByteArray();

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data);
            byte[] hash = md.digest();
            try {
                Files.write(Paths.get(file.getAbsolutePath()+"-md5"), hash, StandardOpenOption.CREATE);
            } catch (IOException ex) {
                    Logger.getLogger(MD5Calculator.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //writeFile(file, hash);
        
            
        
        }
       
       return null;
    }
    
     public void setProgressUpdate(BiConsumer<Integer, Integer> progressUpdate) {
        this.progressUpdate = progressUpdate ;
    }
    static String returnHex(byte[] inBytes) throws Exception {
        String hexString = "";
        for (int i=0; i < inBytes.length; i++) {
            hexString +=
            Integer.toString( ( inBytes[i] & 0xff ) + 0x100, 16).substring( 1 );
        }                                  
        return hexString;
  }      
    @Override 
    protected void succeeded() {
        super.succeeded();
        updateMessage("Done!");
    }
    
    public void writeFile(File file, byte[] hash){
        FileOutputStream newFile = null;
        try {
            newFile = new FileOutputStream(file.getAbsolutePath()+"-md5");
            
            newFile.write(hash);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MD5Calculator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MD5Calculator.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                if (newFile != null)
                   newFile.close();
             } catch (IOException e) {
                e.printStackTrace();
             }
        }
    }
}
