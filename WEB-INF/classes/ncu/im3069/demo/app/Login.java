package ncu.im3069.demo.app;

import org.json.*;

import java.sql.Timestamp;

public class Login {
    /** account 人事帳號 */
    private String account;

    /** password 人事密碼 */
    private String password;

    private String job;

     /** lh，LoginHelper之物件與Login相關之資料庫方法（Sigleton） */
     private LoginHelper lh = LoginHelper.getHelper();
    

    /**
     * 實例化（Instantiates）一個新的（new）Login物件<br>
     * 採用多載（overload）方法進行，此建構子用於建立人事資料時，確認是否有此人
     *
     * @param account  人事帳號
     * @param password 人事密碼
     */
    public Login(String job, String account, String password) {
        this.job = job;
        this.account = account;
        this.password = password;
    }

    /**
     * 取得人事之帳號
     *
     * @return the account 回傳人事帳號
     */
    public String getAccount() {
        return this.account;
    }

    /**
     * 取得人事之密碼
     *
     * @return the password 回傳人事密碼
     */
    public String getPassword() {
        return this.password;
    }

    public String getJob(){
        return this.job;
    }

    /**
     * 取得該 人事所有資料
     *
     * @return the data 取得該人事之所有資料並封裝於JSONObject物件內
     */
    public JSONObject getData() {
        /** 透過JSONObject將該 醫師所需之資料全部進行封裝 */
        JSONObject jso = new JSONObject();
        jso.put("job", getJob());
        jso.put("account", getAccount());
        jso.put("password", getPassword());
        return jso;
    }
}
