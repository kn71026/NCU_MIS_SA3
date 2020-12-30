package ncu.im3069.demo.app;

import java.sql.*;

import java.time.LocalDateTime;

import org.json.*;

public class Medicine {

    /** id，藥品編號 */
    private int id;

    private String name;

    private String quantity;

    private String category;

    private Timestamp modify_date;

    private MedicineHelper mdh = MedicineHelper.getHelper();

    /**
     * 實例化（Instantiates）一個新的（new）Product 物件<br>
     * 採用多載（overload）方法進行，此建構子用於新增產品時
     *
     * @param id 產品編號
     */
    public Medicine(int id) {
        this.id = id;
    }

    /**
     * 實例化（Instantiates）一個新的（new）Product 物件<br>
     * 採用多載（overload）方法進行，此建構子用於新增產品時
     */
    public Medicine(String name, String quantity, String category) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.modify_date = Timestamp.valueOf(LocalDateTime.now());
    }

    /**
     * 實例化（Instantiates）一個新的（new）Product 物件<br>
     * 採用多載（overload）方法進行，此建構子用於修改產品時
     *
     */
    public Medicine(int id, String name, String quantity, String category) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.modify_date = Timestamp.valueOf(LocalDateTime.now());

    }

    /**
     * 實例化（Instantiates）一個新的（new）Product 物件<br>
     * 採用多載（overload）方法進行，此建構子用於查詢產品時
     *
     */
    public Medicine(int id, String name, String quantity, String category, Timestamp modify_date) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.modify_date = modify_date;

    }

    /**
     * 取得產品編號
     *
     * @return int 回傳產品編號
     */
    public int getID() {
        return this.id;
    }

    /**
     * 取得產品名稱
     *
     * @return String 回傳產品名稱
     */
    public String getName() {
        return this.name;
    }

    /**
     * 取得產品價格
     *
     * @return double 回傳產品價格
     */
    public String getQuantity() {
        return this.quantity;
    }

    /**
     * 取得產品圖片
     *
     * @return String 回傳產品圖片
     */
    public String getCategory() {
        return this.category;
    }

    public Timestamp getModifyTime() {
        return this.modify_date;
    }

    /**
     * 更新病患資料
     *
     * @return the JSON object 回傳SQL更新之結果與相關封裝之資料
     */
    public JSONObject update() {
        /** 新建一個JSONObject用以儲存更新後之資料 */
        JSONObject data = new JSONObject();

        /** 檢查該名病患是否已經在資料庫 */
        if (this.id != 0) {
            /** 透過MemberHelper物件，更新目前之病患資料置資料庫中 */
            data = mdh.update(this);
        }

        return data;
    }

    /**
     * 取得產品資訊
     *
     * @return JSONObject 回傳產品資訊
     */
    public JSONObject getData() {
        /** 透過JSONObject將該項產品所需之資料全部進行封裝 */
        JSONObject jso = new JSONObject();
        jso.put("id", getID());
        jso.put("name", getName());
        jso.put("quantity", getQuantity());
        jso.put("category", getCategory());
        jso.put("modify_date", getModifyTime());

        return jso;
    }

}
