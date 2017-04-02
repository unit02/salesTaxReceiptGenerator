package application;

import application.ItemTypeEnum.Type;

public class Item {
 double Price;
 Integer Quantity;
 String ItemName;
 Boolean IsImported;
 ItemTypeEnum.Type Type;
 double TaxAmount;


public Item(Double price, Integer quan, String name, boolean isImported,ItemTypeEnum.Type type){
	 Price = price;
	 Quantity = quan;
	 ItemName = name;
	 IsImported = isImported;
	 Type = type;
}


public Boolean getIsImported() {
	return IsImported;
}



public void setIsImported(Boolean isImported) {
	IsImported = isImported;
}



public double getTaxAmount() {
	return TaxAmount;
}



public void setTaxAmount(double taxAmount) {
	TaxAmount = taxAmount;
}


public ItemTypeEnum.Type getType() {
	return Type;
}

public void setType(ItemTypeEnum.Type type) {
	Type = type;
}

public double getPrice() {
	return Price;
}
public void setPrice(double price) {
	Price = price;
}
public Integer getQuantity() {
	return Quantity;
}
public void setQuantity(Integer quantity) {
	Quantity = quantity;
}
public String getItemName() {
	return ItemName;
}
public void setItemName(String itemName) {
	ItemName = itemName;
}

	
}


