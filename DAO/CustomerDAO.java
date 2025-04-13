package DAO;

import DTO.CustomerDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {
    public CustomerDTO getCustomerById(int customerId) throws SQLException {
        String query = "SELECT * FROM customers WHERE customer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new CustomerDTO(rs.getInt("customer_id"), rs.getString("name"), rs.getString("email"));
            }
        }
        return null;
    }
}