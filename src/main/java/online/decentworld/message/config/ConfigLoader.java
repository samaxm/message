package online.decentworld.message.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * load config from properties files
 * @author Sammax
 *
 */
public class ConfigLoader {
	
	private static Logger logger=LoggerFactory.getLogger(ConfigLoader.class);

	final private static String DATASOURCE_CONFIG_FILE="DataSource.properties";
	final private static String CODIS_CONFIG_FILE="codis_config.properties";
	final private static String ZK_CONFIG_FILE="zk.properties";



	public static class DataSourceConfig{
		static{
			Properties dataSourcePro=new Properties();
			try {
				dataSourcePro.load(ConfigLoader.class.getClassLoader().getResourceAsStream(DATASOURCE_CONFIG_FILE));
				for(String property:dataSourcePro.stringPropertyNames()){
					DataSourceConfig.class.getField(property).set(null, dataSourcePro.getProperty(property));;					
				}
				checkNull(DataSourceConfig.class);
			} catch (Exception e) {
				logger.error("[LOAD_CONFIG_FAILED]",e);
			}
		}
		public static String ENVIORMENT;
	}
	
	public static class CodisConfig{
		static{
			Properties codisPro=new Properties();
			try {
				codisPro.load(ConfigLoader.class.getClassLoader().getResourceAsStream(CODIS_CONFIG_FILE));
				for(String property:codisPro.stringPropertyNames()){
					CodisConfig.class.getField(property).set(null, codisPro.getProperty(property));;					
				}	
				checkNull(CodisConfig.class);
			} catch (Exception e) {
				logger.error("[LOAD_CONFIG_FAILED]",e);
			}
		}
		public static String CODIS_ZK_CONNECTSTR;
		public static String CODIS_PROXY_NAMESPACE;
		
	}

	public static class ZKConfig{
		static{
			Properties zkPro=new Properties();
			try {
				zkPro.load(ConfigLoader.class.getClassLoader().getResourceAsStream(ZK_CONFIG_FILE));
				for(String property:zkPro.stringPropertyNames()){
					ZKConfig.class.getField(property).set(null, zkPro.getProperty(property));
				}
				checkNull(CodisConfig.class);
			} catch (Exception e) {
				logger.error("[LOAD_CONFIG_FAILED]",e);
			}
		}
		public static String ZK_CONNECTSTR;
		public static String NAMESPACE;

	}
	

	
	private static void checkNull(Class<?> clazz){
		for(Field field:clazz.getDeclaredFields()){
			try {
				Object o=field.get(null);
				if(o==null){
					logger.warn("[NULL_PROPERTIES] property#"+field.getName());
					throw new RuntimeException();
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				logger.warn("[INIT_PROPERTIES_ERROR]",e);
			}
		}
	}
	
}
