package aprendo.maven.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
	
	public static EntityManagerFactory factory = null;
	
	static {
		init();
	}
	
	private static void init() {
		try {
			if(factory == null) {
				factory = Persistence.createEntityManagerFactory("aprendo.maven.hibernate");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static EntityManager geEntityManager() {
		return factory.createEntityManager(); // Provê a parte de persistência...
	}
	
	public static Object getPrimaryKey(Object entity) {// retorna a PK
		return factory.getPersistenceUnitUtil().getIdentifier(entity);
	}
}
