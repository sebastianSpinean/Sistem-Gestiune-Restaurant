package PresentationLayer;

import BusinessLayer.*;
import BusinessLayer.MenuItem;
import DataLayer.RestaurantSerializator;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ChefGui extends JFrame implements Observer {

    private JPanel content = new JPanel();
    private JLabel label = new JLabel("Chef prepares");
    private JTextField text = new JTextField(30);
    private IRestaurantProcessing restaurant = RestaurantSerializator.deserializare();

    public ChefGui(){

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        panel1.setLayout(new FlowLayout());
        panel2.setLayout(new FlowLayout());
        panel1.add(label);
        panel2.add(text);
        content.add(panel1);
        content.add(panel2);
        this.setContentPane(content);
        this.pack();
        this.setVisible(true);
        this.setSize(400,400);
        this.setTitle("Chef");

    }

    @Override
    public void update(Observable o, Object arg) {
        restaurant = RestaurantSerializator.deserializare();
        ArrayList<MenuItem> items = ((Restaurant)restaurant).getOrderItems().get((Order) arg);
        String masaj = "";

        for(MenuItem i : items){ //se formeaza mesajul
            if(i instanceof BaseProduct){
                masaj+=((BaseProduct) i).getName()+" ";
            }
            else if(i instanceof CompositeProduct){
                masaj+=((CompositeProduct) i).getName()+" ";
            }
        }
        masaj +="for order with id="+((Order)arg).getOrderId();
        text.setText(masaj);

    }
}
