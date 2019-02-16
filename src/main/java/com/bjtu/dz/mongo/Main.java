package com.bjtu.dz.mongo;

import com.bjtu.dz.bean.JSONClass;
import com.bjtu.dz.cassandra.CassandraConnect;
import com.bjtu.dz.util.ErrorSave;
import com.bjtu.dz.util.MongoDB;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.io.*;

public class Main {
    public static void main(String[] arg){

        String path="MovieLens_ratingUsers.json";
        String errorPath="error.txt";
        try {
            BufferedReader br=new BufferedReader(
                    new InputStreamReader(new FileInputStream(new File(path)),"UTF-8")
            );
            String lineTxt = null;

            float count=0;
            MongoDB mongo=new MongoDB();
            while ((lineTxt = br.readLine()) != null) {
                JSONClass jClass=null;
                try {
                    System.out.println((count++)/10002+"%");
                    JSONObject jsonObject=JSONObject.fromObject(lineTxt);
                    jClass=(JSONClass)JSONObject.toBean(jsonObject, JSONClass.class);
                    mongo.insertOne(jClass);
                }catch (JSONException e){
                    ErrorSave.save(errorPath,count,"JSONException");
                }
            }
            //end while loop
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
