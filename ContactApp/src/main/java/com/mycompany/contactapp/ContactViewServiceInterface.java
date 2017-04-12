/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.contactapp;

import java.util.List;
import javafx.scene.image.Image;
import org.scijava.service.SciJavaService;

/**
 *
 * @author florian
 */
public interface ContactViewServiceInterface extends SciJavaService{
    public void showView();
    public List<Contact> getContactList();
    public void addContact(List<String> newContact, ViewControllerInterface controller);
}
