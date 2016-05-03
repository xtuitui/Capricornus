package com.xiaotuitui.testframework;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.persistence.Column;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.ext.db2.Db2DataTypeFactory;
import org.dbunit.ext.mssql.MsSqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.oracle.OracleDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.xml.sax.InputSource;

public class DbUnitUtil {
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	private static Connection connection;
	
	private static final String DATA_SET = "dataset";
	
	private static final String XML_ENCODING = "UTF-8";
	
	/**
	 * load the static resources
	 * */
	static{
		Properties properties = new Properties();
    	InputStream is = DbUnitUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
    	try {
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		driver = properties.getProperty("jdbc.driverClassName");
		url = properties.getProperty("jdbc.url");
    	username = properties.getProperty("jdbc.username");
    	password = properties.getProperty("jdbc.password");
	}
    /**
     * get the dbunit connection
     * 
     * @return databaseConnection
     * @throws DatabaseUnitException
     * @throws ClassNotFoundException 
     * @throws SQLException 
     * */
    public static IDatabaseConnection getDbunitConnection() throws DatabaseUnitException, ClassNotFoundException, SQLException{
    	Class.forName(driver);
		connection = DriverManager.getConnection(url, username, password);
        IDatabaseConnection databaseConnection = new DatabaseConnection(connection);
        if(driver.toLowerCase().contains("sqlserver")){
        	databaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MsSqlDataTypeFactory());
        }else if(driver.toLowerCase().contains("oracle")){
        	databaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new OracleDataTypeFactory());
        }else if(driver.toLowerCase().contains("db2")){
        	databaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new Db2DataTypeFactory());
        }else if(driver.toLowerCase().contains("mysql")){
        	databaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
        }
        return databaseConnection;
    }
    
    /**
     * get the dbunit connection by schema
     * 
     * @param schema                 sqlserver default is "dbo", if not point this param, it will include sys.trace_xe_action_map and sys.trace_xe_event_map, 
     *                                                 this two table is system table, we do not want backup it.
     * @return databaseConnection
     * @throws DatabaseUnitException 
     * */
    public static IDatabaseConnection getDbunitConnectionByMsSqlAndSchema(String schema) throws DatabaseUnitException, ClassNotFoundException, SQLException{
    	Class.forName(driver);
		connection = DriverManager.getConnection(url, username, password);
        IDatabaseConnection databaseConnection = new DatabaseConnection(connection,schema);
        databaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MsSqlDataTypeFactory());
        return databaseConnection;
    }
    /**
     * get the dbunit dataSet by xml file name
     * 
     * @param name    xml file name
     * @return dataSet
     * @throws DataSetException 
     * */
    public static IDataSet getDbunitDataSetByXmlFileName(String filePath) throws DataSetException{
        IDataSet dataSet = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(DbUnitUtil.class.getClassLoader().getResourceAsStream(filePath))));
        return dataSet;
    }
    /**
     * get the dbunit dataSet by list
     * 
     * @param list    list
     * @return dataSet
     * @throws IOException 
     * @throws DataSetException 
     * @throws NoSuchMethodException 
     * @throws SecurityException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * */
    public static IDataSet getDbunitDataSetByList(List<Object> list, String tableName) throws IOException, DataSetException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    	Document document = DocumentHelper.createDocument();
    	document.setXMLEncoding(XML_ENCODING);
    	Element root = document.addElement(DATA_SET);
    	for(Object object:list){
    		Element e = root.addElement(tableName);
    		Field[] fields = object.getClass().getDeclaredFields();
    		for(Field field:fields){
    			if(Modifier.isStatic(field.getModifiers())){
    				continue;
    			}
    			Column column = field.getAnnotation(Column.class);
    			String columnName = column.name();
    			String fieldName = field.getName();
    			String firstStr = fieldName.substring(0, 1);
    			Method method = object.getClass().getMethod(EntityUtil.ENTITY_GET+fieldName.replaceFirst(firstStr, firstStr.toUpperCase()), new Class[]{});
    			Object value = method.invoke(object);
    			if(value==null){
    				continue;
    			}else{
    				if(value instanceof java.util.Date||value instanceof java.sql.Date){
    					e.addAttribute(columnName, EntityUtil.simpleDateFormat.format(value));
    				}else{
    					e.addAttribute(columnName, value.toString());
    				}
    			}
    		}
    	}
		IDataSet dataSet = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(new StringReader(document.asXML()))));
		return dataSet;
	}
    /**
     * backUp all the table by dbname to a xml file
     * 
     * @param schema                 sqlserver default is "dbo", if not point this param, it will include sys.trace_xe_action_map and sys.trace_xe_event_map, 
     *                                                 this two table is system table, we do not want backup it.
     * @param filePath    the outPut source xml path
     * @throws DatabaseUnitException 
     * @throws SQLException 
     * @throws IOException 
     * @throws ClassNotFoundException 
     * */
    public static void backUpAllTableToXmlFileByMsSqlAndSchema(String filePath,String schema) throws DatabaseUnitException, SQLException, IOException, ClassNotFoundException{
        IDatabaseConnection databaseConnection = getDbunitConnectionByMsSqlAndSchema(schema);
        IDataSet dataSet = databaseConnection.createDataSet();
        Writer writer = new FileWriter(filePath);
        FlatXmlDataSet.write(dataSet, writer);
    }
    /**
     * backUp all the table by dbname to a xml file
     * 
     * @param name    get dbname to connect the database in dbunit
     * @param filePath    the outPut source xml path
     * @throws DatabaseUnitException 
     * @throws SQLException 
     * @throws IOException 
     * @throws ClassNotFoundException 
     * */
    public static void backUpAllTableToXmlFileByMsSql(String filePath) throws DatabaseUnitException, SQLException, IOException, ClassNotFoundException{
        IDatabaseConnection databaseConnection = getDbunitConnection();
        IDataSet dataSet = databaseConnection.createDataSet();
        Writer writer = new FileWriter(filePath);
        FlatXmlDataSet.write(dataSet, writer);
    }
    
    /**
     * backUp custom table by dbname to a xml file
     * 
     * @param name    get dbname to connect the database in dbunit
     * @param tableNames    table name which you want to backUp
     * @param filePath    the outPut source xml path
     * @throws DatabaseUnitException 
     * @throws IOException 
     * @throws ClassNotFoundException 
     * @throws SQLException 
     * */
    public static void backUpCustomTableToXmlFileByMsSql(String name,String[] tableNames,String filePath) throws DatabaseUnitException, IOException, SQLException, ClassNotFoundException{
        IDatabaseConnection databaseConnection = getDbunitConnection();
        QueryDataSet queryDataSet = new QueryDataSet(databaseConnection);
        for(String tableName:tableNames){
            queryDataSet.addTable(tableName);
        }
        Writer writer = new FileWriter(filePath);
        FlatXmlDataSet.write(queryDataSet, writer);
    }
    
    /**
     * resume the table data by xml file
     * 
     * @param name    get dbname to connect the database in dbunit
     * @param filePath    the input source xml path
     * @throws DatabaseUnitException 
     * @throws FileNotFoundException 
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @throws IOException 
     * */
    public static void resumeMsSqlTableByXmlFile(String name,String filePath) throws DatabaseUnitException, SQLException, IOException, ClassNotFoundException {
        IDatabaseConnection databaseConnection = getDbunitConnection();
        IDataSet dataSet = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(new FileInputStream(filePath))));
        DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, dataSet);
    }
    
    public static void closeConnection(IDatabaseConnection databaseConnection){
    	closeDbunitDatabaseConnection(databaseConnection);
    	closeConnection();
    }
    
    private static void closeDbunitDatabaseConnection(IDatabaseConnection databaseConnection){
    	if(databaseConnection!=null){
    		try {
				databaseConnection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    }
    
    private static void closeConnection(){
    	if(connection!=null){
    		try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    }
    
}