package BusinessLayer;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Order implements Serializable {

    private int OrderId;  //id comanda
    private Date date;    //data
    private int table;    //numarul mesei

    public Order(){

    }

    public Order(int orderId, Date date, int table) {
        OrderId = orderId;
        this.date = date;
        this.table = table;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return OrderId == order.OrderId &&
                table == order.table &&
                Objects.equals(date, order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(OrderId, date, table);
    }

    public int getOrderId() {
        return OrderId;
    }


    public Date getDate() {
        return date;
    }



    public int getTable() {
        return table;
    }

}
