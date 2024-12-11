package com.inventario.productos;

import java.io.Serializable;

public abstract class Producto implements Serializable {
    //Atributos 
    private static final long serialVersionUID = 1L;
    protected String codigo;
    protected String nombre;
    protected double precio;
    protected int stock;
    //Constructor
    public Producto(String codigo, String nombre, double precio, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }
    //Metodos abstractos a implementar en clases heredadas
    public abstract void mostrarDetalles();
    public abstract boolean verificarDisponibilidad(int cantidad);
    //Setter
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    //Getter#
    public String getCodigo() {
        return codigo;
    }
    public String getNombre() {
        return nombre;
    }
    public double getPrecio() {
        return precio;
    }
    public int getStock() {
        return stock;
    }   
}
