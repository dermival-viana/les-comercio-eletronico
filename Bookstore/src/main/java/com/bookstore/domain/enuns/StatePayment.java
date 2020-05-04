package com.bookstore.domain.enuns;

public enum StatePayment {
	
	PENDENTE(1, "Pending"),
	QUITADO(2, "Paid"),
	CANCELADO(3, "Canceled");
	
	private long code;
	private String description;
	
	private StatePayment(long code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public long getCode() {
		return code;
	}
	
	public String getDescription () {
		return description;
	}
	
	public static StatePayment toEnum(Long code) {
		
		if (code == null) {
			return null;
		}
		
		for (StatePayment x : StatePayment.values()) {
			if (code.equals(x.getCode())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválid: " + code);
	}

}
