package com.bjtu.dz.neo4j;

import com.bjtu.dz.bean.JSONClass;
import com.bjtu.dz.bean.Neo4jMovie;
import com.bjtu.dz.bean.User;
import com.bjtu.dz.util.ErrorSave;
import com.csvreader.CsvWriter;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class Neo4j {
    public static void main(String[] arg) {

        String path = "MovieLens_ratingUsers.json";
        String errorPath = "error.txt";
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(new File(path)), "UTF-8")
            );
            String lineTxt = null;

            float count = 0;
            Map<Integer,Neo4jMovie> movieMap=new HashMap<Integer, Neo4jMovie>();
            Map<String,User> userMap=new HashMap<String, User>();
            Map<String, Integer> userIdMap = new HashMap<String, Integer>();
            int userIdInit = 10000;
            CsvWriter ratingWriter = new CsvWriter("rating.csv", ',', Charset.defaultCharset());
            String[] ratingTitle={"UserId","MovieId","Rating"};
            ratingWriter.writeRecord(ratingTitle);
            while ((lineTxt = br.readLine()) != null) {
                JSONClass jClass = null;
                try {
                    System.out.println((count++) / 10002 + "%");
                    JSONObject jsonObject = JSONObject.fromObject(lineTxt);
                    jClass = (JSONClass) JSONObject.toBean(jsonObject, JSONClass.class);
                    Neo4jMovie movie = new Neo4jMovie(jClass);
                    User user = new User(jClass);

                    if (userIdMap.containsKey(user.getUserName())) {
                        int userId = userIdMap.get(user.getUserName());
                        user.setUserId(userId);
                        String[] ratingString = {String.valueOf(userId), String.valueOf(movie.getMovieId()),
                                String.valueOf(jClass.getMovie().getRating())};
                        ratingWriter.writeRecord(ratingString);
                    } else {
                        userIdMap.put(user.getUserName(), userIdInit);
                        user.setUserId(userIdInit);
                        String[] ratingString = {String.valueOf(userIdInit), String.valueOf(movie.getMovieId()),
                                String.valueOf(jClass.getMovie().getRating())};
                        ratingWriter.writeRecord(ratingString);
                        userIdInit++;
                    }

                    movieMap.put(movie.getMovieId(),movie);
                    userMap.put(user.getUserName(),user);


                } catch (JSONException e) {
                    ErrorSave.save(errorPath, count, "JSONException");
                }
            }
            //end while loop

            ratingWriter.close();

            CsvWriter movieWriter = new CsvWriter("movie.csv", ',', Charset.defaultCharset());
            String[] movieHeader={"MovieId","MovieTitle"};
            movieWriter.writeRecord(movieHeader);
            for (Neo4jMovie movie:movieMap.values()) {
                String[] data = {String.valueOf(movie.getMovieId()), movie.getMovieTitle()};
                movieWriter.writeRecord(data);
            }
            movieWriter.close();

            CsvWriter userWriter = new CsvWriter("user.csv", ',', Charset.defaultCharset());
            String[] userHeader={"UserId","UserName","Gender","Age","Occupation"};
            userWriter.writeRecord(userHeader);
            for (User user:userMap.values()) {
                String[] data={String.valueOf(user.getUserId()),user.getUserName(),user.getGender(),
                String.valueOf(user.getAge()),user.getOccupation()};
                userWriter.writeRecord(data);
            }
            userWriter.close();


            System.out.println("success");
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

    }
}
