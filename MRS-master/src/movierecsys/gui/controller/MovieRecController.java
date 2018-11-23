/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import movierecsys.be.Movie;
import movierecsys.bll.exception.MovieRecSysException;

/**
 *
 * @author pgn
 */
public class MovieRecController implements Initializable
{

    /**
     * The TextField containing the URL of the targeted website.
     */
    @FXML
    private TextField txtMovieSearcjh;

    /**
     * The TextField containing the query word.
     */
    @FXML
    private ListView<Movie> lstMovies;
    @FXML
    private Button btnSearch;

    private MovieRecModel movieModel;
    @FXML
    private TextField txtMovieName;
    @FXML
    private TextField txtMovieYear;
    @FXML
    private Label lblUpdateOutput;
    @FXML
    private Button btnCreateMovie;
    
    public MovieRecController() throws MovieRecSysException {
        try {
        movieModel = new MovieRecModel();
        } catch(MovieRecSysException x) {
            
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        
    }
    
    public void movieData() {
        lstMovies.setItems(movieModel.getMovieList());
    }

    @FXML
    private void movieSearch(ActionEvent event) {
        lstMovies.setItems(movieModel.searchMovie(movieModel.getMovieList(), txtMovieSearcjh.getText()));
        
    }

    @FXML
    private void CreateMovieHandler(ActionEvent event)
    {
        String movieName = txtMovieName.getText();
        int movieYear = Integer.parseInt(txtMovieYear.getText());
        movieModel.createMovie(movieYear, movieName);
        lblUpdateOutput.setText("Added " + movieName + " to database");
        
    }

}