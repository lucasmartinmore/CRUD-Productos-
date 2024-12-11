package com.inventario.productos;

import java.io.Serializable;

public class Electrodomestico extends Producto implements Serializable {
    //Atributos
    private int garantiaMeses;
    private String categoriaEnergetica;  
    //Constructor
    public Electrodomestico(int garantiaMeses,String categoriaEnergetica, String codigo, String nombre, double precio, int stock) {
        super(codigo, nombre, precio, stock);
        this.garantiaMeses = garantiaMeses;
        this.categoriaEnergetica = categoriaEnergetica;
    }
    //Metodo especifico
    public boolean esBajoConsumo()
    {
        return categoriaEnergetica.equals("A") || categoriaEnergetica.equals("B");
    }
    //Sobrecarga metodo 
    @Override
    public void mostrarDetalles()
    {
        StringBuilder cadena = new StringBuilder();
        cadena.append("Codigo producto: ").append(codigo).append(" | ");
        cadena.append("Cantidad stock: ").append(stock).append(" | ");
        cadena.append("Nombre producto: ").append(nombre).append(" | ");
        cadena.append("Precio producto: ").append(precio).append(" | ");
        cadena.append("Categoria producto: ").append(categoriaEnergetica).append(" | ");
        cadena.append("Garantia producto: ").append(garantiaMeses).append(" | ");
        System.out.println(cadena.toString()); 
    }
    @Override
    public boolean verificarDisponibilidad(int cantidad)
    {
        return cantidad>=stock;
    }  
    //Getter
    public int getGarantiaMeses() {
        return garantiaMeses;
    }
    public String getCategoriaEnergetica() {
        return categoriaEnergetica;
    }
    //Setter
    public void setGarantiaMeses(int garantiaMeses) {
        this.garantiaMeses = garantiaMeses;
    }
    public void setCategoriaEnergetica(String categoriaEnergetica) {
        this.categoriaEnergetica = categoriaEnergetica;
    }
}
