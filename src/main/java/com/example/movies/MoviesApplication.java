package com.example.movies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sergiosanchez.configuration.Config;

@SpringBootApplication
public class MoviesApplication {

	public static void main(String[] args) {
		Config.initConfig();
		SpringApplication.run(MoviesApplication.class, args);
	}
}
