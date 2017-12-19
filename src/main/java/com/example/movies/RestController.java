package com.example.movies;

import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
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
	public ArrayList<Movie> searchMovie(@RequestParam(value = "name") String name, HttpServletResponse  response) throws MalformedURLException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
		return Downloader.searchMovie(name, Config.getDOMAIN());
	}

	@RequestMapping("/getMovieInfo")
	public Movie getMovieInfo(@RequestParam(value = "url") String url, HttpServletResponse  response) throws MalformedURLException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
		return Downloader.getMovieInfo(url, Config.getDOMAIN());
	}

	@RequestMapping("/getMovie")
	public Response getMovie(@RequestParam(value = "address") String address, HttpServletResponse  response) throws MalformedURLException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
		return Downloader.getMovie(Config.getIPADDRESS(), address, Config.getDOMAIN());
	}

	//------LIBRARY METHODS
	@RequestMapping("/addFile")
	public Response addFile(@RequestParam(value = "source") String source, HttpServletResponse  response) throws MalformedURLException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
		return Library.addFile(Config.getIPADDRESS(), source);
	}

	@RequestMapping("/getInfo")
	public JSONArray getMovieInfo(HttpServletResponse  response) throws MalformedURLException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
		return Library.getInfo(Config.getIPADDRESS());
	}

	@RequestMapping("/optionFile")
	public Response optionFile(@RequestParam(value = "hash") String hash, @RequestParam(value = "option") String option, HttpServletResponse  response) throws MalformedURLException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
		return Library.optionFile(Config.getIPADDRESS(), hash, option);
	}

	//------API METHODS
	@RequestMapping("/getMovies")
	public ArrayList<Movie> getMovies(@RequestParam(value = "busquedaURL") String busquedaURL, @RequestParam(value = "year") String year, HttpServletResponse  response) throws MalformedURLException {	
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
		return MoviesAPI.getMovies(busquedaURL,year);
	}
	
	@RequestMapping("/getTrailer")
	public Response getTrailer(@RequestParam(value = "id") int id, HttpServletResponse  response) throws MalformedURLException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
		return MoviesAPI.getTrailer(id);
	}
	
	@RequestMapping("/getCastList")
	public ArrayList<Cast> getCastList(@RequestParam(value = "idPelicula") int idPelicula, HttpServletResponse  response) throws MalformedURLException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
		return MoviesAPI.getCastList(idPelicula);
	}

}
