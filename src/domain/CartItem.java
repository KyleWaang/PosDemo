package domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import common.DiscountType;

public class CartItem {
    private Item item;
    private int quantity;
    private BigDecimal memberSubtotal;
    private List<DiscountDetail> discounts = new ArrayList<>();
    
    
    public CartItem(Item item, int quantity) {
    	this.item = item;
    	this.quantity = quantity;
	}
    
    public Item getItem() {
    	return item;
    }
    
    public int getQuantity() {
    	return quantity;
    }
    
    // 原始小計
    public BigDecimal getOriginalSubtotal() {
        return item.getItemPrice()
        	.multiply(BigDecimal.valueOf(quantity));
    }
    
    // 基準價(若是會員使用會員價，否則原價)
    public BigDecimal getBaseSubtotal() {
        return memberSubtotal != null ? memberSubtotal : getOriginalSubtotal();
    }
    
    public void setMemberSubtotal(BigDecimal memberSubtotal) {
    	this.memberSubtotal = memberSubtotal;
    }
    
    public void addDiscount(DiscountDetail discount) {
    	discounts.add(discount);
    }
    
    public List<DiscountDetail> getDiscounts() {
        return discounts;
    }
    
    public BigDecimal getDiscountByPromotion(String promotionId) {
        return discounts.stream()
            .filter(d -> d.getType() == DiscountType.PROMOTION)
            .filter(d -> d.getSourceId().equals(promotionId))
            .map(DiscountDetail::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public BigDecimal getTotalDiscount() {
    	return discounts.stream()
	        .map(DiscountDetail::getAmount)
	        .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    // 最終價格
    public BigDecimal getFinalSubtotal() {
        return getOriginalSubtotal().subtract(getTotalDiscount());
    }
}