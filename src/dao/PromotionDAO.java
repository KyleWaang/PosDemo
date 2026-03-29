package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import common.Category;
import common.Utils;
import domain.Promotion;

public class PromotionDAO {
	
	public List<Promotion> getAllPromotions() throws Exception {
        Connection conn = DBConnection.getConnection();

        String sql = """
            SELECT * FROM crm_promo_rebate_h;
        """;

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Promotion> list = new ArrayList<>();

        while (rs.next()) {
        	Set<Category> categoryGroup = Utils.parseSet(rs.getString("item_discount_group"))
        			.stream()
        		    .map(code -> {
        		        try {
        		            return Category.fromCode(code);
        		        } catch (Exception e) {
        		            return null;
        		        }
        		    })
        		    .filter(Objects::nonNull)
        		    .collect(Collectors.toSet());
            Promotion p = new Promotion(
            	rs.getString("activity_code"),
            	rs.getString("activity_name"),
				rs.getBigDecimal("meet_critera_amt_g1"),
				rs.getBigDecimal("award_amt_g1"),
				categoryGroup,
				rs.getObject("start_activity_date", LocalDate.class),
			    rs.getObject("end_activity_date", LocalDate.class)
            );
            list.add(p);
        }

        return list;
    }
}