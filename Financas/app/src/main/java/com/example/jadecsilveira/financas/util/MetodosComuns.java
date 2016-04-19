package com.example.jadecsilveira.financas.util;

/**
 * Created by jadecsilveira on 19/04/2016.
 */
public class MetodosComuns {

    public static boolean isNotNull(Object objeto){
        return null!=objeto;
    }
    public static String convertToDouble(Object valor){
        if(valor.toString().contains(".")){
            return valor.toString().replace(".", ",");
        }else{
            return valor.toString()+",00";
        }
    }
}
