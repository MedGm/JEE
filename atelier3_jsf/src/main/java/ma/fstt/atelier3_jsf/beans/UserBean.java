package ma.fstt.atelier3_jsf.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.Data;
import ma.fstt.atelier3_jsf.entities.User;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Named
@SessionScoped
@Data
public class UserBean implements Serializable {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private User currentUser;
    private User newUser = new User();
    private String loginEmail;
    private String loginPassword;
    
    // Helper methods for database operations
    private Optional<User> findByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery(
            "SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        try {
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
    private boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }
    
    @Transactional
    private User save(User user) {
        entityManager.persist(user);
        return user;
    }
    
    private List<User> findAll() {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }
    
    public String login() {
        try {
            if (loginEmail == null || loginEmail.trim().isEmpty() || 
                loginPassword == null || loginPassword.trim().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please fill in all fields"));
                return null;
            }
            
            User user = findByEmail(loginEmail.trim()).orElse(null);
            if (user != null && user.getPassword().equals(loginPassword)) {
                currentUser = user;
                loginEmail = null;
                loginPassword = null;
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Login successful!"));
                return "index?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Invalid email or password"));
                return null;
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Login failed: " + e.getMessage()));
            return null;
        }
    }
    
    public String register() {
        try {
            if (newUser.getEmail() == null || newUser.getEmail().trim().isEmpty() ||
                newUser.getPassword() == null || newUser.getPassword().trim().isEmpty() ||
                newUser.getFirstName() == null || newUser.getFirstName().trim().isEmpty() ||
                newUser.getLastName() == null || newUser.getLastName().trim().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please fill in all required fields"));
                return null;
            }
            
            if (existsByEmail(newUser.getEmail().trim())) {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Email already exists"));
                return null;
            }
            
            newUser.setEmail(newUser.getEmail().trim());
            newUser.setRole(User.UserRole.CUSTOMER); // Set default role
            save(newUser);
            currentUser = newUser;
            newUser = new User();
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Registration successful!"));
            return "index?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Registration failed: " + e.getMessage()));
            return null;
        }
    }
    
    public String logout() {
        currentUser = null;
        return "index?faces-redirect=true";
    }
    
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    public boolean isAdmin() {
        return currentUser != null && currentUser.getRole() == User.UserRole.ADMIN;
    }
    
    public List<User> getAllUsers() {
        return findAll();
    }
}
