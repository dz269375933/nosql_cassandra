package com.bjtu.dz.util;

import com.bjtu.dz.bean.MovieTemp;

public class StringUtil {
    public static MovieTemp StringToMovieTemp(String lineTxt){
        MovieTemp movieTemp = null;
        try{
            String[] array=lineTxt.split(",");
            if(array.length>7){
                int length=array.length;
                String[] aimArray=new String[8];
                aimArray[0]=array[0];
                aimArray[1]=array[1];
                aimArray[2]=array[2];
                aimArray[3]=array[3];
                aimArray[4]="";
                //There are some "," in "movie title" column
                //When data save in the csv file,it will be splited by ","
                // So it will be a big trouble when translate data
                for(int i=4;i<length-3;i++){
                    if(array[i].indexOf("\"")>=0)
                    aimArray[4]+=array[i].substring(0,array[i].lastIndexOf("\""));
                    else aimArray[4]+=array[i];
                }
                aimArray[4]+=array[length-3];
                aimArray[5]=array[length-2];
                aimArray[6]=array[length-1];
                array=aimArray;
            }
            String ageString=array[0].substring(array[0].indexOf(":")+1,array[0].lastIndexOf("\""));
            String genderString=
                    array[1].substring(array[1].indexOf("\"\"")+2,array[1].lastIndexOf("\"\"\""));
            String movieIdString=array[2].substring(array[2].indexOf(":")+1);
            String movieRating=array[3].substring(array[3].indexOf(":")+1);
            String movieTitleString=
                    array[4].substring(array[4].indexOf("\"\"")+2,array[4].lastIndexOf("\"\"\""));
            String occupationString=
                    array[5].substring(array[5].indexOf("\"\"")+2,array[5].lastIndexOf("\"\"\""));
            String userNameString=
                    array[6].substring(array[6].indexOf("\"\"")+2,array[6].lastIndexOf("\"\""));

            movieTemp=new MovieTemp();
            movieTemp.setAge(Integer.valueOf(ageString));
            movieTemp.setGender(genderString);
            movieTemp.setMovieId(Integer.valueOf(movieIdString));
            movieTemp.setMovieRating(Integer.valueOf(movieRating));
            movieTemp.setMovieTitle(movieTitleString);
            movieTemp.setOccupation(occupationString);
            movieTemp.setUserName(userNameString);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(lineTxt);
            System.exit(0);
        }

        return movieTemp;
    }
}
