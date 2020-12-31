package ncu.im3069.demo.app;

import org.json.*;
import java.sql.*;
import java.time.LocalDateTime;

public class Appointment {

    /** id，會員編號 */
    private int id;

    /** name，會員姓名 */
    private String name;

    private String pid;

    /** password，會員密碼 */
    private String dob;

    /** login_times，更新時間的分鐘數 */
    private Timestamp visited_date;

    /** status，會員之組別 */
    private int appointment_number;

    private String clinic_hours;

    private boolean done;

    /** mh，MemberHelper之物件與Member相關之資料庫方法（Sigleton） */
    private AppointmentHelper ah = AppointmentHelper.getHelper();

    /**
     * 實例化（Instantiates）一個新的（new）Order 物件<br>
     * 採用多載（overload）方法進行，此建構子用於建立掛號資料時，產生一個新的掛號
     *
     * @param pid          病患身分證字號
     * @param name         病患姓名
     * @param dob          病患出生日期
     * @param visited_date 病患看診日期
     * @param clinic_hours 病患看診時段
     */

    public Appointment(String pid, String name, String dob, Timestamp visited_date, String clinic_hours) {
        this.pid = pid;
        this.name = name;
        this.dob = dob;
        this.visited_date = visited_date;
        this.clinic_hours = clinic_hours;
    }

    /**
     * 實例化（Instantiates）一個新的（new）Order 物件<br>
     * 採用多載（overload）方法進行，此建構子用於刪除訂單資料時，刪除資料庫已存在的掛號
     *
     * @param name               會員名
     * @param pid                會員姓
     * @param clinic_hours       會員電子信箱
     * @param appointment_number 會員地址
     */
    public Appointment(String pid, String name, String clinic_hours, int appointment_number, boolean done) {
        this.pid = pid;
        this.name = name;
        this.clinic_hours = clinic_hours;
        this.appointment_number = appointment_number;
        this.done = done;
    }

    /**
     * 取得掛號之編號
     *
     * @return the id 回傳掛號掛號編號
     */
    public int getId() {
        return this.id;
    }

    /**
     * 取得會員之編號
     *
     * @return the name 回傳會員編號
     */
    public String getName() {
        return this.name;
    }

    /**
     * 取得會員之編號
     *
     * @return the id 回傳會員編號
     */
    public String getPid() {
        return this.pid;
    }

    /**
     * 取得會員之編號
     *
     * @return the id 回傳會員編號
     */
    public Timestamp getVisitDate() {
        return this.visited_date;
    }

    /**
     * 取得會員之編號
     *
     * @return the id 回傳會員編號
     */
    public int getAppointmentNumber() {
        return this.appointment_number;
    }

    /**
     * 取得會員之編號
     *
     * @return the id 回傳會員編號
     */
    public void setAppointmentNumber(int appointment_number) {
        this.appointment_number = appointment_number;
    }

    /**
     * 取得會員之編號
     *
     * @return the id 回傳會員編號
     */
    public String getClinicHours() {
        return this.clinic_hours;
    }

    /**
     * 取得會員之編號
     *
     * @return the id 回傳會員編號
     */
    public String getDob() {
        return this.dob;
    }

    /**
     * 取得會員之編號
     *
     * @return the id 回傳會員編號
     */
    public boolean getDone() {
        return this.done;
    }

    /**
     * 取得會員之編號
     *
     * @return the id 回傳會員編號
     */
    public void setDone(boolean done) {
        this.done = done;
    }

    /**
     * 取得該名會員所有資料
     *
     * @return the data 取得該名會員之所有資料並封裝於JSONObject物件內
     */
    public JSONObject getData() {
        /** 透過JSONObject將該名會員所需之資料全部進行封裝 */
        JSONObject jso = new JSONObject();
        jso.put("id", getId());
        jso.put("name", getName());
        jso.put("pid", getPid());
        jso.put("visited_date", getVisitDate());
        jso.put("appointment_number", getAppointmentNumber());
        jso.put("clinic_hours", getClinicHours());
        jso.put("dob", getDob());
        jso.put("done", getDone());

        return jso;
    }

}
