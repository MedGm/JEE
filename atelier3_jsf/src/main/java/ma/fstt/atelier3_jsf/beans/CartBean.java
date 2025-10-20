package ma.fstt.atelier3_jsf.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.Data;
import ma.fstt.atelier3_jsf.entities.Cart;
import ma.fstt.atelier3_jsf.entities.CartItem;
import ma.fstt.atelier3_jsf.entities.Product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Named
@SessionScoped
@Data
public class CartBean implements Serializable {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Inject
    private UserBean userBean;
    
    private Cart cart;
    private List<CartItem> cartItems;
    
    // Helper methods for database operations
    private Optional<Cart> findByUser(ma.fstt.atelier3_jsf.entities.User user) {
        TypedQuery<Cart> query = entityManager.createQuery(
            "SELECT c FROM Cart c WHERE c.user = :user", Cart.class);
        query.setParameter("user", user);
        try {
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
    private Optional<Product> findProductById(Long id) {
        Product product = entityManager.find(Product.class, id);
        return Optional.ofNullable(product);
    }
    
    @Transactional
    private Cart saveCart(Cart cart) {
        entityManager.persist(cart);
        return cart;
    }
    
    @Transactional
    private Cart updateCart(Cart cart) {
        return entityManager.merge(cart);
    }
    
    public void loadCart() {
        if (userBean.isLoggedIn()) {
            cart = findByUser(userBean.getCurrentUser()).orElse(null);
            if (cart == null) {
                cart = new Cart();
                cart.setUser(userBean.getCurrentUser());
                saveCart(cart);
            }
            cartItems = cart.getCartItems();
        }
    }
    
    public void addToCart(Long productId, Integer quantity) {
        if (!userBean.isLoggedIn()) {
            return;
        }
        
        loadCart();
        
        Product product = findProductById(productId).orElse(null);
        if (product == null || product.getStock() < quantity) {
            return;
        }
        
        CartItem existingItem = cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
        
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setUnitPrice(product.getPrice());
            cartItems.add(newItem);
        }
        
        cart.setCartItems(cartItems);
        cart.calculateTotal();
        updateCart(cart);
    }
    
    public void removeFromCart(Long productId) {
        if (!userBean.isLoggedIn()) {
            return;
        }
        
        loadCart();
        cartItems.removeIf(item -> item.getProduct().getId().equals(productId));
        cart.setCartItems(cartItems);
        cart.calculateTotal();
        updateCart(cart);
    }
    
    public void updateQuantity(Long productId, Integer newQuantity) {
        if (!userBean.isLoggedIn() || newQuantity <= 0) {
            return;
        }
        
        loadCart();
        cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(newQuantity);
                    item.calculateSubtotal();
                    cart.calculateTotal();
                    updateCart(cart);
                });
    }
    
    public BigDecimal getCartTotal() {
        if (cart == null) {
            return BigDecimal.ZERO;
        }
        return cart.getTotalAmount();
    }
    
    public int getCartItemCount() {
        if (cartItems == null) {
            return 0;
        }
        return cartItems.stream().mapToInt(CartItem::getQuantity).sum();
    }
    
    public void clearCart() {
        if (userBean.isLoggedIn() && cart != null) {
            cartItems.clear();
            cart.setCartItems(cartItems);
            cart.calculateTotal();
            updateCart(cart);
        }
    }
}
