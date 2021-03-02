// --== CS400 File Header Information ==--
// Name: Simon Fu
// Email: hfu52@wisc.edu
// Team: AF blue
// Role: Frontend developer
// TA: Mu Cai
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.io.*;

/**
 * This class contains a set of tests for the frontend of the Movie Mapper project.
 */
public class TestFrontend {

    /**
     * main Method to run the test
     *
     * @param args
     */
    public static void main(String[] args) {
        (new TestFrontend()).runTests(args);
    }

    /**
     * This method calls all of the test methods in the class and ouputs pass / fail
     * for each test.
     *
     * @param args CSV file path
     */
    public void runTests(String[] args) {
        System.out.print("Test enter 'x' to exit (WARNING: if 'x' does not exit app, test won't exit and run " +
                "indefinitely!): ");
        if (this.enterXToExit(args)) {
            System.out.println("PASSED");
        } else {
            System.out.println("FAILED");
        }
        System.out.print("Test frontend initially lists no movie (WARNING: if 'x' does not exit app, test won't exit " +
                "and run indefinitely!): ");
        if (this.testFrontendInitialOutputNoMovie(args)) {
            System.out.println("PASSED");
        } else {
            System.out.println("FAILED");
        }
        System.out.print("Test 'g' load genre selection screen (WARNING: if pressing 'x' twice from the genre " +
                "selection screen does not exit app, test won't exit and run indefinitely!): ");
        if (this.testFrontendGForGenres(args)) {
            System.out.println("PASSED");
        } else {
            System.out.println("FAILED");
        }
        System.out.print("Test 'r' load rating selection screen (WARNING: if pressing 'x' twice from the genre " +
                "selection screen does not exit app, test won't exit and run indefinitely!): ");
        if (this.testFrontendRForRatings(args)) {
            System.out.println("PASSED");
        } else {
            System.out.println("FAILED");
        }
        System.out.print("Test 'r' load rating selection screen and select one rating (WARNING: if pressing 'x' twice" +
                " from the genre selection screen does not exit app, test won't exit and run indefinitely!): ");
        if (this.testFrontendOneRatingSelected(args)) {
            System.out.println("PASSED");
        } else {
            System.out.println("FAILED");
        }
    }

    /**
     * This test runs the front end and redirects its output to a string. It then
     * passes in 'x' as a command. When the front end exists, the tests succeeds.
     * If 'x' does not exist the app, the test will not terminate (it won't fail
     * explicitely in this case). The test fails explicitely if the front end is
     * not instantiated or if an exception occurs.
     *
     * @param args CSV file path
     * @return true if the test passed, false if it failed
     */
    public boolean enterXToExit(String[] args) {
        PrintStream standardOut = System.out;
        InputStream standardIn = System.in;
        try {
            // set the input stream to our input (with an x to test of the program exists)
            String input = "x";
            InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
            System.setIn(inputStreamSimulator);
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            // set the output to the stream captor to read the output of the front end
            System.setOut(new PrintStream(outputStreamCaptor));
            // instantiate when front end is implemented
            Frontend frontend = new Frontend(args);
            frontend.run();
            // set the output back to standard out for running the test
            System.setOut(standardOut);
            // same for standard in
            System.setIn(standardIn);
            if (frontend == null) {
                // test fails
                return false;
            } else {
                // test passed
                return true;
            }
        } catch (Exception e) {
            // make sure stdin and stdout are set correctly after we get exception in test
            System.setOut(standardOut);
            System.setIn(standardIn);
            e.printStackTrace();
            // test failed
            return false;
        }
    }

    /**
     * This test runs the front end and redirects its output to a string. It then
     * passes in 'x' as a command to exit the app. The test succeeds if the front
     * end does not contain any of the three movies (the movie titles are not in
     * the string captured from the front end) by default. It fails if any of
     * those three titles are present in the string or an exception occurs.
     *
     * @param args CSV file path
     * @return true if the test passed, false if it failed
     */
    public boolean testFrontendInitialOutputNoMovie(String[] args) {
        PrintStream standardOut = System.out;
        InputStream standardIn = System.in;
        try {
            // set the input stream to our input (with an x to test of the program exists)
            String input = "x";
            InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
            System.setIn(inputStreamSimulator);
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            // set the output to the stream captor to read the output of the front end
            System.setOut(new PrintStream(outputStreamCaptor));
            // instantiate when front end is implemented
            Frontend frontend = new Frontend(args);
            frontend.run();
            // set the output back to standard out for running the test
            System.setOut(standardOut);
            // same for standard in
            System.setIn(standardIn);
            String appOutput = outputStreamCaptor.toString();
            if (frontend == null || appOutput.contains("NT Live: Cyrano de Bergerac")
                    || appOutput.contains("Metallica and San Francisco Symphony S&M2")
                    || appOutput.contains("Stanley Stanton")) {
                // test failed
                return false;
            } else {
                // test passed
                return true;
            }
        } catch (Exception e) {
            // make sure stdin and stdout are set correctly after we get exception in test
            System.setOut(standardOut);
            System.setIn(standardIn);
            e.printStackTrace();
            // test failed
            return false;
        }
    }

    /**
     * This test runs the front end and redirects its output to a string. It then
     * passes in 'g' as a command to go to the genre selection mode. It then exists
     * the app by pressing 'x' to go back to the main mode and another 'x' to exit.
     * The test succeeds if the genre selection screen contains all five genres
     * from the data. It fails if any of them are missing, the front end has not
     * been instantiated (is null), or there is an exception.
     *
     * @param args CSV file path
     * @return true if the test passed, false if it failed
     */
    public boolean testFrontendGForGenres(String[] args) {
        PrintStream standardOut = System.out;
        InputStream standardIn = System.in;
        try {
            // set the input stream to our input (with an g to test of the program lists genres)
            String input = "g" + System.lineSeparator() + "x" + System.lineSeparator() + "x";
            InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
            System.setIn(inputStreamSimulator);
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            // set the output to the stream captor to read the output of the front end
            System.setOut(new PrintStream(outputStreamCaptor));
            // instantiate when front end is implemented
            Frontend frontend = new Frontend(args);
            frontend.run();
            // set the output back to standard out for running the test
            System.setOut(standardOut);
            // same for standard in
            System.setIn(standardIn);
            // add all tests to this method
            String appOutput = outputStreamCaptor.toString();
            if (frontend != null && appOutput.contains("Drama")
                    && appOutput.contains("Comedy")
                    && appOutput.contains("Fantasy")
                    && appOutput.contains("Romance")
                    && appOutput.contains("Adventure")) {
                // test passes if all genres from the data are displayed on the screen
                return true;
            } else {
                // test failed
                return false;
            }
        } catch (Exception e) {
            // make sure stdin and stdout are set correctly after we get exception in test
            System.setOut(standardOut);
            System.setIn(standardIn);
            e.printStackTrace();
            // test failed
            return false;
        }
    }

    /**
     * This test runs the front end and redirects its output to a string. It then
     * passes in 'r' as a command to go to the ratings selection mode. It then exists
     * the app by pressing 'x' to go back to the main mode and another 'x' to exit.
     * The test succeeds if the ratings selection screen contains all eleven ratings from 0 to 10
     * . It fails if any of them are missing, the front end has not
     * been instantiated (is null), or there is an exception.
     *
     * @param args CSV file path
     * @return true if the test passed, false if it failed
     */
    public boolean testFrontendRForRatings(String[] args) {
        PrintStream standardOut = System.out;
        InputStream standardIn = System.in;
        try {
            // set the input stream to our input (with an g to test of the program lists genres)
            String input = "r" + System.lineSeparator() + "x" + System.lineSeparator() + "x";
            InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
            System.setIn(inputStreamSimulator);
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            // set the output to the stream captor to read the output of the front end
            System.setOut(new PrintStream(outputStreamCaptor));
            // instantiate when front end is implemented
            Frontend frontend = new Frontend(args);
            frontend.run();
            // set the output back to standard out for running the test
            System.setOut(standardOut);
            // same for standard in
            System.setIn(standardIn);
            // add all tests to this method
            String appOutput = outputStreamCaptor.toString();
            if (frontend != null && appOutput.contains("0")
                    && appOutput.contains("1")
                    && appOutput.contains("2")
                    && appOutput.contains("3")
                    && appOutput.contains("4")
                    && appOutput.contains("5")
                    && appOutput.contains("6")
                    && appOutput.contains("7")
                    && appOutput.contains("8")
                    && appOutput.contains("9")
                    && appOutput.contains("10")) {
                // test passes if all ratings from the data are displayed on the screen
                return true;
            } else {
                // test failed
                return false;
            }
        } catch (Exception e) {
            // make sure stdin and stdout are set correctly after we get exception in test
            System.setOut(standardOut);
            System.setIn(standardIn);
            e.printStackTrace();
            // test failed
            return false;
        }
    }

    /**
     * This test runs the front end and redirects its output to a string. It then
     * passes in 'r' as a command to go to the ratings selection mode. It then selects a rating. It then exists
     * the app by pressing 'x' to go back to the main mode and another 'x' to exit.
     * The test succeeds if the ratings selection screen contains all eleven ratings from 0 to 10, and that the movies
     * with corresponding rating appears on the screen
     * It fails if any of them are missing, the front end has not
     * been instantiated (is null), or there is an exception.
     *
     * @param args CSV file path
     * @return true if the test passed, false if it failed
     */
    public boolean testFrontendOneRatingSelected(String[] args) {
        PrintStream standardOut = System.out;
        InputStream standardIn = System.in;
        try {
            // set the input stream to our input (with an g to test of the program lists genres)
            String input = "r" + System.lineSeparator() + "7" + System.lineSeparator() + "x" + System.lineSeparator() + "x";
            InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
            System.setIn(inputStreamSimulator);
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            // set the output to the stream captor to read the output of the front end
            System.setOut(new PrintStream(outputStreamCaptor));
            // instantiate when front end is implemented
            Frontend frontend = new Frontend(args);
            frontend.run();
            // set the output back to standard out for running the test
            System.setOut(standardOut);
            // same for standard in
            System.setIn(standardIn);
            // add all tests to this method
            String appOutput = outputStreamCaptor.toString();
            if (frontend != null && appOutput.contains("0")
                    && appOutput.contains("1")
                    && appOutput.contains("2")
                    && appOutput.contains("3")
                    && appOutput.contains("4")
                    && appOutput.contains("5")
                    && appOutput.contains("6")
                    && appOutput.contains("7")
                    && appOutput.contains("8")
                    && appOutput.contains("9")
                    && appOutput.contains("10")
                    && !appOutput.contains("The Dirt")) {
                /**
                 * test passes if all ratings from the data are displayed on the screen, and that the movie with the corresponding rating displayed on the screen after the user selects the rating
                 */
                return true;
            } else {
                // test failed
                return false;
            }
        } catch (Exception e) {
            // make sure stdin and stdout are set correctly after we get exception in test
            System.setOut(standardOut);
            System.setIn(standardIn);
            e.printStackTrace();
            // test failed
            return false;
        }
    }

}
