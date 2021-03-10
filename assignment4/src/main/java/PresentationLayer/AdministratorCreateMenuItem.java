package PresentationLayer;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import BusinessLayer.IRestaurantProcessing;
import BusinessLayer.Restaurant;
import BusinessLayer.Validator;
import DataLayer.RestaurantSerializator;

public class AdministratorCreateMenuItem extends JFrame {

    private JTextField base = new JTextField(30);
    private JTextField price = new JTextField(34);
    private JTextArea composite = new JTextArea(5,30);


    private JLabel titleBase = new JLabel("Create new Base Product");
    private JLabel baseLabel = new JLabel("Base Product");
    private JLabel priceLabel = new JLabel("Price");

    private JLabel titleComposite  = new JLabel("Create new Composite Product");

    private JButton baseButton = new JButton("Create");
    private JButton compositeButton = new JButton("Create");

    private JPanel content = new JPanel();
    private RestaurantSerializator ser =  new RestaurantSerializator();


    public AdministratorCreateMenuItem(){

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();
        JPanel panel6 = new JPanel();
        JPanel panel7 = new JPanel();
        panel1.setLayout(new FlowLayout());
        panel2.setLayout(new FlowLayout());
        panel3.setLayout(new FlowLayout());
        panel4.setLayout(new FlowLayout());
        panel5.setLayout(new FlowLayout());
        panel6.setLayout(new FlowLayout());
        panel7.setLayout(new FlowLayout());
        panel1.add(titleBase);
        panel2.add(baseLabel);
        panel2.add(base);
        panel3.add(priceLabel);
        panel3.add(price);
        panel4.add(baseButton);
        panel5.add(titleComposite);
        panel6.add(composite);
        panel7.add(compositeButton);
        content.add(panel1);
        content.add(panel2);
        content.add(panel3);
        content.add(panel4);
        content.add(panel5);
        content.add(panel6);
        content.add(panel7);
        this.setContentPane(content);
        this.pack();
        this.setVisible(true);
        this.setTitle("Create new Menu Item");
        createBase();
        createCompositeProduct();


    }

    public void createBase(){
        baseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IRestaurantProcessing restaurant = ser.deserializare();
                String baseProduct = base.getText(); //citeste numele
                String basePrice = price.getText();  //citeste pretul
                Validator validatorProduct = new Validator(baseProduct);
                Validator validatorPrice = new Validator(basePrice);
                if(baseProduct.equals("") || basePrice.equals("") || !validatorProduct.isNotEmpty() || !validatorPrice.isNotEmpty() ){
                    JOptionPane.showMessageDialog(content, "A field is empty");
                }
                else{

                    if(!validatorPrice.isFloatValid()){
                        JOptionPane.showMessageDialog(content, "Price is not valid");
                        price.setText(null);
                    }


                    else if(((Restaurant)restaurant).createNewBaseProduct(baseProduct,basePrice)==1){
                        JOptionPane.showMessageDialog(content, "Base Product was created successfully");
                        price.setText(null);
                        base.setText(null);
                    }
                    else{
                        JOptionPane.showMessageDialog(content, "This Base Product already exists");
                        price.setText(null);
                        base.setText(null);
                    }

                }
            }
        });
    }

    public void createCompositeProduct(){
        compositeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IRestaurantProcessing restaurant = ser.deserializare();
                String compositeProduct = composite.getText(); //citeste numele
                if(compositeProduct.equals("")){
                    JOptionPane.showMessageDialog(content, "The field is empty");
                }
                else {
                    Validator validator = new Validator(compositeProduct);
                    if(!validator.isNotEmpty()){
                        JOptionPane.showMessageDialog(content, "The field is empty");
                    }

                    else{
                        int rez = ((Restaurant)restaurant).createNewCompositeProduct(compositeProduct);
                        if(rez == 0) {
                            JOptionPane.showMessageDialog(content, "A menu item does not exists");
                            composite.setText(null);
                        }
                        else if(rez == 1){
                            JOptionPane.showMessageDialog(content, "Composite product was created successfully");
                            composite.setText(null);
                        }
                        else {
                            JOptionPane.showMessageDialog(content, "This composite product already exists");
                            composite.setText(null);
                        }
                    }



                }
            }

        });
    }


}
