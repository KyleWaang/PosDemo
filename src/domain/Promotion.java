package domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import common.Category;

public class Promotion {
    private String activityCode;
    private String activityName;
    private BigDecimal threshold;
    private BigDecimal discount;
    private Set<Category> categoryGroup;
    private LocalDate startDate;
    private LocalDate endDate;
    
    public Promotion(String activityCode, String activityName, BigDecimal threshold, BigDecimal discount, Set<Category> categoryGroup, LocalDate startDate, LocalDate endDate) {
    	this.activityCode = activityCode;
    	this.activityName = activityName;
    	this.threshold = threshold;
    	this.discount = discount;
    	this.startDate = startDate;
    	this.endDate = endDate;
    	this.categoryGroup = categoryGroup;
    }
    
    public String getActivityCode() {
    	return activityCode;
    }
    
    public String getActivityName() {
    	return activityName;
    }
    
    public BigDecimal getThreshold() {
    	return threshold;
    }
    
    public BigDecimal getDiscount() {
    	return discount;
    }

    public boolean isValid(Category category, LocalDate date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate) && category != null && categoryGroup.contains(category);
    }
}