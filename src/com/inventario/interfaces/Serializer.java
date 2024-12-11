package com.inventario.interfaces;

import java.io.IOException;
import java.util.List;

public interface Serializer<T> {
    //Metodos para cargar y guardar archivo en formato bin
    boolean guardarInventario(String archivo)throws IOException;
    List<T> cargarInventario(String archivo)throws IOException, ClassNotFoundException;
    void generarReporteInventario(String nombreArchivo)throws IOException;
}
