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
import com.sergiosanchez.movies.Genre;
import com.sergiosanchez.movies.Movie;
import com.sergiosanchez.movies.Response;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	//------SERVER METHODS
	@RequestMapping("/availability")
	@CrossOrigin
	public Response availability() throws MalformedURLException {
		Response response = new Response("availability", "200 OK - Server running");
		return response ;
	}
	
	//------DOWNLOADER METHODS
	
	/**
	 * Returns a list of movies available in Downloader page.
	 * @param name
	 * @return ArrayList Movie
	 * @throws MalformedURLException
	 */
	@RequestMapping("/searchMovie")
	@CrossOrigin
	public ArrayList<Movie> searchMovie(@RequestParam(value = "name") String name) throws MalformedURLException {
		return Downloader.searchMovie(name, Config.getDOMAIN());
	}
	
	/**
	 * Return a Movie object with information in Downloader page.
	 * @param url
	 * @return Movie
	 * @throws MalformedURLException
	 */
	@RequestMapping("/getMovieInfo")
	@CrossOrigin
	public Movie getMovieInfo(@RequestParam(value = "url") String url) throws MalformedURLException {
		return Downloader.getMovieInfo(url, Config.getDOMAIN());
	}

	/**
	 * Return an url of the movie location.
	 * @param address
	 * @return Response
	 * @throws MalformedURLException
	 */
	@RequestMapping("/getMovie")
	@CrossOrigin
	public Response getMovie(@RequestParam(value = "address") String address) throws MalformedURLException {
		return Downloader.getMovie(Config.getIPADDRESS(), address, Config.getDOMAIN());
	}

	//------LIBRARY METHODS
	
	/**
	 * Add a Movie to your library.
	 * @param source
	 * @return Response
	 * @throws MalformedURLException
	 */
	@RequestMapping("/addFile")
	@CrossOrigin
	public Response addFile(@RequestParam(value = "source") String source) throws MalformedURLException {
		return Library.addFile(Config.getIPADDRESS(), source);
	}

	/**
	 * Return the status of your library and all  movies.
	 * @return JSONArray
	 * @throws MalformedURLException
	 */
	@RequestMapping("/getInfo")
	@CrossOrigin
	public JSONArray getMovieInfo() throws MalformedURLException {
		return Library.getInfo(Config.getIPADDRESS());
	}

	/**
	 * Execute an operation in a Movie of your library.
	 * @param hash
	 * @param option
	 * @return Response
	 * @throws MalformedURLException
	 */
	@RequestMapping("/optionFile")
	@CrossOrigin
	public Response optionFile(@RequestParam(value = "hash") String hash, @RequestParam(value = "option") String option) throws MalformedURLException {
		return Library.optionFile(Config.getIPADDRESS(), hash, option);
	}

	//------API METHODS
	
	/**
	 * Returns the most rated movies in the last year.
	 * @param busquedaURL
	 * @param year
	 * @return ArrayList Movie 
	 * @throws MalformedURLException
	 */
	@RequestMapping("/getMovies")
	@CrossOrigin
	public ArrayList<Movie> getMovies(@RequestParam(value = "busquedaURL") String busquedaURL, @RequestParam(value = "year") String year) throws MalformedURLException {	
		return MoviesAPI.getMovies(busquedaURL,year);
	}
	
	/**
	 * Returns the YouTube trailer of the Movie.
	 * @param id
	 * @return Response
	 * @throws MalformedURLException
	 */
	@RequestMapping("/getTrailer")
	@CrossOrigin
	public Response getTrailer(@RequestParam(value = "id") int id) throws MalformedURLException {
		return MoviesAPI.getTrailer(id);
	}
	
	/**
	 * Returns a list of movies based on an specific movie.
	 * @param idPelicula
	 * @param res
	 * @return ArrayList Movie
	 * @throws MalformedURLException
	 */
	@RequestMapping("/getRecomendations")
	@CrossOrigin
	public  ArrayList<Movie> getRecomendations(@RequestParam(value = "idPelicula") int idPelicula, ServletResponse res) throws MalformedURLException {
		return MoviesAPI.getRecommendations(idPelicula);
	}
	
	/**
	 * Returns the las popular movies.
	 * @return ArrayList Movie
	 * @throws MalformedURLException
	 */
	@RequestMapping("/getPopular")
	@CrossOrigin
	public  ArrayList<Movie> getPopular() throws MalformedURLException {
		return MoviesAPI.getPopular();
	}

	/**
	 * Returns the list of the genres available in the API.
	 * @return ArrayList Genre
	 * @throws MalformedURLException
	 */
	@RequestMapping("/getGeneros")
	@CrossOrigin
	public  ArrayList<Genre> getGeneros() throws MalformedURLException {
		return MoviesAPI.getGeneros();
	}
	
	/**
	 * Returns a list of movies based on a specific genre.
	 * @param idGenre
	 * @return ArrayList Movie
	 * @throws MalformedURLException
	 */
	@RequestMapping("/getMoviesOf")
	@CrossOrigin
	public  ArrayList<Movie> getMoviesOf(int idGenre) throws MalformedURLException {
		return MoviesAPI.getMoviesOf(idGenre);
	}
	
	/**
	 * Returns the Cast list of a Movie.
	 * @param idPelicula
	 * @param res
	 * @return ArrayList Cast
	 * @throws MalformedURLException
	 */
	@RequestMapping("/getCastList")
	public ArrayList<Cast> getCastList(@RequestParam(value = "idPelicula") int idPelicula, ServletResponse res) throws MalformedURLException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		return MoviesAPI.getCastList(idPelicula);
	}
	
	/**
	 * Returns similar movies of a movie.
	 * @param idMovie
	 * @return ArrayList Movie
	 * @throws MalformedURLException
	 */
	@RequestMapping("/getSimilarMovies")
	public ArrayList<Movie> getSimilarMovies(@RequestParam(value = "idMovie") int idMovie, ServletResponse res) throws MalformedURLException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		return MoviesAPI.getSimilarMovies(idMovie);
	}

}
