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


	final private static String ZK_CONFIG_FILE="zk.properties";


	public static class ZKConfig{
		static{
			Properties zkPro=new Properties();
			try {
				zkPro.load(ConfigLoader.class.getClassLoader().getResourceAsStream(ZK_CONFIG_FILE));
				for(String property:zkPro.stringPropertyNames()){
					ZKConfig.class.getField(property).set(null, zkPro.getProperty(property));
				}
				checkNull(ZKConfig.class);
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
