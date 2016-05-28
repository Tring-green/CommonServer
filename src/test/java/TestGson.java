import Domain.HTTPError;
import Domain.Account;
import com.google.gson.Gson;

/**
 * Created by admin on 2016/5/25.
 */
public class TestGson {
    public static void main(String[] args) {
        Gson gson = new Gson();
        HTTPError error = new HTTPError(150, "The userId has already existed.Register error happens.");
        String json = gson.toJson(error);
        System.out.println(json);

        HTTPError bean = gson.fromJson(json, HTTPError.class);
        System.out.println(bean.getErrorCode());
        System.out.println(bean.getErrorMessage());
        System.out.println(bean.isFlag());

        Account register = new Account();
        register.setFlag(true);
        // Account.Data data = new Account.Data("12345", "123");
        // register.setData(data);
        json = gson.toJson(register);
        System.out.println(json);
        System.out.println(register.isFlag());
        System.out.println(register.getData().toString());
    }
}
