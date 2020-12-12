import com.google.gson.Gson;
import org.dom4j.DocumentException;
import org.json.*;
import org.dom4j.io.SAXReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class programa {
    public static final String DOCUMENTO_XML = "data.xml";
    public static final String DOCUMENTO_JSON = "data.json";

    public static void mostrarActores(String titulo,Videoteca[] videotecas){
        for(Videoteca videoteca : videotecas) {
                for(Pelicula pelicula : videoteca.getPeliculas().getPelicula())
                    if(pelicula.getTitulo().equals(titulo)){
                        System.out.println(pelicula.getReparto());
                        return;
                    }
        }
        System.out.println("La película no está registarda en nuestra videoteca");
    }

    public static void mostrarGeneros(Videoteca[] videotecas, String nombre){
        Set<String> generos = new HashSet<String>();

        for(Videoteca videoteca : videotecas) {
            if (videoteca.getNombre().equals(nombre)) {
                for (Pelicula pelicula : videoteca.getPeliculas().getPelicula()) {
                    String genero = pelicula.getGenero();

                    if (genero == null)
                        generos.add("No calificada");
                    else
                        generos.add(genero);
                }

                System.out.print("La videoteca " + nombre + " está especializada en los siguientes géneros: ");
                int i = 0;
                for (String genero : generos) {
                    if (i == (generos.size() - 1))
                        System.out.print(genero);
                    else
                        System.out.print(genero + ", ");
                    i++;
                }
                System.out.println("");
                return;
            }
        }

        System.out.println("La videoteca " + nombre + " no existe");
    }

    public static void main(String[] args) {
        try {
            // elegir opciones
            boolean opcionFlag = false;
            String opcion = new String();

            do {
                System.out.println("Bienvenido, estas son las opciones disponibles:");
                System.out.println("1: Generar archivo JSON");
                System.out.println("2: Mostrar el reparto de una determinada película");
                System.out.println("3: Mostrar los géneros de una determinada videoteca");
                System.out.println("4: Finalizar el programa\n");
                System.out.println("Introduzca el número correspondiente a la opción deseada:");
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                opcion = input.readLine();

                if(opcion.equals("1")) {
                    // guardamos el archivo XML en un String
                    String doc_xml = Files.readString(Paths.get(DOCUMENTO_XML), StandardCharsets.ISO_8859_1);
                    // aqui validamos el XML usando el DTD correspondiente, en caso de error se produce un DocumentException
                    SAXReader reader = new SAXReader();
                    reader.setValidation(true);
                    reader.read(DOCUMENTO_XML);
                    // convertimos el XML a JSON
                    JSONObject json_xml = XML.toJSONObject(doc_xml);
                    // identamos el JSON y lo imprimimos en un archivo
                    String jsonPrettyPrintString = json_xml.toString(4);
                    Files.writeString(Paths.get(DOCUMENTO_JSON), jsonPrettyPrintString, StandardCharsets.ISO_8859_1);
                    System.out.println("Archivo JSON generado con éxito");
                }
                else if (opcion.equals("2") || opcion.equals("3")){
                    //comprobamos que exista el archivo JSON
                    if(Files.exists(Paths.get(DOCUMENTO_JSON))){
                        String doc_json = Files.readString(Paths.get(DOCUMENTO_JSON), StandardCharsets.ISO_8859_1);
                        JSONObject json = new JSONObject(doc_json);
                        //aquí convertimos el JSON a un objeto videoteca
                        Gson gson = new Gson();
                        Videoteca[] videotecas =  new Videoteca[1];
                        videotecas[0] = gson.fromJson(json.get("videoteca").toString(), Videoteca.class);
                        BufferedReader input_funcion = new BufferedReader(new InputStreamReader(System.in));
                        String nombre = new String();

                        if(opcion.equals("2")) {
                            System.out.println("Introduzca el nombre de la película:");
                            nombre = input_funcion.readLine();
                            mostrarActores(nombre, videotecas);
                        }
                        else {
                            System.out.println("Introduzca el nombre de la videoteca\"");
                            nombre = input_funcion.readLine();
                            mostrarGeneros(videotecas, nombre);
                        }
                    }
                    else
                        System.out.println("Genere el JSON (opción 1) antes de ejecutar esta opción");
                }
                else if (opcion.equals("4"))
                    opcionFlag = true;
                else
                    System.out.println("Opción incorrecta, inténtelo de nuevo");
                System.out.println("");
            } while(!opcionFlag);

        } catch(JSONException | IOException je) {
            System.out.println(je.toString());
        }
        catch (DocumentException de){
            System.out.println(de.toString());
        }

    }
}