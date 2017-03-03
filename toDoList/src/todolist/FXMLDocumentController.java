/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todolist;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import static javafx.scene.text.Font.font;

/**
 *
 * @author florian
 */
public class FXMLDocumentController implements Initializable {
    
     @FXML
    private Button btnadd;
     @FXML
    private Button btndelete;
     @FXML
    private Button btnsetdone;
    @FXML
    private TextField txttask;
    @FXML
    private GridPane grid;
    @FXML
    private ListView list_view;
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        String newtask;
        System.out.println("adding new task");
        if (event.getSource()==btnadd || event.getSource()==txttask)
        {
            newtask = txttask.getText();
            final CheckBox newBox = new CheckBox(newtask);
            Font police = font(20);
            newBox.setFont(police);
            list_view.getItems().add(newBox);
            txttask.clear();
        }
        
        else if (event.getSource()== btndelete){
            ObservableList<CheckBox> boxes = list_view.getItems();
            for (CheckBox i : boxes){
                System.out.println(i);
                if (i.isSelected()){
                    list_view.getItems().remove(i);
                }
            }
        }
        else if(event.getSource()== btnsetdone){
            ObservableList<CheckBox> boxes = list_view.getItems();
            for (CheckBox i : boxes){
                i.setSelected(true);
            }
        }
        
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
