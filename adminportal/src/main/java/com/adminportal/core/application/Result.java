package com.adminportal.core.application;



/**
 * 
 */
/**
 * @author marco
 *
 */





import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.adminportal.domain.DomainEntity;


/**
 * Classe Utilizada pela Fachada para Retornar o Objeto solicitado
 * 
 */

@Component
public class Result extends EntityApplication {

	private String msg;
	private List<? extends DomainEntity> entities = new ArrayList<>();
	private DomainEntity entity;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<? extends DomainEntity> getEntities() {
		return entities;
	}
	public void setEntities(List<? extends DomainEntity> entities) {
		this.entities = entities;
	}
	public DomainEntity getEntity() {
		return entity;
	}
	public void setEntity(DomainEntity entity) {
		this.entity = entity;
	}
	

}