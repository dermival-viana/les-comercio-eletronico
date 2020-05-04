package com.bookstore.web.controller.controller;

import java.security.Principal;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import com.bookstore.core.aplication.Result;
import com.bookstore.core.repository.OrderRepository;
import com.bookstore.domain.*;
import com.bookstore.web.controller.utility.MailConstructor;
import com.bookstore.web.controller.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookstore.core.IFacade;
import com.bookstore.core.service.UserService;
import com.bookstore.core.service.impl.UserSecurityService;
import com.bookstore.domain.security.PasswordResetToken;
import com.bookstore.domain.security.Role;
import com.bookstore.domain.security.UserRole;
import com.bookstore.web.controller.utility.USConstants;

@Controller
public class HomeController {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailConstructor mailConstructor;

	@Autowired
	private UserService userService;

	@Autowired
	private UserSecurityService userSecurityService;

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private IFacade facade;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("classActiveLogin", true);
		return "myAccount";
	}

	@RequestMapping("/hours")
	public String hours() {
		return "hours";
	}

	@RequestMapping("/faq")
	public String faq() {
		return "faq";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/bookshelf")
	public String bookshelf(Model model, Principal principal) throws SQLException, ClassNotFoundException {
		if (principal != null) {
			String username = principal.getName();
			User user = userService.findByUsername(username);
			model.addAttribute("user", user);
		}
		List<Category> categoryList = (List<Category>) facade.findAll(new Category()).getEntities();
		model.addAttribute("categoryList", categoryList);

		List<Book> bookList = (List<Book>) facade.findAll(new Book()).getEntities();
		List<Book> bookS = new ArrayList<>();
		for(Book book : bookList) {
			if (book.isActive()) {
               bookS.add(book);
			}
		}
		model.addAttribute("bookList", bookS);
		model.addAttribute("activeAll", true);
		return "bookshelf";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/bookDetail")
	public String bookDetail(@PathParam("id") Long id, Model model, Principal principal) throws SQLException, ClassNotFoundException {
		if (principal != null) {
			String username = principal.getName();
			User user = userService.findByUsername(username);
			model.addAttribute("user", user);
		}
        Book book = new Book();
		book.setId(id);
		Result resultBook = this.facade.findById(book);
		if(resultBook.getMsg() != null){
			model.addAttribute("Msg", resultBook.getMsg());
		}
		model.addAttribute("book", resultBook.getEntity());

		List<Integer> qtyList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		model.addAttribute("qtyList", qtyList);
		model.addAttribute("qty", 1);

		return "bookDetail";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/forgetPassword")
	public String forgetPassword(HttpServletRequest request, @ModelAttribute("email") String email, Model model) throws SQLException, ClassNotFoundException {

		model.addAttribute("classActiveForgetPassword", true);
		User user = userService.findByEmail(email);
		if (user == null) {
			model.addAttribute("emailNotExist", true);
			return "myAccount";
		}

		String password = SecurityUtility.randomPassword();

		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);

		facade.save(user);

		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);

		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

		SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user,
				password);

		mailSender.send(newEmail);

		model.addAttribute("forgetPasswordEmailSent", "true");

		return "myAccount";
	}

	@RequestMapping("/myProfile")
	public String myProfile(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("orderList", user.getOrderList());
		model.addAttribute("exchangeList", user.getExchangeList());


		UserShipping userShipping = new UserShipping();
		model.addAttribute("userShipping", userShipping);

		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("listOfShippingAddresses", true);

		List<String> stateList = USConstants.listOfUSStatesCode;
		Collections.sort(stateList);
		model.addAttribute("stateList", stateList);
		model.addAttribute("classActiveEdit", true);

		return "myProfile";
	}

	@RequestMapping("/listOfCreditCards")
	public String listOfCreditCards(Model model, Principal principal, HttpServletRequest request) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("orderList", user.getOrderList());

		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);

		return "myProfile";
	}

	@RequestMapping("/listOfShippingAddresses")
	public String listOfShippingAddresses(Model model, Principal principal, HttpServletRequest request) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("orderList", user.getOrderList());

		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("listOfShippingAddresses", true);

		return "myProfile";
	}

	@RequestMapping("/addNewCreditCard")
	public String addNewCreditCard(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);

		model.addAttribute("addNewCreditCard", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);

		UserBilling userBilling = new UserBilling();
		UserPayment userPayment = new UserPayment();

		model.addAttribute("userBilling", userBilling);
		model.addAttribute("userPayment", userPayment);

		List<String> stateList = USConstants.listOfUSStatesCode;
		Collections.sort(stateList);
		model.addAttribute("stateList", stateList);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("orderList", user.getOrderList());

		return "myProfile";
	}

	@RequestMapping("/addNewShippingAddress")
	public String addNewShippingAddress(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);

		model.addAttribute("addNewShippingAddress", true);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("listOfCreditCards", true);

		UserShipping userShipping = new UserShipping();

		model.addAttribute("userShipping", userShipping);

		List<String> stateList = USConstants.listOfUSStatesCode;
		Collections.sort(stateList);
		model.addAttribute("stateList", stateList);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("orderList", user.getOrderList());

		return "myProfile";
	}

	@RequestMapping(value = "/addNewCreditCard", method = RequestMethod.POST)
	public String addNewCreditCard(@ModelAttribute("userPayment") UserPayment userPayment,
			@ModelAttribute("userBilling") UserBilling userBilling, Principal principal, Model model) throws SQLException, ClassNotFoundException {
		User user = userService.findByUsername(principal.getName());
		//userService.updateUserBilling(userBilling, userPayment, user);
		userPayment.setUser(user);
		userPayment.setUserBilling(userBilling);
		userPayment.setDefaultPayment(true);
		userBilling.setUserPayment(userPayment);
		user.getUserPaymentList().add(userPayment);
		facade.save(user);

		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);
		model.addAttribute("orderList", user.getOrderList());

		return "myProfile";
	}

	@RequestMapping(value = "/addNewShippingAddress", method = RequestMethod.POST)
	public String addNewShippingAddressPost(@ModelAttribute("userShipping") UserShipping userShipping,
			Principal principal, Model model) throws SQLException, ClassNotFoundException {
		User user = userService.findByUsername(principal.getName());
		//userService.updateUserShipping(userShipping, user);
		userShipping.setUser(user);
		userShipping.setUserShippingDefault(true);
		user.getUserShippingList().add(userShipping);
		facade.save(user);

		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("listOfShippingAddresses", true);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("orderList", user.getOrderList());

		return "myProfile";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/updateCreditCard")
	public String updateCreditCard(@ModelAttribute("id") Long creditCardId, Principal principal, Model model) throws SQLException, ClassNotFoundException {
		User user = userService.findByUsername(principal.getName());
		
		//UserPayment userPayment = userPaymentService.findById(creditCardId);
		UserPayment userPayment = new UserPayment();
		userPayment.setId(creditCardId);
		Result resultUserPayment = this.facade.findById(userPayment);
		if(resultUserPayment.getMsg() != null){
			model.addAttribute("Msg", resultUserPayment.getMsg());
		}
		userPayment = (UserPayment) resultUserPayment.getEntity();

		if (user.getId() != userPayment.getUser().getId()) {
			return "badRequestPage";
		} else {
			model.addAttribute("user", user);
			UserBilling userBilling = userPayment.getUserBilling();
			model.addAttribute("userPayment", userPayment);
			model.addAttribute("userBilling", userBilling);

			List<String> stateList = USConstants.listOfUSStatesCode;
			Collections.sort(stateList);
			model.addAttribute("stateList", stateList);

			model.addAttribute("addNewCreditCard", true);
			model.addAttribute("classActiveBilling", true);
			model.addAttribute("listOfShippingAddresses", true);

			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("userShippingList", user.getUserShippingList());
			model.addAttribute("orderList", user.getOrderList());

			return "myProfile";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/updateUserShipping")
	public String updateUserShipping(@ModelAttribute("id") Long shippingAddressId, Principal principal, Model model) throws SQLException, ClassNotFoundException {
		User user = userService.findByUsername(principal.getName());
		//UserShipping userShipping = userShippingService.findById(shippingAddressId);
		UserShipping userShipping = new UserShipping();
		userShipping.setId(shippingAddressId);
		Result resultUserShippin = this.facade.findById(userShipping);
		if(resultUserShippin.getMsg() != null){
			model.addAttribute("Msg",resultUserShippin.getMsg());
		}
		userShipping = (UserShipping) resultUserShippin.getEntity();
		
		if (user.getId() != userShipping.getUser().getId()) {
			return "badRequestPage";
		} else {
			model.addAttribute("user", user);

			model.addAttribute("userShipping", userShipping);

			List<String> stateList = USConstants.listOfUSStatesCode;
			Collections.sort(stateList);
			model.addAttribute("stateList", stateList);

			model.addAttribute("addNewShippingAddress", true);
			model.addAttribute("classActiveShipping", true);
			model.addAttribute("listOfCreditCards", true);

			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("userShippingList", user.getUserShippingList());
			model.addAttribute("orderList", user.getOrderList());

			return "myProfile";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/setDefaultPayment", method = RequestMethod.POST)
	public String setDefaultPayment(@ModelAttribute("defaultUserPaymentId") Long defaultPaymentId, Principal principal,
			Model model) throws SQLException, ClassNotFoundException {
		User user = userService.findByUsername(principal.getName());

         List<UserPayment> userPaymentList = (List<UserPayment>) facade.findAll(new UserPayment()).getEntities();
		
		for (UserPayment userPayment : userPaymentList) {
			if(userPayment.getId() == defaultPaymentId) {
				userPayment.setDefaultPayment(true);
				facade.save(userPayment);
			} else {
				userPayment.setDefaultPayment(false);
				facade.save(userPayment);
			}
		}

		model.addAttribute("user", user);
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);

		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("orderList", user.getOrderList());

		return "myProfile";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/setDefaultShippingAddress", method = RequestMethod.POST)
	public String setDefaultShippingAddress(@ModelAttribute("defaultShippingAddressId") Long defaultShippingId,
			Principal principal, Model model) throws SQLException, ClassNotFoundException {
		User user = userService.findByUsername(principal.getName());

        List<UserShipping> userShippingList = (List<UserShipping>) facade.findAll(new UserShipping()).getEntities();
		
		for (UserShipping userShipping : userShippingList) {
			if(userShipping.getId() == defaultShippingId) {
				userShipping.setUserShippingDefault(true);
				facade.save(userShipping);
			} else {
				userShipping.setUserShippingDefault(false);
				facade.save(userShipping);
			}
		}

		model.addAttribute("user", user);
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("listOfShippingAddresses", true);

		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("orderList", user.getOrderList());

		return "myProfile";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/removeCreditCard")
	public String removeCreditCard(@ModelAttribute("id") Long creditCardId, Principal principal, Model model) throws SQLException, ClassNotFoundException {
		User user = userService.findByUsername(principal.getName());
		//UserPayment userPayment = userPaymentService.findById(creditCardId);

		UserPayment userPayment = new UserPayment();
		userPayment.setId(creditCardId);
		Result resultUserPayment = this.facade.findById(userPayment);
		if(resultUserPayment.getMsg() != null){
			model.addAttribute("Msg", resultUserPayment.getMsg());
		}
		userPayment = (UserPayment) resultUserPayment.getEntity();

		if (user.getId() != userPayment.getUser().getId()) {
			return "badRequestPage";
		} else {
			model.addAttribute("user", user);
			
			facade.delete(userPayment);
		

			model.addAttribute("listOfCreditCards", true);
			model.addAttribute("classActiveBilling", true);
			model.addAttribute("listOfShippingAddresses", true);

			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("userShippingList", user.getUserShippingList());
			model.addAttribute("orderList", user.getOrderList());

			return "myProfile";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/removeUserShipping")
	public String removeUserShipping(@ModelAttribute("id") Long userShippingId, Principal principal, Model model) throws SQLException, ClassNotFoundException {
		User user = userService.findByUsername(principal.getName());
		//UserShipping userShipping = userShippingService.findById(userShippingId);
		UserShipping userShipping = new UserShipping();
		userShipping.setId(userShippingId);
		Result resultUserShippin = this.facade.findById(userShipping);
		if(resultUserShippin.getMsg() != null){
			model.addAttribute("Msg", resultUserShippin.getMsg());
		}
		userShipping = (UserShipping) resultUserShippin.getEntity();

		if (user.getId() != userShipping.getUser().getId()) {
			return "badRequestPage";
		} else {
			model.addAttribute("user", user);

			facade.delete(userShipping);

			model.addAttribute("listOfShippingAddresses", true);
			model.addAttribute("classActiveShipping", true);

			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("userShippingList", user.getUserShippingList());
			model.addAttribute("orderList", user.getOrderList());

			return "myProfile";
		}
	}

	@RequestMapping(value = "/newUser", method = RequestMethod.POST)
	public String newUserPost(
			HttpServletRequest request,
			@ModelAttribute("email") String userEmail,
			@ModelAttribute("username") String username,
			Model model) throws Exception {

		model.addAttribute("classActiveNewAccount", true);
		model.addAttribute("email", userEmail);
		model.addAttribute("username", username);

		if (userService.findByUsername(username) != null) {
			model.addAttribute("usernameExists", true);

			return "myAccount";
		}

		if (userService.findByEmail(userEmail) != null) {
			model.addAttribute("emailExists", true);

			return "myAccount";
		}

		User user = new User();
		user.setUsername(username);
		user.setEmail(userEmail);

		String password = SecurityUtility.randomPassword();

		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);

		Role role = new Role();
		role.setRoleId(1);
		role.setName("ROLE_USER");
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(user, role));
		userService.createUser(user, userRoles);

		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);

		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

		SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user,
				password);

		mailSender.send(email);

		model.addAttribute("emailSent", "true");
		model.addAttribute("orderList", user.getOrderList());

		return "myAccount";
	}

	@RequestMapping("/newUser")
	public String newUser(Locale locale, @RequestParam("token") String token, Model model) {
		PasswordResetToken passToken = userService.getPasswordResetToken(token);

		if (passToken == null) {
			String message = "Invalid Token.";
			model.addAttribute("message", message);
			return "redirect:/badRequest";
		}

		User user = passToken.getUser();
		String username = user.getUsername();

		UserDetails userDetails = userSecurityService.loadUserByUsername(username);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		model.addAttribute("user", user);

		model.addAttribute("classActiveEdit", true);

		return "myProfile";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
	public String updateUserInfo(@ModelAttribute("user") User user, @ModelAttribute("newPassword") String newPassword,
			Model model) throws Exception {
		User currentUser = userService.findById(user.getId());

		if (currentUser == null) {
			throw new Exception("User not found");
		}

		/* check email already exists */
		if (userService.findByEmail(user.getEmail()) != null) {
			if (userService.findByEmail(user.getEmail()).getId() != currentUser.getId()) {
				model.addAttribute("emailExists", true);
				return "myProfile";
			}
		}

		/* check username already exists */
		if (userService.findByUsername(user.getUsername()) != null) {
			if (userService.findByUsername(user.getUsername()).getId() != currentUser.getId()) {
				model.addAttribute("usernameExists", true);
				return "myProfile";
			}
		}

//		update password
		if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")) {
			BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
			String dbPassword = currentUser.getPassword();
			if (passwordEncoder.matches(user.getPassword(), dbPassword)) {
				currentUser.setPassword(passwordEncoder.encode(newPassword));
			} else {
				model.addAttribute("incorrectPassword", true);

				return "myProfile";
			}
		}

		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		currentUser.setUsername(user.getUsername());
		currentUser.setEmail(user.getEmail());

		facade.save(currentUser);

		model.addAttribute("updateSuccess", true);
		model.addAttribute("user", currentUser);
		model.addAttribute("classActiveEdit", true);

		model.addAttribute("listOfShippingAddresses", true);
		model.addAttribute("listOfCreditCards", true);

		UserDetails userDetails = userSecurityService.loadUserByUsername(currentUser.getUsername());

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);
		model.addAttribute("orderList", user.getOrderList());

		return "myProfile";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/orderDetail")
	public String orderDetail(@RequestParam("id") Long orderId, Principal principal, Model model) throws ClassNotFoundException, SQLException {
		User user = userService.findByUsername(principal.getName());
		Optional<Order> optionalOrder = this.orderRepository.findById(orderId);

		if(!optionalOrder.isPresent()){
			throw new ClassNotFoundException("Não foi possível localizar a order com id: " + orderId);
		}

		Order order = optionalOrder.get();

		if (order.getUser().getId() != user.getId()) {
			return "badRequestPage";
		} else {

			List<CartItem> cartItemList = (List<CartItem>) facade.findAll(new CartItem()).getEntities();
			cartItemList = cartItemList.stream().filter(cti -> cti.getOrder()==order).collect(Collectors.toList());

			List<Exchange> exchangeList = (List<Exchange>) facade.findAll(new Exchange()).getEntities();

			model.addAttribute("exchangeList", exchangeList);
			model.addAttribute("cartItemList", cartItemList);
			model.addAttribute("user", user);
			model.addAttribute("order", order);


			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("userShippingList", user.getUserShippingList());
			model.addAttribute("orderList", user.getOrderList());
			model.addAttribute("exchangeList", user.getExchangeList());


			UserShipping userShipping = new UserShipping();
			model.addAttribute("userShipping", userShipping);

			List<String> stateList = USConstants.listOfUSStatesCode;
			Collections.sort(stateList);
			model.addAttribute("stateList", stateList);

			model.addAttribute("listOfShippingAddresses", true);
			model.addAttribute("classActiveOrders", true);
			model.addAttribute("classActiveExchanges", true);
			model.addAttribute("listOfCreditCards", true);
			model.addAttribute("displayOrderDetail", true);

			return "myProfile";
		}
	}

}
