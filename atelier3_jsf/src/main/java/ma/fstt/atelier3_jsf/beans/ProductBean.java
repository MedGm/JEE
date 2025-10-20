package ma.fstt.atelier3_jsf.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.Data;
import ma.fstt.atelier3_jsf.entities.Product;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Named
@RequestScoped
@Data
public class ProductBean implements Serializable {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private List<Product> products;
    private Product selectedProduct;
    private String searchTerm;
    private Long selectedCategoryId;
    
    // Helper methods for database operations
    private List<Product> findByNameContaining(String name) {
        TypedQuery<Product> query = entityManager.createQuery(
            "SELECT p FROM Product p WHERE p.name LIKE :name", Product.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }
    
    private List<Product> findByCategory(Long categoryId) {
        TypedQuery<Product> query = entityManager.createQuery(
            "SELECT p FROM Product p WHERE p.category.id = :categoryId", Product.class);
        query.setParameter("categoryId", categoryId);
        return query.getResultList();
    }
    
    private List<Product> findAvailableProducts() {
        TypedQuery<Product> query = entityManager.createQuery(
            "SELECT p FROM Product p WHERE p.stock > 0", Product.class);
        return query.getResultList();
    }
    
    private List<Product> findAll() {
        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p", Product.class);
        return query.getResultList();
    }
    
    private Optional<Product> findById(Long id) {
        Product product = entityManager.find(Product.class, id);
        return Optional.ofNullable(product);
    }
    
    public void loadProducts() {
        try {
            if (selectedCategoryId != null) {
                products = findByCategory(selectedCategoryId);
            } else if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                products = findByNameContaining(searchTerm);
            } else {
                products = findAvailableProducts();
            }
        } catch (Exception e) {
            System.err.println("Error loading products: " + e.getMessage());
            e.printStackTrace();
            products = findAll(); // Fallback to all products
        }
    }
    
    @PostConstruct
    public void init() {
        loadProducts();
    }
    
    public void search() {
        loadProducts();
    }
    
    public void clearSearch() {
        searchTerm = null;
        selectedCategoryId = null;
        loadProducts();
    }
    
    public String viewProduct(Long productId) {
        selectedProduct = findById(productId).orElse(null);
        return "product-detail?faces-redirect=true";
    }
    
    public String selectCategory(Long categoryId) {
        selectedCategoryId = categoryId;
        loadProducts();
        return "products?faces-redirect=true";
    }
}
