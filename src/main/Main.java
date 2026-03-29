package main;

import java.time.LocalDate;
import java.util.Scanner;

import domain.Cart;
import domain.Item;
import domain.service.CartService;
import domain.service.ItemService;
import facade.CheckoutFacade;
import presentation.CheckoutResult;

public class Main {

	public static void main(String[] args) {
//		Cart cart = buildCartTest();
		Cart cart = buildCartFromInput();
		
		CheckoutFacade checkoutFacade = new CheckoutFacade();

    	CheckoutResult result = checkoutFacade.calculate(true, cart, LocalDate.of(2025, 10, 30));
    	printResult(result);
	}
	
	public static Cart buildCartTest() {
		CartService cartService = new CartService();

		Cart cart = new Cart();
 
		cartService.addItemToCart(cart, "p001", 3);
		cartService.addItemToCart(cart, "p002", 2);
		cartService.addItemToCart(cart, "p003", 3);
 
		return cart;
	}
	
	public static Cart buildCartFromInput() {
		Scanner scanner = new Scanner(System.in);
		CartService cartService = new CartService();
		Cart cart = new Cart();

		ItemService itemService = ItemService.getInstance();

		while (true) {
			System.out.print("請輸入商品代碼 (輸入 exit 結束): ");
			String itemCode = scanner.nextLine();

			if (itemCode.equals("exit")) break;

			System.out.print("請輸入數量: ");
			int quantity = Integer.parseInt(scanner.nextLine());

		     
			Item item = itemService.getItem(itemCode);

			if (item == null) {
				System.out.println("商品不存在");
	        	continue;
	        }

			cartService.addItemToCart(cart, itemCode, quantity);
		}
		scanner.close();
		return cart;
	}
	 
	public static void printResult(CheckoutResult result) {
		System.out.println("原價總金額: " + result.getTotalAmount());
		for (String amount : result.getOriginalAmounts()) {
			System.out.println(amount);
		}
		System.out.println();
		for (String msg : result.getMessages()) {
			System.out.println(msg);
		}
		System.out.println();
		System.out.println("總折扣或折讓金額: " + result.getDiscount());
		System.out.println();
		System.out.println("最終應付金額: " + result.getFinalAmount());
	}
}