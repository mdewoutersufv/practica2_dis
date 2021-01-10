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
			Actor actor = new Actor("john","cena");
			Actor actor2 = new Actor("north","#2");
			List<Actor> list1 = new ArrayList<Actor>();
			list1.add(actor);
			list1.add(actor2);
			List<Actor> list2 = new ArrayList<Actor>();
			list2.add(actor);
			List<Actor> list3 = new ArrayList<Actor>();
			list3.add(actor);
			actorRepository.save(actor);
			actorRepository.save(actor2);
			peliculaRepository.save(new Pelicula("a","a","a","a",0,0,list1));
			peliculaRepository.save(new Pelicula("b","b","a","a",0,0,list2));
			peliculaRepository.save(new Pelicula("c", "c", "a", "a", 0, 0, list3));


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