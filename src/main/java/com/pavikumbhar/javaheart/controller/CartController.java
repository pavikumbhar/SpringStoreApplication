package com.pavikumbhar.javaheart.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pavikumbhar.javaheart.bean.Cart;
import com.pavikumbhar.javaheart.model.Customer;
import com.pavikumbhar.javaheart.model.Product;
import com.pavikumbhar.javaheart.service.ProductService;
import com.pavikumbhar.javaheart.service.PurchaseService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author pavikumbhar
 *
 */
@Slf4j
@Controller
@AllArgsConstructor
public class CartController {

	private ProductService productService;

	private Cart cart;

	private PurchaseService purchaseService;

	@RequestMapping(value = "cart/add/{productId}")
	public String addToCart(@PathVariable("productId") Long productId, @RequestHeader("referer") String referedFrom) {
		Product product = productService.findProduct(productId);
		cart.addProduct(product, 1);
		log.debug("Adding product to cart " + product);
		return "redirect:" + referedFrom;
	}

	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String showCart(Model model) {
		model.addAttribute(cart);
		return "cart";
	}

	@RequestMapping(value = "/cart/placeOrder", method = RequestMethod.POST)
	public String placeOrder(HttpSession session, RedirectAttributes redirectAttributes) {
		if (cart.getContents().isEmpty()) {
			redirectAttributes.addFlashAttribute("cartMessage", "Cart empty. Please add products to the cart.");
			return "redirect:/cart";
		} else {
			Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
			purchaseService.savePurchase(cart.getContents(), loggedInUser);
			redirectAttributes.addFlashAttribute("cartMessage", "Order placed. Total cost: " + cart.getTotalCost());
			cart.clearCart();

			return "redirect:/cart";
		}
	}

}
