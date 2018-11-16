/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.gui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import movierecsys.be.Movie;
import movierecsys.dal.MovieDAO;

/**
 *
 * @author Frederik Jensen
 */
public class MovieRecModel
{
    private ObservableList<Movie> movieList;
    
    public MovieRecModel() {
        movieList = FXCollections.observableArrayList();
    }
    
    public List<Movie> getMovieList() throws IOException {
        MovieDAO modao = new MovieDAO();
        List<Movie> modaoList = new ArrayList();
        modaoList = modao.getAllMovies();
        
        for (Movie m : modaoList) {
            movieList.add(m);
        }
        
        return movieList;
    }
}
