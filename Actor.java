public class Actor
{

    private String nombre;
    private String enlaceWikipedia;

    public Actor(String nombre, String enlaceWikipedia)
    {
        this.nombre = nombre;
        this.enlaceWikipedia = enlaceWikipedia;
    }

    @Override
    public String toString()
    {
        return "Nombre: " + nombre + ", Enlace de la Wikipedia:" + enlaceWikipedia;
    }

}
