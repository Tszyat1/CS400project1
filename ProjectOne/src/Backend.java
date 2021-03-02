//--== CS400 File Header Information ==--
//Name: Wenjia Li
//Email: wli498@wisc.edu
//Team: blue
//Role: Back End Developer
//TA: Mu Cai
//Lecturer: Florian Heimerl
//Notes to Grader: <optional extra notes>

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.io.FileReader;
import java.io.Reader;

public class Backend implements BackendInterface {

	public List<String> SetGenre = new ArrayList<String>();

	public List<String> SetRating = new ArrayList<String>();

	public HashTableMap<String, ArrayList<MovieInterface>> genreToMovie = new HashTableMap<String, ArrayList<MovieInterface>>();

	public HashTableMap<String, ArrayList<MovieInterface>> scoreToMovie = new HashTableMap<String, ArrayList<MovieInterface>>();

	List<MovieInterface> movies = null;

	// ---------------------constructors-----------------------------

	public Backend(String args[]) {

		try {
			Reader r = new FileReader(args[0]);
			read(r);
		} catch (Exception e) {
			// file is invalid
			System.out.println(e);
		}
	}

	public Backend(Reader r) {
		read(r);
	}
	
	// a helper method used to read and save in hash
	private void read(Reader r) {
		MovieDataReaderInterface reader = new MovieDataReader();

		try {
			movies = reader.readDataSet(r);
		} catch (Exception e) {
			// file is invalid
			System.out.println(e);
		}
		//save in hash
		for (MovieInterface movie : movies) {
			for (String genre : movie.getGenres()) {
				if (!genreToMovie.containsKey(genre)) {
					genreToMovie.put(genre, new ArrayList<MovieInterface>());
				}
				genreToMovie.get(genre).add(movie);
			}

			String score = String.valueOf(movie.getAvgVote().intValue());
			if (!scoreToMovie.containsKey(score)) {
				scoreToMovie.put(score, new ArrayList<MovieInterface>());
			}
			scoreToMovie.get(score).add(movie);
		}
	}


	// ----------------------methods----------------------------------
	public void addGenre(String genre) {
		if (SetGenre.indexOf(genre) == -1) {
			SetGenre.add(genre);
		}
	}

	public void addAvgRating(String rating) {
		if (SetRating.indexOf(rating) == -1) {
			SetRating.add(rating);
		}
	}

	public void removeGenre(String genre) {
		SetGenre.remove(genre);
	}

	public void removeAvgRating(String rating) {
		SetRating.remove(rating);
	}

	public List<String> getGenres() {
		return SetGenre;
	}

	public List<String> getAvgRatings() {
		return SetRating;
	}

	public int getNumberOfMovies() {
		return getFilteredMovies().size();
	}

	public List<String> getAllGenres() {
		List<String> allGenre = new ArrayList<String>();

		for (MovieInterface movie : movies) {
			for (String genre : movie.getGenres()) {
				if (allGenre.indexOf(genre) == -1) {
					allGenre.add(genre);
				}
			}
		}

		return allGenre;
	}

	public List<MovieInterface> getThreeMovies(int startingIndex) {
		List<MovieInterface> movies = getFilteredMovies();
		//make selected movie in order
		movies.sort(new Comparator<MovieInterface>() {
			public int compare(MovieInterface M1, MovieInterface M2) {
				if (null == M1.getAvgVote()) {
					return 1;
				}
				if (null == M2.getAvgVote()) {
					return -1;
				}
				return M2.getAvgVote().compareTo(M1.getAvgVote());
			}
		});
		
		int toIndex = startingIndex + 3;
		//check if it have three element, if not use less elements
		if (movies.size() <= toIndex) {
			toIndex = movies.size();
		}
		
		return movies.subList(startingIndex, toIndex);

	}
	
	//-----------------helper methods------------------------
	
	//to pick out filtered movie
	private List<MovieInterface> getFilteredMovies() {
		List<MovieInterface> FMovies = new ArrayList<MovieInterface>();
		
		//if user have selected rating, then get all movie filtered
		if(SetRating.size() != 0) {
			FMovies.clear();
			for (int i = 0; i < SetRating.size(); i++) {
				for (MovieInterface movie : movies) {
					String score = String.valueOf(SetRating.get(i));
					if (scoreToMovie.containsKey(score)) {
						if (scoreToMovie.get(score).contains(movie)) {
							if(!FMovies.contains(movie)) {
								FMovies.add(movie);
							}		
						}
					}
				}
			}
		}
		
		
		//if user have selected  genre, then get all movies filtered
		if(SetGenre.size() != 0) {
			FMovies.clear();
			for (String genre : SetGenre) {
				if (genreToMovie.containsKey(genre)){
					//get movies of selected genre from hash table				
					for(MovieInterface movie : genreToMovie.get(genre)) {
						if(!FMovies.contains(movie)) {
							FMovies.add(movie);
						}
						//check if these movie contains all genre,if not remove it
						for(String mgenre : SetGenre) {
							if (!movie.getGenres().contains(mgenre)) {
								FMovies.remove(movie);
							}
						}
					}
				}
			}
		}	
		


		

		return FMovies;
	}


}
