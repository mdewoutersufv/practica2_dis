package org.dis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
public class Practica2DisApplication {

	private static final Logger log = LoggerFactory.getLogger(Practica2DisApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(Practica2DisApplication.class);
	}

	@Bean
	public CommandLineRunner loadData(PeliculaRepository peliculaRepository, ActorRepository actorRepository) {
		return (args) -> {
			// save Peliculas
			Actor actor = new Actor("Jhon Cena","https://es.wikipedia.org/wiki/John_Cena");
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
		};
	}

}