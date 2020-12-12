
import java.util.List;

public class Peliculas {
    private List<Pelicula> pelicula;

    @Override
    public String toString() {
        return "Peliculas{" +
                "pelicula=" + pelicula +
                '}';
    }

    public List<Pelicula> getPelicula() {
        return pelicula;
    }
}