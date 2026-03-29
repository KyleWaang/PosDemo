package domain.service;

import domain.Cart;
import domain.CartItem;
import domain.Item;

public class CartService {
    public void addItemToCart(Cart cart, String itemCode, int qty) {
    	ItemService itemService = ItemService.getInstance();

        Item item = itemService.getItem(itemCode);

        if (item == null) {
            throw new RuntimeException("商品不存在");
        }

        cart.addItem(new CartItem(item, qty));
    }
}