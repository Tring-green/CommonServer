import java.io.IOException;

/**
 * Created by admin on 2016/5/28.
 */
public class TestException {
    public static void main(String[] args) {
        try {
            throw new IOException();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(123);
    }
}
