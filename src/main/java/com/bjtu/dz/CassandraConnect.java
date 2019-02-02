package com.bjtu.dz;

import com.bjtu.dz.bean.*;
import com.datastax.driver.core.*;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import java.util.ArrayList;
import java.util.List;

public class CassandraConnect {
    private Cluster cluster;
    private Session session;
    private String keySpace="movieRating";
    private String table="userMovieRating";
    Mapper<UserMovieRating> mapper;
    Mapper<MovieUserRating> movieMapper;
    MovieUserRating movieUserRating;
    UserMovieRating rating;

    public CassandraConnect() throws Exception {
            //define cluster class
            cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
            //get session object
            session = cluster.connect();

    }
    public void createTableUserMovie() throws Exception{
        //create key space
        String createKeySpaceCQL=
                "create keyspace if not exists " +keySpace+
                        " with "
                        + "replication={'class':'SimpleStrategy','replication_factor':3}";
        session.execute(createKeySpaceCQL);
        //create movie type
        String createMovieType="" +
                "create type if not exists "+keySpace
                +".movieType(movie_id int,user_rating int,movie_title varchar)";
        session.execute(createMovieType);


        //create column family
        String createTableCQL =
                "create table if not exists movieRating."+table+"(" +
                        "username varchar," +
                        "gender varchar," +
                        "age int," +
                        "occupation varchar," +
                        "movie List<frozen<movieType>>," +
                        "primary key(username)" +
                        ")";
        session.execute(createTableCQL);
        mapper=new MappingManager(session).mapper(UserMovieRating.class);
    }

    public void createTableMovieUser() throws Exception{
        //create key space
        String createKeySpaceCQL=
                "create keyspace if not exists " +keySpace+
                        " with "
                        + "replication={'class':'SimpleStrategy','replication_factor':3}";
        session.execute(createKeySpaceCQL);
//        String createUserType="" +
//                "create type if not exists "+keySpace+".userType(userName varchar,user_rating int,gender varchar," +
//                "occupation varchar,age int)";
//        session.execute(createUserType);


        //create column family
        String createTableCQL =
                "create table if not exists movieRating.movieUserRating(" +
                        "movie_id int," +
                        "movie_title varchar," +
                        "userName varchar," +
                        "rating int," +
                        "primary key(movie_id,rating,userName)" +
                        ")";
        session.execute(createTableCQL);
        movieMapper=new MappingManager(session).mapper(MovieUserRating.class);
    }
    public void createTableMovie() throws Exception{
        //create key space
        String createKeySpaceCQL=
                "create keyspace if not exists " +keySpace+
                        " with "
                        + "replication={'class':'SimpleStrategy','replication_factor':3}";
        session.execute(createKeySpaceCQL);

        //create column family
        String createTableCQL =
                "create table if not exists movieRating.movie(" +
                        "movie_id int," +
                        "movie_title varchar," +
                        "primary key(movie_id)" +
                        ")";
        session.execute(createTableCQL);
    }
    public void insertMovieUserRating(MovieTemp object) throws Exception{
        movieUserRating=new MovieUserRating(object);
        movieMapper.save(movieUserRating);
    }

//    public void updateMovie(MovieTemp movieTemp) throws Exception{
//        int movie_id=movieTemp.getMovieId();
//        BoundStatement  boundStatement =
//                session.prepare("update movieRating.movieUserRating " +
//                        "set userName = ?,rating = ? where movie_id = "+movie_id)
//                        .bind(movieTemp.getUserName(),movieTemp.getMovieRating());
//        session.execute(boundStatement);
//    }


    public void insert(JSONClass object) throws Exception{
        rating=new UserMovieRating(object);
        mapper.save(rating);
//        Movie movie=object.getMovieData();
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
    public void update(JSONClass object) throws Exception{
        String userName=object.getName();
        List<MovieType> movieList=new ArrayList<MovieType>();
        movieList.add(new MovieType(object.getMovie()));
        BoundStatement  boundStatement =
                session.prepare("update movieRating.userMovieRating set movie = movie +" +
                        "? where userName = '"+userName+"'")
                .bind(movieList);
        session.execute(boundStatement);
    }
    public void selectAll(){

            //search
            String queryCQL =
            "select * from movieRating.rating";
            ResultSet rs = session.execute(queryCQL);
            List<Row> dataList = rs.all();
            for (Row row : dataList) {
                System.out.println(row.getString(0));
            }
    }
    public void dropTableUserMovie() throws Exception{
        String CQL="drop table IF EXISTS movieRating.userMovieRating";
        session.execute(CQL);
    }
    public void dropTableMovieUser() throws Exception{
        String CQL="drop table IF EXISTS movieRating.movieUserRating";
        session.execute(CQL);
    }
    public void exit(){
        session.close();
        cluster.close();
    }

    public void insertMovie(MovieTemp movieTemp) {
        Insert insert = QueryBuilder.insertInto("movieRating","movie")
                .value("movie_id",movieTemp.getMovieId())
                .value("movie_title",movieTemp.getMovieTitle())
                ;

        session.execute(insert);

    }
}
