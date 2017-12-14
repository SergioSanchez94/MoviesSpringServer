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
import com.sergiosanchez.movies.Response;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	//------DOWNLOADER METHODS
	@RequestMapping("/searchMovie")
	public ArrayList<Movie> searchMovie(@RequestParam(value = "name") String name) throws MalformedURLException {
		return Downloader.searchMovie(name, Config.getDOMAIN());
	}

	@RequestMapping("/getMovieInfo")
	public Movie getMovieInfo(@RequestParam(value = "url") String url) throws MalformedURLException {
		return Downloader.getMovieInfo(url, Config.getDOMAIN());
	}

	@RequestMapping("/getMovie")
	public Response getMovie(@RequestParam(value = "address") String address) throws MalformedURLException {
		return Downloader.getMovie(Config.getIPADDRESS(), address, Config.getDOMAIN());
	}

	//------LIBRARY METHODS
	@RequestMapping("/addFile")
	public Response addFile(@RequestParam(value = "source") String source) throws MalformedURLException {
		return Library.addFile(Config.getIPADDRESS(), source);
	}

	@RequestMapping("/getInfo")
	public Response getMovieInfo() throws MalformedURLException {
		return Library.getInfo(Config.getIPADDRESS());
	}

	@RequestMapping("/optionFile")
	public Response optionFile(@RequestParam(value = "hash") String hash, @RequestParam(value = "option") String option) throws MalformedURLException {
		return Library.optionFile(Config.getIPADDRESS(), hash, option);
	}

	//------API METHODS
	@RequestMapping("/getMovies")
	public ArrayList<Movie> getMovies(@RequestParam(value = "busquedaURL") String busquedaURL, @RequestParam(value = "year") String year) throws MalformedURLException {	
		return MoviesAPI.getMovies(busquedaURL,year);
	}
	
	@RequestMapping("/getTrailer")
	public Response getTrailer(@RequestParam(value = "id") int id) throws MalformedURLException {
		return MoviesAPI.getTrailer(id);
	}
	
	@RequestMapping("/getCastList")
	public ArrayList<Cast> getCastList(@RequestParam(value = "idPelicula") int idPelicula) throws MalformedURLException {
		return MoviesAPI.getCastList(idPelicula);
	}

}
