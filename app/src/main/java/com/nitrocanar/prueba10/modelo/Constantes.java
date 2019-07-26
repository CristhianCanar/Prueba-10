package com.nitrocanar.prueba10.modelo;

public class Constantes {
    static public   String NOMBRE_BD="global";
    static public int VERSION = 1;

    static private String NOMBRE_TABLA = "DATOS";
    static private String COLUMNA_1 = "DAT_AUDIO";

    static public String CREACION_TABLA = ("create table "+NOMBRE_TABLA+" ("+"DAT_ID PRIMARY KEY AUTOINCREMENT, "+COLUMNA_1+" TEXT"+" )");
}
