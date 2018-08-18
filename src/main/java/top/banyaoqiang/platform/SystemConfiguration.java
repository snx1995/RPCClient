package top.banyaoqiang.platform;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

/**
 * Created by 班耀强 on 2018/8/18
 */
public final class SystemConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(SystemConfiguration.class);
    private static HashMap<String, Object> mapConfig = null;

    public static String SERVICE_ADDR = null;
    public static Integer SERVICE_PORT = null;

    static {
        File config = new File("config.json");
        if (config.exists()) {
            byte[] bytes = new byte[1024];

            FileInputStream fi;
            try {
                fi = new FileInputStream(config);
                int l = fi.read(bytes);
                String s = new String(bytes, 0, l);
                Gson gsonConfig = new Gson();

                mapConfig = gsonConfig.fromJson(s, HashMap.class);

                SERVICE_ADDR = getStringField("serviceAddr");
                SERVICE_PORT = getIntField("servicePort");

            } catch (Exception e) {
                logger.error("读取配置文件出错: {}", e.getMessage());
            }
        } else logger.error("config not found");
    }

    private static int getIntField(String key) {
        return ((Double) mapConfig.get(key)).intValue();
    }

    private static String getStringField(String key) {
        return (String) mapConfig.get(key);
    }
}
