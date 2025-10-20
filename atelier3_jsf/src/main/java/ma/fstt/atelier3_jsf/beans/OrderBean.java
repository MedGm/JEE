package ma.fstt.atelier3_jsf.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.Data;
import ma.fstt.atelier3_jsf.entities.Order;
import ma.fstt.atelier3_jsf.entities.OrderItem;

import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
@Data
public class OrderBean implements Serializable {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private List<Order> allOrders;
    
    // Helper methods for database operations
    private List<Order> findAllOrders() {
        TypedQuery<Order> query = entityManager.createQuery("SELECT o FROM Order o", Order.class);
        return query.getResultList();
    }
    
    @Transactional
    private Order saveOrder(Order order) {
        entityManager.persist(order);
        return order;
    }
    
    public void loadAllOrders() {
        allOrders = findAllOrders();
    }
    
    public List<Order> getAllOrders() {
        if (allOrders == null) {
            loadAllOrders();
        }
        return allOrders;
    }
    
    @Inject
    private CartBean cartBean;
    
    @Inject
    private UserBean userBean;
    
    private Order currentOrder;
    private String orderDate;
    private String orderTotal;
    
    public String placeOrder() {
        if (!userBean.isLoggedIn() || cartBean.getCartItemCount() == 0) {
            return "checkout?faces-redirect=true&error=true";
        }
        
        Order order = new Order();
        order.setUser(userBean.getCurrentUser());
        order.setTotalAmount(cartBean.getCartTotal());
        order.setShippingAddress(userBean.getCurrentUser().getAddress());
        
        // Convert cart items to order items
        for (ma.fstt.atelier3_jsf.entities.CartItem cartItem : cartBean.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getUnitPrice());
            order.getOrderItems().add(orderItem);
        }
        
        saveOrder(order);
        cartBean.clearCart();
        
        // Set order details for confirmation page
        this.currentOrder = order;
        this.orderDate = order.getOrderDate().toString();
        this.orderTotal = order.getTotalAmount().toString();
        
        return "order-confirmation?faces-redirect=true&orderId=" + order.getId();
    }
    
    public String getOrderDate() {
        return orderDate;
    }
    
    public String getOrderTotal() {
        return orderTotal;
    }
}
