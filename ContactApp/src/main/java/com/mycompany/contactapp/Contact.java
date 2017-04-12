/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.contactapp;

import javafx.scene.image.Image;

/**
 *
 * @author florian
 */
public class Contact {
    private String name;
    private String emailAdress;
    private String description;
    private Image icon;
    private static Image defaultIcon = new Image("file:ContactIcon.png",32,32,false,false);

    public Contact(String name, String phoneNumber, String description, Image icon) {
        this.name = name;
        this.emailAdress = phoneNumber;
        this.description = description;
        this.icon = icon;
    }

    public Contact(String name, String phoneNumber, String description) {
        this.name = name;
        this.emailAdress = phoneNumber;
        this.description = description;
        this.icon = defaultIcon;
    }

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.emailAdress = phoneNumber;
        this.icon = defaultIcon;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return emailAdress;
    }

    public String getDescription() {
        return description;
    }

    public Image getIcon() {
        return icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.emailAdress = phoneNumber;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }
    
        
}
