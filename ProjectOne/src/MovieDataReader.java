// --== CS400 File Header Information ==--
// Name: Wong Tsz Yat
// Email: twong42@wisc.edu
// Team: AF blue
// Role: Data Wrangler
// TA: Mu
// Lecturer: Gary Dahl
// Notes to Grader: I define private methods in Reader class to help me skip and read the data

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * This class implement the MovieDataReaderInterface to enable the Back End Engineer to load a data file.
 */
public class MovieDataReader implements MovieDataReaderInterface {

	/**
	 * This method reads in the input file, and returns a list of Movies object from the data file
	 * @param inputFileReader data file to reads in
	 * @return a list contains all of the Movie objects read in from the data file
	 * @throws IOException when the file cannot be opened for reading or there is some error while reading the file
	 * @throws DataFormatException when there is an error because the file does not have the correct format
	 */
	@Override
	public List<MovieInterface> readDataSet(Reader inputFileReader) throws IOException, DataFormatException{
		Scanner fileToRead = new Scanner(inputFileReader);
		ArrayList<MovieInterface> movies = new ArrayList<MovieInterface>();
		String line = fileToRead.nextLine();
		while (fileToRead.hasNextLine()) {
			line = fileToRead.nextLine();
			if (line.isBlank())
				break;
			String[] str = line.split(",");
			if (str.length < 13)
				throw new DataFormatException("The line contains less columns than the header line in the file!!!");
			Scanner readLines = new Scanner(line);
			Movie movieToAdd = new Movie();
			readLines.useDelimiter(",");
			movieToAdd.setTitle(titleSetter(readLines)); // sets the title
			skipHelper(readLines); // skips the original_title
			int year = Integer.parseInt(readLines.next());
			movieToAdd.setYear(year); // sets the year
			movieToAdd.setGenres(genresSetter(readLines)); // sets the genres
			readLines.next(); // skips the duration
			skipHelper(readLines); // skips the country
			skipHelper(readLines); // skips the language
			movieToAdd.setDirector(directorSetter(readLines)); // sets the director
			skipHelper(readLines); // skips the writers
			skipHelper(readLines); // skips the production_company
			skipHelper(readLines); // skips the actors
			movieToAdd.setDescription(descriptionSetter(readLines)); // sets the description
			readLines.useDelimiter("\n");
			String avg = readLines.next().substring(1);
			if (!isNumeric(avg))
				throw new DataFormatException("The provided avg_vote is not a number!!!");
			float avgVote = Float.parseFloat(avg);
			movieToAdd.setAvgVote(avgVote);  // sets the avg_vote
			movies.add(movieToAdd);
			readLines.close();
		}
		fileToRead.close();
		return movies;
	}

	/**
	 * Private helper function that reads and sets the title of this Movie
	 * @param file scanner file to read
	 * @return A String representing the title of this Movie
	 * @throws DataFormatException if the title is in incorrect format
	 */
	private static String titleSetter(Scanner file) throws DataFormatException {
		String title;
		String temp = file.next();
		if (temp.substring(0, 1).equals("\"")) {
			file.useDelimiter("\"");
			title = temp.substring(1) + file.next();
			if (isNumeric(title))
				throw new DataFormatException("The provided Title is in incorrect format!");
			file.useDelimiter(",");
			file.next();
			return title;
		}
		title = temp;
		if (isNumeric(title))
			throw new DataFormatException("The provided Title is in incorrect format!");
		return title;
	}

	/**
	 * Private helper function to reads and sets the genres of this Movie
	 * @param file scanner file to read in
	 * @return a List of String representing the genres of this Movie
	 * @throws DataFormatException if the genres is in incorrect format
	 */
	private static List<String> genresSetter(Scanner file) throws DataFormatException{
		String genres;
		String[] g;
		List<String> gen;
		String temp = file.next();
		if (temp.substring(0, 1).equals("\"")) {
			file.useDelimiter("\"");
			genres = temp.substring(1) + file.next();
			g = genres.split(", ");
			gen = Arrays.asList(g.clone());
			file.useDelimiter(",");
			file.next();
			for (String s : gen){
				if (isNumeric(s))
					throw new DataFormatException("The provided genres is in incorrect format!");
			}
			return gen;
		}
		g = new String[1];
		g[0] = temp;
		gen = Arrays.asList(g.clone());
		for (String s : gen){
			if (isNumeric(s))
				throw new DataFormatException("The provided genres is in incorrect format!");
		}
		return gen;
	}

	/**
	 * Private helper function that reads and sets the director of this Movie
	 * @param file scanner file to read
	 * @return A String representing the director of this Movie
	 * @throws DataFormatException if the director is in incorrect format
	 */
	private static String directorSetter(Scanner file) throws DataFormatException{
		String director;
		String temp = file.next();
		if (temp.equals(""))
			return null;
		if (temp.substring(0, 1).equals("\"")) {
			file.useDelimiter("\"");
			director = temp.substring(1) + file.next();
			file.useDelimiter(",");
			file.next();
			if (isNumeric(director))
				throw new DataFormatException("The provided director is in incorrect format!");
			return director;
		}
		director = temp;
		if (isNumeric(director))
			throw new DataFormatException("The provided director is in incorrect format!");
		return director;
	}

	/**
	 * Private helper function that reads and sets the description of this Movie
	 * @param file scanner file to read
	 * @return A String representing the description of this Movie
	 * @throws DataFormatException if the description is in incorrect format
	 */
	private static String descriptionSetter(Scanner file) throws DataFormatException{
		String description;
		String temp = file.next();
		if (!temp.contains("\"")) {
			description = temp;
			if (isNumeric(description))
				throw new DataFormatException("The provided description is in incorrect format!");
			return description;
		}
		file.useDelimiter("\"");
		String quoteInside = file.next();
		description = temp.substring(1) + quoteInside;
		file.useDelimiter(",");
		String s = file.next();
		if (s.length() > 3) {
			if (s.contains(".")) {
				description += s;
				if (isNumeric(description))
					throw new DataFormatException("The provided description is in incorrect format!");
				return description;
			}
			file.useDelimiter("\"");
			description += s + file.next();
			file.useDelimiter(",");
			description += file.next();
			file.useDelimiter(",");
		}
		if (isNumeric(description))
			throw new DataFormatException("The provided description is in incorrect format!");
		return description;
	}

	/**
	 * Private helper function that helps skip unnecessary data
	 * @param file scanner file to reads in
	 */
	private static void skipHelper(Scanner file) {
		String temp = file.next();
		if (temp.equals(""))
			return;
		if (temp.substring(0, 1).equals("\"")) {
			file.useDelimiter("\"");
			file.next();
			file.useDelimiter(",");
			file.next();
			return;
		}
		file.useDelimiter(",");
		return;
	}

	/**
	 * Private helper method to check if the input str is a number
	 * @param str
	 * @return true if the input String represent a number, false otherwise
	 */
	private static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch(NumberFormatException e){
			return false;
		}
	}
}

