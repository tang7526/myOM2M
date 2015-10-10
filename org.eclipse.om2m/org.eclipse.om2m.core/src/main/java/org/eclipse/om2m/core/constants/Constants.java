package org.eclipse.om2m.core.constants;

public class Constants {

    public static final String SCL_ID = System.getProperty("org.eclipse.om2m.sclBaseId","nscl");
    
    /** Default admin access right profile */
    public static final String ADMIN_PROFILE_ID = "AR_ADMIN";
    
	// DB parameters
    /** Boolean specifying if the database should be reset */
    public static final boolean DB_RESET = Boolean.valueOf(System.getProperty("org.eclipse.om2m.dbReset","true"));
	/** URL of the database (file, memory, server...) */
	public static final String DB_URL = System.getProperty("org.eclipse.om2m.dbUrl", "jdbc:h2:./data/database");
	/** JDBC Driver used for the database */
	public static final String DB_DRIVER = System.getProperty("org.eclipse.om2m.dbDriver", "org.h2.Driver");
	/** User parameter for the database */
	public static final String DB_USER = System.getProperty("org.eclipse.om2m.dbUser", "om2m");
	/** User password for the database */
	public static final String DB_PASSWORD = System.getProperty("org.eclipse.om2m.dbPassword", "om2m");
	
    //SearchStrings prefixes
    /** Search String resource type prefix. */
    public static final String SEARCH_STRING_RES_TYPE = "ResourceType/";
    /** Search String resource id prefix. */
    public static final String SEARCH_STRING_RES_ID = "ResourceID/";
    
	/** Name of the persistence unit in persistence.xml file */
	public static final String PERSISTENCE_UNIT_NAME = "om2mdb";

}
