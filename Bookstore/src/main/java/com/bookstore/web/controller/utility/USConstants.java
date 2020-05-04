package com.bookstore.web.controller.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class USConstants {
	
	public final static String US = "US";
	
	public final static Map<String, String> mapOfUSStates = new HashMap<String, String>() {
		{
                    put("AC", "Acre");
                    put("AL", "Alagoas");
                    put("AM", "Amazonas");
                    put("BA", "Bahia");
                    put("CE", "Ceará");
                    put("DF", "Distrito Federal");
                    put("ES", "Espírito Santo");
                    put("GO", "Goiás");
                    put("MA", "Maranhão");
                    put("MT", "Mato Grosso");
                    put("MS", "Mato Grosso do Sul");
                    put("MG", "Minas Gerais");
                    put("PA", "Pará");
                    put("PB", "Paraíba");
                    put("PR", "Paraná");
                    put("PE", "Pernambuco");
                    put("PI", "Piauí");
                    put("RJ", "Rio de Janeiro");
                    put("RN", "Rio Grande do Norte");
                    put("RS", "Rio Grande do Sul");
                    put("RO", "Rondônia");
                    put("RR", "Roraima");
                    put("SC", "Santa Catarina");
                    put("SE", "Sergipe");
                    put("SP", "Sao Paulo");
                    put("TO", "Tocantins");

        }
	};
	
	public final static List<String> listOfUSStatesCode = new ArrayList<>(mapOfUSStates.keySet());
	public final static List<String> listOfUSStatesName = new ArrayList<>(mapOfUSStates.values());

}
