/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import movierecsys.be.Rating;
import movierecsys.be.User;

/**
 *
 * @author pgn
 */
public class RatingDAO
{

    private static final String RATING_SOURCE = "data/user_ratings";
    private static final String RATING_FILE = "data/ratings.txt";

    private static final int RECORD_SIZE = Integer.BYTES * 3;

    /**
     * Persists the given rating.
     *
     * @param rating the rating to persist.
     */
    public void createRating(Rating rating) throws IOException
    {
        Path path = new File(RATING_FILE).toPath();
        int movieId = rating.getMovie();
        int userId = rating.getUser();
        int ratingScore = rating.getRating();
        
        try (BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.SYNC, StandardOpenOption.APPEND, StandardOpenOption.WRITE)) {
            bw.newLine();
            bw.write(movieId + "," + userId + "," + ratingScore);
        }
    }

    /**
     * Updates the rating to reflect the given object. Assumes that the source
     * file is in order by movie ID, then User ID..
     *
     * @param rating The updated rating to persist.
     * @throws java.io.IOException
     */
    public void updateRating(Rating rating) throws IOException
    {
        try (RandomAccessFile raf = new RandomAccessFile(RATING_SOURCE, "rw"))
        {
            long totalRatings = raf.length();
            long low = 0;
            long high = ((totalRatings - 1) / RECORD_SIZE) * RECORD_SIZE;
            while (high >= low) //Binary search of movie ID
            {
                long pos = (((high + low) / 2) / RECORD_SIZE) * RECORD_SIZE;
                raf.seek(pos);
                int movId = raf.readInt();
                int userId = raf.readInt();

                if (rating.getMovie() < movId) //We did not find the movie.
                {
                    high = pos - RECORD_SIZE; //We half our problem size to the upper half.
                } else if (rating.getMovie() > movId) //We did not find the movie.
                {
                    low = pos + RECORD_SIZE; //We half our problem size (Just the lower half)
                } else //We found a movie match, not to search for the user:
                {
                    if (rating.getUser() < userId) //Again we half our problem size
                    {
                        high = pos - RECORD_SIZE;
                    } else if (rating.getUser() > userId) //Another half sized problem
                    {
                        low = pos + RECORD_SIZE;
                    } else //Last option, we found the right row:
                    {
                        raf.write(rating.getRating()); //Remember the to reads at line 60,61. They positioned the filepointer just at the ratings part of the current record.
                        return; //We return from the method. We are done here. The try with resources will close the connection to the file.
                    }
                }
            }
        }
        throw new IllegalArgumentException("Rating not found in file, can't update!"); //If we reach this point we have been searching for a non-present rating.
    }

    /**
     * Removes the given rating.
     *
     * @param rating
     * @throws java.io.IOException
     */
    public void deleteRating(Rating rating) throws IOException
    {
        String tempFile = "temp.txt";
        File oldFile = new File(RATING_FILE);
        File newFile = new File(tempFile);
        List<Rating> newRatingList = new ArrayList<>();
        newRatingList = getAllRatings();
        try {
            FileWriter fw = new FileWriter(tempFile);
            BufferedWriter bw = new BufferedWriter(fw);
            for  (Rating raton : newRatingList) {
                if (rating.getUser() != raton.getUser() && rating.getMovie() != rating.getMovie()) {
                    bw.write(raton.getMovie() + "," + raton.getUser() + "," + raton.getRating());
                    bw.newLine();
                }
                else if (rating.getUser() == raton.getUser() && rating.getMovie() != rating.getMovie()) {
                    bw.write(raton.getMovie() + "," + raton.getUser() + "," + raton.getRating());
                    bw.newLine();
                }
                else if (rating.getUser() != raton.getUser() && rating.getMovie() == rating.getMovie()) {
                    bw.write(raton.getMovie() + "," + raton.getUser() + "," + raton.getRating());
                    bw.newLine();
                }
            }
            bw.flush();
            bw.close();
            oldFile.delete();
            File dump = new File(RATING_SOURCE);
            newFile.renameTo(dump);
        } catch (Exception x) {
            
        }
    }

    /**
     * Gets all ratings from all users.
     *
     * @return List of all ratings.
     */
    public List<Rating> getAllRatings() throws IOException
    {
        List<Rating> allRatings = new ArrayList<>();
        byte[] all = Files.readAllBytes(new File(RATING_SOURCE).toPath()); //I get all records as binary data!
        for (int i = 0; i < all.length; i += RECORD_SIZE)
        {
            int movieId = ByteBuffer.wrap(all, i, Integer.BYTES).order(ByteOrder.BIG_ENDIAN).getInt();
            int userId = ByteBuffer.wrap(all, i + Integer.BYTES, Integer.BYTES).order(ByteOrder.BIG_ENDIAN).getInt();
            int rating = ByteBuffer.wrap(all, i + Integer.BYTES * 2, Integer.BYTES).order(ByteOrder.BIG_ENDIAN).getInt();
            Rating r = new Rating(movieId, userId, rating);
            allRatings.add(r);
        }
        Collections.sort(allRatings, (Rating o1, Rating o2) ->
        {
            int movieCompare = Integer.compare(o1.getMovie(), o2.getMovie());
            return movieCompare == 0 ? Integer.compare(o1.getUser(), o2.getUser()) : movieCompare;
        });
        return allRatings;
    }

    /**
     * Get all ratings from a specific user.
     *
     * @param user The user
     * @return The list of ratings.
     */
    public List<Rating> getRatings(User user)
    {
        //TODO Get user ratings.
        return null;
    }

    private Rating getRatingFromLine(String line) throws NumberFormatException
    {
        String[] cols = line.split(",");
        int movId = Integer.parseInt(cols[0]);
        int userId = Integer.parseInt(cols[1]);
        int rating = Integer.parseInt(cols[2]);
        return new Rating(movId, userId, rating);
    }

}
