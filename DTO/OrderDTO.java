package DTO;
import java.util.Date;
import java.util.List;

public class OrderDTO {
    private int orderId;
    private int customerId;
    private Date orderDate;
    private List<OrderItemDTO> orderItems;


    public OrderDTO(int orderId, int customerId, Date orderDate, List<OrderItemDTO> orderItems) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderItems = orderItems;
    }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
    public List<OrderItemDTO> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItemDTO> orderItems) { this.orderItems = orderItems; }
}