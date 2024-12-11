package com.inventario.productos;

import com.inventario.gestores.*;
import com.inventario.excepciones.*;
import java.io.IOException;
import java.util.Scanner;
public class SP_MoreLucas {
 public static void main(String[] args) throws ProductoNoEncontradoException, IOException, ClassNotFoundException {
 Gestor gestor = new Gestor();
 Scanner scanner = new Scanner(System.in);
 int opcion;
 do {
 System.out.println("\n--- Gestión de Inventario ---");
 System.out.println("1.    Agregar Producto");
 System.out.println("2.    Mostrar Todos los Productos");
 System.out.println("3.    Buscar Producto por Código");
 System.out.println("4.    Modificar Producto");
 System.out.println("5.    Eliminar Producto");
 System.out.println("6.    Guardar Inventario en Archivo");
 System.out.println("7.    Cargar Inventario desde Archivo");
 System.out.println("8.    Filtrar productos");
 System.out.println("9.    Generar reporte Inventario");
 System.out.println("10.   Salir");
 System.out.print("Seleccione una opción: ");
 opcion = scanner.nextInt();
 switch (opcion) {
 case 1 -> gestor.agregarProductoDesdeConsola(scanner);
 case 2 -> gestor.mostrarInventario();
 case 3 -> gestor.buscarProductoPorCodigoDesdeConsola(scanner);
 case 4 -> gestor.modificarProductoDesdeConsola(scanner);
 case 5 -> gestor.eliminarProductoDesdeConsola(scanner);
 case 6 -> gestor.guardarInventario("inventario.dat");
 case 7 -> gestor.cargarInventario("inventario.dat");
 case 8 -> gestor.filtrarProducto(scanner);
 case 9 -> gestor.generarReporteInventario("inventario.txt");
 case 10 -> System.out.println("Saliendo del sistema...");
 default -> System.out.println("Opción inválida. Intente nuevamente.");
 }
 } while (opcion != 10);
 scanner.close();
 }
}