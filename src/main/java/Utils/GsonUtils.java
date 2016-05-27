package Utils;

import com.google.gson.Gson;

/**
 * Created by admin on 2016/5/26.
 */
public class GsonUtils {
    public static Gson sGson ;

    public static Object fromJson(String json, Class<? extends Object> classOfT) {
        return sGson.fromJson(json, classOfT);
    }

    private static GsonUtils instance;

    private GsonUtils() {
        sGson = new Gson();
    }

    public static GsonUtils getInstance() {
        if (instance == null) {
            synchronized (GsonUtils.class) {
                if (instance == null) {
                    instance = new GsonUtils();
                }
            }
        }
        return instance;
    }
}
