import java.util.List;

// --== CS400 File Header Information ==--
// Author: CS400 Course Staff
// Email: heimerl@cs.wisc.edu / dahl@cs.wisc.edu
// Notes: This interface is part of the starter archive for Projecct One
//        in spring 2021.
public interface MovieInterface extends Comparable<MovieInterface> {
	
	public String getTitle();
	public Integer getYear();
	public List<String> getGenres();
	public String getDirector();
	public String getDescription();
	public Float getAvgVote();
	
	// from super interface Comparable
	public int compareTo(MovieInterface otherMovie);

}
