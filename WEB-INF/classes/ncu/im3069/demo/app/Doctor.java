package ncu.im3069.demo.app;

import org.json.*;

import java.sql.Timestamp;
import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class Doctor
 * Doctor類別（class）具有會員所需要之屬性與方法，並且儲存與會員相關之商業判斷邏輯<br>
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class Doctor {
    
    /** id，會員編號 */
    private int id;
    
    /** account，會員帳號 */
    private String account;
    
    /** name，會員姓名 */
    private String name;
    
    /** password，會員密碼 */
    private String password;
    
    /**dob，會員生日 */
    private String dob;

    /** phone，會員電話 */
    private int phone;
    
    /** address，會員地址 */
    private String address;

    /**create_date，生成日期 */
    private Timestamp create_date;

    /**modify_date，修改日期 */
    private Timestamp modify_date;
    
    /** dh，DoctorHelper之物件與Doctor相關之資料庫方法（Sigleton） */
    private DoctorHelper dh =  DoctorHelper.getHelper();
    
    /**
     * 實例化（Instantiates）一個新的（new）Doctor物件<br>
     * 採用多載（overload）方法進行，此建構子用於建立醫師資料時，產生一名新的醫師
     *
     * @param account 醫師帳號
     * @param password 醫師密碼
     * @param name 醫師姓名
     * @param dob 醫師生日
     * @param phone 醫師電話
     * @param address 醫師地址
     */
    public Doctor(String account,String password,String name,String dob,int phone
    ,String address){
        this.account = account;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.address = address;
        update();
    }

    /**
     * 實例化（Instantiates）一個新的（new）Doctor物件<br>
     * 採用多載（overload）方法進行，此建構子用於更新醫師資料時，產生一名醫師同時需要去資料庫檢索原有修改日期
     * 
     * @param id 醫師編號
     * @param account 醫師帳號
     * @param password 醫師密碼
     * @param name 醫師姓名
     * @param dob 醫師生日
     * @param phone 醫師電話
     * @param address 醫師地址
     * @param create_date 生成日期
     * @param modify_date 修改日期
     */
    public Doctor(int id,String account,String password,String name,String dob,int phone
    ,String address,Timestamp create_date,Timestamp modify_date) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.address = address;
        this.create_date = create_date;
        this.modify_date = modify_date;

        /** 取回原有資料庫內該名醫師之更新日期 */
        /**getmodify_date();*/
    }
    
    /**
     * 實例化（Instantiates）一個新的（new）Doctor物件<br>
     * 採用多載（overload）方法進行，此建構子用於查詢醫師資料時，將每一筆資料新增為一個醫師物件
     *
     * @param id 醫師編號
     * @param account 醫師帳號
     * @param password 醫師密碼
     * @param name 醫師姓名
     * @param dob 醫師生日
     * @param phone 醫師電話
     * @param address 醫師地址
     */
    public Doctor(int id,String account,String password,String name,String dob,int phone
    ,String address)
    {
        this.id = id;
        this.account = account;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.address = address;
        update();
    }
    
    /**
     * 取得醫師之編號
     *
     * @return the id 回傳醫師編號
     */
    public int getID() {
        return this.id;
    }

    /**
     * 取得會員之地址
     *
     * @return the account 回傳會員地址
     */
    public String getAccount() {
        return this.account;
    }
    
    /**
     * 取得醫師之姓名
     *
     * @return the name 回傳醫師姓名
     */
    public String getName() {
        return this.name;
    }

    /**
     * 取得醫師之密碼
     *
     * @return the password 回傳醫師密碼
     */
    public String getPassword() {
        return this.password;
    }
    
    /**
     * 取得更新資料時間之分鐘數
     *
     * @return the modify times 回傳更新資料時間之分鐘數
     */
    public int getModify_date() {
        return this.modify_date;
    }
    
    /**
     * 更新會員資料
     *
     * @return the JSON object 回傳SQL更新之結果與相關封裝之資料
     */
    public JSONObject update() {
        /** 新建一個JSONObject用以儲存更新後之資料 */
        JSONObject data = new JSONObject();
        /** 取得更新資料時間（即現在之時間）之分鐘數 */
        /**Calendar calendar = Calendar.getInstance();
        this.login_times = calendar.get(Calendar.MINUTE);*/
        /** 計算帳戶所屬之組別 */
        /**calcAccName();*/
        
        /** 檢查該名會員是否已經在資料庫 */
        if(this.id != 0) {
            /** 若有則將目前更新後之資料更新至資料庫中 */
            dh.updateLoginTimes(this);
            /** 透過MemberHelper物件，更新目前之會員資料置資料庫中 */
            data = dh.update(this);
        }
        
        return data;
    }
    
    /**
     * 取得該名會員所有資料
     *
     * @return the data 取得該名會員之所有資料並封裝於JSONObject物件內
     */
    public JSONObject getData() {
        /** 透過JSONObject將該名會員所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("id", getID());
        jso.put("name", getName());
        jso.put("account", getAccount());
        jso.put("password", getPassword());
        jso.put("dob", getDob());
        jso.put("phone", getPhone());
        jso.put("address", getAddress());
        jso.put("create_date", getCreate_date());
        jso.put("modify_date", getModify_date());
        
        return jso;
    }
    
    /**
     * 取得資料庫內之更新資料時間分鐘數與會員組別
     *
     */
    /**private void getLoginTimesStatus()*/ 
     /{
        /** 透過MemberHelper物件，取得儲存於資料庫的更新時間分鐘數與會員組別 */
        JSONObject data = mh.getLoginTimesStatus(this);
        /** 將資料庫所儲存該名會員之相關資料指派至Member物件之屬性 */
        this.login_times = data.getInt("login_times");
        this.status = data.getString("status");
    }    
    
    /**
     * 計算會員之組別<br>
     * 若為偶數則為「偶數會員」，若為奇數則為「奇數會員」
     */
    private void calcAccName() 
    {
        /** 計算目前分鐘數為偶數或奇數 */
        String curr_status = (this.login_times % 2 == 0) ? "偶數會員" : "奇數會員";
        /** 將新的會員組別指派至Member之屬性 */
        this.status = curr_status;
        /** 檢查該名會員是否已經在資料庫，若有則透過MemberHelper物件，更新目前之組別狀態 */
        if(this.id != 0) mh.updateStatus(this, curr_status);
    }
}