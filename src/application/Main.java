package application;

import java.sql.SQLException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws ClassNotFoundException, SQLException {
		DataBase database = new DataBase();

		TextArea screen = new TextArea();
		screen.setPrefWidth(350);
		screen.setEditable(false);
		Button deleteEmp = new Button ("delete Employee");
		deleteEmp.setPrefSize( 120,20);
		Button refresh = new Button("Refresh");
		refresh.setPrefSize(120, 20);


		TextField Eid = new TextField();
		Eid.setPromptText("id");
		TextField Ename = new TextField();
		Ename.setPromptText("Name");
		TextField Ephone = new TextField();
		Ephone.setPromptText("Phone");
		TextField Eaddress = new TextField();
		Eaddress.setPromptText("Address");
		TextField ESalary = new TextField();
		ESalary.setPromptText("Salary");
		HBox h2 = new HBox(10);
		h2.getChildren().addAll(Eid,Ename,Eaddress,Ephone,ESalary);
		h2.setAlignment(Pos.CENTER);
		h2.setMinHeight(50);



		//Table View
		TableView<Employees> DataTable = new TableView<Employees>();
		DataTable.setEditable(true);
		DataTable.setMaxHeight(700);
		DataTable.setMaxWidth(550);

		TableColumn<Employees, Integer> idCol = new TableColumn<Employees, Integer>("id");
		idCol.setMinWidth(50);
		idCol.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("Eid"));
		


		TableColumn<Employees, String> nameCol = new TableColumn<Employees, String>("Name");
		nameCol.setEditable(true);
		nameCol.setMinWidth(150);
		nameCol.setCellValueFactory(new PropertyValueFactory<Employees, String>("Ename"));
		nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		nameCol.setOnEditCommit(new EventHandler<CellEditEvent<Employees,String>>(){

			@Override
			public void handle(CellEditEvent<Employees, String> event) {
				Employees employee = event.getRowValue();
				employee.setEname(event.getNewValue());
				try {
					screen.appendText(database.updateName(event.getNewValue(), employee.getEid()));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});




		TableColumn<Employees, String> addressCol = new TableColumn<Employees, String>("Address");
		addressCol.setPrefWidth(150);
		addressCol.setCellValueFactory(new PropertyValueFactory<Employees, String>("Eaddress"));
		addressCol.setCellFactory(TextFieldTableCell.forTableColumn());
		addressCol.setOnEditCommit(new EventHandler<CellEditEvent<Employees, String>>(){

			@Override
			public void handle(CellEditEvent<Employees, String> event) {
				Employees employee = event.getRowValue();
				employee.setEaddress(event.getNewValue());
				try {
					screen.appendText(database.updateAddress(event.getNewValue(), employee.getEid()));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});

		TableColumn<Employees, Integer> phoneCol = new TableColumn<Employees, Integer>("Phone");
		phoneCol.setMinWidth(50);
		phoneCol.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("Ephone"));
		phoneCol.setCellFactory(TextFieldTableCell.<Employees,Integer>forTableColumn(new IntegerStringConverter()));
		phoneCol.setOnEditCommit(new EventHandler<CellEditEvent<Employees, Integer>>(){

			@Override
			public void handle(CellEditEvent<Employees, Integer> event) {
				Employees employee = event.getRowValue();
				employee.setEphone(event.getNewValue());
				try {
					screen.appendText(database.updatePhone(event.getNewValue(), employee.getEid()));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});


		TableColumn<Employees, Double> salaryCol = new TableColumn<Employees, Double>("Salary");
		salaryCol.setMinWidth(50);
		salaryCol.setCellValueFactory(new PropertyValueFactory<Employees, Double>("Esalary"));
		salaryCol.setCellFactory(TextFieldTableCell.<Employees, Double>forTableColumn(new DoubleStringConverter()));
		salaryCol.setOnEditCommit(new EventHandler<CellEditEvent<Employees, Double>>(){

			@Override
			public void handle(CellEditEvent<Employees, Double> event) {
				Employees employee = event.getRowValue();
				employee.setEsalary(event.getNewValue());
				try {
					screen.appendText(database.updateSalary(event.getNewValue(), employee.getEid()));
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});

		DataTable.getColumns().addAll(idCol,nameCol, addressCol,phoneCol,salaryCol);







		//Buttons
		Button addEmp = new Button("insert Employee");
		addEmp.setPrefSize(120, 20);
		addEmp.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(Eid.getText().isBlank() || Ename.getText().isBlank() || Ephone.getText().isBlank() || Eaddress.getText().isBlank()|| ESalary.getText().isBlank()) {
					screen.appendText("Please Fill All the Fields!!\n");
				}
				else {
					Employees employee = new Employees(Integer.parseInt(Eid.getText()), Ename.getText(), Eaddress.getText(), Integer.parseInt(Ephone.getText()), Double.parseDouble(ESalary.getText()));
					database.dataList.add(employee);
					clear(ESalary, Eaddress, Ephone, Ename, Eid);
					try {
						screen.appendText(database.insertData(employee));
						DataTable.refresh();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		});

		refresh.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				DataTable.refresh();
			}

		});

		
	        deleteEmp.setOnAction((ActionEvent e) -> {        	 
	        	 ObservableList<Employees> selectedRows = DataTable.getSelectionModel().getSelectedItems();
	        	ArrayList<Employees> rows = new ArrayList<>(selectedRows);
	        	rows.forEach(row -> {
	        		DataTable.getItems().remove(row); 
	        		try {
						database.delete(row.getEid());
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
	        		DataTable.refresh();
	        		});   
	        });
	        

		VBox h1 = new VBox(10);
		h1.setAlignment(Pos.CENTER);
		h1.getChildren().addAll(addEmp, deleteEmp, refresh);
		h1.setPrefWidth(150);	


		screen.appendText(database.connectDB());
		screen.appendText(database.getData());


		try {


			BorderPane root = new BorderPane();
			root.setCenter(DataTable);
			root.setRight(screen);
			root.setLeft(h1);
			root.setBottom(h2);
			Scene scene = new Scene(root,1100,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Employee Table");
			primaryStage.show();
			DataTable.setItems(database.dataList);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {


		launch(args);

	}
	public void clear(TextField Eid, TextField Ename, TextField Ephone, TextField Eaddress, TextField ESalary) {
		Eid.setText("");
		Ename.setText("");
		Ephone.setText("");
		Eaddress.setText("");
		ESalary.setText("");
	}

}
