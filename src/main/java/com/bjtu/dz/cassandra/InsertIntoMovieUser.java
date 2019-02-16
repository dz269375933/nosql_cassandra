package com.bjtu.dz.cassandra;

import com.bjtu.dz.bean.MovieTemp;
import com.bjtu.dz.util.ErrorSave;
import com.bjtu.dz.util.StringUtil;

import java.io.*;

public class InsertIntoMovieUser {
    public static void main(String[] arg){
        String path="movieUser2.csv";
        String errorPath="error.txt";
        try {
            BufferedReader br=new BufferedReader(
                    new InputStreamReader(new FileInputStream(new File(path)),"UTF-8")
            );
            String lineTxt = null;

            CassandraConnect cc=new CassandraConnect();
            cc.dropTableMovieUser();
            cc.createTableMovieUser();

            float count=0;

            while ((lineTxt = br.readLine()) != null) {
                MovieTemp movieTemp=null;
                System.out.println((count++)/10002+"%");
                movieTemp=StringUtil.StringToMovieTemp(lineTxt);

                try {
                    cc.insertMovieUserRating(movieTemp);

                }catch (Exception e){
                    e.printStackTrace();
                    ErrorSave.save(errorPath,count,"cassandraError");
                }
            }
            //end while loop
            cc.exit();
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
