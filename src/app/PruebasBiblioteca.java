package app;

import enums.TipoRecorrido;
import enums.TipoUnidad;
import modelo.Libro;
import modelo.UnidadBiblioteca;
import negocio.ArbolBiblioteca;
import negocio.CatalogoABB;

public class PruebasBiblioteca {

    private static int pasadas = 0;
    private static int total = 0;

    public static void main(String[] args) {
        System.out.println("========== PRUEBAS DE LA BIBLIOTECA FISEI ==========\n");

        ArbolBiblioteca soloRaiz = new ArbolBiblioteca(
                new UnidadBiblioteca("BIB", "Biblioteca FISEI", TipoUnidad.BIBLIOTECA));
        verificar("Biblioteca solo con la raiz: altura 0", soloRaiz.altura() == 0);
        verificar("Biblioteca solo con la raiz: 1 hoja", soloRaiz.contarHojas() == 1);

        ArbolBiblioteca arbol = DatosPrueba.cargarBibliotecaInicial();
        verificar("Biblioteca inicial: altura 2", arbol.altura() == 2);
        verificar("Biblioteca inicial: 4 hojas", arbol.contarHojas() == 4);
        verificar("Biblioteca inicial: 13 libros", arbol.contarLibrosTotal() == 13);

        verificar("Nivel de BIB = 0", arbol.nivel("BIB") == 0);
        verificar("Nivel de sw (minusculas) = 1", arbol.nivel("sw") == 1);
        verificar("Nivel de SW-E2 = 2", arbol.nivel("SW-E2") == 2);
        verificar("Nivel de XYZ = -1", arbol.nivel("XYZ") == -1);

        boolean estanteEnRaiz = arbol.agregar("BIB",
                new UnidadBiblioteca("X-E1", "Estante invalido", TipoUnidad.ESTANTE));
        verificar("Agregar ESTANTE como hijo de BIBLIOTECA = false", estanteEnRaiz == false);

        CatalogoABB cat = arbol.buscar("SW-E1").getCatalogo();
        verificar("Preorden SW-E1 = 60 35 20 45 40 80 70 75 90",
                secuencia(cat.recorrer(TipoRecorrido.PREORDEN))
                        .equals("60 35 20 45 40 80 70 75 90"));
        verificar("Inorden SW-E1 = 20 35 40 45 60 70 75 80 90",
                secuencia(cat.recorrer(TipoRecorrido.INORDEN))
                        .equals("20 35 40 45 60 70 75 80 90"));
        verificar("Posorden SW-E1 = 20 40 45 35 75 70 90 80 60",
                secuencia(cat.recorrer(TipoRecorrido.POSORDEN))
                        .equals("20 40 45 35 75 70 90 80 60"));

        verificar("Insertar codigo repetido 60 = false",
                cat.insertar(new Libro(60, "Repetido", "Autor X", 2020)) == false);

        verificar("caminoBusqueda(42) = [60, 35, 45, 40]",
                cat.caminoBusqueda(42).toString().equals("[60, 35, 45, 40]"));
        verificar("contarEnRango(40, 75) = 5", cat.contarEnRango(40, 75) == 5);

        cat.insertar(new Libro(42, "Libro 42", "Autor 42", 2024));
        cat.eliminar(20);
        cat.eliminar(70);
        cat.eliminar(60);
        verificar("Tras insertar 42 y eliminar 20,70,60: preorden = 75 35 45 40 42 80 90",
                secuencia(cat.recorrer(TipoRecorrido.PREORDEN))
                        .equals("75 35 45 40 42 80 90"));

        verificar("Buscar codigo inexistente = null", cat.buscar(999) == null);
        verificar("Modificar codigo inexistente = false",
                cat.modificar(999, "T", "A", 2020) == false);
        verificar("Eliminar codigo inexistente = false", cat.eliminar(999) == false);

        boolean excepcion = false;
        try {
            new Libro(-5, "Invalido", "Autor", 2020);
        } catch (IllegalArgumentException e) {
            excepcion = true;
        }
        verificar("Libro con codigo invalido lanza IllegalArgumentException", excepcion);

        excepcion = false;
        try {
            new Libro(10, "Invalido", "Autor", 1800);
        } catch (IllegalArgumentException e) {
            excepcion = true;
        }
        verificar("Libro con anio fuera de 1900-2026 lanza IllegalArgumentException", excepcion);

        CatalogoABB corrupto = new CatalogoABB();
        corrupto.insertar(new Libro(50, "A", "Autor A", 2020));
        corrupto.insertar(new Libro(30, "B", "Autor B", 2020));
        corrupto.insertar(new Libro(70, "C", "Autor C", 2020));
        corrupto.buscar(30).getClass(); // sin efecto; se manipula por nodos
        corrupto.setRaiz(corrupto.getRaiz()); // raiz accesible para construir el caso
        corrupto.getRaiz().getIzquierdo().setIzquierdo(
                new negocio.NodoLibro(new Libro(80, "Malo", "Autor M", 2020)));
        verificar("esABBValido() con nieto mal ubicado = false", corrupto.esABBValido() == false);
        verificar("esABBValido() en el catalogo correcto = true",
                DatosPrueba.cargarBibliotecaInicial().buscar("SW-E1").getCatalogo().esABBValido());

        int[] preorden = {60, 35, 20, 45, 40, 80, 70, 75, 90};
        CatalogoABB reconstruido = CatalogoABB.desdePreorden(preorden);
        verificar("Extra: desdePreorden reconstruye el mismo recorrido inorden",
                secuencia(reconstruido.recorrer(TipoRecorrido.INORDEN))
                        .equals("20 35 40 45 60 70 75 80 90"));

        System.out.println("\n====================================================");
        System.out.println(pasadas + "/" + total + " pruebas OK");
        System.out.println("====================================================");
    }

    private static void verificar(String descripcion, boolean condicion) {
        total++;
        if (condicion) {
            pasadas++;
            System.out.println("[OK]   " + descripcion);
        } else {
            System.out.println("[FALLO] " + descripcion);
        }
    }

    private static String secuencia(java.util.List<Libro> libros) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < libros.size(); i++) {
            if (i > 0) {
                sb.append(" ");
            }
            sb.append(libros.get(i).getCodigo());
        }
        return sb.toString();
    }
}
