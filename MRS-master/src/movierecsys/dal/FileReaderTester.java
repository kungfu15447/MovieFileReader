/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal;

import java.io.IOException;
import java.util.List;
import movierecsys.be.Movie;

/**
 *
 * @author pgn
 */
public class FileReaderTester
{

    /**
     * Example method. This is the code I used to create the users.txt files.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
        MovieDAO movieDao = new MovieDAO();
<<<<<<< HEAD
        Movie movie = movieDao.getMovie(1501); //Only run this once, or you will get multiple entries!
        System.out.println(movie);
         //Only run this once, or you will get multiple entries!
        Movie movie2 = new Movie(17771, 1966, "Django");
        movieDao.deleteMovie(movie2);
=======

        Movie movie = movieDao.getMovie(1500); //Only run this once, or you will get multiple entries!
        System.out.println(movie);

        Movie movie2 = new Movie(17771, 1966, "Django");
        movieDao.deleteMovie(movie2);
        
         //Only run this once, or you will get multiple entries!
        
>>>>>>> f0031fbe97553c83cabe11b97ce60353e1064a8b

    }
}
