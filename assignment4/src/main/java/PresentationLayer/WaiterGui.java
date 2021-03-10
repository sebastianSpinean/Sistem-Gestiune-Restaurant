package PresentationLayer;

import BusinessLayer.*;
import BusinessLayer.MenuItem;
import DataLayer.RestaurantSerializator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class WaiterGui extends JFrame {

    private JPanel content = new JPanel();
    private JLabel title = new JLabel("Waiter Window");
    private JButton create = new JButton("Create order");
    private JButton view = new JButton("View");

    public WaiterGui(){
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        panel1.setLayout(new FlowLayout());
        panel2.setLayout(new FlowLayout());
        panel3.setLayout(new FlowLayout());
        panel1.add(title);
        panel2.add(create);
        panel3.add(view);
        content.add(panel1);
        content.add(panel2);
        content.add(panel3);
        this.setContentPane(content);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("Waiter");
        this.setSize(300,300);
        create();
        view();
    }

    public void create(){
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WaiterCreateOrder createOrder = new WaiterCreateOrder();
            }
        });
    }

    public void view(){
        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IRestaurantProcessing restaurant = RestaurantSerializator.deserializare();
                Map<Order, ArrayList<MenuItem>> orderItems = ((Restaurant)restaurant).getOrderItems();
                String[][] cells = new String[orderItems.size()][4];
                String[] heders = {"Table","Date","Orders","Total price"};
                int k = 0;

                for( Map.Entry<Order, ArrayList<MenuItem>> i : orderItems.entrySet()){//se creaza celulele tabelului
                    String name = "";
                    float price = 0;
                    ArrayList<MenuItem> list = i.getValue();
                    for(MenuItem j : list){
                        if(j instanceof BaseProduct){
                            name = name + ((BaseProduct) j).getName()+"/";
                            price = price + ((BaseProduct) j).getPrice();
                        }
                        else if(j instanceof CompositeProduct){
                            name = name + ((CompositeProduct) j).getName()+"/";
                            price = price + ((CompositeProduct) j).getPrice();
                        }
                    }
                    name = name.substring(0,name.length()-1);
                    cells[k][0] = i.getKey().getTable()+"";
                    cells[k][1] = i.getKey().getDate()+"";
                   cells[k][2] = name;
                    cells[k][3] = price+"";
                    k++;
                }
                WaiterViewOrder gui = new WaiterViewOrder(cells,heders);



            }
        });

    }
}

