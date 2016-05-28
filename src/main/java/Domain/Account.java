package Domain;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by admin on 2016/5/26.
 */
public class Account {

    public Account(boolean flag, Data data) {
        this.flag = flag;
        this.data = data;
    }

    public Account() throws UnsupportedEncodingException {
        data = new Data();
    }

    private boolean flag;
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        public Data() {
        }

        private String userId;// 账号
        private String name;// 用户名
        private String icon;// 用户图像
        private String passwd;//用户密码

        public String getPasswd() {
            return passwd;
        }

        public void setPasswd(String passwd) {
            this.passwd = passwd;
        }

        private int sex;// 性别 0:未设置 1:女 2:男 3:其他
        private String sign;// 用户个性签名
        private String area;// 用户所在区域
        private String token;// 用户与服务器交互的唯一标

        public Data(String userId, String passwd, String name, int sex, String icon, String sign, String area, String token) throws UnsupportedEncodingException {
            this.passwd = passwd;
            this.userId = userId;
            this.name = name;
            this.icon = icon;
            this.sex = sex;
            this.sign = sign;
            this.area = area;
            this.token = token;
            transencode();
        }

        public void transencode() throws UnsupportedEncodingException {
            name = URLEncoder.encode(name, "utf-8");
            sign = URLEncoder.encode(sign, "utf-8");
            area = URLEncoder.encode(area, "utf-8");
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "userId='" + userId + '\'' +
                    ", name='" + name + '\'' +
                    ", icon='" + icon + '\'' +
                    ", passwd='" + passwd + '\'' +
                    ", sex=" + sex +
                    ", sign='" + sign + '\'' +
                    ", area='" + area + '\'' +
                    ", token='" + token + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "flag=" + flag +
                ", data=" + data +
                '}';
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}
