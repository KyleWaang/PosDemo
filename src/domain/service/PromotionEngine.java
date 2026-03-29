package domain.service;

import java.math.BigDecimal;

import common.Category;
import common.DiscountType;
import domain.Cart;
import domain.CartItem;
import domain.DiscountDetail;
import domain.PromotionGroup;

public class PromotionEngine {

    private static final BigDecimal MEMBER_RATE = new BigDecimal("0.95");

    public void applyMemberDiscount(Cart cart) {

        for (CartItem ci : cart.getItems()) {

            if (ci.getItem().getItemCategory() == Category.WINE) {
                BigDecimal original = ci.getOriginalSubtotal();
                BigDecimal discounted = original.multiply(MEMBER_RATE);
                BigDecimal discount = original.subtract(discounted);
                
                ci.setMemberSubtotal(discounted);
                ci.addDiscount(new DiscountDetail(
                    DiscountType.MEMBER,
                    discount,
                    null,
                    "會員95折"
                ));
            }
        }
    }

    public boolean isPromotionEligible(PromotionGroup group) {
        BigDecimal total = group.getItems().stream()
            .map(CartItem::getBaseSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return total.compareTo(group.getPromotion().getThreshold()) >= 0;
    }
}