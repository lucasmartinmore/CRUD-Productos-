package com.inventario.excepciones;
//Excepcion personalizada que permite dar un mensaje a travez de un mensaje pasado por consola
public class ProductoNoEncontradoException extends Exception{
    public ProductoNoEncontradoException(String mensaje) {
    super(mensaje);
    }
}
