package com.bjtu.dz;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CassandraConnect {
    private Cluster cluster;
    private Session session;
    private String keySpace="movieRating";
    private String table="rating";
    public CassandraConnect(){
        try {
            //定义cluster类
            cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
            //需要获取session对象
            session = cluster.connect();
            this.dropTable();
            this.createTable();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void createTable(){
        //创建键空间  防止发生错误 if not exists
        String createKeySpaceCQL=
                "create keyspace if not exists " +keySpace+
                        " with "
                        + "replication={'class':'SimpleStrategy','replication_factor':3}";
        session.execute(createKeySpaceCQL);
        //创建列族
        String createTableCQL =
                "create table if not exists movieRating.rating(" +
                        "username varchar," +
                        "movie_id int," +
                        "movie_title varchar," +
                        "user_rating int," +
                        "gender varchar," +
                        "age int," +
                        "occupation varchar," +
                        "primary key((username,movie_id))" +
                        ")";
        session.execute(createTableCQL);
    }
    public void insert(JSONClass object){
        //插入数据

        Insert insert = QueryBuilder.insertInto(keySpace, table)
                .value("username",object.getName())
                .value("movie_id",object.getMovie().getId())
                .value("movie_title",object.getMovie().getTitle())
                .value("user_rating",object.getMovie().getRating())
                .value("gender",object.getGender())
                .value("age",object.getAge())
                .value("occupation",object.getOccupation());

        try{
            session.execute(insert);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    public void selectAll(){

            //查询
            String queryCQL =
            "select * from movieRating.rating";
            ResultSet rs = session.execute(queryCQL);
            List<Row> dataList = rs.all();
            for (Row row : dataList) {
                System.out.println(row.getString(0));
            }
    }
    public void dropTable(){
        String CQL="drop table IF EXISTS movieRating.rating";
        try{
            session.execute(CQL);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void exit(){
        session.close();
        cluster.close();
    }

}
