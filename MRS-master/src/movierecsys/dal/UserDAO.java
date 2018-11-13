/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import movierecsys.be.Movie;
import movierecsys.be.User;

/**
 *
 * @author pgn
 */
public class UserDAO
{
    
    /**
     * Gets a list of all known users.
     * @return List of users.
     */
    public List<User> getAllUsers() throws IOException
    {
        List<User> users = new ArrayList<>();
        String source = "data/users.txt";
        File file = new File(source);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line;
            while((line = reader.readLine()) != null)
            {
                if(!line.isEmpty())
                {
                    try
                        {
                     User use = stringArrayToUser(line);
                     users.add(use);
                    }catch(Exception ex)
                    {
                        //lal
                    }
                }
                
            }
        } 
        return users;
    }
    
    
    
    
    private User stringArrayToUser(String line)
    {
        String[] arrUser = line.split(",");
        int id = Integer.parseInt(arrUser[0]);
        String title = arrUser[1];

        User user = new User(id, title);
        return user;
    }        
    
    /**
     * Gets a single User by its ID.
     * @param id The ID of the user.
     * @return The User with the ID.
     */
    public User getUser(int id)
    {
        //TODO Get User
        return null;
    }
    
    /**
     * Updates a user so the persistence storage reflects the given User object.
     * @param user The updated user.
     */
    public void updateUser(User user)
    {
        //TODO Update user.
    }
    
}

