package com.inventario.interfaces;

import com.inventario.productos.Producto;
//interface funcional para utilizar dependiendo el criterio implementado, devuelve un boolean
@FunctionalInterface
public interface FiltroProducto {
    boolean filtrar(Producto producto);
}
