package Domain;

/**
 * Created by admin on 2016/5/26.
 */
public class HttpRegister {

    private boolean flag;
    private datas data;

    public datas getData() {
        return data;
    }

    public void setData(datas data) {
        this.data = data;
    }

    public static class datas {
        private String userId;
        private String passwd;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPasswd() {
            return passwd;
        }

        public void setPasswd(String passwd) {
            this.passwd = passwd;
        }

        public datas(String userId, String passwd) {
            this.userId = userId;
            this.passwd = passwd;
        }
    }



    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}
