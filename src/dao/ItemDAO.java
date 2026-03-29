package dao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

import common.Category;
import domain.Item;

public class ItemDAO {

    public List<Item> getAllItems() throws Exception {
        Connection conn = DBConnection.getConnection();
        String sql = """
        		SELECT 
				    i.item_code,
				    i.item_c_name,
				    i.category01,
				    p.unit_price AS current_price,
				    p.begin_date
				FROM im_item i
				LEFT JOIN im_item_price p
				    ON i.item_code = p.item_code
				    AND p.begin_date = (
				        SELECT MAX(p2.begin_date)
				        FROM im_item_price p2
				        WHERE p2.item_code = i.item_code
				    );
        """;
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Item> list = new ArrayList<>();

        while (rs.next()) {
        	// 避免未設值 -> null
        	BigDecimal price = (BigDecimal) rs.getObject("current_price");
     
            Item item = new Item(
	    		rs.getString("item_code"),
	    		rs.getString("item_c_name"),
	    		Category.fromCode(rs.getString("category01")),
	    		price
	    	);
            list.add(item);
        }

        return list;
    }
}