package oriseus.Sagitarius_equipment.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class ConfigHundler {
    
    private static File path = new File(System.getProperty("user.dir"));		
    private static File folder = new File(path.getParentFile() + File.separator +  "properties");

    private static final String FILE = folder + File.separator + "sagitariusEqupment.properties";
    private static Properties props = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream(FILE)) {
            props.load(fis);
        } catch (Exception e) {

        }
    }

    public static String get(String key, String value) {
        return props.getProperty(key, value);
    }

    public static void set(String key, String value) throws Exception {
        props.setProperty(key, value);
        try (FileOutputStream fos = new FileOutputStream(FILE)) {
            props.store(fos, "Config");
        }
    }
}
