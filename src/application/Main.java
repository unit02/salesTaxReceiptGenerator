package application;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Sales Tax");

			GridPane grid = setUpForm(new GridPane());
			Scene scene = new Scene(grid, 500, 700);
			primaryStage.setScene(scene);

			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	//Test cases, comment out which set you don't want to test
	public ObservableList<Item> populateTable(){
		ObservableList<Item> data = FXCollections.observableArrayList();
		//input 1
		data.add(new Item(12.49, 1, "Book", false, ItemTypeEnum.Type.Book));
		data.add(new Item(14.99, 1, "Music CD", false, ItemTypeEnum.Type.Other));
		data.add(new Item(0.85, 1, "Chocolate Bar", false, ItemTypeEnum.Type.Food));
		
		//input 2
		data.add(new Item(10.00, 1, "Box of Chocolates", true, ItemTypeEnum.Type.Food));
		data.add(new Item(18.99, 1, "Perfume", true, ItemTypeEnum.Type.Other));

		//input 3
		data.add(new Item(27.99, 1, "Perfume", true, ItemTypeEnum.Type.Other));
		data.add(new Item(18.99, 1, "Perfume", false, ItemTypeEnum.Type.Other));		
		data.add(new Item(9.75, 1, "Headache Pills", true, ItemTypeEnum.Type.MedicalSupply));
		data.add(new Item(11.25, 1, "Chocolates", true, ItemTypeEnum.Type.Food));
		
		return data;
	}

/*
 * Method to calculate the amount of tax for each item. Also adds it to the price.
 */
	private Item calculateTax(Item item){
		//item type tax
		if(item.Type == ItemTypeEnum.Type.Other){
			item.TaxAmount +=  Math.round(item.Price *0.1 * 20) / 20.0;
			item.Price = Math.round(item.Price * 1.1 * 20) / 20.0;
		}
		//importers tax
		if(item.IsImported){
			item.TaxAmount +=Math.round(item.Price *0.05 * 20) / 20.0; 
			item.Price = Math.round( item.Price *1.05 * 20) / 20.0;
		}

		item.TaxAmount = item.TaxAmount * item.Quantity;
		return item;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	
	/*
	 * Method to set up the GUI and add event handlers to the buttons
	 */
	private GridPane setUpForm(GridPane grid){

		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label userNameLabel = new Label("Quantity:");
		grid.add(userNameLabel, 0, 1);

		TextField quantityTextField = new TextField();
		
		//ensure only numbers can be input into text box
		quantityTextField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText("Please enter a numerical value");
					alert.showAndWait();
					quantityTextField.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		grid.add(quantityTextField, 1, 1);

		Label itemLabel = new Label("Item:");
		grid.add(itemLabel, 0, 2);

		TextField itemTextField = new TextField();
		grid.add(itemTextField, 1, 2);

		Label priceLabel = new Label("Price:");
		grid.add(priceLabel, 0, 3);

		TextField priceTextField = new TextField();

		//ensure only numbers can be input into text boxes
		priceTextField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Invalid Input");
					alert.setHeaderText("Please enter a numerical value");
					alert.showAndWait();

					priceTextField.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		grid.add(priceTextField, 1, 3);

		Label catLabel = new Label("Category:");
		grid.add(catLabel, 0, 4);

		//retrieves the values of the enum and adds them to the combo box
		ObservableList<ItemTypeEnum.Type> options = 
				FXCollections.observableArrayList(ItemTypeEnum.Type.values());
		ComboBox comboBox = new ComboBox(options);
		grid.add(comboBox, 1, 4);

		Label importedLabel = new Label("Imported:");
		grid.add(importedLabel, 0, 5);

		CheckBox userCheckBox = new CheckBox();
		grid.add(userCheckBox, 1, 5);
		
//sets up the columns in the table 
		TableView<Item> list = new TableView<Item>();
		TableColumn quantityCol = new TableColumn("Quantity");
		quantityCol.setCellValueFactory(
				new PropertyValueFactory<Item,Integer>("Quantity"));

		TableColumn itemCol = new TableColumn("Item");  
		itemCol.setCellValueFactory(
				new PropertyValueFactory<Item,String>("ItemName")); 

		TableColumn priceCol = new TableColumn("Price");
		priceCol.setCellValueFactory(
				new PropertyValueFactory<Item,Double>("Price")); 

		TableColumn importedCol = new TableColumn("Imported");
		importedCol.setCellValueFactory(
				new PropertyValueFactory<Item,Boolean>("isImported"));

		TableColumn typeCol = new TableColumn("Type");
		typeCol.setCellValueFactory(
				new PropertyValueFactory<Item,ItemTypeEnum.Type>("Type"));
		
		//adds data to the table
		ObservableList<Item> data = populateTable();
		list.setItems(data);
		list.getColumns().removeAll();
		list.getColumns().addAll(quantityCol, itemCol, priceCol,importedCol,typeCol);
		GridPane.setColumnSpan(list, GridPane.REMAINING);
		grid.add(list, 0, 7);

		Button addButton = new Button("Add to List");
		grid.add(addButton, 0, 6);
		addButton.setOnAction(e -> {	 
			//adds the new item to the list
			data.add(new Item(
					Double.valueOf(priceTextField.getText()),
					Integer.valueOf(quantityTextField.getText()),
					itemTextField.getText(),
					userCheckBox.isSelected(),
					(ItemTypeEnum.Type)comboBox.getValue()));

		});

		Button getReceiptButton = new Button("Get Receipt");
		getReceiptButton.setOnAction(e -> {	 
			//goes through all items on the list to calculate tax and the total cost
			StringBuffer sb = new StringBuffer();
			double totalCost = 0;
			double totalTaxes = 0;
			for (Item item : data){
				Item receiptItem = calculateTax(item);
				sb.append(receiptItem.Quantity + " " +
						receiptItem.ItemName + " : " +
						receiptItem.Price + "\n"
						);			
				totalCost += receiptItem.Price * receiptItem.Quantity;
				totalTaxes += receiptItem.TaxAmount;
			}
			
			//round to 2 dp
			totalCost = (double) Math.round(totalCost * 100) / 100;
			totalTaxes = (double) Math.round(totalTaxes * 100) / 100;

			sb.append("\nSales Taxes : " + totalTaxes + "\n" + "Total : "+ totalCost);
			Stage stage = new Stage();
			stage.setTitle("Receipt");
			FlowPane root = new FlowPane();
			TextArea receiptText = new TextArea(sb.toString());
			receiptText.setMinHeight(300);
			root.getChildren().add(receiptText);
			root.setMinHeight(300);
			Scene s = new Scene(root, 300, 300);
			stage.setScene(s);
			stage.show();

		});

		grid.add(getReceiptButton, 1, 6);
		return grid;
	}

	public static void main(String[] args) {
		launch(args);
	}


}

