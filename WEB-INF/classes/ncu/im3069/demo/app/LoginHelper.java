package ncu.im3069.demo.app;

import java.sql.*;
import java.time.LocalDateTime;
import org.json.*;

import ncu.im3069.demo.util.DBMgr;

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
     * 檢查該名醫師之帳號是否重複註冊
     *
     * @param d 一名醫師之Doctor物件
     * @return boolean 若重複註冊回傳True，若該帳號不存在則回傳False
     */
    public boolean checkDuplicate(Login l) {
        /** 紀錄SQL總行數，若為「-1」代表資料庫檢索尚未完成 */
        int row = -1;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT count(*) FROM `sa_project`. ? WHERE `account` = ?";

            /** 取得所需之參數 */
            String job = l.getJob();
            String account = l.getAccount();

            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1,job);
            pres.setString(2, account);
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
         * 判斷是否已經有一筆該帳號之資料 若無一筆則回傳False，否則回傳True
         */
        return (row == 0) ? false : true;
    }

    public boolean checkLogin(String job, String account, String password) {
        /** 紀錄SQL總行數，若為「-1」代表資料庫檢索尚未完成 */
        int row = -1;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT count(*) FROM `sa_project`.? WHERE `account` = ? AND `password` = ?";
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, job);
            pres.setString(2, account);
            pres.setString(3, password);
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
         * 判斷是否已經有一筆該帳號之資料 若無一筆則回傳False，否則回傳True
         */
        return (row == 0) ? false : true;
    }
    public int getIDByAccount(String account) {
        /** 新建一個 Doctor 物件之 d 變數，用於紀錄每一位查詢回之醫師資料 */
        Login l = null;
        /** 用於儲存所有檢索回之醫師，以JSONArray方式儲存 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        int hmr_id = 0;
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            String job = l.getJob();
            /** SQL指令 */
            String sql = "SELECT * FROM `sa_project`.? WHERE `account` = ? LIMIT 1";

            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1,job);
            pres.setString(2, account);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            /** 正確來說資料庫只會有一筆該醫師編號之資料，因此其實可以不用使用 while 迴圈 */
            while (rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;

                /** 將 ResultSet 之資料取出 */
                hmr_id = rs.getInt("id");
                
                
                 /** Timestamp create_date = rs.getTimestamp("create_date"); Timestamp modify_date
                 * = rs.getTimestamp("modify_date");
                 */

            }

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


        return hmr_id;
    }
}