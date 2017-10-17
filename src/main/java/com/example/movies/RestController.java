package com.example.movies;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sergiosanchez.configuration.Config;
import com.sergiosanchez.connections.Downloader;
import com.sergiosanchez.connections.Library;
import com.sergiosanchez.connections.MoviesAPI;
import com.sergiosanchez.movies.Cast;
import com.sergiosanchez.movies.Movie;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	//------DOWNLOADER METHODS
	@RequestMapping("/searchMovie")
	public ArrayList<Movie> searchMovie(@RequestParam(value = "name") String name) throws MalformedURLException {
		return Downloader.searchMovie(name, Config.getDOMAIN());
	}

	@RequestMapping("/getMovieInfo")
	public Movie getMovieInfo(@RequestParam(value = "url") String url) throws MalformedURLException {
		return Downloader.getMovieInfo(url, Config.getIPADDRESS());
	}

	@RequestMapping("/getMovie")
	public String getMovie(@RequestParam(value = "address") String address) throws MalformedURLException {
		return Downloader.getMovie(Config.getIPADDRESS(), address, Config.getDOMAIN());
	}

	//------LIBRARY METHODS
	@RequestMapping("/addFile")
	public String addFile(@RequestParam(value = "source") String source) throws MalformedURLException {
		return Library.addFile(Config.getIPADDRESS(), source);
	}

	@RequestMapping("/getInfo")
	public String getMovieInfo() throws MalformedURLException {
		return Library.getInfo(Config.getIPADDRESS());
	}

	@RequestMapping("/removeFile")
	public String removeFile(@RequestParam(value = "hash") String hash) throws MalformedURLException {
		return Library.removeFile(Config.getIPADDRESS(), hash);
	}

	//------API METHODS
	@RequestMapping("/getMovies")
	public ArrayList<Movie> getMovies(@RequestParam(value = "busquedaURL") String busquedaURL, @RequestParam(value = "year") String year) throws MalformedURLException {	
		return MoviesAPI.getMovies("https://api.themoviedb.org/3/search/movie?api_key="
				+ Config.getAPIKEY() + "&language=es-ES&query=" + busquedaURL
				+ "&page=1&include_adult=false&region=Spain&year="
				+ year);
	}
	
	@RequestMapping("/getTrailer")
	public String getTrailer(@RequestParam(value = "id") int id) throws MalformedURLException {
		return MoviesAPI.getTrailer(id);
	}
	
	@RequestMapping("/getCastList")
	public ArrayList<Cast> getCastList(@RequestParam(value = "idPelicula") int idPelicula) throws MalformedURLException {
		return MoviesAPI.getCastList(idPelicula);
	}

}
