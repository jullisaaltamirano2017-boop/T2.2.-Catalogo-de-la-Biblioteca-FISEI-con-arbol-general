package modelo;

public class Libro {
    private final int codigo;
    private String titulo;
    private String autor;
    private int anio;

    public Libro(int codigo, String titulo, String autor, int anio) {
        if (codigo <= 0) {
            throw new IllegalArgumentException("El codigo del libro debe ser un entero mayor que 0.");
        }
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El titulo del libro no puede estar vacio.");
        }
        if (autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("El autor del libro no puede estar vacio.");
        }
        if (anio < 1900 || anio > 2026) {
            throw new IllegalArgumentException("El anio del libro debe estar entre 1900 y 2026.");
        }
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
    }

    public int getCodigo() { return codigo; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El titulo del libro no puede estar vacio.");
        }
        this.titulo = titulo;
    }

    public String getAutor() { return autor; }

    public void setAutor(String autor) {
        if (autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("El autor del libro no puede estar vacio.");
        }
        this.autor = autor;
    }

    public int getAnio() { return anio; }

    public void setAnio(int anio) {
        if (anio < 1900 || anio > 2026) {
            throw new IllegalArgumentException("El anio del libro debe estar entre 1900 y 2026.");
        }
        this.anio = anio;
    }

    @Override
    public String toString() {
        return codigo + " - " + titulo + " (" + autor + ", " + anio + ")";
    }
}
