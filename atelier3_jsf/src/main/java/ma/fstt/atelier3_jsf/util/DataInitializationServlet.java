package ma.fstt.atelier3_jsf.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.fstt.atelier3_jsf.entities.Category;
import ma.fstt.atelier3_jsf.entities.Product;
import ma.fstt.atelier3_jsf.entities.User;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name = "DataInitializationServlet", urlPatterns = "/init-data", loadOnStartup = 1)
public class DataInitializationServlet extends HttpServlet {
    
    private EntityManagerFactory emf;
    private EntityManager em;
    
    @Override
    public void init() throws ServletException {
        super.init();
        try {
            emf = Persistence.createEntityManagerFactory("default");
            em = emf.createEntityManager();
            initializeData();
        } catch (Exception e) {
            System.err.println("Error initializing EntityManager: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public void destroy() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
        super.destroy();
    }
    
    private void initializeData() {
        try {
            // Check if data already exists
            Long userCount = em.createQuery("SELECT COUNT(u) FROM User u", Long.class).getSingleResult();
            if (userCount > 0) {
                System.out.println("Database already initialized with data!");
                return;
            }
            
            System.out.println("Initializing database with sample data...");
            
            // Start transaction
            em.getTransaction().begin();
            
            // Create categories
            Category electronics = new Category();
            electronics.setName("Electronics");
            electronics.setDescription("Electronic devices and gadgets");
            em.persist(electronics);
            
            Category clothing = new Category();
            clothing.setName("Clothing");
            clothing.setDescription("Fashion and apparel");
            em.persist(clothing);
            
            Category books = new Category();
            books.setName("Books");
            books.setDescription("Books and literature");
            em.persist(books);
            
            // Create admin user
            User admin = new User();
            admin.setEmail("medgm@store.com");
            admin.setPassword("medgm123");
            admin.setFirstName("Med");
            admin.setLastName("Gm");
            admin.setRole(User.UserRole.ADMIN);
            em.persist(admin);
            
            // Create customer user
            User customer = new User();
            customer.setEmail("customer@store.com");
            customer.setPassword("customer123");
            customer.setFirstName("mohamed");
            customer.setLastName("ali");
            customer.setPhone("0644246223");
            customer.setAddress("AV RIF NR 88 KHANDAK ZERBOUH");
            em.persist(customer);
            
            // Create sample products
            Product laptop = new Product();
            laptop.setName("Laptop Computer");
            laptop.setDescription("High-performance laptop with 16GB RAM and 256GB NVME!!");
            laptop.setPrice(new BigDecimal("3999.99"));
            laptop.setStock(10);
            laptop.setImageUrl("https://karamtech.ma/wp-content/uploads/2024/05/HP-EliteBook-840-G7-Intel-Core-i5-10310U-Ordinateur-portable-356-cm-14-16-Go-DDR4-256-Go-SSD-Argent-Reconditionnee_PC-PORTABLE-Laptop_36937_1-7.jpeg");
            laptop.setCategory(electronics);
            em.persist(laptop);
            
            Product smartphone = new Product();
            smartphone.setName("Smartphone");
            smartphone.setDescription("Latest smartphone with advanced camera and 5G connectivity");
            smartphone.setPrice(new BigDecimal("1699.99"));
            smartphone.setStock(25);
            smartphone.setImageUrl("https://m.media-amazon.com/images/I/61aiFCe6PpL._AC_SL1500_.jpg");
            smartphone.setCategory(electronics);
            em.persist(smartphone);
            
            Product tshirt = new Product();
            tshirt.setName("Cotton T-Shirt");
            tshirt.setDescription("Comfortable 100% cotton t-shirt in various colors");
            tshirt.setPrice(new BigDecimal("89.99"));
            tshirt.setStock(50);
            tshirt.setImageUrl("https://hemptique.com/cdn/shop/files/hemptique-hemp-crew-neck-t-shirt-black.jpg?v=1725914958&width=1500");
            tshirt.setCategory(clothing);
            em.persist(tshirt);
            
            Product novel = new Product();
            novel.setName("Programming Book");
            novel.setDescription("Comprehensive guide to modern programming techniques");
            novel.setPrice(new BigDecimal("300"));
            novel.setStock(20);
            novel.setImageUrl("https://bpbonline.com/cdn/shop/products/9789391392062.jpg?v=1755669998");
            novel.setCategory(books);
            em.persist(novel);
            
            Product cookbook = new Product();
            cookbook.setName("JEE Programming Book");
            cookbook.setDescription("Complete guide to Java Enterprise Edition development");
            cookbook.setPrice(new BigDecimal("400"));
            cookbook.setStock(15);
            cookbook.setImageUrl("https://m.media-amazon.com/images/I/91t5bwHU4DL._UF1000,1000_QL80_.jpg");
            cookbook.setCategory(books);
            em.persist(cookbook);
            
            // Commit transaction
            em.getTransaction().commit();
            
            System.out.println("Database initialized successfully with sample data!");
            
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.getWriter().println("Database initialization completed!");
    }
}
