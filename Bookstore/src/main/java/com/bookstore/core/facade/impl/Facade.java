package com.bookstore.core.facade.impl;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;


import com.bookstore.core.business.stock.ValidateReturnStock;
import com.bookstore.core.dao.impl.*;
import com.bookstore.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.core.IDAO;
import com.bookstore.core.IFacade;
import com.bookstore.core.IStrategy;
import com.bookstore.core.aplication.Result;


@Service
public class Facade implements IFacade {

    /**
     * Injeção das classes de DAOs
     **/


    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private CategoryDAO categoryDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private BookToCartItemDAO bookCartItemDAO;
    @Autowired
    private CartItemDAO cartItemDAO;
    @Autowired
    private ShoppingCartDAO shoppingCartDAO;
    @Autowired
    private UserPaymentDAO userPaymentDAO;
    @Autowired
    private UserShippingDAO userShippingDAO;
    @Autowired
    private ExchangeDAO exchangeDAO;
    @Autowired
    private ExchangeCouponDAO exchangeCouponDAO;
    @Autowired
    private PromotionalCouponDAO promotionalCouponDAO;
    @Autowired
    private ValidateReturnStock vReturnStock;



    private Map<String, IDAO> daos;

    private Map<String, Map<String, List<IStrategy>>> rns;

    private Result result;

    @PostConstruct
    public void initMap() {

        daos = new HashMap<String, IDAO>();
        rns = new HashMap<String, Map<String, List<IStrategy>>>();

        daos.put(Category.class.getName(), categoryDAO);
        daos.put(Book.class.getName(), bookDAO);
        daos.put(User.class.getName(), userDAO);
        daos.put(BookToCartItem.class.getName(), bookCartItemDAO);
        daos.put(Order.class.getName(), orderDAO);
        daos.put(CartItem.class.getName(), cartItemDAO);
        daos.put(ShoppingCart.class.getName(), shoppingCartDAO);
        daos.put(UserPayment.class.getName(), userPaymentDAO);
        daos.put(UserShipping.class.getName(), userShippingDAO);
        daos.put(Exchange.class.getName(), exchangeDAO);
        daos.put(ExchangeCoupon.class.getName(), exchangeCouponDAO);
        daos.put(PromotionalCoupon.class.getName(), promotionalCouponDAO);


        // --------------------- Hash Validate return stock ------------------------------//
        // Creating coupon RNS
        List<IStrategy> rnsBookInStock = new ArrayList<IStrategy>();
        rnsBookInStock.add(vReturnStock);
        Map<String, List<IStrategy>> mapReturnInStock = new HashMap<String, List<IStrategy>>();
        mapReturnInStock.put("UPDATE", rnsBookInStock);
        rns.put(Book.class.getName(), mapReturnInStock);

    }

    @Override
    public Result save(DomainEntity entity) throws SQLException, ClassNotFoundException {
        result = new Result();
        String nmClasse = entity.getClass().getName();
        String msg = executeRules(entity, "SAVE");
        if(msg == null){
            IDAO dao = daos.get(nmClasse);
            try {
                dao.save(entity);
                result.setEntity(entity);
            } catch (SQLException e) {
                e.printStackTrace();
                result.setMsg("Not foi impossible realization of the registre!");
            }
        }else{
            result.setMsg(msg);
        }
        return result;
    }

    @Override
    public Result update(DomainEntity entity) throws SQLException, ClassNotFoundException {
        result = new Result();
        String nmClasse = entity.getClass().getName();
        String msg = executeRules(entity, "UPDATE");
        if(msg == null){
            IDAO dao = daos.get(nmClasse);
            try {
                dao.update(entity);
                result.setEntity(entity);
            } catch (SQLException e) {
                e.printStackTrace();
                result.setMsg("Not possible realization of the update!");
            }
        }else{
            result.setMsg(msg);
        }
        return result;
    }

    @Override
    public Result delete(DomainEntity entity) throws SQLException, ClassNotFoundException {
        result = new Result();
        String nmClasse = entity.getClass().getName();
        String msg = executeRules(entity, "DELETE");
        if(msg == null){
            IDAO dao = daos.get(nmClasse);
            try {
                dao.delete(entity);
                result.setEntity(entity);
            } catch (SQLException e) {
                e.printStackTrace();
                result.setMsg("Not possible realization of the delete!");
            }
        }else{
            result.setMsg(msg);
        }
        return result;
    }

    @Override
    public Result findAll(DomainEntity entity) throws SQLException, ClassNotFoundException {
        result = new Result();
        String nmClasse = entity.getClass().getName();

        String msg = executeRules(entity, "SEARCH");

        if(msg == null){
            IDAO dao = daos.get(nmClasse);
            List<DomainEntity> entidades = new ArrayList<DomainEntity>();
            if (nmClasse.equals(UserPayment.class.getName())) {
                entity = userPaymentDAO.findById(entity);
            }else if (nmClasse.equals(UserShipping.class.getName())) {
                entity = userShippingDAO.findById(entity);
            }else if (nmClasse.equals(ExchangeCoupon.class.getName())) {
                result.setEntities( exchangeCouponDAO.findAllByActiveTrue(entity));
                return result;
            }else if (nmClasse.equals(PromotionalCoupon.class.getName())) {
                result.setEntities(promotionalCouponDAO.findAllByActiveTrue(entity));
                return result;
            }
            try {
                if(entity.getClass().equals(Book.class)&& entity.getId()!= null){
                    result.setEntities(bookDAO.findAll(entity));
                }
                else if (nmClasse.equals(ShoppingCart.class.getName())) {
                    result.setEntities (cartItemDAO.findByShoppingCart(entity));

                }else {
                    result.setEntities(dao.findAll(entity));
                }
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }else{
            result.setMsg(msg);

        }

        return result;

    }

    @Override
    public Result findById(DomainEntity entity) throws SQLException, ClassNotFoundException {
        result = new Result();
        String nmClasse = entity.getClass().getName();

        String msg =executeRules(entity, "FIND");

        if(msg == null){
            IDAO dao = daos.get(nmClasse);
            try {
                if (nmClasse.equals(ExchangeCoupon.class.getName())) {
                    entity = exchangeCouponDAO.findByCode(entity);
                    result.setEntity(entity);
                    return result;
                }
                else if(nmClasse.equals(PromotionalCoupon.class.getName())) {
                    entity = promotionalCouponDAO.findByCode(entity);
                    result.setEntity(entity);
                    return result;
                }
                else if(nmClasse.equals(BookToCartItem.class.getName())) {
                    entity = bookCartItemDAO.findByCartItem(entity);
                    result.setEntity(entity);
                    return result;
                }
                else {

                    result.setEntity(dao.findById(entity));
                }
            } catch (SQLException | ClassNotFoundException e) {

                e.printStackTrace();
            }
        }else{
            result.setMsg(msg);

        }

        return result;

    }
    private String executeRules(DomainEntity entity, String operacao) throws SQLException, ClassNotFoundException {
        String nmClass = entity.getClass().getName();
        StringBuilder msg = new StringBuilder();
        Map<String, List<IStrategy>> rulesOperation = rns.get(nmClass);
        if (rulesOperation != null) {
            List<IStrategy> rules = rulesOperation.get(operacao);
            if (rules != null) {
                for (IStrategy s : rules) {
                    String m = s.process(entity);
                    if (m.trim().length() != 0) {
                        msg.append(m);
                        msg.append("\n");
                    }
                }
            }
        }
        if (msg.length() > 0)
            return msg.toString();
        else
            return null;
    }

}