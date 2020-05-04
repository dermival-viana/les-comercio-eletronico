package com.adminportal.domain.enun;

public enum StatusOrder {

	    TRANSITO("EM TRANSITO",1),
	    ENTREGUE("ENTREGUE",2),
	    CANCELADO("CANCELADO",3),
	    DEVOLVIDO("DEVOLVIDO",4),
	    EM_PROCESSAMENTO("EM PROCESSAMENTO",5);

	    private long code;
	    private String description;

	    StatusOrder(String description, long code){
	        this.description = description;
	        this.code = code;
	    }

	    public long getCode(){
	        return this.code;
	    }

	    public String getDescription(){
	        return this.description;
	    }

		
	
}
