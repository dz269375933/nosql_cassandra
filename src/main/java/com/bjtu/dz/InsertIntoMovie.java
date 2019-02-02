package com.bjtu.dz;

import com.bjtu.dz.bean.MovieTemp;
import com.bjtu.dz.util.ErrorSave;
import com.bjtu.dz.util.StringUtil;

import java.io.*;

public class InsertIntoMovie {
    public static void main(String[] arg){
        String path="movieUser2.csv";
        String errorPath="error.txt";
        try {
            BufferedReader br=new BufferedReader(
                    new InputStreamReader(new FileInputStream(new File(path)),"UTF-8")
            );
            String lineTxt = null;

            CassandraConnect cc=new CassandraConnect();
            cc.createTableMovie();

            float count=0;
            boolean flag=true;
            int lastMovieId=-1;

            while ((lineTxt = br.readLine()) != null) {
                MovieTemp movieTemp=null;
                System.out.println((count++)/10002+"%");
                movieTemp=StringUtil.StringToMovieTemp(lineTxt);

                try {
                    if(flag){
                        flag=false;
                        lastMovieId=movieTemp.getMovieId();
                        cc.insertMovie(movieTemp);
                        continue;
                    }
                    if(lastMovieId!=movieTemp.getMovieId()){
                        cc.insertMovie(movieTemp);
                        lastMovieId=movieTemp.getMovieId();
                    }


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
