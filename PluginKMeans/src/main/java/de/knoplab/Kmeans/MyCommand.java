/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.Kmeans;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import net.imagej.Dataset;
import net.imagej.DatasetService;
import net.imagej.axis.AxisType;
import net.imagej.axis.CalibratedAxis;
import net.imagej.ops.OpService;
import net.imglib2.Cursor;
import net.imglib2.RandomAccess;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;
import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.command.ContextCommand;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
//import net.sf.javaml.clustering.KMeans;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;
/**
 *
 * @author cyril
 */
@Plugin(type = Command.class, menuPath = "Plugins > My Custom command")
public class MyCommand extends ContextCommand {

    @Parameter(type = ItemIO.INPUT)
    Dataset input;

    @Parameter(type = ItemIO.OUTPUT)
    Dataset output;

    @Parameter
    DatasetService datasetService;

    
    @Parameter
    OpService opService;
    
    @Override
    public void run() {

        process();

    }

    public <T extends RealType<T>> void process() {

        // getting the number of dimension of the image
        int numDimension = input.numDimensions();

        // creating a array that will hold the dimensions
        long[] dimension = new long[input.numDimensions()];

        // creating an array that will hold the dimensions type
        CalibratedAxis[] axes = new CalibratedAxis[numDimension];

        // copying the data in the arrays
        input.dimensions(dimension);
        input.axes(axes);

        // use stream to map the CalibratedAxis
        // to their axis type
        AxisType[] types = Stream.of(axes)
                .map(axe -> axe.type())
                .toArray(size -> new AxisType[size]);
        
        //creating the output
        output = datasetService.create(new FloatType(), dimension, "My output", types, true);        
        System.out.println(input.getChannels());
        
        
        
        Cursor<RealType<?>> inputCursor = input.cursor();
        
        List<PixelPoint> pixelList = new ArrayList<>();
        
       
        
        List<CentroidCluster> kmeansResult = null;
        // algo for multi chanels
        
        pixelList.clear();
        
        inputCursor.reset();
        System.out.println(dimension[0]);
        while (inputCursor.hasNext()) {
            inputCursor.fwd();
            // J'ai un probleme, mon cursor parcour tous les pixels dimension par dimension, donc je me retrouve avec une liste contenant 3fois chaque pixel(une fois par dimension)
            // je ne sais pas si il faut que je creer un seul pixelPoint (ce qui implique de verifier si il existe deja avant de le creer et donc de parcourir la liste)
            // ou bien creer 3x chaque pixel mais je ne sais pas comment faire pour que chacun soit bien dans sa dimension (couleur) sachant que mon curseur parcour les dimensions dans l'ordre 0,2,1
            
            switch (inputCursor.getIntPosition(2)) {
                case 0:
                    PixelPoint newPixel = new PixelPoint(new int[]{inputCursor.getIntPosition(0), inputCursor.getIntPosition(1)});
                    newPixel.setRed(inputCursor.get().getRealDouble());
                    pixelList.add(newPixel);
                    break;
                case 1:
                    {
                        PixelPoint pixel = pixelList.get(inputCursor.getIntPosition(0)+(inputCursor.getIntPosition(1)*(int)dimension[0]));
                        pixel.setGreen(inputCursor.get().getRealDouble());
                        break;
                    }
                default:
                    {
                        PixelPoint pixel = pixelList.get(inputCursor.getIntPosition(0)+(inputCursor.getIntPosition(1)*(int)dimension[0]));
                        pixel.setBlue(inputCursor.get().getRealDouble());
                        break;
                    }
            }
            //if (inputCursor.getIntPosition(0)== 200)System.out.println(pixelList.get(inputCursor.getIntPosition(0)+inputCursor.getIntPosition(1)).getPixelValue()[1]);
            //if (inputCursor.getIntPosition(0)== 200)System.out.println(inputCursor.getIntPosition(0)+(inputCursor.getIntPosition(1)*512));
        }
        /*
        for (PixelPoint pixel : pixelList){
                    if (pixel.getPosition()[0]== 250){
                        System.out.println(pixel.getPixelValue()[1]);
                    }
                }
        */
        System.out.println("coucou");
        for (int i = 0; i < (int)input.getChannels(); i++) {
             // applying the kmeans function from apache common math3 library on the list
            final int pos = i;
            
            pixelList.forEach((PixelPoint pixel) -> {
                
                pixel.setDimension(pos);
            });
            System.out.println("bouh !");
            /*
            if (pos == 1){
                 for (PixelPoint pixel : pixelList){
                    if (pixel.getPosition()[0]== 250){
                        System.out.println(pixel.getPixelValue()[1]);
                    }
                }
            }
            */
            KMeansPlusPlusClusterer kmeans = null;
            kmeans = new KMeansPlusPlusClusterer(2);
            kmeansResult = kmeans.cluster(pixelList);
            
            
           
            int maxValue = Integer.MAX_VALUE;
            CentroidCluster supCluster = null;

            // checking wich cluster contains darkers pixels
            for (CentroidCluster cluster : kmeansResult){
                PixelPoint pixel = pixelList.get((int) cluster.getCenter().getPoint()[0]);
                if ( pixel.getPixelValue()[i] < maxValue){
                    maxValue = (int) pixel.getPixelValue()[i];
                    supCluster = cluster;
                }
            }

            // we put all the pixel from the darkest cluster to black and the others to white
            for (CentroidCluster cluster : kmeansResult){
                if (cluster.equals(supCluster)){
                    cluster.getPoints()
                        .forEach((pixel) -> {
                            PixelPoint pi = (PixelPoint) pixel;
                            pi.setBlack(pos);
                        });
                }
                else{
                    cluster.getPoints()
                        .forEach((pixel) -> {
                            PixelPoint pi = (PixelPoint) pixel;
                            pi.setWhite(pos);
                        });
                }
            }
            kmeansResult.clear();
            RandomAccess<? extends RealType> radacc = output.randomAccess();

            pixelList.forEach((pixel) -> {
                radacc.setPosition( new int [] {pixel.getPosition()[0],pixel.getPosition()[1],pos});
                radacc.get().setReal(pixel.getPixelValue()[pos]);
            });
            
        }
        System.out.println("Finis");
        /*
        // creating a list of pixelPoint, a custom object wich implements clusterable so we can apply the kmeans function on it
        
        while (inputCursor.hasNext()) {
            inputCursor.fwd();
            pixelList.add(new PixelPoint(new double[] {inputCursor.get().getRealDouble()}, new int[]{inputCursor.getIntPosition(0), inputCursor.getIntPosition(1),0}));
            
        }
        
        // applying the kmeans function from apache common math3 library on the list
        KMeansPlusPlusClusterer kmeans = new KMeansPlusPlusClusterer(2);
        List<CentroidCluster> kmeansResult = kmeans.cluster(pixelList);
        int[] maxValue = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        CentroidCluster supCluster = null;
        
        // checking wich cluster contains darkers pixels
        for (CentroidCluster cluster : kmeansResult){
            PixelPoint pixel = pixelList.get((int) cluster.getCenter().getPoint()[0]);
            if ( pixel.getPixelValue()[0] < maxValue[0]){
                maxValue[0] = (int) pixel.getPixelValue()[0];
                supCluster = cluster;
            }
        }
        
        // we put all the pixel from the darkest cluster to black and the others to white
        for (CentroidCluster cluster : kmeansResult){
            if (cluster.equals(supCluster)){
                cluster.getPoints()
                    .forEach((pixel) -> {
                        PixelPoint pi = (PixelPoint) pixel;
                        pi.setBlack();
                    });
            }
            else{
                cluster.getPoints()
                    .forEach((pixel) -> {
                        PixelPoint pi = (PixelPoint) pixel;
                        pi.setWhite();
                    });
            }
        }
        
        //creating the output
        output = datasetService.create(new FloatType(), dimension, "My output", types, true);
        
        
        RandomAccess<? extends RealType> radacc = output.randomAccess();
        
        pixelList.forEach((pixel) -> {
            radacc.setPosition( pixel.getPosition());
            radacc.get().setReal(pixel.getPixelValue()[0]);
        });
        
        */

    }

}
