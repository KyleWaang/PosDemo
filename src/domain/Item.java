package domain;

import java.math.BigDecimal;

import common.Category;

public class Item {
	private final String itemCode;
	private final String itemName;
	private final Category itemCategory;
	private final BigDecimal itemPrice;
    
    public Item(String itemCode, String itemName, Category itemCategory, BigDecimal itemPrice) {
    	this.itemCode = itemCode;
    	this.itemName = itemName;
    	this.itemCategory = itemCategory;
    	this.itemPrice = itemPrice;
	}
    
    public String getItemCode() {
    	return itemCode;
    }
    
    public String getItemName() {
    	return itemName;
    }
    
    public Category getItemCategory() {
    	return itemCategory;
    }
    
    public BigDecimal getItemPrice() {
    	return itemPrice;
    }
}