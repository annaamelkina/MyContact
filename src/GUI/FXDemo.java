package GUI;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import ContactEntry.ContactEntry;
import ContactManager.ContactManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
 
//GUI with JavaFX
public class FXDemo extends Application {
 
    private final TableView<ContactEntry> table = new TableView<>();//javafx table view
    private ObservableList<ContactEntry> data = null;//TableData field
    ContactManager cm = new ContactManager();//Contact Management field
    boolean dialog_appear = false;//stage controller field
    final HBox hb = new HBox();//layout of javafx stage
    
    //main function of this project
    public static void main(String[] args) {
        launch(args);
    }
 
    //run function of javafx stage(initiate all controls here)
    @Override
    public void start(Stage stage) {
    	//create new scene and set scale and title
        Scene scene = new Scene(new Group());
        stage.setWidth(850);
        stage.setHeight(550);
        stage.setTitle("MyContactManager");
 
        //set column informations of table
        TableColumn idCol = new TableColumn("ID");
        idCol.setMinWidth(150);
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("IDName"));
 
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(150);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("FirstName"));
 
        TableColumn lastNameCol = new TableColumn("LastName");
        lastNameCol.setMinWidth(150);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("LastName"));
 
        
        TableColumn emailCol = new TableColumn("Email");
        emailCol.setMinWidth(150);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<>("Email"));

        TableColumn phoneCol = new TableColumn("Phone");
        phoneCol.setMinWidth(150);
        phoneCol.setCellValueFactory(
                new PropertyValueFactory<>("Phone"));
        
        //set table data here
        setTableData();
        table.setItems(data);
        table.getColumns().addAll(idCol, firstNameCol,lastNameCol,emailCol,phoneCol);
        
        //initiate control buttons and add here
        final Button addButton = new Button("Add");
        final Button loadButton = new Button("Load");
        final Button editButton = new Button("EDIT");
        final Button deleteButton = new Button("DELETE");
        final Button searchButton = new Button("Search");
        
        addButton.setOnAction((ActionEvent e) -> {
        	
        	if(!dialog_appear){
        		System.out.println("dialog_appear "+dialog_appear);
        		dialog_appear = true;
        		initTextInputDialog("Add Contact","","","","","Add","Close",-1);
        	}
         });
        
        loadButton.setOnAction((ActionEvent e) -> {
        	
        	setTableData();
         });
        
        deleteButton.setOnAction((ActionEvent e) -> {
        	
        	int sei = table.getSelectionModel().getSelectedIndex();
        	System.out.println("selected index "+sei);
        	if(sei < 0){
        		Alert alert = new Alert(AlertType.INFORMATION);
	        	alert.setTitle("Information Dialog");
	        	alert.setHeaderText("No Contact Entry is selected");
	        	alert.setContentText("Please Select Contact Entry To Delete!");
	
	        	alert.showAndWait();
	        	return;
        	}
        	deleteContactEntry(sei);
         });
        
        editButton.setOnAction((ActionEvent e) -> {
        	
        	int sei = table.getSelectionModel().getSelectedIndex();
        	System.out.println("selected index "+sei);
        	if(sei < 0){
        		Alert alert = new Alert(AlertType.INFORMATION);
	        	alert.setTitle("Information Dialog");
	        	alert.setHeaderText("No Contact Entry is selected");
	        	alert.setContentText("Please Select Contact Entry To Edit!");
	
	        	alert.showAndWait();
	        	return;
        	}
        	ContactEntry ce = cm.retrieveContactArray().get(sei);
        	if(!dialog_appear){
        		//System.out.println("dialog_appear "+dialog_appear);
        		dialog_appear = true;
        		initTextInputDialog("Edit Contact",ce.getFirstName(),ce.getLastName(),ce.getPhone(),ce.getEmail(),"Apply","Cancel",sei);
        	}
         });

        searchButton.setOnAction((ActionEvent e) -> {
        	
        	if(!dialog_appear){
        		System.out.println("dialog_appear "+dialog_appear);
        		dialog_appear = true;
        		initTextInputDialog("Add Contact","","","","","Search","Close",-2);
        	}
         });

        hb.getChildren().addAll(addButton);
        hb.getChildren().addAll(loadButton);
        hb.getChildren().addAll(editButton);
        hb.getChildren().addAll(deleteButton);
        hb.getChildren().addAll(searchButton);
        addButton.setMinWidth(100);
        loadButton.setMinWidth(100);
        editButton.setMinWidth(100);
        deleteButton.setMinWidth(100);
        searchButton.setMinWidth(100);
 
        hb.setSpacing(30);
 
        final VBox vbox = new VBox();
        vbox.setSpacing(30);
        vbox.setPadding(new Insets(25, 0, 0, 25));
        vbox.getChildren().addAll(table, hb);
        
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();
    }
    
    //function to delete contact
    private void deleteContactEntry(int sei) {
		// TODO Auto-generated method stub
		cm.deleteContact(sei);
		setTableData();
	}

    //function for controlling of sub dialog(for add and edit)
	public void initTextInputDialog(String title, String firstNameVal,String lastNameVal,String phoneNumVal,String emailAddrVal,String applyBtnVal,String cancelBtnVal,int is_edit)
    {
    	
        // creating the grid of input dialogue in size 500*350 pixels
        Stage dialogStage = new Stage();
        dialogStage.setWidth(500);
        dialogStage.setHeight(350);
        dialogStage.setTitle(title);

        // creating of the single vertical column in the grid of input dialogue
        final VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(25, 0, 0, 25)); //margins around the whole grid (top/right/bottom/left) - 25 pixels indent from left and top

        //creating of the single horizontal row in the grid of input dialogue
        final HBox firstNameHB = new HBox();
        final Label firstNamelbl = new Label("FirstName"); //creating the lable
        final TextField firstNameValue = new TextField(); // creating the text field
        firstNameValue.setText(firstNameVal); //setting the variable for the text field
        firstNameHB.getChildren().addAll(firstNamelbl); //setting the label
        firstNameHB.getChildren().addAll(firstNameValue); //setting the value
        firstNameHB.setSpacing(42);
        firstNameHB.setPadding(new Insets(10, 0, 0, 100));
        
        final HBox secondNameHB = new HBox();
        final Label secondNamelbl = new Label("LastName");
        final TextField secondNameValue = new TextField();
        secondNameValue.setText(lastNameVal);
        secondNameHB.getChildren().addAll(secondNamelbl);
        secondNameHB.getChildren().addAll(secondNameValue);
        secondNameHB.setSpacing(43);
        secondNameHB.setPadding(new Insets(10, 0, 0, 100));
        
        final HBox phoneNumHB = new HBox();
        final Label phoneNumlbl = new Label("PhoneNum");
        final TextField phoneNumValue = new TextField();
        phoneNumValue.setText(phoneNumVal);
        phoneNumHB.getChildren().addAll(phoneNumlbl);
        phoneNumHB.getChildren().addAll(phoneNumValue);
        phoneNumHB.setSpacing(38);
        phoneNumHB.setPadding(new Insets(10, 0, 0, 100));
        
        final HBox emailAddrHB = new HBox();
        final Label emailAddrlbl = new Label("EmailAddr");
        final TextField emailAddrValue = new TextField();
        emailAddrValue.setText(emailAddrVal);
        emailAddrHB.getChildren().addAll(emailAddrlbl);
        emailAddrHB.getChildren().addAll(emailAddrValue);
        emailAddrHB.setSpacing(42);
        emailAddrHB.setPadding(new Insets(10, 0, 0, 100));

        //creating of the buttons under the grid in the add dialogue
        final HBox btnGrpHB = new HBox();
        final Button okBtn = new Button(applyBtnVal); // OK button
        final Button cancelBtn = new Button(cancelBtnVal); //CANCEL button
        cancelBtn.setOnAction((ActionEvent e) -> {
        	dialogStage.close();
        	dialog_appear = false;
         });
        okBtn.setOnAction((ActionEvent e) -> {
        	if(is_edit == -1){
        	   cm.addContact(new ContactEntry(String.valueOf(cm.generateContactId()),firstNameValue.getText(),secondNameValue.getText(),phoneNumValue.getText(),emailAddrValue.getText()));
        	   setTableData(); //adding action
        	}
        	else if(is_edit >= 0){
        		cm.editContact(is_edit,firstNameValue.getText(),secondNameValue.getText(),phoneNumValue.getText(),emailAddrValue.getText());
        		setTableData(); //editing action
        	}
        	else {
        		ContactEntry ce = cm.searchContacts(firstNameValue.getText(), secondNameValue.getText(), phoneNumValue.getText(), emailAddrValue.getText());
        		if(ce != null){
	        		firstNameValue.setText(ce.getFirstName());
	        		secondNameValue.setText(ce.getLastName());
	        		phoneNumValue.setText(ce.getPhone());
	        		emailAddrValue.setText(ce.getEmail());
        		}
        		else
        		{
        			Alert alert = new Alert(AlertType.INFORMATION);
    	        	alert.setTitle("Information Dialog");
    	        	alert.setHeaderText("No Search Result");
    	        	alert.setContentText("Cannot Find Contact!");
    	
    	        	alert.showAndWait();
        		}
        	}
        	
         });
        okBtn.setMinWidth(70);
        cancelBtn.setMinWidth(70);
        btnGrpHB.getChildren().addAll(okBtn);
        btnGrpHB.getChildren().addAll(cancelBtn);
        btnGrpHB.setSpacing(42);
        btnGrpHB.setPadding(new Insets(10, 0, 0, 170));
        
        vbox.getChildren().addAll(firstNameHB,secondNameHB,phoneNumHB,emailAddrHB,btnGrpHB);
        
        dialogStage.setScene(new Scene(vbox));
        
        dialogStage.show();
        dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");
                dialog_appear = false;
            }
        });        
    }
    
	//function to set data for table
    public void setTableData()
    {
    	String export_data = "";
    	List<ContactEntry> contacts = cm.retrieveContactArray(); //creating of empty array
    	
    	if(data == null && contacts.size() > 0)//cleaning of the array
    		data = FXCollections.observableArrayList(contacts.get(0));
    	data.clear();
    	for(int i=0;i<contacts.size();i++){//setting array with the data from the file ContactDB.txt
    		data.add(contacts.get(i));
    		export_data += contacts.get(i).getIDName()+" ";
    		export_data += contacts.get(i).getFirstName()+" ";
    		export_data += contacts.get(i).getLastName()+" ";
    		export_data += contacts.get(i).getPhone()+" ";
    		export_data += contacts.get(i).getEmail()+" ";
    		export_data += "\r\n";
    	}
    	
    	File dbFile = new File("ContactDB.txt");
    	try {//try to access the file
			FileWriter fw = new FileWriter(dbFile);
			fw.write(export_data);
			fw.close();
		} catch (IOException e) {//showing the errors if file is not accessible
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
} 