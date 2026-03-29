package presentation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import domain.Cart;
import domain.CartItem;
import domain.Promotion;
import domain.PromotionGroup;

public class CheckoutResultBuilder {

    public CheckoutResult build(
        Cart cart,
        Map<String, PromotionGroup> groups
    ) {

        CheckoutResult result = new CheckoutResult();

        // 原價
        for (CartItem ci : cart.getItems()) {
            result.getOriginalAmounts().add(
                "  - " + ci.getItem().getItemName() + ": " +
                ci.getOriginalSubtotal().setScale(0, RoundingMode.HALF_UP)
            );
        }

        // 活動 + 攤提
        for (PromotionGroup group : groups.values()) {

            Promotion promotion = group.getPromotion();
            List<CartItem> items = group.getItems();

            // 商品名稱
            String itemNames = items.stream()
                .map(ci -> ci.getItem().getItemName())
                .collect(Collectors.joining(", "));

            // 該活動總折扣
            BigDecimal subDiscount = items.stream()
                .map(ci -> ci.getDiscountByPromotion(promotion.getActivityCode()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            // 活動摘要
            result.getMessages().add(
                "商品: " + itemNames +
                ", 根據活動: " + promotion.getActivityName() +
                " 折扣或折讓金額: " +
                subDiscount.setScale(0, RoundingMode.HALF_UP)
            );

            // 攤提明細
            if (items.size() > 1) {
                for (CartItem ci : items) {

                    BigDecimal allocated =
                        ci.getDiscountByPromotion(promotion.getActivityCode());

                    if (allocated.compareTo(BigDecimal.ZERO) > 0) {
                        result.getMessages().add(
                            "  - " + ci.getItem().getItemName() +
                            " 攤提: " +
                            allocated.setScale(0, RoundingMode.HALF_UP)
                        );
                    }
                }
            }
        }

        // 原始總金額
        BigDecimal total = cart.getOriginalTotal();

        BigDecimal discount = cart.getItems().stream()
            .map(CartItem::getTotalDiscount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        result.setTotalAmount(total.setScale(0, RoundingMode.HALF_UP));
        result.setDiscount(discount.setScale(0, RoundingMode.HALF_UP));
        result.setFinalAmount(
            total.subtract(discount).setScale(0, RoundingMode.HALF_UP)
        );

        return result;
    }
}