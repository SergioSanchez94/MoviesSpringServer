package com.example.movies;

import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	@CrossOrigin
	public ArrayList<Movie> searchMovie(@RequestParam(value = "name") String name) throws MalformedURLException {
		return Downloader.searchMovie(name, Config.getDOMAIN());
	}
	
	@RequestMapping("/getMovieInfo")
	@CrossOrigin
	public Movie getMovieInfo(@RequestParam(value = "url") String url) throws MalformedURLException {
		return Downloader.getMovieInfo(url, Config.getDOMAIN());
	}

	@RequestMapping("/getMovie")
	@CrossOrigin
	public Response getMovie(@RequestParam(value = "address") String address) throws MalformedURLException {
		return Downloader.getMovie(Config.getIPADDRESS(), address, Config.getDOMAIN());
	}
	
	@RequestMapping("/availability")
	@CrossOrigin
	public Response availability() throws MalformedURLException {
		Response response = new Response("availability", "200 OK - Server running");
		return response ;
	}

	//------LIBRARY METHODS
	@RequestMapping("/addFile")
	@CrossOrigin
	public Response addFile(@RequestParam(value = "source") String source) throws MalformedURLException {
		return Library.addFile(Config.getIPADDRESS(), source);
	}

	@RequestMapping("/getInfo")
	@CrossOrigin
	public JSONArray getMovieInfo() throws MalformedURLException {
		return Library.getInfo(Config.getIPADDRESS());
	}

	@RequestMapping("/optionFile")
	@CrossOrigin
	public Response optionFile(@RequestParam(value = "hash") String hash, @RequestParam(value = "option") String option) throws MalformedURLException {
		return Library.optionFile(Config.getIPADDRESS(), hash, option);
	}

	//------API METHODS
	@RequestMapping("/getMovies")
	@CrossOrigin
	public ArrayList<Movie> getMovies(@RequestParam(value = "busquedaURL") String busquedaURL, @RequestParam(value = "year") String year) throws MalformedURLException {	
		return MoviesAPI.getMovies(busquedaURL,year);
	}
	
	@RequestMapping("/getTrailer")
	@CrossOrigin
	public Response getTrailer(@RequestParam(value = "id") int id) throws MalformedURLException {
		return MoviesAPI.getTrailer(id);
	}
	
	@RequestMapping("/getCastList")
	public ArrayList<Cast> getCastList(@RequestParam(value = "idPelicula") int idPelicula, ServletResponse res) throws MalformedURLException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		return MoviesAPI.getCastList(idPelicula);
	}

}
