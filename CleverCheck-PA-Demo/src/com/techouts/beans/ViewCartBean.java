package com.techouts.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.techouts.pojo.CartItems;
import com.techouts.services.CartService;

@ManagedBean
public class ViewCartBean {

	@ManagedProperty("#{cartService}")
	private CartService cartService;
	@ManagedProperty("#{homeBean}")
	private HomeBean homeBean;

	private static final Logger LOG = Logger.getLogger(ViewCartBean.class);

	private List<CartItems> cartItems;

	private int totalItems;

	private int totalQuantity;

	@PostConstruct
	public void init() {
		cartItems = new ArrayList<CartItems>();
		cartItems.addAll(cartService.getcatitems());
		totalItems = cartItems.size();
		totalQuantity = 0;
		if (totalItems != 0) {
			for (CartItems c : cartItems) {
				totalQuantity += c.getQuantity();
			}
		} else {
			totalQuantity = 0;
		}
		LOG.info(totalQuantity);
	}

	public void removeFromCart(CartItems item) {
		if (cartService.isAddedSKUtocart()) {
			addMessage("SKU Quantity are being Calculated.....");
			return;
		}
		cartService.removeFromCart(item);
		LOG.info(item);
		cartItems.remove(item);
		init();
		homeBean.setCartItems(cartService.getCartCount());
		homeBean.loadbufamily();
	}

	public void addMessage(String summary) {
		LOG.info(summary);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("", summary));

	}

	public void emptyCart() {
		if (cartService.isAddedSKUConfigTocart()) {
			addMessage("SKU Quantity are being Calculated.....");
			return;
		}
		LOG.info(cartService);
		cartService.removeAllFromCart();
		init();
		homeBean.setCartItems(cartService.getCartCount());
		homeBean.loadbufamily();
	}

	public List<CartItems> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItems> cartItems) {
		this.cartItems = cartItems;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public CartService getCartService() {
		return cartService;
	}

	public void setCartService(CartService cartService) {
		this.cartService = cartService;
	}

	public HomeBean getHomeBean() {
		return homeBean;
	}

	public void setHomeBean(HomeBean homeBean) {
		this.homeBean = homeBean;
	}

}
