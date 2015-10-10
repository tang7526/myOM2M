package org.eclipse.om2m.core.dao;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.core.constants.Constants;
import org.eclipse.persistence.config.PersistenceUnitProperties;

public class DBAccess {
	/** Logger */
	private static final Log LOGGER = LogFactory.getLog(DBAccess.class);

	/** Local instance of the object */
	private static DBAccess dbAccess = new DBAccess();

	/** EntityManagerFactory connected to the DB */

	private EntityManagerFactory emf;

	private DBAccess() {
	}

	public static DBAccess getInstance() {
		return dbAccess;
	}
	
	public static EntityManager createEntityManager(){
		return getInstance().emf.createEntityManager();
	}
	
	/**
	 * Closes the connection to the database.
	 */
	private void close() {
		if(emf != null){
			emf.close();
		}
	}
	
	public void init(){
		LOGGER.info("Intializing DataBase...");
		try{			
			Map<Object,Object> properties = new HashMap<Object, Object>() ; 
			properties.put(PersistenceUnitProperties.JDBC_DRIVER, Constants.DB_DRIVER);
			properties.put(PersistenceUnitProperties.JDBC_URL, Constants.DB_URL);
			properties.put(PersistenceUnitProperties.JDBC_USER, Constants.DB_USER);
			properties.put(PersistenceUnitProperties.JDBC_PASSWORD, Constants.DB_PASSWORD);
			
			if (Constants.DB_RESET){
				properties.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.DROP_AND_CREATE);
			} else {
				properties.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.CREATE_OR_EXTEND);
			}
			
			LOGGER.info("Creating new EntityManagerFactory...");
			emf = Persistence.createEntityManagerFactory("om2mdb", properties);
		} catch (Exception e){
			LOGGER.error("Error in creation of EntityManagerFactory",e);
		}
		if (emf != null){
			LOGGER.info("DataBase initialized.");
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			em.close();
		} else {
			LOGGER.error("ERROR initializing Database: EntityManagerFactory is null!");
		}
	}
}
