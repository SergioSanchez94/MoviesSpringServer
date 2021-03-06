package com.sergiosanchez.connections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sergiosanchez.configuration.Config;
import com.sergiosanchez.movies.Cast;
import com.sergiosanchez.movies.Genre;
import com.sergiosanchez.movies.Movie;
import com.sergiosanchez.movies.Response;

/**
 * Clase encargada de realizar peticiones a la API externa de información de
 * películas
 * 
 * @author Sergio Sanchez
 *
 */
public class MoviesAPI {

	// Constantes a compartir por las distinas clases
	public static String IP;
	public static String NAME;
	public static String DOMINIO;

	/**
	 * Obtiene un array de objetos Movie con la información de la URL de la API que
	 * le paseamos por parámetro
	 * 
	 * @param direccionAPI
	 * @return ArrayList<Movie>
	 */
	public static ArrayList<Movie> getMovies(String busquedaURL, String year) {

		busquedaURL = busquedaURL.replace(" ", "%20");
		if (busquedaURL.contains("(") && busquedaURL.contains(")")) {
			busquedaURL = busquedaURL.substring(0, busquedaURL.indexOf("("));
		}
		if (busquedaURL.contains("[") && busquedaURL.contains("]")) {
			busquedaURL = busquedaURL.substring(0, busquedaURL.indexOf("["));
		}

		String direccionAPI = "https://api.themoviedb.org/3/search/movie?api_key=" + Config.getAPIKEY()
				+ "&language=es-ES&query=" + busquedaURL + "&page=1&include_adult=false&region=Spain&year=" + year;
		ArrayList<Movie> movies = new ArrayList<Movie>();

		try {
			URL url = new URL(direccionAPI);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;

			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((output = br.readLine()) != null) {
				JSONObject jObject;
				Movie movie;
				try {
					jObject = new JSONObject(output);
					JSONArray results = jObject.getJSONArray("results");

					for (int i = 0; i < results.length(); i++) {
						movie = new Movie();
						JSONObject resultado = results.getJSONObject(i);
						movie.setName(resultado.getString("title"));
						movie.setId(resultado.getInt("id"));
						movie.setDescription(resultado.getString("overview"));
						movie.setVoteAverage(resultado.getInt("vote_average"));
						movie.setDate(resultado.getString("release_date"));
						movie.setImg("https://image.tmdb.org/t/p/w500/" + resultado.getString("poster_path"));
						movies.add(movie);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} catch (ProtocolException e1) {
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return movies;

	}

	/**
	 * Obtiene la URL del trailer a Youtube de la película
	 * 
	 * @param name
	 * @param year
	 * @return String
	 * @throws MalformedURLException
	 */
	public static Response getTrailer(int id) throws MalformedURLException {

		Response response = new Response(null, null);
		String trailer = "";
		String key = "";
		HttpURLConnection conn = null;

		try {
			URL url = new URL("http://api.themoviedb.org/3/movie/" + id + "/videos?api_key=" + Config.getAPIKEY()
					+ "&language=es-ES");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;

			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			JSONObject jObject;
			while ((output = br.readLine()) != null) {
				try {
					jObject = new JSONObject(output);
					JSONArray results = jObject.getJSONArray("results");
					JSONObject resultado = results.getJSONObject(0);
					key = resultado.getString("key");
					trailer = "https://www.youtube.com/watch?v=" + key;
					response.setService("getTrailer");
					response.setResponse(trailer);

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		conn.disconnect();

		return response;
	}

	/**
	 * Devuelve la lista de actores de una película por su ID
	 * 
	 * @param idPelicula
	 * @return ArrayList<Cast>
	 */
	public static ArrayList<Cast> getCastList(int idPelicula) {

		ArrayList<Cast> castList = new ArrayList<Cast>();

		try {
			URL url = new URL("https://api.themoviedb.org/3/movie/" + idPelicula + "/credits?api_key="
					+ Config.getAPIKEY() + "&language=es-ES&page=1");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;

			while ((output = br.readLine()) != null) {
				JSONObject jObject;
				try {
					jObject = new JSONObject(output);
					JSONArray results = jObject.getJSONArray("cast");

					// Maximo 10 actores
					for (int i = 0; i < 15; i++) {
						Cast cast = new Cast(0, null, null, null);
						JSONObject resultado = results.getJSONObject(i);
						cast.setId(resultado.getInt("cast_id"));
						cast.setCharacter(resultado.getString("character"));
						cast.setActor(resultado.getString("name"));
						cast.setImg("https://image.tmdb.org/t/p/w500" + resultado.getString("profile_path"));
						castList.add(cast);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return castList;

	}

	public static ArrayList<Movie> getPopular() {

		String direccionAPI = "https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key="
				+ Config.getAPIKEY() + "&language=es-ES";
		ArrayList<Movie> movies = new ArrayList<Movie>();

		try {
			URL url = new URL(direccionAPI);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;

			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((output = br.readLine()) != null) {
				JSONObject jObject;
				Movie movie;
				try {
					jObject = new JSONObject(output);
					JSONArray results = jObject.getJSONArray("results");

					for (int i = 0; i < results.length(); i++) {
						movie = new Movie();
						JSONObject resultado = results.getJSONObject(i);
						movie.setName(resultado.getString("title"));
						movie.setId(resultado.getInt("id"));
						movie.setDescription(resultado.getString("overview"));
						movie.setVoteAverage(resultado.getInt("vote_average"));
						movie.setDate(resultado.getString("release_date"));
						movie.setImg("https://image.tmdb.org/t/p/w500/" + resultado.getString("poster_path"));
						movies.add(movie);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} catch (ProtocolException e1) {
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return movies;

	}

	public static ArrayList<Movie> getRecommendations(int idPelicula) {

		String direccionAPI = "https://api.themoviedb.org/3/movie/" + idPelicula + "/recommendations?api_key="
				+ Config.getAPIKEY() + "&language=es-ES";
		ArrayList<Movie> movies = new ArrayList<Movie>();

		try {
			URL url = new URL(direccionAPI);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;

			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((output = br.readLine()) != null) {
				JSONObject jObject;
				Movie movie;
				try {
					jObject = new JSONObject(output);
					JSONArray results = jObject.getJSONArray("results");

					for (int i = 0; i < results.length(); i++) {
						movie = new Movie();
						JSONObject resultado = results.getJSONObject(i);
						movie.setName(resultado.getString("title"));
						movie.setId(resultado.getInt("id"));
						movie.setDescription(resultado.getString("overview"));
						movie.setVoteAverage(resultado.getInt("vote_average"));
						movie.setDate(resultado.getString("release_date"));
						movie.setImg("https://image.tmdb.org/t/p/w500/" + resultado.getString("poster_path"));
						movies.add(movie);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} catch (ProtocolException e1) {
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return movies;

	}

	public static ArrayList<Genre> getGeneros() {

		String direccionAPI = "https://api.themoviedb.org/3/genre/movie/list?language=es-ES&api_key="
				+ Config.getAPIKEY();
		ArrayList<Genre> genres = new ArrayList<Genre>();

		try {
			URL url = new URL(direccionAPI);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;

			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((output = br.readLine()) != null) {
				JSONObject jObject;
				Genre genre;
				try {
					jObject = new JSONObject(output);
					JSONArray genresFound = jObject.getJSONArray("genres");

					for (int i = 0; i < genresFound.length(); i++) {
						genre = new Genre();
						JSONObject genreFound = genresFound.getJSONObject(i);
						genre.setName(genreFound.getString("name"));
						genre.setId(genreFound.getInt("id"));
						genres.add(genre);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} catch (ProtocolException e1) {
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return genres;

	}

	public static ArrayList<Movie> getMoviesOf(int idGenre) {

		String direccionAPI = "https://api.themoviedb.org/3/genre/" + idGenre
				+ "/movies?language=es-ES&sort_by=created_at.asc&api_key=" + Config.getAPIKEY();
		ArrayList<Movie> movies = new ArrayList<Movie>();

		try {
			URL url = new URL(direccionAPI);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;

			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((output = br.readLine()) != null) {
				JSONObject jObject;
				Movie movie;
				try {
					jObject = new JSONObject(output);
					JSONArray results = jObject.getJSONArray("results");

					for (int i = 0; i < results.length(); i++) {
						movie = new Movie();
						JSONObject resultado = results.getJSONObject(i);
						movie.setName(resultado.getString("title"));
						movie.setId(resultado.getInt("id"));
						movie.setDescription(resultado.getString("overview"));
						movie.setVoteAverage(resultado.getInt("vote_average"));
						movie.setDate(resultado.getString("release_date"));
						movie.setImg("https://image.tmdb.org/t/p/w500/" + resultado.getString("poster_path"));
						movies.add(movie);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} catch (ProtocolException e1) {
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return movies;

	}

	public static ArrayList<Movie> getSimilarMovies(int idMovie) {

		String direccionAPI = "https://api.themoviedb.org/3/movie/" + idMovie
				+ "/similar?language=es-ES&page=1&api_key=" + Config.getAPIKEY();
		ArrayList<Movie> movies = new ArrayList<Movie>();

		try {
			URL url = new URL(direccionAPI);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;

			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((output = br.readLine()) != null) {
				JSONObject jObject;
				Movie movie;
				try {
					jObject = new JSONObject(output);
					JSONArray results = jObject.getJSONArray("results");

					for (int i = 0; i < results.length(); i++) {
						movie = new Movie();
						JSONObject resultado = results.getJSONObject(i);
						movie.setName(resultado.getString("title"));
						movie.setId(resultado.getInt("id"));
						movie.setDescription(resultado.getString("overview"));
						movie.setVoteAverage(resultado.getInt("vote_average"));
						movie.setDate(resultado.getString("release_date"));
						movie.setImg("https://image.tmdb.org/t/p/w500/" + resultado.getString("poster_path"));
						movies.add(movie);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} catch (ProtocolException e1) {
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return movies;

	}

	// 1 = Premiere
	// 2 = Theatrical (limited)
	// 3 = Theatrical
	// 4 = Digital
	// 5 = Physical
	// 6 = TV
	public static Response getDigitalReleaseDate(int idMovie) {

		String direccionAPI = "https://api.themoviedb.org/3/movie/" + idMovie + "/release_dates?api_key="
				+ Config.getAPIKEY();
		Response response = new Response("getDigitalReleaseDate", "NOT AVAILABLE");

		String digitalReleaseDate = null;

		try {
			URL url = new URL(direccionAPI);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;

			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((output = br.readLine()) != null) {
				JSONObject jObject;
				try {
					jObject = new JSONObject(output);
					JSONArray results = jObject.getJSONArray("results");

					for (int i = 0; i < results.length(); i++) {
						JSONObject resultado = results.getJSONObject(i);

						JSONArray releases_dates = resultado.getJSONArray("release_dates");

						for (int j = 0; j < releases_dates.length(); j++) {
							JSONObject release_datesObject = releases_dates.getJSONObject(j);

							int type = release_datesObject.getInt("type");

							if (type == 5) {
								digitalReleaseDate = release_datesObject.getString("release_date");

								try {
									DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
											Locale.ENGLISH);
									Date dateDigital;
									dateDigital = format.parse(digitalReleaseDate);

									Date currentDate = new Date();

									if (currentDate.after(dateDigital)) {
										response.setResponse("AVAILABLE");
									} else {

										// Calendar cal = Calendar.getInstance();
										// cal.setTime(dateDigital);
										// int year = cal.get(Calendar.YEAR);
										// int month = cal.get(Calendar.MONTH);
										// int day = cal.get(Calendar.DAY_OF_MONTH);
										//
										// response.setResponse(day + "-" + (month + 1) + "-" + year);
									}

								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								break;

							}

						}

					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		} catch (ProtocolException e1) {
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return response;

	}
}
