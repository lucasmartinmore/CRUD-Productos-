package com.inventario.gestores;

import com.inventario.interfaces.Serializer;
import com.inventario.excepciones.ProductoNoEncontradoException;
import com.inventario.interfaces.FiltroProducto;
import com.inventario.productos.Alimento;
import com.inventario.productos.Producto;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import com.inventario.productos.Electrodomestico;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class Gestor implements Serializer { //implementar la interface para guardar y cargar
    //Atributo

    private ArrayList<Producto> inventarioProductos;

    //Constructor
    public Gestor() {
        this.inventarioProductos = new ArrayList<>();
    }

    //Sobrecarga clase Serializable
    @Override
    public boolean guardarInventario(String nombreArchivo) {
        //creo mi objeto para guardar en un archivo
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            out.writeObject(inventarioProductos); // Serializa la lista completa de productos
            System.out.println("\n----- Se guardaron los productos en binario! -----\n");
            return true;
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
            return false;
        }
    }

    @Override
    public ArrayList<Producto> cargarInventario(String nombreArchivo) throws IOException, ClassNotFoundException {
        ArrayList<Producto> productosCargados = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            // Lee la lista de productos cargada en inventario 
            Object productos = ois.readObject();
            // Verificamos si el objeto leído es una lista de productos
            if (productos instanceof ArrayList<?>) {
                productosCargados = (ArrayList<Producto>) productos;
                System.out.println("\n----- Productos leídos desde el archivo binario! -----");
            } else {
                System.out.println("El archivo no contiene una lista de productos válida.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: El archivo no existe.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        inventarioProductos = productosCargados;

        return productosCargados;
    }

    //Metodos especificos
    public void mostrarInventario() {
        if (inventarioProductos.isEmpty()) {
            System.out.println("\n\n---- No se cargaron productos aún! ----\n");
        } else {
            System.out.println("\n\n---- Inventario de Productos ----\n");
            for (Producto producto : inventarioProductos) {
                producto.mostrarDetalles();
            }
        }
    }

    public void agregarProductoDesdeConsola(Scanner scanner) {
        System.out.println("\n----- Agregar Producto -----\n");

        System.out.print("Ingrese el código del producto: ");
        String codigo = scanner.next();

        System.out.print("Ingrese el nombre del producto: ");
        String nombre = scanner.next();

        System.out.print("Ingrese el precio del producto: ");
        double precio = scanner.nextDouble();

        System.out.print("Ingrese el stock del producto: ");
        int stock = scanner.nextInt();

        System.out.println("Seleccione el tipo de producto:\n");
        System.out.println("1) Electrodoméstico");
        System.out.println("2) Alimento");
        System.out.print("Opción: ");
        int tipo = scanner.nextInt();

        Producto producto = null;

        switch (tipo) {
            case 1 -> {
                //Categoria Electrodomestico
                System.out.print("Ingrese los meses de garantía: ");
                int garantiaMeses = scanner.nextInt();
                System.out.print("Ingrese la categoría energética (A, A+, B, C, D, E o F): ");
                String categoriaEnergetica = scanner.next();

                producto = new Electrodomestico(garantiaMeses, categoriaEnergetica, codigo, nombre, precio, stock);
            }
            case 2 -> {
                //Categoria Alimento
                System.out.print("Ingrese la fecha de vencimiento (YYYY-MM-DD): ");
                String fechaVencimientoString = scanner.next();
                LocalDate fechaVencimiento = LocalDate.parse(fechaVencimientoString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                System.out.print("¿Requiere refrigeración? (true/false): ");
                boolean refrigeracionRequerida = scanner.nextBoolean();

                producto = new Alimento(fechaVencimiento, refrigeracionRequerida, codigo, nombre, precio, stock);
            }
            default -> {
                System.out.println("\n\n----- Opción no válida. Cancelando creación de producto -----.");
                return;
            }
        }
        //Agrego al Inventario
        if (producto != null) {
            inventarioProductos.add(producto);
            System.out.println("\n\n----- Producto agregado exitosamente! -----\n");
            guardarInventario("inventario.dat");
            producto.mostrarDetalles();
        }
    }

    public void eliminarProductoDesdeConsola(Scanner scanner) throws IOException {
        mostrarInventario();
        System.out.println("\n\n------ Eliminar Producto ------");
        try {
            Producto producto = buscarProductoPorCodigoDesdeConsola(scanner);

            System.out.println("\n----- Producto encontrado -----");
            producto.mostrarDetalles();
            System.out.print("Confirmar eliminación del producto (si/no): ");
            scanner.nextLine();
            String confirmacion = scanner.nextLine().trim();

            if (confirmacion.equalsIgnoreCase("si")) {
                inventarioProductos.remove(producto);
                System.out.println("\n----- Producto eliminado exitosamente! -----\n");
                guardarInventario("inventario.dat");
            } else {
                System.out.println("\n----- Eliminación cancelada! -----");
            }
        } catch (ProductoNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage()); // Producto no encontrado
        }
    }

    public Producto buscarProductoPorCodigoDesdeConsola(Scanner scanner) throws ProductoNoEncontradoException {

        System.out.print("Ingrese el código del producto: ");
        String codigo = scanner.next();
        for (Producto producto : inventarioProductos) {
            if (producto.getCodigo().equalsIgnoreCase(codigo)) {
                System.out.println("\n----- Producto encontrado -----");
                producto.mostrarDetalles();
                return producto;
            }
        }
        throw new ProductoNoEncontradoException("\n----- El codigo ingresado: " +codigo+ " no es correcto! -----");
    }

    public void modificarProductoDesdeConsola(Scanner scanner) throws IOException {
        mostrarInventario();
        try {
            Producto producto = buscarProductoPorCodigoDesdeConsola(scanner);

            System.out.println("\n----- Producto encontrado -----");
            producto.mostrarDetalles();
            System.out.println("\n----- Menú de modificación -----\n");
            System.out.println("1) Nombre");
            System.out.println("2) Precio");
            System.out.println("3) Stock");
            System.out.print("Que desea modificar?: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> {
                    System.out.print("Ingrese el nuevo nombre del producto: ");
                    String nuevoNombre = scanner.nextLine();
                    producto.setNombre(nuevoNombre);
                    System.out.println("----- Nombre actualizado correctamente! -----");
                    guardarInventario("inventario.dat");
                }
                case 2 -> {
                    System.out.print("Ingrese el nuevo precio del producto: ");
                    double nuevoPrecio = scanner.nextDouble();
                    producto.setPrecio(nuevoPrecio);
                    System.out.println("----- Precio actualizado correctamente! ----- ");
                    guardarInventario("inventario.dat");
                }
                case 3 -> {
                    System.out.print("Ingrese el nuevo stock del producto: ");
                    int nuevoStock = scanner.nextInt();
                    producto.setStock(nuevoStock);
                    System.out.println("----- Stock actualizado correctamente! -----");
                    guardarInventario("inventario.dat");
                }
                default ->
                    System.out.println("Opción incorrecta!");
            }

            guardarInventario("inventario.dat");
        } catch (ProductoNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public List<Producto> filtrarProductos(FiltroProducto filtro) {
        return inventarioProductos.stream()
                .filter(filtro::filtrar) // Usamos el filtro que se pasa como parámetro
                .collect(Collectors.toList());
    }

    public void filtrarProducto(Scanner scanner) {
        System.out.println("\n\n------- Menú de Filtrado -------\n");
        System.out.println("1) Stock bajo (200 unidades)");
        System.out.println("2) Por categoria - Electrodomestico");
        System.out.println("3) Por categoria - Alimento");
        System.out.print("Que desea filtrar?: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1 -> {
                FiltroProducto filtroStockBajo = producto -> producto.getStock() <= 200;
                List<Producto> productosConStockBajo = filtrarProductos(filtroStockBajo);
                if (productosConStockBajo.isEmpty()) {
                    System.out.println("No se encuentran productos stock bajo!");
                } else {
                    System.out.println("\n\n----- Productos con stock bajo -----");
                    productosConStockBajo.forEach(producto -> producto.mostrarDetalles());
                }
            }

            case 2 -> {
                FiltroProducto filtroPorCategoriaElectrodomestico = producto -> producto instanceof Electrodomestico;
                List<Producto> productosElectrodomesticos = filtrarProductos(filtroPorCategoriaElectrodomestico);
                if (productosElectrodomesticos.isEmpty()) {
                    System.out.println("No se encontraron productos de categoria Electrodomestico.");
                } else {
                    System.out.println("\n\n----- Productos de categoría Electrodomestico -----\n");
                    productosElectrodomesticos.forEach(producto -> producto.mostrarDetalles());
                }
            }

            case 3 -> {
                FiltroProducto filtroPorCategoriaAlimento = producto -> producto instanceof Alimento;
                List<Producto> productosAlimentos = filtrarProductos(filtroPorCategoriaAlimento);
                if (productosAlimentos.isEmpty()) {
                    System.out.println("No se encontraron productos de categoria Alimento.");
                } else {
                    System.out.println("\n\n----- Productos de categoría Alimento -----\n");
                    productosAlimentos.forEach(producto -> producto.mostrarDetalles());
                }
            }
            default ->
                System.out.println("Opción no válida. Por favor, intente nuevamente.");
        }
    }

    @Override
    public void generarReporteInventario(String nombreArchivo) throws IOException {
        if (inventarioProductos.isEmpty()) {
            System.out.println("\n\n----- El inventario está vacío. No se puede generar el reporte. -----\n");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            writer.write("----- Reporte de Inventario -----\n\n");
            writer.write("Fecha de generación: " + LocalDate.now() + "\n\n");
            for (Producto producto : inventarioProductos) {
                writer.write(producto.toString() + "\n");
            }
            System.out.println("\n----- Reporte generado exitosamente! -----\n");
        } catch (IOException e) {
            System.out.println("ERROR: No se pudo generar el reporte. " + e.getMessage());
        }
    }

}
