package domain.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import common.DiscountType;
import domain.Cart;
import domain.CartItem;
import domain.DiscountDetail;
import domain.Promotion;
import domain.PromotionGroup;

public class PromotionAllocator {

    public void allocate(Cart cart, PromotionGroup group) {

        List<CartItem> items = group.getItems();
        Promotion promotion = group.getPromotion();
        BigDecimal discount = promotion.getDiscount();

        BigDecimal total = items.stream()
    		.map(CartItem::getBaseSubtotal)
    	    .reduce(BigDecimal.ZERO, BigDecimal::add);

    	BigDecimal allocatedSum = BigDecimal.ZERO;

        for (int i = 0; i < items.size(); i++) {

            CartItem ci = items.get(i);
            BigDecimal allocated;

            BigDecimal subtotal = ci.getBaseSubtotal();

            if (i == items.size() - 1) {
                allocated = discount.subtract(allocatedSum);
            } else {
                allocated = subtotal
                    .multiply(discount)
                    .divide(total, 0, RoundingMode.HALF_UP);

                allocatedSum = allocatedSum.add(allocated);
            }

            ci.addDiscount(new DiscountDetail(
                DiscountType.PROMOTION,
                allocated,
                promotion.getActivityCode(),
                promotion.getActivityName()
            ));
        }
    }
}