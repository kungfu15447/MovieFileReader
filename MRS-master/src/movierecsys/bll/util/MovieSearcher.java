/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.bll.util;

import java.util.ArrayList;
import java.util.List;
import movierecsys.be.Movie;

/**
 *
 * @author pgn
 */
public class MovieSearcher
{
    public List<Movie> search(List<Movie> searchBase, String query)
    {
        ArrayList<Movie> movieList = new ArrayList();
        if (query.isEmpty()) {
            for (Movie m : searchBase) {
                movieList.add(m);
            }
        }else {
            for (Movie m : searchBase) {
                if (m.getTitle().contains(query)) {
                    movieList.add(m);
                }
            }
        }
        return movieList;
    }
    
}
