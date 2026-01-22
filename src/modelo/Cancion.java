package modelo;

public class Cancion {
    private String titulo;
    private String rutaArchivo;
    private String artista; // Opcional, pero útil para la lista

    public Cancion(String titulo, String artista, String rutaArchivo) {
        this.titulo = titulo;
        this.artista = artista;
        this.rutaArchivo = rutaArchivo;
    }

    // --- GETTERS ---
    // Permiten obtener la información sin modificarla
    
    public String getTitulo() {
        return titulo;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public String getArtista() {
        return artista;
    }

    // --- SETTERS ---
    // Permiten cambiar los datos si fuera necesario (ej. editar nombre)

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    // Método útil para que al imprimir la canción en consola o en la JList se vea bien
    @Override
    public String toString() {
        return titulo + " - " + artista;
    }
}