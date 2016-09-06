package online.decentworld.message.cache;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Sammax on 2016/9/5.
 */
public final class CacheConfig {

    public static final String CODIS_ZK_CONNECTSTR;
    public static final String CODIS_PROXY_NAMESPACE;



    static {
        Properties p=new Properties();
        try {
            p.load(CacheConfig.class.getClassLoader().getResourceAsStream("codis_config.properties"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        CODIS_PROXY_NAMESPACE=p.getProperty("CODIS_PROXY_NAMESPACE");
        CODIS_ZK_CONNECTSTR=p.getProperty("CODIS_ZK_CONNECTSTR");
    }

    public static void main(String[] args) {
        System.out.println(CacheConfig.CODIS_PROXY_NAMESPACE);
    }
}
