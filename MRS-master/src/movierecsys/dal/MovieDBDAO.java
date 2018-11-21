/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import movierecsys.be.Movie;

/**
 *
 * @author Frederik Jensen
 */
public class MovieDBDAO implements IMovieRepository
{
    private final DBConnection dbc;
    public MovieDBDAO() {
        dbc = new DBConnection();
    }

    @Override
    public Movie createMovie(int releaseYear, String title) throws IOException
    {
        int id = getNextAvailableMovieID();
        try (Connection con = dbc.getConnection()) {
            Statement statement = con.createStatement();
            String sql = "INSERT INTO Movie (id,year,title) VALUES("
                    + id + ","
                    + releaseYear + ",'"
                    + title + "');";
            statement.executeUpdate(sql);
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        Movie movie = new Movie(id, releaseYear, title);
        return movie;
    }

    @Override
    public void deleteMovie(Movie movie) throws IOException
    {
        try (Connection con = dbc.getConnection()) {
            Statement statement = con.createStatement();
            String sql = "DELETE FROM Movie WHERE id = '" + movie.getId() +"';";
            statement.executeUpdate(sql);
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Movie> getAllMovies() throws IOException
    {
        List<Movie> movies = new ArrayList<>();
        
        try(Connection con = dbc.getConnection()) 
        {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Movie;");
            while (rs.next()) {
                int id = rs.getInt("id");
                int year = rs.getInt("year");
                String title = rs.getString("title");
                Movie movie = new Movie(id, year, title);
                movies.add(movie);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return movies;
    }

    @Override
    public Movie getMovie(int id) throws IOException
    {
        try (Connection con = dbc.getConnection()) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Movie WHERE id = " + id + ";");
            if (rs.next()) {
            int movieId = rs.getInt("id");
            int year = rs.getInt("year");
            String title = rs.getString("title");
            Movie movie = new Movie(movieId,year,title);
            return movie;
            }else {
                return null;
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public int getNextAvailableMovieID() throws IOException
    {
        List<Movie> allMovies = getAllMovies();
        int highId = allMovies.get(allMovies.size() - 1).getId();
        return highId + 1;
    }

    @Override
    public void updateMovie(Movie movie) throws IOException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
