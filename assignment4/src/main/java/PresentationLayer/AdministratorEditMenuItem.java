package PresentationLayer;

import BusinessLayer.IRestaurantProcessing;
import BusinessLayer.Restaurant;
import BusinessLayer.Validator;
import DataLayer.RestaurantSerializator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdministratorEditMenuItem extends JFrame {

    private JPanel content = new JPanel();
    private JLabel title = new JLabel("Edit a menu item");
    private JLabel baseLabel = new JLabel("Base Product");
    private JLabel priceLabel = new JLabel("New price");

    private JTextField baseText = new JTextField(30);
    private JTextField priceText = new JTextField(30);
    private JButton button = new JButton("Edit");

    private RestaurantSerializator ser =  new RestaurantSerializator();
    private IRestaurantProcessing restaurant = ser.deserializare();

    public AdministratorEditMenuItem(){
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        panel1.setLayout(new FlowLayout());
        panel2.setLayout(new FlowLayout());
        panel3.setLayout(new FlowLayout());
        panel4.setLayout(new FlowLayout());
        panel1.add(title);
        panel2.add(baseLabel);
        panel2.add(baseText);
        panel3.add(priceLabel);
        panel3.add(priceText);
        panel4.add(button);
        content.add(panel1);
        content.add(panel2);
        content.add(panel3);
        content.add(panel4);
        this.setContentPane(content);
        this.pack();
        this.setVisible(true);
        this.setTitle("Edit Menu Item");
        edit();

    }

    public void edit(){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restaurant = ser.deserializare();
                String base = baseText.getText(); //citeste numele
                String price = priceText.getText();//citeste pretul
                Validator validator1 = new Validator(base);
                Validator validator2 = new Validator(price);
                if(!validator1.isNotEmpty() || base.equals("") || !validator2.isNotEmpty() || price.equals("")){
                    JOptionPane.showMessageDialog(content, "A field is empty");
                }
                else if(!validator2.isFloatValid()){
                    JOptionPane.showMessageDialog(content, "Price is not valid");
                }
                else{
                    ((Restaurant)restaurant).editMenuItem(base,Float.parseFloat(price));
                    JOptionPane.showMessageDialog(content, "Edit successffuly");
                    baseText.setText(null);
                    priceText.setText(null);
                }
            }
        });
    }

}
