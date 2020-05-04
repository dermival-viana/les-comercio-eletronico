package com.bookstore.web.controller.controller;


import java.math.BigDecimal;
import java.security.Principal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.bookstore.core.aplication.Result;
import com.bookstore.core.repository.ExchangeCouponRepository;
import com.bookstore.core.repository.PromotionalCouponRepository;
import com.bookstore.domain.*;

import com.bookstore.web.controller.controller.helper.CheckoutHelperController;
import com.bookstore.web.controller.utility.MailConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookstore.core.IFacade;
import com.bookstore.core.service.UserService;
import com.bookstore.web.controller.utility.USConstants;

@Controller
public class CheckoutController {

    private ShippingAddress shippingAddress = new ShippingAddress();
    private BillingAddress billingAddress = new BillingAddress();
    private Payment payment = new Payment();
    private Payment payment2 = new Payment();
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConstructor mailConstructor;

    @Autowired
    private UserService userService;

    @Autowired
    private CheckoutHelperController checkoutHelperController;

    @Autowired
    private IFacade facade;


    @SuppressWarnings("unchecked")
    @RequestMapping("/checkout")
    public String checkout(@RequestParam("id") Long cartId,
                           @RequestParam(value = "missingRequiredField", required = false) boolean missingRequiredField, Model model,
                           Principal principal) throws SQLException, ClassNotFoundException {
        User user = userService.findByUsername(principal.getName());

        if (cartId != user.getShoppingCart().getId()) {
            return "badRequestPage";
        }

        List<CartItem> cartItemList = (List<CartItem>) facade.findAll(new CartItem()).getEntities();
        cartItemList = cartItemList.stream().filter(cti -> cti.getShoppingCart() == user.getShoppingCart()).collect(Collectors.toList());

        //if exchange coupons is Active?
        List<ExchangeCoupon> exchangeCoupons = (List<ExchangeCoupon>) this.facade.findAll(new ExchangeCoupon()).getEntities();
        //if promotional coupons is Active?
        List<PromotionalCoupon> promotionalCoupons = (List<PromotionalCoupon>) this.facade.findAll(new PromotionalCoupon()).getEntities();


        if (cartItemList.size() == 0) {
            model.addAttribute("emptyCart", true);
            return "forward:/shoppintCart/cart";
        }

        for (CartItem cartItem : cartItemList) {
            if (cartItem.getBook().getInStockNumber() < cartItem.getQty()) {
                model.addAttribute("notEnoughStock", true);
                return "forward:/shoppingCart/cart";
            }
        }

        List<UserShipping> userShippingList = user.getUserShippingList();
        List<UserPayment> userPaymentList = user.getUserPaymentList();

        model.addAttribute("userShippingList", userShippingList);
        model.addAttribute("userPaymentList", userPaymentList);


        if (userPaymentList.size() == 0) {
            model.addAttribute("emptyPaymentList", true);
        } else {
            model.addAttribute("emptyPaymentList", false);
        }

        if (userShippingList.size() == 0) {
            model.addAttribute("emptyShippingList", true);
        } else {
            model.addAttribute("emptyShippingList", false);
        }

        ShoppingCart shoppingCart = user.getShoppingCart();

        for (UserShipping userShipping : userShippingList) {
            if (userShipping.isUserShippingDefault()) {
                checkoutHelperController.setByUserShipping(userShipping, shippingAddress);
            }
        }

        for (UserPayment userPayment : userPaymentList) {
            if (userPayment.isDefaultPayment()) {
                checkoutHelperController.setByUserPayment(userPayment, payment);
                checkoutHelperController.setByUserBilling(userPayment.getUserBilling(), billingAddress);
            }
        }


        model.addAttribute("shippingAddress", shippingAddress);
        model.addAttribute("payment", payment);
        model.addAttribute("payment2", payment2);
        model.addAttribute("billingAddress", billingAddress);
        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("shoppingCart", user.getShoppingCart());
        model.addAttribute("exchangeCoupons", exchangeCoupons);
        model.addAttribute("promotionalCoupons", promotionalCoupons);

        List<String> stateList = USConstants.listOfUSStatesCode;
        Collections.sort(stateList);
        model.addAttribute("stateList", stateList);

        model.addAttribute("classActiveShipping", true);

        if (missingRequiredField) {
            model.addAttribute("missingRequiredField", true);
        }

        return "checkout";

    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String checkoutPost(@ModelAttribute("shippingAddress") ShippingAddress shippingAddress,
                               @ModelAttribute("billingAddress") BillingAddress billingAddress,
                               @ModelAttribute("payment") Payment payment,
                               @ModelAttribute("billingSameAsShipping") String billingSameAsShipping,
                               @ModelAttribute("exchangeCouponCode") String exchangeCouponCode,
                               @ModelAttribute("promotionalCouponCode") String promotionalCouponCode,
                               @ModelAttribute("shippingMethod") String shippingMethod,
                               Principal principal, Model model) throws Exception {

        ShoppingCart shoppingCart = userService.findByUsername(principal.getName()).getShoppingCart();


        List<CartItem> cartItemList = (List<CartItem>) facade.findAll(new CartItem()).getEntities();
        cartItemList = cartItemList.stream().filter(cti -> cti.getShoppingCart() == shoppingCart).collect(Collectors.toList());
        model.addAttribute("cartItemList", cartItemList);

        if (billingSameAsShipping.equals("true")) {
            billingAddress.setBillingAddressName(shippingAddress.getShippingAddressName());
            billingAddress.setBillingAddressStreet1(shippingAddress.getShippingAddressStreet1());
            billingAddress.setBillingAddressStreet2(shippingAddress.getShippingAddressStreet2());
            billingAddress.setBillingAddressCity(shippingAddress.getShippingAddressCity());
            billingAddress.setBillingAddressState(shippingAddress.getShippingAddressState());
            billingAddress.setBillingAddressCountry(shippingAddress.getShippingAddressCountry());
            billingAddress.setBillingAddressZipcode(shippingAddress.getShippingAddressZipcode());
        }

        if (shippingAddress.getShippingAddressStreet1().isEmpty()
                || shippingAddress.getShippingAddressCity().isEmpty()
                || shippingAddress.getShippingAddressState().isEmpty()
                || shippingAddress.getShippingAddressName().isEmpty()
                || shippingAddress.getShippingAddressZipcode().isEmpty()
                || payment.getCardNumber().isEmpty()
                || payment.getCvc() == 0 || billingAddress.getBillingAddressStreet1().isEmpty()
                || billingAddress.getBillingAddressCity().isEmpty()
                || billingAddress.getBillingAddressState().isEmpty()
                || billingAddress.getBillingAddressName().isEmpty()
                || billingAddress.getBillingAddressZipcode().isEmpty())
            return "redirect:/checkout?id=" + shoppingCart.getId() + "&missingRequiredField=true";

        User user = userService.findByUsername(principal.getName());
        ExchangeCoupon exchangeCoupon = null;
        if (!exchangeCouponCode.isEmpty()) {
            ExchangeCoupon codExchangeCoupon = new ExchangeCoupon();
            codExchangeCoupon.setCode(exchangeCouponCode);
            Result resultExchangeCoupon = this.facade.findById(codExchangeCoupon);

            if (resultExchangeCoupon.getEntity() != null) {
                exchangeCoupon = (ExchangeCoupon) resultExchangeCoupon.getEntity();
                BigDecimal orderValue = shoppingCart.getGrandTotal().subtract(exchangeCoupon.getValue());
                if (orderValue.compareTo(BigDecimal.ZERO) == -1) {
                    BigDecimal exchangeCouponValue = orderValue.multiply(new BigDecimal(-1));
                    exchangeCoupon.setValue(exchangeCouponValue);
                    shoppingCart.setGrandTotal(BigDecimal.ZERO);
                } else {
                    exchangeCoupon.setActive(false);
                }
                this.facade.save(exchangeCoupon);
            }
        }

        PromotionalCoupon promotionalCoupon = null;
        if (!promotionalCouponCode.isEmpty()) {
            //Optional<PromotionalCoupon> optionalPromotionalCoupon = this.promotionalCouponRepository.findByCode(promotionalCouponCode);
            PromotionalCoupon codPromotionalCoupon = new PromotionalCoupon();
            codPromotionalCoupon.setCode(promotionalCouponCode);
            Result resultPromotionalCoupon = this.facade.findById(codPromotionalCoupon);

            if (resultPromotionalCoupon.getEntity() != null) {
                promotionalCoupon = (PromotionalCoupon) resultPromotionalCoupon.getEntity();
                promotionalCoupon.setActive(false);
                this.facade.save(promotionalCoupon);
            }
        }

        Order order = checkoutHelperController.createOrder(shoppingCart, shippingAddress, billingAddress, payment, shippingMethod, user, exchangeCoupon, promotionalCoupon);

        //mailSender.send(mailConstructor.constructOrderConfirmationEmail(user, order, Locale.ENGLISH));

        checkoutHelperController.clearShoppingCart(shoppingCart);

        LocalDate today = LocalDate.now();
        LocalDate estimatedDeliveryDate;

        if (shippingMethod.equals("groundShipping")) {
            estimatedDeliveryDate = today.plusDays(5);
        } else {
            estimatedDeliveryDate = today.plusDays(3);
        }

        model.addAttribute("estimatedDeliveryDate", estimatedDeliveryDate);
        model.addAttribute("orderTotal", order.getOrderTotal());

        return "orderSubmittedPage";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/setShippingAddress")
    public String setShippingAddress(@RequestParam("userShippingId") Long userShippingId, Principal principal,
                                     Model model) throws SQLException, ClassNotFoundException {
        User user = userService.findByUsername(principal.getName());

        List<UserShipping> userShippings = (List<UserShipping>) facade.findAll(new UserShipping()).getEntities();
        userShippings = userShippings.stream().filter(usS -> usS.getId() == userShippingId).collect(Collectors.toList());
        UserShipping userShipping = userShippings.get(0);

        if (userShipping.getUser().getId() != user.getId()) {
            return "badRequestPage";
        } else {
            checkoutHelperController.setByUserShipping(userShipping, shippingAddress);

            List<CartItem> cartItemList = (List<CartItem>) facade.findAll(new CartItem()).getEntities();
            cartItemList = cartItemList.stream().filter(cti -> cti.getShoppingCart() == user.getShoppingCart()).collect(Collectors.toList());
            model.addAttribute("shippingAddress", shippingAddress);
            model.addAttribute("payment", payment);
            model.addAttribute("billingAddress", billingAddress);
            model.addAttribute("cartItemList", cartItemList);
            model.addAttribute("shoppingCart", user.getShoppingCart());

            List<String> stateList = USConstants.listOfUSStatesCode;
            Collections.sort(stateList);
            model.addAttribute("stateList", stateList);

            List<UserShipping> userShippingList = user.getUserShippingList();
            List<UserPayment> userPaymentList = user.getUserPaymentList();

            model.addAttribute("userShippingList", userShippingList);
            model.addAttribute("userPaymentList", userPaymentList);

            model.addAttribute("shippingAddress", shippingAddress);

            model.addAttribute("classActiveShipping", true);

            if (userPaymentList.size() == 0) {
                model.addAttribute("emptyPaymentList", true);
            } else {
                model.addAttribute("emptyPaymentList", false);
            }

            model.addAttribute("emptyShippingList", false);

            return "checkout";
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/setPaymentMethod")
    public String setPaymentMethod(@RequestParam("userPaymentId") Long userPaymentId, Principal principal,
                                   Model model) throws SQLException, ClassNotFoundException {
        User user = userService.findByUsername(principal.getName());
        UserPayment userPayment = new UserPayment();
        userPayment.setId(userPaymentId);
        Result resultUserPayment = this.facade.findById(userPayment);
        if (resultUserPayment != null) {
            model.addAttribute("Msg", resultUserPayment.getMsg());
        }
        userPayment = (UserPayment) resultUserPayment.getEntity();
        UserBilling userBilling = userPayment.getUserBilling();

        if (userPayment.getUser().getId() != user.getId()) {
            return "badRequestPage";
        } else {
            checkoutHelperController.setByUserPayment(userPayment, payment);


            List<CartItem> cartItemList = (List<CartItem>) facade.findAll(new CartItem()).getEntities();
            cartItemList = cartItemList.stream().filter(cti -> cti.getShoppingCart() == user.getShoppingCart()).collect(Collectors.toList());
            checkoutHelperController.setByUserBilling(userBilling, billingAddress);

            model.addAttribute("shippingAddress", shippingAddress);
            model.addAttribute("payment", payment);
            model.addAttribute("billingAddress", billingAddress);
            model.addAttribute("cartItemList", cartItemList);
            model.addAttribute("shoppingCart", user.getShoppingCart());

            List<String> stateList = USConstants.listOfUSStatesCode;
            Collections.sort(stateList);
            model.addAttribute("stateList", stateList);

            List<UserShipping> userShippingList = user.getUserShippingList();
            List<UserPayment> userPaymentList = user.getUserPaymentList();

            model.addAttribute("userShippingList", userShippingList);
            model.addAttribute("userPaymentList", userPaymentList);

            model.addAttribute("shippingAddress", shippingAddress);

            model.addAttribute("classActivePayment", true);

            model.addAttribute("emptyPaymentList", false);

            if (userShippingList.size() == 0) {
                model.addAttribute("emptyShippingList", true);
            } else {
                model.addAttribute("emptyShippingList", false);
            }

            return "checkout";
        }
    }

}
