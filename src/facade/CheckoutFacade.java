package facade;

import java.time.LocalDate;
import java.util.Map;

import domain.Cart;
import domain.PromotionGroup;
import domain.service.PromotionAllocator;
import domain.service.PromotionEngine;
import domain.service.PromotionService;
import presentation.CheckoutResult;
import presentation.CheckoutResultBuilder;

public class CheckoutFacade {

    private PromotionEngine engine = new PromotionEngine();
    private PromotionAllocator allocator = new PromotionAllocator();
    private PromotionService promotionService = PromotionService.getInstance();

    public CheckoutResult calculate(boolean isMember, Cart cart, LocalDate date) {
        // A. 依據各項商品判斷會員身份與優惠
        // 若商品為「酒類」則適用會員95折
    	// 其他品項不享會員折扣
        if (isMember) {
            engine.applyMemberDiscount(cart);
        }
        
        // B. 帶入活動清單，並判斷有效活動
  		// 依活動主檔篩選「當日有效」的活動(2025/10/30)
  		// 根據活動型態(折扣/折讓)套用至符合的商品大類
  		// 每項活動僅可使用一次(不可重複折抵)
        Map<String, PromotionGroup> groups =
            promotionService.buildPromotionGroups(cart, date);

        for (PromotionGroup group : groups.values()) {

            if (engine.isPromotionEligible(group)) {
                allocator.allocate(cart, group);
            }
        }

        // C. 攤提折抵與最終金額輸出
		// 若多項商品參與同一活動，需依各品項金額比例攤提折抵金額
		// 折扣後金額需四捨五入至整數
		// 輸出內容須包含：
		// 原價金額
		// 折扣或折讓金額
		// 攤提後最終應付金額
        return new CheckoutResultBuilder()
            .build(cart, groups);
    }
}