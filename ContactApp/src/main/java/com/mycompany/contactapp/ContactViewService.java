/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.contactapp;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import org.scijava.Prioritized;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginService;
import org.scijava.service.AbstractService;
import org.scijava.service.SciJavaService;

/**
 *
 * @author florian
 */
@Plugin(type = SciJavaService.class,priority = 10)
public class ContactViewService extends AbstractService implements ContactViewServiceInterface{
    ViewControllerInterface currentView;

    List<ViewControllerInterface> viewList;
    
    @Parameter
    PluginService pluginService;
    
    
    List<Contact> contactList= new ArrayList<>();

    public ContactViewService() {
        Image jc = new Image("file:JCVD.jpg",64,64,false,false);
        this.contactList.add(new Contact("Jean-Claude", "jean-claude@vadhame.com", "Grand amateur de chihuahua", jc));
        this.contactList.add(new Contact("Chuck Norris", "chuck@norris.com", "Chuck Norris a deja compté jusqu'a l'infini ... deux fois"));
    }

    public List<Contact> getContactList() {
        return this.contactList;
    }
    
    
    
    @Override
    public void showView() {

        if (viewList == null) {
            viewList = pluginService.createInstancesOfType(ViewControllerInterface.class);
            if (viewList.size() == 0) {
                System.out.println("No view found");
            } else {
                ViewControllerInterface view = viewList.get(0);
                currentView = view;

                view.init();
                view.show();

            }
        }

    }


    @Override
    public void addContact(List<String> newContact, ViewControllerInterface controller) {
        Contact contact ;
        if (newContact.get(3) == null){
            contact = new Contact(newContact.get(0), newContact.get(1), newContact.get(2));
        }
        else if (newContact.get(3) == null && newContact.get(2)==null){
            contact = new Contact(newContact.get(0), newContact.get(1));
        }
        else {
            contact = new Contact(newContact.get(0), newContact.get(1), newContact.get(2), new Image(newContact.get(3)));
        }
        this.contactList.add(contact);
        currentView.refresh();
        currentView.newContactNotification(contact.getName());
    }
}
