// --== CS400 File Header Information ==--
// Name: Wong Tsz Yat
// Email: twong42@wisc.edu
// Team: AF blue
// Role: Data Wrangler
// TA: Mu
// Lecturer: Gary Dahl
// Notes to Grader: I define private methods in Reader class to help me skip and read the data

import java.util.List;

/**
 * This class represents each movie object that implements the MovieInterface
 * Each Movie object will have properties title, year, genres, directors, description, and avgVote
 */
public class Movie implements MovieInterface {

	// instance fields
	private String title;
	private Integer year;
	private List<String> genres;
	private String director;
	private String description;
	private float avgVote;

	/**
	 * Accessor to get the title of this Movie
	 *
	 * @return the title of this movie
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * Accessor to get the year of this Movie
	 *
	 * @return the year of this movie
	 */
	@Override
	public Integer getYear() {
		return year;
	}

	/**
	 * Accessor to get the genres of this Movie
	 *
	 * @return a list of Strings representing the genres of this Movie
	 */
	@Override
	public List<String> getGenres() {
		return genres;
	}

	/**
	 * Accessor to get the director of this Movie
	 *
	 * @return the director of this movie
	 */
	@Override
	public String getDirector() {
		return director;
	}

	/**
	 * Accessor to get the description of this Movie
	 *
	 * @return the description of this movie
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * Accessor to get the average vote of this Movie
	 *
	 * @return a float representing the average vote of this movie
	 */
	@Override
	public Float getAvgVote() {
		return avgVote;
	}

	/**
	 * Mutator to set the title of this Movie
	 *
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Mutator to set the year of this Movie
	 *
	 * @param year
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * Mutator to set the genres of this Movie
	 *
	 * @param g
	 */
	public void setGenres(List<String> g) {
		this.genres = g;
	}


	/**
	 * Mutator to set the director of this Movie
	 *
	 * @param director
	 */
	public void setDirector(String director) {
		this.director = director;
	}

	/**
	 * Mutator to set the description of this Movie
	 *
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Mutator to set the average vote of this Movie
	 *
	 * @param avgVote
	 */
	public void setAvgVote(float avgVote) {
		this.avgVote = avgVote;
	}

	/**
	 * This method help to compare the Movie based on their average vote
	 * If sort the Movies, movies will appear in descending order
	 *
	 * @param otherMovie
	 * @return an int indicating the result of the comparison
	 */
	@Override
	public int compareTo(MovieInterface otherMovie) {
		if (this.getTitle().equals(otherMovie.getTitle()) || this.getAvgVote().equals(otherMovie.getAvgVote())) {
			return 0;
			// sort by rating
		} else if (this.getAvgVote() < otherMovie.getAvgVote()) {
			return +1;
		} else {
			return -1;
		}
	}

	/**
	 * This method
	 *
	 * @return a String that contains a structured version of the movie with the attributes listed above
	 */
	@Override
	public String toString() {
		String out = "Title: " + getTitle() + "\n" + "Year: " + getYear() + "\n" + "Genres: " + getGenres() + "\n" +
						"Director: " + getDirector() + "\n" + "Description: " + getDescription() + "\n" + "Average Vote: " + getAvgVote();
		return out;
	}
}
