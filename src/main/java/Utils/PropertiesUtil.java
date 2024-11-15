package Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private PropertiesUtil() {}
    private static Properties properties = new Properties();
    static {
        loadProperties();
    }

    private static void loadProperties() {
        try(InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")){
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}