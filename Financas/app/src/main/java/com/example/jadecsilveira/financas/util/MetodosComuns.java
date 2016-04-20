package com.example.jadecsilveira.financas.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public static String convertToDateSQL(String data){
        data = data.replace(".", "");
        String dia = data.substring(0, 2);
        String mes = data.substring(2, 4);
        String ano = data.substring(4, 8);
        return ano+"-"+mes+"-"+dia;
    }
    public static Date convertToDate(String data){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        data = data.replace("-", "");
        String ano = data.substring(0, 4);
        String mes = data.substring(4, 6);
        String dia = data.substring(6, 8);

        try {
            Date dataFormatada = new Date(format.parse(dia+"/"+mes +"/"+ano).getTime());
            return dataFormatada;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
