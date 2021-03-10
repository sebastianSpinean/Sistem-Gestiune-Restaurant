package PresentationLayer;

import BusinessLayer.*;
import BusinessLayer.MenuItem;
import DataLayer.RestaurantSerializator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WaiterCreateOrder extends JFrame {

    private JPanel content = new JPanel();
    private JLabel title = new JLabel("Create new order");
    private JLabel tableLabel = new JLabel("Select a table");
    private JLabel productLabel = new JLabel("Select menu item");

    private JComboBox item;

    private JTextField tableText = new JTextField(6);
    private JTextArea orderText = new JTextArea(7,30);
    private JButton addItem = new JButton("Add item");
    private JButton finalize = new JButton("Finalize order");
    private JButton clear = new JButton("Clear");

    private ArrayList<MenuItem> items = new ArrayList<MenuItem>();
    private IRestaurantProcessing restaurant = RestaurantSerializator.deserializare();
    private ChefGui oldChef;
    private ChefGui newChef = new ChefGui();



    public WaiterCreateOrder(){

        MenuItem[] menu = new MenuItem[((Restaurant)restaurant).getMenuItems().size()];
        int j=0;
        for(MenuItem i : ((Restaurant)restaurant).getMenuItems()){
            menu[j]=i;
            j++;
        }
        item = new JComboBox(menu);
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        orderText.setEditable(false);
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();
       JPanel panel6 = new JPanel();

        panel1.setLayout(new FlowLayout());
        panel2.setLayout(new FlowLayout());
        panel3.setLayout(new FlowLayout());
        panel4.setLayout(new FlowLayout());
        panel5.setLayout(new FlowLayout());
        panel6.setLayout(new FlowLayout());

        panel1.add(title);
        panel2.add(tableLabel);
        panel2.add(tableText);
        panel3.add(productLabel);
        panel3.add(item);

        panel4.add(addItem);
        panel5.add(orderText);
        panel6.add(finalize);
        panel6.add(clear);
        content.add(panel1);
        content.add(panel2);
        content.add(panel3);
        content.add(panel4);
        content.add(panel5);
        content.add(panel6);
        this.setContentPane(content);
        this.pack();
        this.setVisible(true);
        this.setSize(400,400);
        this.setTitle("Create order");
        addSimpleItem();
        finalizareOrder();
        clear();

    }

    public void addSimpleItem(){
        addItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//se adauga un produs la comanda
                Restaurant restaurant = RestaurantSerializator.deserializare();
                MenuItem itemSelected = (MenuItem)item.getSelectedItem();
                items.add(itemSelected);
                if(itemSelected instanceof BaseProduct){
                    orderText.append(itemSelected.toString()+"\n");
                }
                else if(itemSelected instanceof CompositeProduct){
                    orderText.append(itemSelected.toString()+"\n");

                }


            }
        });
    }

    public void finalizareOrder(){
        finalize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //se finalizeaza comanda
                Restaurant restaurant = RestaurantSerializator.deserializare();
                oldChef = newChef;
                oldChef.setVisible(false);
                newChef = new ChefGui();
                restaurant.addObserver(newChef);
                String table = tableText.getText();
                Validator validator = new Validator(table);
                if(!validator.isNotEmpty() || table.equals("")){
                    JOptionPane.showMessageDialog(content, "Select tabel field is empty");
                }
                else if(!validator.isFloatValid()){
                    JOptionPane.showMessageDialog(content, "Select tabel field is not a number");
                }
                else{
                    if(items.isEmpty()){
                        return;
                    }
                    restaurant.createOrder(items,Integer.parseInt(table));
                }
                items = new ArrayList<MenuItem>();
                orderText.setText(null);
                tableText.setText(null);
            }
        });
    }

    public void clear(){
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                items = new ArrayList<MenuItem>();
                orderText.setText(null);
                tableText.setText(null);
            }
        });
    }


}
