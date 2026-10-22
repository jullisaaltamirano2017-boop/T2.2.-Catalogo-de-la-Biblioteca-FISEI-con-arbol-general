package negocio;

import enums.TipoUnidad;
import modelo.UnidadBiblioteca;

public class ArbolBiblioteca {
    private NodoUnidad raiz;

    public ArbolBiblioteca(UnidadBiblioteca biblioteca) {
        if (biblioteca == null || biblioteca.getTipo() != TipoUnidad.BIBLIOTECA) {
            throw new IllegalArgumentException("La raiz debe ser una unidad de tipo BIBLIOTECA.");
        }
        this.raiz = new NodoUnidad(biblioteca);
    }

    public NodoUnidad getRaiz() { return raiz; }

    public boolean agregar(String codigoPadre, UnidadBiblioteca nueva) {
        if (nueva == null || existeCodigo(nueva.getCodigo())) {
            return false;
        }
        NodoUnidad padre = buscar(codigoPadre);
        if (padre == null || !padre.getDato().getTipo().puedeTenerHijo(nueva.getTipo())) {
            return false;
        }
        padre.agregarHijo(new NodoUnidad(nueva));
        return true;
    }

    private boolean existeCodigo(String codigo) {
        return existeCodigo(raiz, codigo);
    }

    private boolean existeCodigo(NodoUnidad nodo, String codigo) {
        if (nodo == null) {
            return false;
        }
        if (nodo.getDato().getCodigo().equalsIgnoreCase(codigo)) {
            return true;
        }
        for (NodoUnidad hijo : nodo.getHijos()) {
            if (existeCodigo(hijo, codigo)) {
                return true;
            }
        }
        return false;
    }

    public NodoUnidad buscar(String codigo) {
        return buscar(raiz, codigo);
    }

    private NodoUnidad buscar(NodoUnidad nodo, String codigo) {
        if (nodo == null) {
            return null;
        }
        if (nodo.getDato().getCodigo().equalsIgnoreCase(codigo)) {
            return nodo;
        }
        for (NodoUnidad hijo : nodo.getHijos()) {
            NodoUnidad encontrado = buscar(hijo, codigo);
            if (encontrado != null) {
                return encontrado;
            }
        }
        return null;
    }

    public int altura() {
        return altura(raiz);
    }

    private int altura(NodoUnidad nodo) {
        if (nodo == null || nodo.esHoja()) {
            return 0;
        }
        int maxAlturaHijos = 0;
        for (NodoUnidad hijo : nodo.getHijos()) {
            int alturaHijo = altura(hijo);
            if (alturaHijo > maxAlturaHijos) {
                maxAlturaHijos = alturaHijo;
            }
        }
        return 1 + maxAlturaHijos;
    }

    public int contarHojas() {
        return contarHojas(raiz);
    }

    private int contarHojas(NodoUnidad nodo) {
        if (nodo == null) {
            return 0;
        }
        if (nodo.esHoja()) {
            return 1;
        }
        int total = 0;
        for (NodoUnidad hijo : nodo.getHijos()) {
            total += contarHojas(hijo);
        }
        return total;
    }

    public int nivel(String codigo) {
        return nivel(raiz, codigo, 0);
    }

    private int nivel(NodoUnidad nodo, String codigo, int nivelActual) {
        if (nodo == null) {
            return -1;
        }
        if (nodo.getDato().getCodigo().equalsIgnoreCase(codigo)) {
            return nivelActual;
        }
        for (NodoUnidad hijo : nodo.getHijos()) {
            int nivelHijo = nivel(hijo, codigo, nivelActual + 1);
            if (nivelHijo != -1) {
                return nivelHijo;
            }
        }
        return -1;
    }

    public String mostrarJerarquia() {
        StringBuilder sb = new StringBuilder();
        mostrarJerarquia(raiz, 0, sb);
        return sb.toString();
    }

    private void mostrarJerarquia(NodoUnidad nodo, int nivel, StringBuilder sb) {
        if (nodo == null) {
            return;
        }
        for (int i = 0; i < nivel; i++) {
            sb.append("  ");
        }
        sb.append(nodo.getDato().getCodigo()).append(" - ").append(nodo.getDato().getNombre());
        if (nodo.getCatalogo() != null) {
            sb.append(" [").append(nodo.getCatalogo().contar()).append(" libros]");
        }
        sb.append("\n");
        for (NodoUnidad hijo : nodo.getHijos()) {
            mostrarJerarquia(hijo, nivel + 1, sb);
        }
    }

    public int contarLibrosTotal() {
        return contarLibrosTotal(raiz);
    }

    private int contarLibrosTotal(NodoUnidad nodo) {
        if (nodo == null) {
            return 0;
        }
        int total = (nodo.getCatalogo() != null) ? nodo.getCatalogo().contar() : 0;
        for (NodoUnidad hijo : nodo.getHijos()) {
            total += contarLibrosTotal(hijo);
        }
        return total;
    }
}
