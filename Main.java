import BUS.OrderBUS;
import DTO.OrderDTO;
import DTO.OrderItemDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            List<OrderItemDTO> items = new ArrayList<>();
            items.add(new OrderItemDTO(0, 0, 1, 2, 0));
            items.add(new OrderItemDTO(0, 0, 2, 1, 0));

            OrderDTO order = new OrderDTO(0, 1, new Date(), items);
            OrderBUS orderBUS = new OrderBUS();


            orderBUS.addOrder(order);
            System.out.println("Order added successfully!");


            List<OrderDTO> orders = orderBUS.getOrderHistory(1);
            for (OrderDTO o : orders) {
                System.out.println("Order ID: " + o.getOrderId() + ", Date: " + o.getOrderDate());
                System.out.println("Total: " + orderBUS.calculateOrderTotal(o));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}