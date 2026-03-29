package repository;

import java.util.List;

import dao.ItemDAO;
import domain.Item;

public class ItemRepository {
	public List<Item> loadAll() throws Exception {
		ItemDAO dao = new ItemDAO();
    	return dao.getAllItems();
    }
}
