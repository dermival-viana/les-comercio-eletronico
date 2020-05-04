package com.adminportal.core.facade.impl;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.adminportal.core.business.book.ValidateDataBook;
import com.adminportal.core.business.book.ValidateSellingValue;
import com.adminportal.core.business.category.ValidateDataCategory;
import com.adminportal.core.business.exchangeCoupon.ValidateGenerateExchangeCoupon;
import com.adminportal.core.business.promotionalCoupon.ValidatePromotionalCoupon;
import com.adminportal.core.business.stock.ValidateStock;
import com.adminportal.core.dao.*;
import com.adminportal.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminportal.core.IDAO;
import com.adminportal.core.IFacade;
import com.adminportal.core.IStrategy;
import com.adminportal.core.application.Result;


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
    private ExchangeDAO exchangeDAO;
    @Autowired
    private ExchangeCouponDAO exchangeCouponDAO;

    @Autowired
    private PromotionalCouponDAO promotionalCouponDAO;
    @Autowired
    private CartItemDAO cartItemDAO;

    @Autowired
    private PriceGroupDAO priceGroupDAO;

    @Autowired
    private  ValidateGenerateExchangeCoupon vGenerateExchangeCoupon;
    @Autowired
    private ValidatePromotionalCoupon vPromotionalCoupon;
    @Autowired
    private ValidateStock vReturnStock;

    private Map<String, IDAO> daos;


    private Map<String, Map<String, List<IStrategy>>> rns;

    private Result result;

    @PostConstruct
    public void initMap() {
        /* Intânciando o Map de DAOS */
        daos = new HashMap<String, IDAO>();
        /* Intânciando o Map de Regras de Neg�cio */
        rns = new HashMap<String, Map<String, List<IStrategy>>>();


        /* Adicionando cada dao no MAP indexando pelo nome da classe */
        daos.put(Category.class.getName(), categoryDAO);
        daos.put(Book.class.getName(), bookDAO);
        daos.put(User.class.getName(), userDAO);
        daos.put(Category.class.getName(), categoryDAO);
        daos.put(Order.class.getName(), orderDAO);
        daos.put(Exchange.class.getName(), exchangeDAO);
        daos.put(ExchangeCoupon.class.getName(), exchangeCouponDAO);
        daos.put(PromotionalCoupon.class.getName(), promotionalCouponDAO);
        daos.put(CartItem.class.getName(), cartItemDAO);
        daos.put(PriceGroup.class.getName(), priceGroupDAO);

        // --------------------- Hash Book ------------------------------//

        // criando RNS do Livro e salvando a lista
        List<IStrategy> rnsBookSave = new ArrayList<IStrategy>();
        rnsBookSave.add(new ValidateDataBook());
        rnsBookSave.add(new ValidateSellingValue());
        Map<String, List<IStrategy>> mapBook = new HashMap<String, List<IStrategy>>();
        mapBook.put("SAVE", rnsBookSave);
        rns.put(Book.class.getName(), mapBook);

        // --------------------- Hash Category ------------------------------//

        List<IStrategy> rnsCategorySave = new ArrayList<IStrategy>();
        ValidateDataCategory vDataCategory = new ValidateDataCategory();
        rnsCategorySave.add(vDataCategory);

        // Criando RNS das categoria para o categoryUpdate
        List<IStrategy> rnsCategoryUpdate = new ArrayList<IStrategy>();
        rnsCategoryUpdate.add(vDataCategory);

        //Criando a operacao de Map dos Livros, categoria operacao de RNS.
        Map<String, List<IStrategy>> mapCategory = new HashMap<String, List<IStrategy>>();

        // Adicionando RNS Lista para cada operacao com o livro
        mapCategory.put("SAVE", rnsCategorySave);
        //mapCategory.put("UPDATE", rnsCategoryUpdate);

        rns.put(Category.class.getName(), mapCategory);


        // --------------------- Hash Troca de Cupom ------------------------------//

		List<IStrategy> rnsGenerateCouponUpdate = new ArrayList<IStrategy>();
		rnsGenerateCouponUpdate.add(vGenerateExchangeCoupon);
		Map<String, List<IStrategy>> mapGenerateExchangeCoupon = new HashMap<String, List<IStrategy>>();
		mapGenerateExchangeCoupon.put("UPDATE", rnsGenerateCouponUpdate);
		rns.put(Exchange.class.getName(), mapGenerateExchangeCoupon);

        // --------------------- Hash Validando returno estoque ------------------------------//

        List<IStrategy> rnsBookInStockUpdate = new ArrayList<IStrategy>();
        rnsBookInStockUpdate.add(vReturnStock);
        Map<String, List<IStrategy>> mapReturnInStock = new HashMap<String, List<IStrategy>>();
        mapReturnInStock.put("UPDATE", rnsBookInStockUpdate);
        rns.put(Book.class.getName(), mapReturnInStock);


        // --------------------- Hash Cupom Promocional ------------------------------//
        // Creating coupon RNS
        List<IStrategy> rnsPromotionalCouponSave = new ArrayList<IStrategy>();
        rnsPromotionalCouponSave.add(vPromotionalCoupon);
        Map<String, List<IStrategy>> mapPromotionalCoupon = new HashMap<String, List<IStrategy>>();
        mapPromotionalCoupon.put("SAVE", rnsPromotionalCouponSave);
        rns.put(PromotionalCoupon.class.getName(), mapPromotionalCoupon);



    }

    @Override
    public Result save(DomainEntity entity) {
        result = new Result();
        String nmClass = entity.getClass().getName();
        String msg = null;
        try {
            msg = executeRules(entity, "SAVE");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(msg == null){
            IDAO dao = daos.get(nmClass);
            try {

                dao.save(entity);
                List<DomainEntity> entidades = new ArrayList<DomainEntity>();
                entidades.add(0, entity);
                result.setEntities(entidades);
            } catch (SQLException e) {
                e.printStackTrace();
                result.setMsg("Não foi possivel realizar o registro!");
            }
        }else{
            result.setMsg(msg);
        }
        return result;
    }

    @Override
    public Result update(DomainEntity entity) throws SQLException, ClassNotFoundException {
        result = new Result();
        String nmClass = entity.getClass().getName();
        String msg = executeRules(entity, "UPDATE");
        if(msg == null){
            IDAO dao = daos.get(nmClass);
            try {
                dao.update(entity);
                List<DomainEntity> entities = new ArrayList<DomainEntity>();
                entities.add(0, entity);
                result.setEntities(entities);
            } catch (SQLException e) {
                e.printStackTrace();
                result.setMsg("Não foi possivel realizar o update!");
            }
        }else{
            result.setMsg(msg);
        }
        return result;
    }

    @Override
    public Result delete(DomainEntity entity) throws SQLException, ClassNotFoundException {
        result = new Result();
        String nmClass = entity.getClass().getName();
        String msg = executeRules(entity, "DELETE");
        if(msg == null){
            IDAO dao = daos.get(nmClass);
            try {
                dao.delete(entity);
                List<DomainEntity> entidades = new ArrayList<DomainEntity>();
                entidades.add(entity);
                result.setEntities(entidades);
            } catch (SQLException e) {
                e.printStackTrace();
                result.setMsg("Not possible realization of the registre!");
            }
        }else{
            result.setMsg(msg);
        }
        return result;
    }

    @Override
    public Result findAll(DomainEntity entity) throws SQLException, ClassNotFoundException {
        result = new Result();
        String nmClass = entity.getClass().getName();
        String msg = executeRules(entity, "CONSULT");
        if (msg == null) {
            IDAO dao = daos.get(nmClass);
            try {
                result.setEntities(dao.findAll(entity));
            } catch (SQLException e) {

                e.printStackTrace();
            }
        } else {
            result.setMsg(msg);
        }
        return result;
    }

    @Override
    public Result findById(DomainEntity entity) throws SQLException, ClassNotFoundException {
        result = new Result();
        String nmClass = entity.getClass().getName();

        String msg = executeRules(entity, "SEARCH");

        if (msg == null) {
            IDAO dao = daos.get(nmClass);

            try {

                result.setEntity(dao.findById(entity));
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
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