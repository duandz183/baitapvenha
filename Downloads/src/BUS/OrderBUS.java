package BUS;
import DAO.OrderDAO;
import DAO.ProductDAO;
import DTO.OrderDTO;
import DTO.OrderItemDTO;
import DTO.ProductDTO;

import java.util.Date;
import java.util.List;

public class OrderBUS {
    private OrderDAO orderDAO;
    private ProductDAO productDAO;

    public OrderBUS() {
        this.orderDAO = new OrderDAO();
        this.productDAO = new ProductDAO();
    }


    public void addOrder(OrderDTO order) throws Exception {

        for (OrderItemDTO item : order.getOrderItems()) {
            ProductDTO product = productDAO.getProductById(item.getProductId());
            if (product == null) {
                throw new Exception("Product not found: " + item.getProductId());
            }
            item.setPrice(product.getPrice() * item.getQuantity());
        }
        order.setOrderDate(new Date());
        orderDAO.addOrder(order);
    }


    public List<OrderDTO> getOrderHistory(int customerId) throws Exception {
        return orderDAO.getOrdersByCustomerId(customerId);
    }


    public double calculateOrderTotal(OrderDTO order) {
        double total = 0;
        for (OrderItemDTO item : order.getOrderItems()) {
            total += item.getPrice();
        }
        return total;
    }
}