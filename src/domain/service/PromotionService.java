package domain.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Category;
import domain.Cart;
import domain.CartItem;
import domain.Promotion;
import domain.PromotionGroup;
import repository.PromotionRepository;

public class PromotionService {
	private static PromotionService instance;

    private Map<String, Promotion> promotions = new HashMap<>();

    private PromotionService() {
        try {
        	loadAllPromotions();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized PromotionService getInstance() {
        if (instance == null) {
            instance = new PromotionService();
        }
        return instance;
    }
    
    private void loadAllPromotions() throws Exception {
    	PromotionRepository pr = new PromotionRepository();
    	promotions = pr.loadAll();
    }
    
    // key: activityCode, value: PromotionGroup
    public Map<String, PromotionGroup> buildPromotionGroups(Cart cart, LocalDate date) {
    	 Map<String, PromotionGroup> promotionGroupMap = new HashMap<>();

         for (CartItem cartItem : cart.getItems()) {
             List<Promotion> validPromotions =
                 filterValidPromotions(cartItem.getItem().getItemCategory(), date);

             for (Promotion promotion : validPromotions) {

             	PromotionGroup group = promotionGroupMap.computeIfAbsent(
         		    promotion.getActivityCode(),
         		    k -> new PromotionGroup(promotion)
         		);

                 group.getItems().add(cartItem);
             }
         }
         return promotionGroupMap;
    }

    // 找可用活動
    public List<Promotion> filterValidPromotions(Category category, LocalDate now) {
    	return promotions.values().stream()
                .filter(p -> p.isValid(category, now))
                .toList();
    }
}