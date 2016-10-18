package com.example.jadecsilveira.financas.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
            String valorAntesVirgula = valor.toString().substring(0, valor.toString().indexOf("."));
            String valorDepoisVirgula = valor.toString().substring(valor.toString().indexOf(".")+1, valor.toString().length());
            if(valorDepoisVirgula.length()>2){
                valorDepoisVirgula = valorDepoisVirgula.substring(0, 2);
            }
            String valorFormatado = valorAntesVirgula + "," +valorDepoisVirgula;
            return valorFormatado;
        }else if(!valor.toString().contains(",")){
            return valor.toString()+",00";
        }else{
            return valor.toString().replace(",", ".");
        }
    }
    public static String convertToDateSQL(String data){
        data = data.replace("/", "");
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

    public static String convertDateToStringSQL(Date data){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(data);
    }

    public static String convertDateToStringView(Date data){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data);
    }
}
