/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import movierecsys.be.Movie;

/**
 *
 * @author pgn
 */
public class MovieDAO implements IMovieRepository
{

    private static final String MOVIE_SOURCE = "data/movie_titles.txt";

    /**
     * Gets a list of all movies in the persistence storage.
     *
     * @return List of movies.
     * @throws java.io.IOException 
     */
    @Override
    public List<Movie> getAllMovies() throws IOException
    {
        List<Movie> allMovies = new ArrayList<>();
        String source = "data/movie_titles.txt";
        File file = new File(source);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) //Using a try with resources!
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                if (!line.isEmpty())
                {
                    try
                    {
                        Movie mov = stringArrayToMovie(line);
                        allMovies.add(mov);
                    } catch (Exception ex)
                    {
                        //Do nothing. Optimally we would log the error.
                    }
                }
            }
        }
        return allMovies;
    }

    /**
     * Reads a movie from the comma separated line.
     *
     * @param line the comma separated line.
     * @return The representing Movie object.
     * @throws NumberFormatException
     */
    private Movie stringArrayToMovie(String line)
    {
        String[] arrMovie = line.split(",");

        int id = Integer.parseInt(arrMovie[0]);
        int year = Integer.parseInt(arrMovie[1]);
        String title = arrMovie[2];

        Movie mov = new Movie(id, year, title);
        return mov;
    }

    /**
     * Creates a movie in the persistence storage.
     *
     * @param releaseYear The release year of the movie
     * @param title The title of the movie
     * @return The object representation of the movie added to the persistence
     * storage.
     */
    @Override
    public Movie createMovie(int releaseYear, String title) throws IOException
    {
        Path path = new File(MOVIE_SOURCE).toPath();
        int id;
        try (BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.SYNC, StandardOpenOption.APPEND, StandardOpenOption.WRITE))
        {
            id = getNextAvailableMovieID();
            bw.newLine();
            bw.write(id + "," + releaseYear + "," + title);
        }
        return new Movie(id, releaseYear, title);
    }

    /**
     * Examines all stored movies and returns the next available highest ID.
     *
     * @return
     * @throws IOException
     */
    @Override
    public int getNextAvailableMovieID() throws IOException
    {
        List<Movie> allMovies = getAllMovies();
        int highId = allMovies.get(allMovies.size() - 1).getId();
        return highId + 1;
    }

    /**
     * Deletes a movie from the persistence storage.
     *
     * @param movie The movie to delete.
     */
    @Override
    public void deleteMovie(Movie movie) throws IOException
    {
        String tempFile = "temp.txt";
        File oldFile = new File(MOVIE_SOURCE);
        File newFile = new File(tempFile);
        List<Movie> newMovieList = new ArrayList();
        List<Movie> oldMovieList = getAllMovies();
        try
        {
            for (int i = 0; i < oldMovieList.size(); i++) {
                if (oldMovieList.get(i).getId() != movie.getId()) {
                    newMovieList.add(oldMovieList.get(i));
                }
            }
            FileWriter fw = new FileWriter(tempFile);
            BufferedWriter bw = new BufferedWriter(fw);
            for(Movie move : newMovieList) {
                bw.write(move.getId() + "," + move.getYear() + "," + move.getTitle());
                bw.newLine();
            }
            bw.flush();
            bw.close();
            oldFile.delete();
            File dump = new File(MOVIE_SOURCE);
            newFile.renameTo(dump);
            
        }
        catch(IOException e){
            System.out.println("Something went wrong");
        }
    }

    /**
     * Updates the movie in the persistence storage to reflect the values in the
     * given Movie object.
     *
     * @param movie The updated movie.
     */
    @Override
    public void updateMovie(Movie movie) throws IOException
    {
        String tempFile = "temp.txt";
        File oldFile = new File(MOVIE_SOURCE);
        File newFile = new File(tempFile);
        List<Movie> oldMovieList = getAllMovies();
        try {
            FileWriter fw = new FileWriter(tempFile);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Movie move : oldMovieList) {
                if (movie.getId() == move.getId()) {
                    bw.write(movie.getId() + "," + movie.getYear() + "," + movie.getTitle());
                    bw.newLine();
                }else {
                    bw.write(move.getId() + "," + move.getYear() + "," + move.getTitle());
                    bw.newLine();
                }  
            }
            bw.flush();
            bw.close();
            oldFile.delete();
            File dump = new File(MOVIE_SOURCE);
            newFile.renameTo(dump);
        }
        catch (Exception x) {
            System.out.println("Something went wrong");
        }
    }

    /**
     * Gets a the movie with the given ID.
     *
     * @param id ID of the movie.
     * @return A Movie object.
     */
    @Override
    public Movie getMovie(int id) throws IOException
    {
        List<Movie> movieID = getAllMovies();
        for (int i = 0; i < movieID.size(); i++){
        if (id == movieID.get(i).getId()) {
            return movieID.get(i);
        }
    }
        throw new IllegalArgumentException("No movie with the ID " + id + " was found.");
}
}