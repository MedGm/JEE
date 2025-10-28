package web;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class EJBClientUtil {
    
    private static final String WILDFLY_HOST = "localhost";
    private static final int WILDFLY_PORT = 8080; // or 9090 if you changed it

    @SuppressWarnings("unchecked")
    public static <T> T lookupRemoteEJB(String jndiName) throws NamingException {
        Properties jndiProperties = new Properties();
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, 
            "org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProperties.put(Context.PROVIDER_URL, 
            "remote+http://" + WILDFLY_HOST + ":" + WILDFLY_PORT);
        jndiProperties.put("jboss.naming.client.ejb.context", true);
        
        Context context = new InitialContext(jndiProperties);
        return (T) context.lookup(jndiName);
    }
    
    /**
     * Build JNDI name for WildFly EJB
     */
    public static String buildJndiName(String moduleName, String beanName, String interfaceName) {
        // ejb:/{app-name}/{module-name}/{distinct-name}/{bean-name}!{interface}
        return String.format("ejb:/%s//%s!%s", moduleName, beanName, interfaceName);
    }
    
    /**
     * Convenience method for our EJBs
     */
    public static <T> T lookupEJB(String beanName, String interfaceName) throws NamingException {
        String jndiName = buildJndiName("atelier4_ejb-1.0-SNAPSHOT", beanName, interfaceName);
        return lookupRemoteEJB(jndiName);
    }
}

