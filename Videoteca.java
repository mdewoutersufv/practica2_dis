import java.util.List;

public class Videoteca {

    private String nombre;
    private String ubicacion;
    private Peliculas peliculas;
    private int fecha;


    public Videoteca(String nombre, String ubicacion, Peliculas peliculas, int fecha) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.peliculas = peliculas;
        this.fecha = fecha;
    }

    public Peliculas getPeliculas() {
        return peliculas;
    }

    @Override
    public String toString() {
        return "Nombre=" + nombre + ", Ubicacion=" + ubicacion + ", Peliculas=" + peliculas + ", fecha=" + fecha;
    }

    public String getNombre() {
        return nombre;
    }
}