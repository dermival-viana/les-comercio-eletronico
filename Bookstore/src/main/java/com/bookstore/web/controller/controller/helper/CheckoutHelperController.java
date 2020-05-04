package com.bookstore.web.controller.controller.helper;


import java.math.BigDecimal;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.bookstore.core.repository.CartItemRepository;
import com.bookstore.core.repository.ShoppingCartRepository;
import com.bookstore.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookstore.core.IFacade;
import com.bookstore.core.aplication.Result;
import com.bookstore.domain.enuns.StatusOrder;


@Component
public class CheckoutHelperController {

    @Autowired
    private Result result;

    @Autowired
    private IFacade facade;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress) {
        shippingAddress.setShippingAddressName(userShipping.getUserShippingName());
        shippingAddress.setShippingAddressStreet1(userShipping.getUserShippingStreet1());
        shippingAddress.setShippingAddressStreet2(userShipping.getUserShippingStreet2());
        shippingAddress.setShippingAddressCity(userShipping.getUserShippingCity());
        shippingAddress.setShippingAddressState(userShipping.getUserShippingState());
        shippingAddress.setShippingAddressCountry(userShipping.getUserShippingCountry());
        shippingAddress.setShippingAddressZipcode(userShipping.getUserShippingZipcode());

        return shippingAddress;
    }

    public Payment setByUserPayment(UserPayment userPayment, Payment payment) {
        payment.setType(userPayment.getType());
        payment.setHolderName(userPayment.getHolderName());
        payment.setCardNumber(userPayment.getCardNumber());
        payment.setExpiryMonth(userPayment.getExpiryMonth());
        payment.setPaymentCoupon(userPayment.getPayment().getPaymentCoupon());
        payment.setPaymentTicket(userPayment.getPayment().getPaymentTicket());
        payment.setExpiryYear(userPayment.getExpiryYear());
        payment.setCvc(userPayment.getCvc());

        return payment;
    }

    public BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress) {
        billingAddress.setBillingAddressName(userBilling.getUserBillingName());
        billingAddress.setBillingAddressStreet1(userBilling.getUserBillingStreet1());
        billingAddress.setBillingAddressStreet2(userBilling.getUserBillingStreet2());
        billingAddress.setBillingAddressCity(userBilling.getUserBillingCity());
        billingAddress.setBillingAddressState(userBilling.getUserBillingState());
        billingAddress.setBillingAddressCountry(userBilling.getUserBillingCountry());
        billingAddress.setBillingAddressZipcode(userBilling.getUserBillingZipcode());

        return billingAddress;
    }

    @SuppressWarnings("unchecked")
    public synchronized Order createOrder(ShoppingCart shoppingCart,
                                          ShippingAddress shippingAddress,
                                          BillingAddress billingAddress,
                                          Payment payment,
                                          String shippingMethod,
                                          User user, ExchangeCoupon exchangeCoupon,
                                          PromotionalCoupon promotionalCoupon) throws Exception {
        Order order = new Order();
        order.setBillingAddress(billingAddress);
        order.setStatusOrder(StatusOrder.EM_PROCESSAMENTO);
        order.setPayment(payment);
        order.setShippingAddress(shippingAddress);
        order.setShippingMethod(shippingMethod);


        List<CartItem> cartItemList = (List<CartItem>) facade.findAll(new CartItem()).getEntities();
        cartItemList = cartItemList.stream().filter(cti -> cti.getShoppingCart() == shoppingCart).collect(Collectors.toList());

        for (CartItem cartItem : cartItemList) {
            Book book = cartItem.getBook();
            cartItem.setOrder(order);
            book.setInStockNumber(book.getInStockNumber() - cartItem.getQty());
        }

        BigDecimal shoppingCartValue = shoppingCart.getGrandTotal().multiply(new BigDecimal(0.06));
        BigDecimal orderTotalWithShoppingCart = shoppingCart.getGrandTotal().add(shoppingCartValue);
        BigDecimal orderTotalWithCoupon = orderTotalWithShoppingCart;

        if (promotionalCoupon != null)
            orderTotalWithCoupon = orderTotalWithCoupon.subtract(promotionalCoupon.getValue());


        order.setCartItemList(cartItemList);
        order.setOrderDate(Calendar.getInstance().getTime());
        order.setOrderTotal(orderTotalWithCoupon);
        shippingAddress.setOrder(order);
        billingAddress.setOrder(order);
        payment.setOrder(order);
        order.setUser(user);
        result = facade.save(order);

        if(result.getMsg() != null){
            throw new Exception(result.getMsg());
        }

        order = (Order) result.getEntity();
        return order;
    }

    public void clearShoppingCart(ShoppingCart shoppingCart) {
        List<CartItem> cartItemList = this.cartItemRepository.findByShoppingCart(shoppingCart);

        for (CartItem cartItem : cartItemList) {
            cartItem.setShoppingCart(null);
            cartItemRepository.save(cartItem);
        }

        shoppingCart.setGrandTotal(new BigDecimal(0));

        shoppingCartRepository.save(shoppingCart);
    }
}
