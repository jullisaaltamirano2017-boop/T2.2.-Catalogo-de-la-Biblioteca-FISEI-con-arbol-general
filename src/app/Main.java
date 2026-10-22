package app;

import enums.TipoRecorrido;
import enums.TipoUnidad;
import modelo.Libro;
import modelo.UnidadBiblioteca;
import negocio.ArbolBiblioteca;
import negocio.CatalogoABB;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static ArbolBiblioteca biblioteca;

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");
            System.out.println();
            switch (opcion) {
                case 1: cargarEstructuraInicial(); break;
                case 2: mostrarJerarquia(); break;
                case 3: agregarUnidad(); break;
                case 4: buscarUnidad(); break;
                case 5: consultarMetricas(); break;
                case 6: insertarLibro(); break;
                case 7: buscarLibro(); break;
                case 8: modificarLibro(); break;
                case 9: eliminarLibro(); break;
                case 10: mostrarRecorrido(false); break;
                case 11: mostrarRecorrido(true); break;
                case 12: ejecutarRetos(); break;
                case 13: ejecutarPruebas(); break;
                case 0: System.out.println("Hasta pronto."); break;
                default: System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n===== BIBLIOTECA FISEI - Arbol general + ABB =====");
        System.out.println(" 1. Cargar la estructura inicial");
        System.out.println(" 2. Mostrar la jerarquia");
        System.out.println(" 3. Agregar unidad (area/estante)");
        System.out.println(" 4. Buscar unidad");
        System.out.println(" 5. Altura, hojas, nivel y total de libros");
        System.out.println(" 6. Insertar libro en un estante");
        System.out.println(" 7. Buscar libro en un estante");
        System.out.println(" 8. Modificar libro de un estante");
        System.out.println(" 9. Eliminar libro de un estante");
        System.out.println("10. Mostrar recorrido de un estante");
        System.out.println("11. Mostrar recorrido con traza");
        System.out.println("12. Ejecutar los retos");
        System.out.println("13. Ejecutar pruebas (PruebasBiblioteca)");
        System.out.println(" 0. Salir");
    }

    private static void cargarEstructuraInicial() {
        biblioteca = DatosPrueba.cargarBibliotecaInicial();
        System.out.println("Estructura inicial cargada.");
        System.out.println(biblioteca.mostrarJerarquia());
    }

    private static void mostrarJerarquia() {
        if (!hayBiblioteca()) return;
        System.out.println(biblioteca.mostrarJerarquia());
    }

    private static void agregarUnidad() {
        if (!hayBiblioteca()) return;
        sc.nextLine();
        System.out.print("Codigo del padre: ");
        String padre = sc.nextLine().trim();
        System.out.print("Codigo de la nueva unidad (MAYUSCULAS): ");
        String codigo = sc.nextLine().trim();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Tipo (1=AREA, 2=ESTANTE): ");
        int t = leerEntero("");
        TipoUnidad tipo = (t == 2) ? TipoUnidad.ESTANTE : TipoUnidad.AREA;
        boolean ok = biblioteca.agregar(padre, new UnidadBiblioteca(codigo, nombre, tipo));
        System.out.println(ok ? "Unidad agregada." : "No se pudo agregar (jerarquia invalida o codigo repetido).");
    }

    private static void buscarUnidad() {
        if (!hayBiblioteca()) return;
        sc.nextLine();
        System.out.print("Codigo a buscar: ");
        String codigo = sc.nextLine().trim();
        var nodo = biblioteca.buscar(codigo);
        System.out.println(nodo == null ? "No existe la unidad." : "Encontrada: " + nodo.getDato());
    }

    private static void consultarMetricas() {
        if (!hayBiblioteca()) return;
        sc.nextLine();
        System.out.println("Altura (aristas): " + biblioteca.altura());
        System.out.println("Hojas: " + biblioteca.contarHojas());
        System.out.println("Total de libros: " + biblioteca.contarLibrosTotal());
        System.out.print("Codigo para consultar nivel: ");
        String codigo = sc.nextLine().trim();
        System.out.println("Nivel de " + codigo + ": " + biblioteca.nivel(codigo));
    }

    private static CatalogoABB seleccionarEstante() {
        if (!hayBiblioteca()) return null;
        sc.nextLine();
        System.out.print("Codigo del estante: ");
        String codigo = sc.nextLine().trim();
        var nodo = biblioteca.buscar(codigo);
        if (nodo == null || nodo.getCatalogo() == null) {
            System.out.println("No existe un estante con ese codigo.");
            return null;
        }
        return nodo.getCatalogo();
    }

    private static void insertarLibro() {
        CatalogoABB cat = seleccionarEstante();
        if (cat == null) return;
        System.out.print("Codigo del libro: ");
        int codigo = leerEntero("");
        System.out.print("Titulo: ");
        String titulo = sc.nextLine().trim();
        System.out.print("Autor: ");
        String autor = sc.nextLine().trim();
        System.out.print("Anio (1900-2026): ");
        int anio = leerEntero("");
        try {
            boolean ok = cat.insertar(new Libro(codigo, titulo, autor, anio));
            System.out.println(ok ? "Libro insertado." : "El codigo ya existe en el estante.");
        } catch (IllegalArgumentException e) {
            System.out.println("Datos invalidos: " + e.getMessage());
        }
    }

    private static void buscarLibro() {
        CatalogoABB cat = seleccionarEstante();
        if (cat == null) return;
        System.out.print("Codigo del libro: ");
        int codigo = leerEntero("");
        Libro libro = cat.buscar(codigo);
        System.out.println(libro == null ? "No existe ese libro." : libro.toString());
    }

    private static void modificarLibro() {
        CatalogoABB cat = seleccionarEstante();
        if (cat == null) return;
        System.out.print("Codigo del libro a modificar: ");
        int codigo = leerEntero("");
        System.out.print("Nuevo titulo: ");
        String titulo = sc.nextLine().trim();
        System.out.print("Nuevo autor: ");
        String autor = sc.nextLine().trim();
        System.out.print("Nuevo anio (1900-2026): ");
        int anio = leerEntero("");
        boolean ok = cat.modificar(codigo, titulo, autor, anio);
        System.out.println(ok ? "Libro modificado." : "No existe ese libro.");
    }

    private static void eliminarLibro() {
        CatalogoABB cat = seleccionarEstante();
        if (cat == null) return;
        System.out.print("Codigo del libro a eliminar: ");
        int codigo = leerEntero("");
        boolean ok = cat.eliminar(codigo);
        System.out.println(ok ? "Libro eliminado." : "No existe ese libro.");
    }

    private static void mostrarRecorrido(boolean conTraza) {
        CatalogoABB cat = seleccionarEstante();
        if (cat == null) return;
        System.out.print("Tipo (1=PREORDEN, 2=INORDEN, 3=POSORDEN): ");
        int t = leerEntero("");
        TipoRecorrido tipo = (t == 2) ? TipoRecorrido.INORDEN : (t == 3) ? TipoRecorrido.POSORDEN : TipoRecorrido.PREORDEN;
        if (conTraza) {
            System.out.println(cat.trazaRecorrido(tipo));
        } else {
            List<Libro> libros = cat.recorrer(tipo);
            StringBuilder sb = new StringBuilder();
            for (Libro libro : libros) {
                sb.append(libro.getCodigo()).append(" ");
            }
            System.out.println("Recorrido " + tipo + ": " + sb);
        }
    }

    private static void ejecutarRetos() {
        CatalogoABB cat = seleccionarEstante();
        if (cat == null) return;
        System.out.print("Rango a (contarEnRango): ");
        int a = leerEntero("");
        System.out.print("Rango b: ");
        int b = leerEntero("");
        System.out.println("contarEnRango(" + a + ", " + b + ") = " + cat.contarEnRango(a, b));
        System.out.print("Codigo para caminoBusqueda: ");
        int codigo = leerEntero("");
        System.out.println("caminoBusqueda(" + codigo + ") = " + cat.caminoBusqueda(codigo));
        System.out.println("esABBValido() = " + cat.esABBValido());
    }

    private static void ejecutarPruebas() {
        System.out.println("(Ejecutando la bateria de pruebas en un proceso aparte...)");
        System.out.println("Use: java -cp bin app.PruebasBiblioteca  |  opcion integrada: ver clase app.PruebasBiblioteca");
    }

    private static boolean hayBiblioteca() {
        if (biblioteca == null) {
            System.out.println("Primero cargue la estructura inicial (opcion 1).");
            return false;
        }
        return true;
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Ingrese un numero entero: ");
        }
        return sc.nextInt();
    }
}
