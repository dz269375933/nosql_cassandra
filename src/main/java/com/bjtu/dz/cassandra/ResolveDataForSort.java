package com.bjtu.dz.cassandra;

import com.bjtu.dz.bean.JSONClass;
import com.bjtu.dz.bean.MovieTemp;
import com.bjtu.dz.util.ErrorSave;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.io.*;

public class ResolveDataForSort {
    public static void main(String[] arg){
        String path="MovieLens_ratingUsers.json";
        String errorPath="error.txt";
        try {
            BufferedReader br=new BufferedReader(
                    new InputStreamReader(new FileInputStream(new File(path)),"UTF-8")
            );
            String lineTxt = null;


            float count=0;
            MovieTemp movieTemp;

            //Read data from movieUser.csv
            //after this,I sort movieId in csv file
            FileWriter fileWriter = null;
            try {
                File f=new File("movieUser.csv");
                fileWriter = new FileWriter(f, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            PrintWriter printWriter = new PrintWriter(fileWriter);

            while ((lineTxt = br.readLine()) != null) {
                JSONClass jClass=null;
                try {
                    System.out.println((count++)/10002+"%");
                    JSONObject jsonObject=JSONObject.fromObject(lineTxt);
                    jClass=(JSONClass)JSONObject.toBean(jsonObject, JSONClass.class);

                }catch (JSONException e){
                    e.printStackTrace();
                    ErrorSave.save(errorPath,count,"JSONException");
                }
                movieTemp=new MovieTemp(jClass);
                JSONObject json = JSONObject.fromObject(movieTemp);
                printWriter.println(json);
            }
            //end while loop
            printWriter.flush();
            try {
                fileWriter.flush();
                printWriter.close();
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
