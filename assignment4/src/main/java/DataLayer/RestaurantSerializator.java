package DataLayer;


import BusinessLayer.Restaurant;

import java.io.*;

public class RestaurantSerializator {

    private static String file;

    public RestaurantSerializator(){

    }
    public RestaurantSerializator(String file){
        this.file=file;

    }

    public static void serializare(Restaurant restaurant){ //serializeaza restaurantul

        try{

            FileOutputStream fos  = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(restaurant);

            oos.flush();

            oos.close();
            fos.close();

        }catch(Exception e){

        }

    }

    public static Restaurant deserializare(){ //deserealizeaza


        Restaurant restaurant = new Restaurant();
        try{
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            restaurant = (Restaurant) ois.readObject();

            ois.close();
            fis.close();
        }
        catch (Exception ioe){

        }
        return restaurant;
    }
}
