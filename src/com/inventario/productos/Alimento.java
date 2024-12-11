package com.inventario.productos;

import java.time.LocalDate;

public class Alimento extends Producto {
    //Atributos
    private LocalDate fechaDeVencimiento;
    private boolean refrigeracionRequerida;
    //Constructor
    public Alimento(LocalDate fechaDeVencimiento, boolean refrigeracionRequerida, String codigo, String nombre, double precio, int stock) {
        super(codigo, nombre, precio, stock);
        this.fechaDeVencimiento = fechaDeVencimiento;
        this.refrigeracionRequerida = refrigeracionRequerida;
    }
    //Sobreescritura 
    @Override
    public void mostrarDetalles()
    {
        StringBuilder cadena = new StringBuilder();
        cadena.append("Codigo producto: ").append(codigo).append(" | ");
        cadena.append("Cantidad stock: ").append(stock).append(" | ");
        cadena.append("Nombre producto: ").append(nombre).append(" | ");
        cadena.append("Precio producto: ").append(precio).append(" | ");
        cadena.append("Fecha vencimiento: ").append(fechaDeVencimiento).append(" | ");
        cadena.append("Requiere refrigeracion: ").append(refrigeracionRequerida).append(" | ");
        System.out.println(cadena.toString()); 
    }
    @Override
    public boolean verificarDisponibilidad(int cantidad)
    {
        return cantidad>=stock;
    } 
    //Metodo especifico
    public boolean estaVencido() {
        return LocalDate.now().isAfter(fechaDeVencimiento);
    }
    //Getter
    public LocalDate getFechaDeVencimiento() {
        return fechaDeVencimiento;
    }
    public boolean getRefrigeracionRequerida()
    {
        return refrigeracionRequerida;
    }
    //Setter
    public void setFechaDeVencimiento(LocalDate fechaDeVencimiento) {
        this.fechaDeVencimiento = fechaDeVencimiento;
    }
    public void setRefrigeracionRequerida(boolean refrigeracionRequerida) {
        this.refrigeracionRequerida = refrigeracionRequerida;
    } 
}
