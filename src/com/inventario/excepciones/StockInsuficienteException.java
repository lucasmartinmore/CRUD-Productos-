package com.inventario.excepciones;
//Excepcion personalizada que permite dar un mensaje a travez de un mensaje pasado por consola
public class StockInsuficienteException extends Exception {
    public StockInsuficienteException(String mensaje) {
    super(mensaje);
    }
}
