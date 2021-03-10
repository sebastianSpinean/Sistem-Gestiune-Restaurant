package DataLayer;

import BusinessLayer.BaseProduct;
import BusinessLayer.CompositeProduct;
import BusinessLayer.MenuItem;
import BusinessLayer.Order;

import java.io.*;
import java.util.ArrayList;

public class FileWriterBill {

    public void generateBill(ArrayList<MenuItem> items, Order order,float price){
        File pf = new File("bill"+order.getOrderId()+".txt");
        try {
            pf.createNewFile();            //se creaza fisierul in care sa se afiseze datele

        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            FileWriter myWriter = new FileWriter("bill"+order.getOrderId()+".txt"); //s escriu datele
            myWriter.write("Table:"+order.getOrderId()+"\n");
            for(MenuItem i : items){
                if(i instanceof BaseProduct){
                    myWriter.write(((BaseProduct) i).getName()+" "+((BaseProduct) i).getPrice()+"\n");
                }
                else if(i instanceof CompositeProduct){
                    myWriter.write(((CompositeProduct) i).getName()+" "+((CompositeProduct) i).getPrice()+"\n");
                }
            }
            myWriter.write("Total price:"+price);
            myWriter.close();




        }catch(Exception e){

        }


    }
}
