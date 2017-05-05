/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.Kmeans;

import org.apache.commons.math3.ml.clustering.Clusterable;


/**
 *
 * @author florian
 */
public class PixelPoint implements Clusterable{
    
    private double[] pixelValue = new double[]{0,0,0};
    private int[] position;
    private int dimension = 0;

    public PixelPoint(double [] pixelValue, int[] position) {
        this.pixelValue = pixelValue;
        this.position = position;
    }

    public PixelPoint(int[] position) {
        this.position = position;
    }
    
    public void setRed(double pixelValue) {
        this.pixelValue[0] = pixelValue;
    }
    public void setGreen(double pixelValue) {
        this.pixelValue[1] = pixelValue;
    }
    public void setBlue(double pixelValue) {
        this.pixelValue[2] = pixelValue;
    }
    public double[] getPixelValue() {
        return pixelValue;
    }

    public int[] getPosition() {
        return position;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public int getDimension() {
        return dimension;
    }
    
    
    public void setWhite(int dim){
        this.pixelValue[this.dimension] = 255;
        /*
        for (int i = 0 ; i < this.pixelValue.length; i++){
            this.pixelValue[i] = 255;
        }
        */
    }
    
    public void setBlack(int dim){
        this.pixelValue[this.dimension] = 0;
        /*
        for (int i = 0 ; i < this.pixelValue.length; i++){
            this.pixelValue[i] = 0;
        }
*/
    }

    public void setPixelValue(double[] pixelValue) {
        this.pixelValue = pixelValue;
    }
    
    
    @Override
    public double[] getPoint() {
        return new double[]{this.pixelValue[this.dimension]};
        
    }
    
}
