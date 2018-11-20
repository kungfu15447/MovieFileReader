/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
        migrateRatings();
    }
    
    public static void migrateMovies() throws IOException {
        MovieDAO modao = new MovieDAO();
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName("10.176.111.31");
        ds.setDatabaseName("moviesrs");
        ds.setUser("CS2018A_11");
        ds.setPassword("CS2018A_11");
        
        List<Movie> movies = new ArrayList<>();
        movies = modao.getAllMovies();
        
        try(Connection con = ds.getConnection()) 
        {
            Statement statement = con.createStatement();
            for (Movie movie : movies) {
                String sql = "INSERT INTO Movie (id,year,title) VALUES(" 
                        + movie.getId() + "," 
                        + movie.getYear() + ",'" 
                        + movie.getTitle().replace("'", "") + "');";
                System.out.print(sql);
                int i = statement.executeUpdate(sql);
                System.out.println("Affected row: " + i);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public static void migrateUsers() throws IOException
    {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName("10.176.111.31");
        ds.setDatabaseName("Netflix");
        ds.setUser("CS2018A_14");
        ds.setPassword("CS2018A_14");

        List<User> users = new UserDAO().getAllUsers();

        try (Connection con = ds.getConnection())
        {
            Statement statement = con.createStatement();
            int counter = 0;
            for (User user : users)
            {
                String sql = "INSERT INTO [User] (id,name) VALUES("
                        + user.getId() + ",'"
                        + user.getName() + "');";
                statement.addBatch(sql);
                counter++;
                if (counter % 1000 == 0)
                {
                    statement.executeBatch();
                    System.out.println("Added 1000 users.");
                }
            }
            if (counter % 1000 != 0)
            {
                statement.executeBatch();
                System.out.println("Added final batch of users.");
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    public static void migrateRatings() throws IOException {
        RatingDAO rdao = new RatingDAO();
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName("10.176.111.31");
        ds.setDatabaseName("moviesrs");
        ds.setUser("CS2018A_11");
        ds.setPassword("CS2018A_11");
        List<Rating> ratings = new ArrayList<>();
        ratings = rdao.getAllRatings();
        
        try (Connection con = ds.getConnection()){
            Statement statement = con.createStatement();
            for (Rating rating : ratings) {
                String sql = "INSERT INTO Rating (movieId,userId,rating) VALUES("
                    + rating.getMovie() + ","
                    + rating.getUser() + ","
                    + rating.getRating() + ");";
                System.out.println(sql);
                int i = statement.executeUpdate(sql);
                System.out.println("Affected row: " + i);
            }
        }catch (SQLException ex) {
            
        }
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
