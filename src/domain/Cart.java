package domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;
    
    public Cart() {
    	this.items = new ArrayList<CartItem>();
    }
    
    public List<CartItem> getItems() {
    	return items;
    }
    
    public void addItem(CartItem item) {
        items.add(item);
    }
    
    public BigDecimal getOriginalTotal() {
        return items.stream()
		    .map(CartItem::getOriginalSubtotal)
		    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}