package com.mycompany.controller.cart;

import org.broadleafcommerce.core.order.service.exception.ItemNotFoundException;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;
import org.broadleafcommerce.core.web.controller.cart.BroadleafCartController;
import org.broadleafcommerce.core.web.order.CartState;
import org.broadleafcommerce.core.web.order.model.AddToCartItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController extends BroadleafCartController {
	
	@RequestMapping("")
	public String cart(HttpServletRequest request, HttpServletResponse response, Model model) throws PricingException {
		return super.cart(request, response, model);
	}
	
	/*
	 * The Heat Clnic does not show the cart when a product is added. Instead, when the product is added via an AJAX
	 * POST that requests JSON, we only need to return a few attributes to update the state of the page. The most
	 * efficient way to do this is to call the regular add controller method, but instead return a map that contains
	 * the necessary attributes. By using the @ResposeBody tag, Spring will automatically use Jackson to convert the
	 * returned object into JSON for easy processing via JavaScript.
	 */
	@RequestMapping(value = "/add", produces = "application/json")
	public @ResponseBody Map<String, Object> addJson(HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute("addToCartItem") AddToCartItem addToCartItem) throws IOException, PricingException {
		super.add(request, response, model, addToCartItem);
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("productId", addToCartItem.getProductId());
		responseMap.put("productName", catalogService.findProductById(addToCartItem.getProductId()).getName());
		responseMap.put("quantityAdded", addToCartItem.getQuantity());
		responseMap.put("cartItemCount", String.valueOf(CartState.getCart(request).getItemCount()));
		
		return responseMap;
	}
	
	@RequestMapping(value = "/add", produces = "text/html")
	public String add(HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute("addToCartItem") AddToCartItem addToCartItem) throws IOException, PricingException {
		return super.add(request, response, model, addToCartItem);
	}
	
	@RequestMapping("/updateQuantity")
	public String updateQuantity(HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute("addToCartItem") AddToCartItem addToCartItem) throws IOException, PricingException, ItemNotFoundException {
		return super.updateQuantity(request, response, model, addToCartItem);
	}
	
	@RequestMapping("/remove")
	public String remove(HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute("addToCartItem") AddToCartItem addToCartItem) throws IOException, PricingException, ItemNotFoundException {
		return super.remove(request, response, model, addToCartItem);
	}
	
	@RequestMapping("/empty")
	public String empty(HttpServletRequest request, HttpServletResponse response, Model model) throws PricingException {
		return super.empty(request, response, model);
	}
	
}