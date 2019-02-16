package com.bjtu.dz.cassandra;

import com.bjtu.dz.bean.JSONClass;
import com.bjtu.dz.util.ErrorSave;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.io.*;

public class InsertIntoUserMovie {
    public static void main(String[] arg){
        String path="MovieLens_ratingUsers.json";
        String errorPath="error.txt";
        try {
            BufferedReader br=new BufferedReader(
                    new InputStreamReader(new FileInputStream(new File(path)),"UTF-8")
            );
            String lineTxt = null;

            CassandraConnect cc=new CassandraConnect();
            cc.dropTableUserMovie();
            cc.createTableUserMovie();

            float count=0;
            String lastUser="";
            boolean flag=true;

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

                try {
                    if(flag){
                        flag=false;
                        lastUser=jClass.getName();
                        cc.insert(jClass);
                        continue;
                    }
                    if(!lastUser.equals(jClass.getName())){
                        lastUser=jClass.getName();
                        cc.insert(jClass);
                    }else{
                        cc.update(jClass);
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
