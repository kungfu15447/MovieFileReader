/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import movierecsys.be.Movie;
import movierecsys.be.Rating;
import movierecsys.be.User;

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
        RatingDAO ratingDao = new RatingDAO();
        Rating rate = new Rating(8, 1744889, -5);
        ratingDao.deleteRating(rate);
        
        
        
    }
    public static void createRafFriendlyRatingsFile() throws IOException
    {
        String target = "data/ratings.txt";
        RatingDAO ratingDao = new RatingDAO();
        List<Rating> all = ratingDao.getAllRatings();
        
        try (RandomAccessFile raf = new RandomAccessFile(target, "rw"))
        {
            for (Rating rating : all)
            {
                raf.writeInt(rating.getMovie());
                raf.writeInt(rating.getUser());
                raf.writeInt(rating.getRating());
            }
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
    public void movieDAOMethods() throws IOException {
        MovieDAO movieDao = new MovieDAO();

        Movie movie = movieDao.getMovie(1501);
        System.out.println(movie);
        System.out.println("Hej");
         //Only run this once, or you will get multiple entries!
        Movie movie2 = new Movie(1, 2003, "Dinosaur Planet");
        movieDao.updateMovie(movie2);
        
        System.exit(0);
    }
    
    public void userDAOMethods() throws IOException {
        UserDAO userDao = new UserDAO();
        List<User> userList = userDao.getAllUsers();
        for (User user : userList) {
            System.out.println(user);
        }
        System.out.println("Before update:");
        System.out.println(userDao.getUser(7));
        
        User user1 = new User(7, "Big nibba");
        userDao.updateUser(user1);
        
        System.out.println("After update:");
        System.out.println(userDao.getUser(7));
        
        
    }
}
