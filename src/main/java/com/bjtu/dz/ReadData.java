package com.bjtu.dz;

import net.sf.json.JSONObject;

import java.io.*;

public class ReadData {
    public static void main(String[] arg){
        String path="C:\\Users\\dz\\Desktop\\MovieLens_ratingUsers.json\\MovieLens_ratingUsers.json";
        try {
            BufferedReader br=new BufferedReader(
                    new InputStreamReader(new FileInputStream(new File(path)),"UTF-8")
            );
            String lineTxt = null;
            CassandraConnect cc=new CassandraConnect();
            float count=0;
            while ((lineTxt = br.readLine()) != null) {
                JSONObject jsonObject=JSONObject.fromObject(lineTxt);
                JSONClass jClass=(JSONClass)JSONObject.toBean(jsonObject, JSONClass.class);
                cc.insert(jClass);
                System.out.println((count++/10002)+"%");
            }
            cc.exit();
            System.out.println("success");
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
