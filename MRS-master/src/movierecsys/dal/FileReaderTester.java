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
        Movie movie = movieDao.getMovie(1500); //Only run this once, or you will get multiple entries!
        System.out.println(movie);
=======
        Movie movie = new Movie(17771, 1966, "Django");
        movieDao.deleteMovie(movie);
        
         //Only run this once, or you will get multiple entries!
        
>>>>>>> b55b5e85047161a63a528673221ee1de24ee561a
    }
}
