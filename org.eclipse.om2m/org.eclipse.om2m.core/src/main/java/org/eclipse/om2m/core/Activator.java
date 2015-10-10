package org.eclipse.om2m.core;

import java.util.Date;

import javax.persistence.EntityManager;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.resource.Refs;
import org.eclipse.om2m.commons.resource.SclBase;
import org.eclipse.om2m.commons.resource.SearchStrings;
import org.eclipse.om2m.commons.utils.DateConverter;
import org.eclipse.om2m.core.constants.Constants;
import org.eclipse.om2m.core.dao.DAOFactory;
import org.eclipse.om2m.core.dao.DBAccess;

public class Activator implements BundleActivator {

	/** Logger */
	private static Log LOGGER = LogFactory.getLog(Activator.class);

	@Override
	public void start(BundleContext context) throws Exception {
		LOGGER.info("Starting SCL..");
		initScl();
		LOGGER.info("SCL Started.");

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		LOGGER.info("Stopping SCL");
	}

	public static void initScl() {
		// Init JPA DBAccess
		LOGGER.info("Init JPA DB Access");
		DBAccess.getInstance().init();

		// Create SclBase resource
		LOGGER.info("Create SclBase resource");
		initSclBase();

		//
		// // Create AccessRight resource
		// LOGGER.info("Create AccessRight resource");
		// initAccessRight();
		//
		// // Create JAXBContext instance for ETSI resources
		// LOGGER.info("Init XmlMapper");
		// XmlMapper.getInstance();
		//
		// // Create JAXBContext instance for oBIX objects
		// LOGGER.info("Init ObixMapper");
		// ObixMapper.getInstance();
	}
	
    public static void initSclBase() {
        // Create SclBase object
        SclBase sclBase = new SclBase();
        sclBase.setUri(Constants.SCL_ID);
        sclBase.setAccessRightID(sclBase.getUri() + Refs.ACCESSRIGHTS_REF+"/" + Constants.ADMIN_PROFILE_ID);
        sclBase.setCreationTime(DateConverter.toXMLGregorianCalendar(new Date()).toString());
        sclBase.setLastModifiedTime(DateConverter.toXMLGregorianCalendar(new Date()).toString());
        SearchStrings searchStrings = new SearchStrings();
        searchStrings.getSearchString().add(Constants.SEARCH_STRING_RES_TYPE + sclBase.getClass().getSimpleName());
        searchStrings.getSearchString().add(Constants.SEARCH_STRING_RES_ID + Constants.SCL_ID);
        sclBase.setSearchStrings(searchStrings);

        EntityManager em = DBAccess.createEntityManager();
        em.getTransaction().begin();
        if (DAOFactory.getSclBaseDAO().find(sclBase.getUri(), em) == null){
            DAOFactory.getSclBaseDAO().create(sclBase, em);
        } else {
            DAOFactory.getSclBaseDAO().update(sclBase, em);
        }
        em.getTransaction().commit();
        em.close();
    }
}
