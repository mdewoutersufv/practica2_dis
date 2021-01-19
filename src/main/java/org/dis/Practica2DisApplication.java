package org.dis;

import com.google.gson.*;
import org.dom4j.DocumentException;
import org.json.*;
import org.dom4j.io.SAXReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Practica2DisApplication {

	private static final Logger log = LoggerFactory.getLogger(Practica2DisApplication.class);
	public static final String DOCUMENTO_JSON = System.getProperty("user.dir") + "data.json";
	public static Videoteca videoteca;

	public static void main(String[] args) {
		SpringApplication.run(Practica2DisApplication.class);
	}

	@Bean
	public CommandLineRunner loadData(PeliculaRepository peliculaRepository, ActorRepository actorRepository) {
		return (args) -> {
			// save Peliculas
			/*Actor actor = new Actor("Jhon Cena","https://es.wikipedia.org/wiki/John_Cena");
			Actor actor2 = new Actor("Danny DeVito","https://es.wikipedia.org/wiki/Danny_DeVito");
			List<Actor> list1 = new ArrayList<Actor>();
			list1.add(actor);
			list1.add(actor2);
			List<Actor> list2 = new ArrayList<Actor>();
			list2.add(actor);
			List<Actor> list3 = new ArrayList<Actor>();
			list3.add(actor2);
			actorRepository.save(actor);
			actorRepository.save(actor2);
			peliculaRepository.save(new Pelicula("¡Vaya Santa Claus!",
					"Scott Calvin (Tim Allen) es el padre divorciado de Charlie (Eric Lloyd). Scott se encuentra enfadado porque la madre de Charlie, Laura (Wendy Crewson), y su padrastro, un psiquiatra llamado Neal, le han contado que Santa Claus no existe. Mientras el enfadado Charlie visita a su padre el día de Navidad un ruido en el tejado hace subir a su padre que se enfrenta con el intruso que se acaba cayendo al vacío. El intruso accidentalmente muerto resulta ser Santa y debido a una misteriosa cláusula ahora Scott deberá ocupar su puesto. (FILMAFFINITY)",
					"Comedia","https://www.imdb.com/title/tt0111070/",1994,97,list1));
			peliculaRepository.save(new Pelicula("Apocalypse Now",
					"Durante la guerra de Vietnam, al joven Capitán Willard, un oficial de los servicios de inteligencia del ejército estadounidense, se le ha encomendado entrar en Camboya con la peligrosa misión de eliminar a Kurtz, un coronel renegado que se ha vuelto loco. El capitán deberá ir navegar por el río hasta el corazón de la selva, donde parece ser que Kurtz reina como un buda despótico sobre los miembros de la tribu Montagnard, que le adoran como a un dios. (FILMAFFINITY)",
					"Bélico","https://www.imdb.com/title/tt0078788/",1979,147,list2));
			peliculaRepository.save(new Pelicula("Batman Forever",
					"La ciudad de Gotham está amenazada por un hombre que intenta vengarse de la Humanidad; su rostro está desfigurado por el ácido y se hace llamar El Caras. Por otra parte, un psicópata con intención de dominar el mundo ha inventado un estrafalario aparato que permite absorber las ondas cerebrales de los seres humanos. Ambos se unirán para lograr sus propósitos. (FILMAFFINITY)",
					"Acción", "https://www.imdb.com/title/tt0112462", 1995, 121, list3));


			// fetch all Peliculas
			log.info("Peliculas found with findAll():");
			log.info("-------------------------------");
			for (Pelicula Pelicula : peliculaRepository.findAll()) {
				log.info(Pelicula.toString());
			}
			log.info("");

			// fetch an individual Pelicula by ID
			Optional<Pelicula> Pelicula = peliculaRepository.findById(1L);
			log.info("Pelicula found with findOne(1L):");
			log.info("--------------------------------");
			log.info(Pelicula.toString());
			log.info("");

			// fetch Peliculas by last name
			log.info("Pelicula found with findByLastNameStartsWithIgnoreCase('Wells'):");
			log.info("--------------------------------------------");
			for (Pelicula bauer : peliculaRepository
					.findByTituloStartsWithIgnoreCase("a")) {
				log.info(bauer.toString());
			}
			log.info("");
		};*/

			cargarPeliculasJSON(peliculaRepository, actorRepository, DOCUMENTO_JSON);
			log.info(new Gson().toJson(videoteca));
		};
	}

	public static void cargarPeliculasJSON(PeliculaRepository repoPeli, ActorRepository repoActor, String docJson) throws IOException {
		String doc_json1 = Files.readString(Paths.get(docJson), StandardCharsets.ISO_8859_1);

		// Obtain Array
		JsonObject json1 = JsonParser.parseString(doc_json1).getAsJsonObject();
		//aquí convertimos el JSON a un objeto videoteca
		Gson gson = new Gson();
		JsonObject videotecaObj = json1.getAsJsonObject("videoteca");

		String nombre = videotecaObj.get("nombre").getAsString();
		String ubicacion = videotecaObj.get("ubicacion").getAsString();
		String fechaActualizacion = videotecaObj.get("fechaActualizacion").getAsString();
		List<Pelicula> peliculas = new ArrayList<Pelicula>();

		JsonArray peliculasObj = videotecaObj.getAsJsonObject("peliculas").getAsJsonArray("pelicula");

		for (JsonElement elem1 : peliculasObj) {
			JsonObject peliculaObj = elem1.getAsJsonObject();
			String titulo = peliculaObj.get("titulo").getAsString();
			String sinopsis = peliculaObj.get("sinopsis").getAsString();
			String genero = peliculaObj.get("genero").getAsString();
			String enlace = peliculaObj.get("enlace").getAsString();
			int agno = peliculaObj.get("agno").getAsInt();
			int duracion = peliculaObj.get("duracion").getAsInt();
			List<Actor> reparto = new ArrayList<Actor>();

			JsonArray repartoObj = peliculaObj.getAsJsonObject("reparto").getAsJsonArray("actor");

			for (JsonElement elem2 : repartoObj) {
				JsonObject actorObj = elem2.getAsJsonObject();
				String nombreActor = actorObj.get("nombre").getAsString();
				String enlaceWikipedia = actorObj.get("enlaceWikipedia").getAsString();
				Actor actor = new Actor(nombreActor, enlaceWikipedia);
				reparto.add(actor);
			}

			Pelicula pelicula = new Pelicula(titulo, sinopsis, genero, enlace, agno, duracion, reparto);
			peliculas.add(pelicula);
		}

		videoteca = new Videoteca(nombre, ubicacion, peliculas, fechaActualizacion);
		System.out.println(videoteca);

		repoPeli.deleteAll();
		repoActor.deleteAll();

		for(Pelicula pelicula : peliculas){
			for(Actor actor : pelicula.getReparto())
				repoActor.save(actor);
			repoPeli.save(pelicula);
		}
	}

	public static void guardarPeliculasJSON(String docJSON){
		String json = new String("");

		json += "\"videoteca\": {\"ubicacion\": ";
		json += videoteca.getUbicacion();
		}

}