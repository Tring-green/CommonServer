package Domain;

/**
 * Created by admin on 2016/5/26.
 */
public class Account {

    private boolean flag;
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private String userId;// 账号
        private String name;// 用户名
        private String icon;// 用户图像
        private int sex;// 性别 0:未设置 1:女 2:男 3:其他
        private String sign;// 用户个性签名
        private String area;// 用户所在区域
        private String token;// 用户与服务器交互的唯一标
        private boolean current;// 是否是当前用户

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

        public boolean isCurrent() {
            return current;
        }

        public void setCurrent(boolean current) {
            this.current = current;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

    }



    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}
