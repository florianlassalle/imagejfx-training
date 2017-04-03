/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foldercomparator;

import com.sun.org.apache.bcel.internal.generic.Select;
import java.io.File;
import java.io.IOException;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.scijava.Context;
import org.scijava.SciJava;
import org.scijava.event.EventHandler;
import org.scijava.plugin.Parameter;

/**
 *
 * @author florian
 * 
 * to do :
 * 
 * moulinette pour traiter les dossiers
 * 
 */
public class ComparatorController extends GridPane{
    
    
    @Parameter
    ComparatorServiceInterface comparatorServiceInterface;
    
    @Parameter
    Stage stage;
    @Parameter
    FXMLLoader loader;
    @Parameter
    Context context;
    
    @FXML
    ProgressBar progressBar;
    @FXML
    Button select;
    @FXML
    Button start;
    @FXML
    Label label;
    
    public static String CHANGE = "change folder";
    private FolderField sourceField;
    private FolderField targetField;
    

    public ComparatorController() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Scene.fxml")); // c'est toujour galere de trouver le bon chemin, penser a mettre le fxml dans le bon dossier comme ici
        loader.setRoot(this);       //root est la base de la page, ici anchorPane
        loader.setController(this); //definition du controlleur
        loader.load(); // generation de la fenetre.
        
        // Comme dit plus haut, on injecte scijava ici plutot que dans le main, ce qui permet qu'il soit injecté avant de lancer les fonctions d'initialisation
        
        SciJava scijava = new SciJava();
        scijava.context().inject(this);
        sourceField = new FolderField( context, FieldType.SOURCE);
        targetField = new FolderField(context, FieldType.TARGET);
        this.add(sourceField, 0, 0); // column=0 row=0
        this.add(targetField, 0, 2); // column=0 row=2
        
         
    }
    
    public void switchValues(){
        System.out.println(sourceField.getTypeProperty().getValue());
        if (sourceField.getTypeProperty().getValue().equals(FieldType.SOURCE)) {
            sourceField.setTypeProperty(FieldType.TARGET);
            targetField.setTypeProperty(FieldType.SOURCE);
        } else {
            sourceField.setTypeProperty(FieldType.SOURCE);
            targetField.setTypeProperty(FieldType.TARGET);
        }
        
    }
    
    public void startSynch(){
        if (!sourceField.getFileProperty().equals(null) && !targetField.getFileProperty().equals(null)) {
            comparatorServiceInterface.synchroniseFolders();
        } else {
        }
        
    }
    
    @EventHandler
    public void onSynchProgressEvent(SynchroniserProgressEvent event){
        Platform.runLater( () ->
            running(event)
        );

    }
    @EventHandler
    public void onSynchEnddingEvent(SynchroniserEndEvent event){
        /*
        When the list of items is updated, we refresh the view
        */
        Platform.runLater( () ->
        refresh()
        );
    }
    
    public void running(SynchroniserProgressEvent event){
        label.setVisible(true);
        label.textProperty().bind(event.getSynchroniser().messageProperty());
        progressBar.setVisible(true);
        progressBar.progressProperty().bind(event.getSynchroniser().progressProperty());
        start.setDisable(false);
        start.setText("Cancel");
        start.setOnAction((ActionEvent eventbis) -> cancel());
    }
    
    public void refresh(){
        label.setVisible(true);
        label.textProperty().unbind();
        label.setText("Done");
        progressBar.progressProperty().unbind();
        progressBar.setDisable(true);
        start.setText("Start");
        start.setOnAction((ActionEvent event)-> startSynch());
    }
    
    @FXML
    public void cancel(){
        comparatorServiceInterface.cancelTask();
        refresh();
        
    }
        
        
    
    public class FolderField extends HBox{
        private String SELECT_FOLDER = "Select folder";
        @Parameter
        ComparatorServiceInterface comparatorServiceInterface;
        
        @FXML
        Button selectButton;
        @FXML
        TextField textField;
        @FXML
        Label label;
        
        private final ObjectProperty<File> fileProperty = new SimpleObjectProperty<>();
        
        private final ObjectProperty<FieldType> typeProperty = new SimpleObjectProperty<>();
                
        private final ObservableValue<String> selectButtonText = Bindings.createStringBinding(this::getSelectButtonText, fileProperty);
        private final ObservableValue<String> textFieldText = Bindings.createStringBinding(this::getTextFieldText, fileProperty);
        private final ObservableValue<String> typeText = Bindings.createStringBinding(this::getTypeText, typeProperty);
        private final ObservableValue<File> fileObservableValue = Bindings.createObjectBinding(this::getfileObservableValue, fileProperty);

        public FolderField(Context context, FieldType type) throws IOException {
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/FolderField.fxml")); // c'est toujour galere de trouver le bon chemin, penser a mettre le fxml dans le bon dossier comme ici
            loader.setRoot(this);       //root est la base de la page, ici anchorPane
            loader.setController(this); //definition du controlleur
            loader.load(); // generation de la fenetre.
            context.inject(this);
            this.typeProperty.set(type);
            
            selectButton.textProperty().bind(selectButtonText);
            selectButton.setOnAction(this::onSelectButtonClicked);
            
            textField.textProperty().bind(textFieldText);
            
            label.textProperty().bind(typeText);
            disablingButtons(true);
            
        }
        
        public void disablingButtons(boolean disabling){
            if (this.typeProperty.getValue().equals(FieldType.TARGET)) {
                selectButton.setDisable(disabling);
                textField.setDisable(disabling);
            } 
        }
        
        public String getSelectButtonText (){
            // if the file property is empty
            if (this.fileProperty.getValue() == null) {
                return SELECT_FOLDER;
            } else {
                // returns the name of the file
                return (this.fileProperty.getValue().getName());
            }
        }
        public String getTextFieldText (){
            // if the file property is empty
            if (this.fileProperty.getValue() == null) {
                return SELECT_FOLDER;
            } else {
                // returns the name of the file
                return (this.fileProperty.getValue().getAbsolutePath());
            }
        }
        public File getfileObservableValue (){
            return this.fileProperty.getValue();
        }
        public String getTypeText (){
            return this.typeProperty.getValue().toString();
           
        }
        
        public void onSelectButtonClicked(ActionEvent event){
            DirectoryChooser chooser = new DirectoryChooser();

            File directory = chooser.showDialog(null);
            this.fileProperty.set(directory);
            
            comparatorServiceInterface.setFolder(this.fileObservableValue, this.typeProperty.getValue());
           

        }
        
        public void setTypeProperty(FieldType type){
            typeProperty.set(type);
        }

        public ObjectProperty<FieldType> getTypeProperty() {
            return typeProperty;
        }

        public ObjectProperty<File> getFileProperty() {
            return fileProperty;
        }
        
        
        /*
        @EventHandler
        public void onFolderChangesEvent(FolderChangesEvent event){
            if (event.getType().equals(this.typeProperty.getValue())) {
                Platform.runLater( () ->
                    this.fileProperty.set(event.getFolder())
                    
                    );
                
                
            }
            
        }
*/
        @EventHandler
        public void onFieldEnablingEvent(FieldEnabling event){
            Platform.runLater( () ->
                disablingButtons(!event.getEnabled())
                    
            );
                
                
            
        }
        
        
        
    }
    
    
}
