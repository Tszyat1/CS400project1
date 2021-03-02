// --== CS400 File Header Information ==--
// Name: Simon Fu
// Email: hfu52@wisc.edu
// Team: AF blue
// Role: Frontend developer
// TA: Mu Cai
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class is a Movie Mapper. This should expose useful commands, provide clear feedback, and
 * help the user with feedback about correct program usage and any errors that might arise. There are three modes:
 * base mode, genre mode, and rating mode that users could use to find movies.
 */
public class Frontend {
    public Backend backend;
    private int currentMode = 0;// 0 for base mode, 1 for genre mode, 2 for rating mode
    private boolean exitProgram = false;
    private Scanner scanner = new Scanner(System.in);

    /**
     * This constructor instantiates a frontend with the command line arguments
     * passed to the backend when started by the user. The arguments are expected
     * to contain the path to the data file that the backend will read in.
     *
     * @param args CSV file path for the program
     */
    public Frontend(String[] args) {
        backend = new Backend(args);
        this.scanner = new Scanner(System.in);
        exitProgram = false;
        if (backend == null) {
            System.out.println("Error: fail to instantiate the backend. Wrong string arguments");
        }
    }

    /**
     * The base mode is the mode that Movie Mapper will be in when started. It will display a welcome message, and
     * then a list of the top 3 (by average rating) selected movies. This list may be empty or contain less than 3
     * movies if no 3 movies are in the selection set. users will be able to type in numbers to navigate through the
     * list of selected movies. The character ‘g’ will be the command to enter the genre selection mode, character
     * ‘r’ will get the user to the rating selection mode. When the user types ‘x’, the program will exit.
     */
    private void baseMode() {
        for (int i = 0; i < 11; i++) {
            backend.addAvgRating(i + "");
        }
        List<MovieInterface> moviesList = backend.getThreeMovies(0);
        System.out.println("Currently in base mode");
        //print top 3 movies
        if (moviesList != null) {
            System.out.println("Here are the top movies");
            for (int i = 0; i < backend.getNumberOfMovies() && i < moviesList.size(); i++) {
                System.out.print("Top " + (i + 1) + " : ");
                System.out.println(moviesList.get(i).toString());
            }
        }
        while (true) {
            //prompt user about commands.
            System.out.println("======\nYou can type in numbers bigger than 0 and smaller or equal to " +
                    backend.getNumberOfMovies() + " to scroll through the list\nPress 'g' to genre selection mode" +
                    ". \nPress 'r' to rating selection mode. \nPress 'x' " +
                    "to exit the program");
            String input = "";
            if (scanner.hasNextLine()) {
                input = scanner.nextLine();
            }
            //Decides if the input is a letter or not
            boolean isLetter = false;
            for (char c :
                    input.toCharArray()) {
                if (Character.isLetter(c)) {
                    isLetter = true;
                    break;
                }
            }
            //Press 'x' exit the program
            if (isLetter) {
                if (input.equals("x")) {
                    exitProgram = true;
                    return;
                    //Press 'g' Go to genre mode
                } else if (input.equals("g")) {
                    currentMode = 1;
                    return;
                }
                //Press 'r' Go to rating mode
                else if (input.equals("r")) {
                    currentMode = 2;
                    return;
                    //Other letters are invalid inputs
                } else {
                    System.out.println("Invalid input!");
                    continue;
                }
            }
            //When the input is not letter, try to convert it into Integer
            Integer intInput;
            try {
                intInput = Integer.parseInt(input);
                //If cannot convert to Integer, catch the exception and input again
            } catch (Exception e) {
                System.out.println("Invalid input!");
                continue;
            }
            //If the input falls out of the bound, invalid input
            if (intInput <= 0 || intInput > backend.getNumberOfMovies()) {
                System.out.println("Invalid input when scrolling the list.");
                continue;
            }
            //Scroll through the movies list by displaying three movies after the index
            moviesList = backend.getThreeMovies(intInput - 1);
            for (int i = 0; i < moviesList.size(); i++) {
                System.out.println(intInput + " :" + moviesList.get(i).toString());
                intInput++;
            }
        }

    }

    /**
     * The genre selection mode lets users select and deselect genres based on which movies are retrieved from the
     * database.any number from the list of genres will select or deselect the respective genre. The command ‘x’ will
     * cause the program to return to the base mode.
     */
    private void genreMode() {
        System.out.println("======\nCurrently in Genre selection mode.");
        while (true) {
            List<String> genresSelected = backend.getGenres();
            List<String> allGenres = backend.getAllGenres();
            //print out all genres, and mark the genres that are selected with *
            System.out.println("Here is the list of all genres. Selected Genres are marked with *:");
            for (int i = 0; i < allGenres.size(); i++) {
                String genreName = allGenres.get(i);
                if (genresSelected.contains(genreName)) {
                    System.out.println(" * " + i + " : " + allGenres.get(i));
                } else {
                    System.out.println(i + " : " + allGenres.get(i));
                }
            }
            System.out.println("======\nEnter the index of genres to select or deselect them\nPress 'x' to exit the " +
                    "genre " +
                    "selection mode");
            String input = "";
            if (scanner.hasNextLine()) {
                input = scanner.nextLine();
            }
            //Decides if the input is a letter or not
            boolean isLetter = false;
            for (char c :
                    input.toCharArray()) {
                if (Character.isLetter(c)) {
                    isLetter = true;
                    break;
                }
            }
            //If the input is a letter
            if (isLetter) {
                //if the input equals 'x', exit to base mode
                if (input.equals("x")) {
                    currentMode = 0;
                    //Remove all genre selections previously
                    List<String> copyOfGenresSelected = new ArrayList<>();
                    for (String genres :
                            genresSelected) {
                        copyOfGenresSelected.add(genres);
                    }
                    for (String genres :
                            copyOfGenresSelected) {
                        backend.removeGenre(genres);
                    }
                    return;
                    //If not, invalid input
                } else {
                    System.out.println("Invalid input!");
                    continue;
                }
            }
            //If the input is not a letter, try to convert it into int
            Integer intInput;
            try {
                intInput = Integer.parseInt(input);
                //If cannot convert to int, invalid input
            } catch (Exception e) {
                System.out.println("Invalid input!");
                continue;
            }
            //If the int is out of bound, invalid input
            if (intInput < 0 || intInput >= allGenres.size()) {
                System.out.println("Invalid input!");
                continue;
            }
            String genre = allGenres.get(intInput);
            //see if the genre user wants to select or deselect is already selected or not
            if (genresSelected.contains(genre)) {
                //if it is selected already, remove it from selection
                backend.removeGenre(genre);
            } else {
                //if not, add it to selection
                backend.addGenre(genre);
            }
            int count = 1;
            System.out.println("Here are the movies selected with the genres");
            for (int j = 0; j < backend.getNumberOfMovies(); j = j + 3) {
                List<MovieInterface> moviesList = backend.getThreeMovies(j);
                if (moviesList != null) {
                    for (int i = 0; i < backend.getNumberOfMovies() && i < moviesList.size(); i++) {
                        System.out.print("Top " + count + " : ");
                        count++;
                        System.out.println(moviesList.get(i).toString());
                    }
                }
            }


        }

    }

    /**
     * Rating selection Mode allows users to select and deselect ratings.The mode will display a numbered list of
     * ratings that users can select.numbers will select / deselect ratings and ‘x’ will return the user to the base
     * mode.
     */
    private void rateMode() {
        System.out.println("======\nCurrently in Rating selection mode");
        for (int i = 0; i < 11; i++) {
            backend.addAvgRating(i + "");
        }
        while (true) {
            List<String> ratingsSelected = backend.getAvgRatings();
            System.out.println("Ratings are from 0 to 10\nHere is the list of all ratings\nselected ratings are " +
                    "marked with '*':");
            //show the list of all ratings, and selected ratings are marked with *;
            for (int i = 0; i < 11; i++) {
                if (ratingsSelected.contains(i + "")) {
                    System.out.println(" * Rating " + i);
                } else {
                    System.out.println("Rating " + i);
                }
            }
            System.out.println("======\nEnter numbers from 0 to 10 to select or deselect ratings\nPress 'x' to exit " +
                    "rating " +
                    "selection mode");
            String input = "";
            if (scanner.hasNextLine()) {
                input = scanner.nextLine();
            }
            //Decides if the input is a letter or not
            boolean isLetter = false;
            for (char c :
                    input.toCharArray()) {
                if (Character.isLetter(c)) {
                    isLetter = true;
                    break;
                }
            }
            //if the input is a letter
            if (isLetter) {
                //see if it equals 'x'. If does, go back to base mode.
                if (input.equals("x")) {
                    currentMode = 0;
                    //Remove all selections made previously
                    List<String> copyOfRatingsSelected = new ArrayList<>();
                    for (String ratings :
                            ratingsSelected) {
                        copyOfRatingsSelected.add(ratings);
                    }
                    for (String ratings :
                            copyOfRatingsSelected) {
                        backend.removeAvgRating(ratings);
                    }
                    return;
                    //if the input is letter other than 'x', invalid input
                } else {
                    System.out.println("Invalid input!");
                    continue;
                }
            }
            //If the input is not a letter, try to convert it into Integer
            Integer intInput;
            try {
                intInput = Integer.parseInt(input);
                //If fail to convert it, invalid input
            } catch (Exception e) {
                System.out.println("Invalid input!");
                continue;
            }
            //If the input is out of bound, invalid input
            if (intInput < 0 || intInput > 10) {
                System.out.println("Invalid input!");
                continue;
            }
            //If the rating is already selected, remove it from selection
            if (ratingsSelected.contains(intInput + "")) {
                backend.removeAvgRating(intInput + "");
            } else {
                //if not, add it to selection
                backend.addAvgRating(intInput + "");
            }
            int count=1;
            System.out.println("Here are the movies selected with the ratings");
            for (int j = 0; j < backend.getNumberOfMovies(); j = j + 3) {
                List<MovieInterface> moviesList = backend.getThreeMovies(j);
                if (moviesList != null) {
                    for (int i = 0; i < backend.getNumberOfMovies() && i < moviesList.size(); i++) {
                        System.out.print("Top " + count + " : ");
                        count++;
                        System.out.println(moviesList.get(i).toString());
                    }
                }
            }
        }

    }

    /**
     * The method that runs the program. Starting with base mode and a welcome message.
     */
    public void run() {
        //print welcome message
        System.out.println("Welcome to the Movie Mapper program");
        while (this.exitProgram == false) {
            if (this.currentMode == 0) {
                this.baseMode();
            }
            if (this.currentMode == 1) {
                this.genreMode();
            }
            if (this.currentMode == 2) {
                this.rateMode();
            }
        }
        this.scanner.close();
    }

    /**
     * THe main method for frontend that would initialize the program
     *
     * @param args the CSV file path
     */
    public static void main(String[] args) {
        //Initialize frontend
        Frontend frontend;
        frontend = new Frontend(args);
        frontend.run();
    }
}
