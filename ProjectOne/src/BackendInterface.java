import java.util.List;

// --== CS400 File Header Information ==--
// Author: CS400 Course Staff
// Email: heimerl@cs.wisc.edu / dahl@cs.wisc.edu
// Notes: This interface is part of the starter archive for Projecct One
//        in spring 2021.
public interface BackendInterface {
	
	public void addGenre(String genre);
	public void addAvgRating(String rating);
	public void removeGenre(String genre);
	public void removeAvgRating(String rating);
	public List<String> getGenres();
	public List<String> getAvgRatings();
	public int getNumberOfMovies();
	public List<String> getAllGenres();
	public List<MovieInterface> getThreeMovies(int startingIndex);

}
