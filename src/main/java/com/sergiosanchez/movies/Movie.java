package com.sergiosanchez.movies;

import java.util.ArrayList;

/**
 * Clase objeto que almacena la información de una película
 * @author Sergio Sanchez
 *
 */
public class Movie {

	int id;
	String name;
	String date;
	String description;
	String quality;
	String url;
	String img;
	String size;
	String trailer;
	int voteAverage;
	ArrayList<Cast> casting;
	
	/**
	 * @param name
	 * @param url
	 * @param img 
	 */
	public Movie(int id, String name, String date, String description, String quality, String url, String img, String size, String trailer, int voteAverage, ArrayList<Cast> casting) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.description = description;
		this.url = url;
		this.img = img;
		this.quality = quality;
		this.size = size;
		this.trailer = trailer;
		this.voteAverage = voteAverage;
		this.casting = casting;
	}
	
	public Movie(){}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the quality
	 */
	public String getQuality() {
		return quality;
	}

	/**
	 * @param quality the quality to set
	 */
	public void setQuality(String quality) {
		this.quality = quality;
	}

	/**
	 * @return the img
	 */
	public String getImg() {
		return img;
	}

	/**
	 * @param img the img to set
	 */
	public void setImg(String img) {
		this.img = img;
	}

	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * @return the trailer
	 */
	public String getTrailer() {
		return trailer;
	}

	/**
	 * @param trailer the trailer to set
	 */
	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}

	/**
	 * @return the voteAverage
	 */
	public int getVoteAverage() {
		return voteAverage;
	}

	/**
	 * @param i the voteAverage to set
	 */
	public void setVoteAverage(int i) {
		this.voteAverage = i;
	}

	/**
	 * @return the casting
	 */
	public ArrayList<Cast> getCasting() {
		return casting;
	}

	/**
	 * @param casting the casting to set
	 */
	public void setCasting(ArrayList<Cast> casting) {
		this.casting = casting;
	}
	
}
