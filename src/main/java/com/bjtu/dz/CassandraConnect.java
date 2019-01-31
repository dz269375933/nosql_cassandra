package com.bjtu.dz;

import com.bjtu.dz.bean.JSONClass;
import com.bjtu.dz.bean.Movie;
import com.bjtu.dz.bean.MovieType;
import com.bjtu.dz.bean.UserMovieRating;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CassandraConnect {
    private Cluster cluster;
    private Session session;
    private String keySpace="movieRating";
    private String table="userMovieRating";
    Mapper<UserMovieRating> mapper;

    public CassandraConnect() throws Exception {
            //定义cluster类
            cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
            //需要获取session对象
            session = cluster.connect();
            this.dropTable();
            this.createTable();

    }
    public void createTable() throws Exception{
        //create key space
        String createKeySpaceCQL=
                "create keyspace if not exists " +keySpace+
                        " with "
                        + "replication={'class':'SimpleStrategy','replication_factor':3}";
        session.execute(createKeySpaceCQL);
        //create movie type
        String createMovieType="" +
                "create type if not exists "+keySpace+".movieType(movie_id int,user_rating int,movie_title varchar)";
        session.execute(createMovieType);


        //create column family
        String createTableCQL =
                "create table if not exists movieRating."+table+"(" +
                        "username varchar," +
                        "gender varchar," +
                        "age int," +
                        "occupation varchar," +
                        "movie map<int,frozen<movieType>>," +
                        "primary key(username)" +
                        ")";
        session.execute(createTableCQL);
        mapper=new MappingManager(session).mapper(UserMovieRating.class);
    }
    public void insert(JSONClass object) throws Exception{
        UserMovieRating rating=new UserMovieRating(object);
        mapper.save(rating);
//        Movie movie=object.getMovie();
//        Map map1=new HashMap();
//        map1.put("movie_id",movie.getId());
//        map1.put("user_rating",movie.getRating());
//        map1.put("movie_title",movie.getTitle());
//        MovieType movieType=new MovieType(movie);
//
//        Map<Integer,MovieType> movieMap=new HashMap<Integer,MovieType>();
//        movieMap.put(movie.getId(),movieType);
////        movieMap.put(movie.getId(),map1);
//
//
//
//
//        Insert insert = QueryBuilder.insertInto(keySpace, table)
//                .value("username",object.getName())
//                .value("gender",object.getGender())
//                .value("age",object.getAge())
//                .value("occupation",object.getOccupation())
//                .value("movie",movieType)
//                ;
//
//        session.execute(insert);

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
    public void dropTable() throws Exception{
        String CQL="drop table IF EXISTS movieRating.userMovieRating";
        session.execute(CQL);
    }
    public void exit(){
        session.close();
        cluster.close();
    }

}
