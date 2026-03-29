package domain.service;

import domain.Item;
import repository.ItemRepository;

import java.util.*;

public class ItemService {

    private static ItemService instance;

    // key: itemCode, value: item
    private Map<String, Item> itemMap = new HashMap<String, Item>();

    private ItemService() {
        try {
            loadItems();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized ItemService getInstance() {
        if (instance == null) {
            instance = new ItemService();
        }
        return instance;
    }

    private void loadItems() throws Exception {
    	ItemRepository ir = new ItemRepository();
        List<Item> items = ir.loadAll();

        for (Item item : items) {
            itemMap.put(item.getItemCode(), item);
        }
    }

    public Item getItem(String itemCode) {
        return itemMap.get(itemCode);
    }
}