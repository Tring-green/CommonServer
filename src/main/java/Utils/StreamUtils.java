package Utils;

import java.io.*;

/**
 * Created by admin on 2016/5/24.
 */
public class StreamUtils {
    public static BufferedReader createBufferedReader(Object obj, Object cd) throws IOException {
        if (obj instanceof InputStream) {
            if (cd == null)
                return new BufferedReader(new InputStreamReader((InputStream) obj));
            else
                return new BufferedReader(new InputStreamReader((InputStream) obj, (String) cd));
        }
        if (obj instanceof File)
            return new BufferedReader(createFileReader((File) obj));
        if (obj instanceof Reader)
            return new BufferedReader((Reader) obj);
        return null;
    }

    public static FileReader createFileReader(File file) throws FileNotFoundException {
        return new FileReader(file);
    }
}
