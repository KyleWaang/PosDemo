package repository;

import java.util.Map;
import java.util.stream.Collectors;

import dao.PromotionDAO;
import domain.Promotion;

public class PromotionRepository {
	PromotionDAO dao = new PromotionDAO();
	public Map<String, Promotion> loadAll() throws Exception {
        return dao.getAllPromotions().stream()
            .collect(Collectors.toMap(
                Promotion::getActivityCode,
                p -> p,
                (existing, replacement) -> existing
            ));
    }
}
