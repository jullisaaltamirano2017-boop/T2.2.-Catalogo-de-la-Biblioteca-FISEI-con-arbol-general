package modelo;

import enums.TipoUnidad;

public class UnidadBiblioteca {
    private final String codigo;
    private String nombre;
    private final TipoUnidad tipo;

    public UnidadBiblioteca(String codigo, String nombre, TipoUnidad tipo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El codigo de la unidad es obligatorio.");
        }
        if (!codigo.equals(codigo.toUpperCase())) {
            throw new IllegalArgumentException("El codigo de la unidad debe estar en mayusculas: " + codigo);
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la unidad no puede estar vacio.");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de la unidad es obligatorio.");
        }
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getCodigo() { return codigo; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la unidad no puede estar vacio.");
        }
        this.nombre = nombre;
    }

    public TipoUnidad getTipo() { return tipo; }

    @Override
    public String toString() {
        return codigo + " (" + nombre + ", " + tipo + ")";
    }
}
