package domain;

import java.util.ArrayList;
import java.util.List;

// 多項商品參與同一個活動
public class PromotionGroup {
    private Promotion promotion;
    private List<CartItem> items = new ArrayList<>();

    public PromotionGroup(Promotion promotion) {
        this.promotion = promotion;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public List<CartItem> getItems() {
        return items;
    }
}