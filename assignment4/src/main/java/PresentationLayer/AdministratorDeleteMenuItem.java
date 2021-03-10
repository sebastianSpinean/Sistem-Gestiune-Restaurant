package PresentationLayer;

import BusinessLayer.IRestaurantProcessing;
import BusinessLayer.Restaurant;
import BusinessLayer.Validator;
import DataLayer.RestaurantSerializator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdministratorDeleteMenuItem extends JFrame {

    private JPanel content = new JPanel();
    private JLabel title = new JLabel("Delete a menu item");
    private JButton button = new JButton("Delete");
    private JTextField text = new JTextField(30);

   private RestaurantSerializator ser =  new RestaurantSerializator();
   private IRestaurantProcessing restaurant = ser.deserializare();

    public AdministratorDeleteMenuItem(){
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        panel1.add(title);
        panel2.add(text);
        panel3.add(button);
        content.add(panel1);
        content.add(panel2);
        content.add(panel3);
        this.setContentPane(content);
        this.pack();
        this.setVisible(true);
        this.setTitle("Delete Menu Item");
        delete();



    }

    public void delete(){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restaurant = ser.deserializare(); //citeste numele
                String field = text.getText();
                Validator validator = new Validator(field);
                if(!validator.isNotEmpty() || field.equals("")){
                    JOptionPane.showMessageDialog(content, "The field is empty");
                }
                else {
                    ((Restaurant)restaurant).deleteMenuItem(field);
                    JOptionPane.showMessageDialog(content, "Deleted successfully");
                    text.setText(null);

                }
            }
        });
    }



}
