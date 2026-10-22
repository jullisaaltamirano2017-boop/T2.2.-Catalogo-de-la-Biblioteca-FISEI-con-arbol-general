package negocio;

import enums.TipoUnidad;
import modelo.UnidadBiblioteca;
import java.util.ArrayList;
import java.util.List;

public class NodoUnidad {
    private UnidadBiblioteca dato;
    private List<NodoUnidad> hijos;
    private CatalogoABB catalogo;

    public NodoUnidad(UnidadBiblioteca dato) {
        if (dato == null) {
            throw new IllegalArgumentException("La unidad del nodo no puede ser null.");
        }
        this.dato = dato;
        this.hijos = new ArrayList<>();
        // Solo los ESTANTE tienen catalogo ABB de libros
        this.catalogo = (dato.getTipo() == TipoUnidad.ESTANTE) ? new CatalogoABB() : null;
    }

    public UnidadBiblioteca getDato() { return dato; }

    public void setDato(UnidadBiblioteca dato) {
        if (dato == null) {
            throw new IllegalArgumentException("La unidad del nodo no puede ser null.");
        }
        this.dato = dato;
    }

    public List<NodoUnidad> getHijos() { return hijos; }

    public CatalogoABB getCatalogo() { return catalogo; }

    public void setCatalogo(CatalogoABB catalogo) { this.catalogo = catalogo; }

    public void agregarHijo(NodoUnidad hijo) {
        if (hijo == null) {
            throw new IllegalArgumentException("No se puede agregar un hijo null.");
        }
        hijos.add(hijo);
    }

    public boolean esHoja() { return hijos.isEmpty(); }
}
