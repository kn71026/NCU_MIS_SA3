package ncu.im3069.demo.app;

import java.sql.*;
import java.time.LocalDateTime;
import org.json.*;

import ncu.im3069.demo.util.DBMgr;
import login;

public class LoginHelper {
    private LoginHelper() {

    }

    /** 靜態變數，儲存LoginHelper物件 */
    private static LoginHelper lh;

    /** 儲存JDBC資料庫連線 */
    private Connection conn = null;

    /** 儲存JDBC預準備之SQL指令 */
    private PreparedStatement pres = null;

    /**
     * 靜態方法<br>
     * 實作Singleton（單例模式），僅允許建立一個LoginHelper物件
     *
     * @return the helper 回傳LoginHelper物件
     */
    public static LoginHelper getHelper() {
        /** Singleton檢查是否已經有LoginHelper物件，若無則new一個，若有則直接回傳 */
        if (lh == null)
            lh = new LoginHelper();

        return lh;
    }
    
    /**
     * 檢查該名人事之帳號是否重複
     *
     * @param l 一名人事之Login物件
     * @return boolean 若帳號重複回傳True，若該帳號不存在則回傳False
     */
    public boolean checkDuplicate(Login l) {
        /** 紀錄SQL總行數，若為「-1」代表資料庫檢索尚未完成 */
        int row = -1;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            if(login.html.option == "doctor"){
            /** SQL指令 */
            String sql = "SELECT count(*) FROM `sa_project`.`doctor` WHERE `account`.`password` = ?";
            }
            else if(是行政){
            String sql = "SELECT count(*) FROM `sa_project`.`administration` WHERE `account`.`password` = ?";    
            }
            else(是藥師){
            String sql = "SELECT count(*) FROM `sa_project`.`pharmacist` WHERE `account`.`password` = ?";    
            }
            /** 取得所需之參數 */
            String account = l.getAccount();
            String password = l.getPassword();

            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, account);
            pres.setString(2,password);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 讓指標移往最後一列，取得目前有幾行在資料庫內 */
            rs.next();
            row = rs.getInt("count(*)");
            System.out.print(row);

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }

        /**
         * 判斷是否已經有一筆該帳號之資料 若無一筆則回傳False，有則回傳True
         */
        return (row == 0) ? false : true;
    }
}
