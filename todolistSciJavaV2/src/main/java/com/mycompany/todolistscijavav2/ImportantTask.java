/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolistscijavav2;


import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import static javafx.scene.text.Font.font;

/**
 *
 * @author florian
 */
public class ImportantTask implements TaskStyler{
    private Font fontSize = font(20);
    private Color color = Color.web("orange");
    

    @Override
    public Font getFont() {
        return fontSize;
    }

    @Override
    public Color getColor() {
        return color;
    }
}

