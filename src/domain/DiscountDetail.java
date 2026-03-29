package domain;

import java.math.BigDecimal;

import common.DiscountType;

public class DiscountDetail {
	private DiscountType type;
    private BigDecimal amount;
    
    private String sourceId;   // activityCode, null for MEMBER
    private String sourceName; // activityName
    
    public DiscountDetail(DiscountType type, BigDecimal amount, String sourceId, String sourceName) {
        this.type = type;
        this.amount = amount;
        this.sourceId = sourceId;
        this.sourceName = sourceName;
    }
    
    public DiscountType getType() {
    	return type;
    }

    public BigDecimal getAmount() {
    	return amount;
    }
    
    public String getSourceId() {
    	return sourceId;
    }
    
    public String getSourceName() {
    	return sourceName;
    }
}