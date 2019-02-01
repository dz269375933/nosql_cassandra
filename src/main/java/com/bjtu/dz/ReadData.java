package com.bjtu.dz;

import com.bjtu.dz.bean.JSONClass;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.io.*;

public class ReadData {
    public static void main(String[] arg){
        String path="C:\\Users\\dz\\Desktop\\MovieLens_ratingUsers.json\\MovieLens_ratingUsers.json";
        String errorPath="C:\\Users\\dz\\Desktop\\MovieLens_ratingUsers.json\\error.txt";
        try {
            BufferedReader br=new BufferedReader(
                    new InputStreamReader(new FileInputStream(new File(path)),"UTF-8")
            );
            String lineTxt = null;

            CassandraConnect cc=new CassandraConnect();

            float count=0;
            String lastUser="";
            while ((lineTxt = br.readLine()) != null) {
                JSONClass jClass=null;
                try {
                    System.out.println((count++/10002)+"%");
                    JSONObject jsonObject=JSONObject.fromObject(lineTxt);
                    jClass=(JSONClass)JSONObject.toBean(jsonObject, JSONClass.class);
                }catch (JSONException e){
                    e.printStackTrace();
                    ErrorSave.save(errorPath,count,"JSONException");
                }


                try {
                    if(lastUser==null || lastUser.equals("")){
                        lastUser=jClass.getName();
                        cc.insert(jClass);
                    }else{
                        cc.update(jClass);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    System.exit(0);
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
