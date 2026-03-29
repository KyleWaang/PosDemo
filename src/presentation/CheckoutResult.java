package presentation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CheckoutResult {
    private BigDecimal totalAmount;
    private BigDecimal discount;
    private BigDecimal finalAmount;
    
    private List<String> originalAmounts = new ArrayList<>();
    private List<String> messages = new ArrayList<>();
    
    public BigDecimal getTotalAmount() {
    	return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
    	this.totalAmount = totalAmount;
    }
    
    public BigDecimal getDiscount() {
    	return discount;
    }
    
    public void setDiscount(BigDecimal discount) {
    	this.discount = discount;
    }
    
    public BigDecimal getFinalAmount() {
    	return finalAmount;
    }
    
    public void setFinalAmount(BigDecimal finalAmount) {
    	this.finalAmount = finalAmount;
    }
    
    public List<String> getOriginalAmounts() {
    	return originalAmounts;
    }
    
    public List<String> getMessages() {
    	return messages;
    }
}