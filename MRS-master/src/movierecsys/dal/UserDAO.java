/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
     * @throws java.io.IOException
     */
    public User getUser(int id) throws IOException
    {
        List<User> userIdList = getAllUsers();
        for (User user : userIdList) {
            if (user.getId() == id) {
                return user;
            }
        }
        throw new IllegalArgumentException("No user with the ID " + id + " was found.");
    }
    
    /**
     * Updates a user so the persistence storage reflects the given User object.
     * @param user The updated user.
     * @throws java.io.IOException
     */
    public void updateUser(User user) throws IOException
    {
        String tempFile = "temp.txt";
        String originalFile = "data/users.txt";
        File oldFile = new File(originalFile);
        File newFile = new File(tempFile);
        List<User> newUserList = getAllUsers();
        
        try {
            FileWriter fw = new FileWriter(tempFile);
            BufferedWriter bw = new BufferedWriter(fw);
            for (User uses : newUserList) {
                if (uses.getId() == user.getId()) {
                    bw.write(user.getId() + "," + user.getName());
                    bw.newLine();
                }else {
                    bw.write(uses.getId() + "," + uses.getName());
                    bw.newLine();
                }
            }
            bw.flush();
            bw.close();
            oldFile.delete();
            File dump = new File(originalFile);
            newFile.renameTo(dump);
        }
        catch (IOException x) {
            System.out.println("Something went wrong");
        }
    }
    
}

