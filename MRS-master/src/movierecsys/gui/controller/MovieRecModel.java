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
import movierecsys.bll.MRSManager;
import movierecsys.bll.MRSOwsLogicFacade;
import movierecsys.bll.exception.MovieRecSysException;
import movierecsys.dal.MovieDAO;

/**
 *
 * @author Frederik Jensen
 */
public class MovieRecModel
{
    private ObservableList<Movie> movieList;
    private MRSOwsLogicFacade logiclayer;
    
    public MovieRecModel() throws MovieRecSysException {
        movieList = FXCollections.observableArrayList();
        logiclayer = new MRSManager();
        movieList.addAll(logiclayer.getAllMovies());
    }
    
    public ObservableList<Movie> getMovieList() {
        return movieList;
    }
}
