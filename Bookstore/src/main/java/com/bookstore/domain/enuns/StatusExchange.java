package com.bookstore.domain.enuns;

public enum StatusExchange {

	 
        TROCA_EM_ANALISE("TROCA EM ANALISE",1),
	    TROCA_AUTORIZADA("TROCA AUTORIZADA",2),	    
	    EM_TROCA("EM TROCA",3),	    
	    TROCA_NAO_AUTORIZADA("TROCA NÃO AUTORIZADA",4);
	   

	    private long code;
	    private String description;

	    StatusExchange(String description, long code){
	        this.description = description;
	        this.code= code;
	    }

	    public long getCode(){
	        return this.code;
	    }

	    public String getDescription(){
	        return this.description;
	    }
	
}
