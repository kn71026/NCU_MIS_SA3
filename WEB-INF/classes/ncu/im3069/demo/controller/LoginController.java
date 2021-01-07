package ncu.im3069.demo.controller;

import java.io.*;
/**import java.sql.Timestamp;
*/

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.Cookie;

import org.json.*;


import ncu.im3069.demo.app.Login;
import ncu.im3069.demo.app.LoginHelper;
import ncu.im3069.tools.JsonReader;

import javax.servlet.annotation.WebServlet;

@WebServlet("/api/login.do")
public class LoginController extends HttpServlet {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** lh，LoginHelper之物件與Login相關之資料庫方法（Sigleton） */
    private LoginHelper lh = LoginHelper.getHelper();

    /**
     * 處理Http Method請求POST方法（新增資料）
     *
     * @param request  Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException      Signals that an I/O exception has occurred.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();

        /** 取出經解析到JSONObject之Request參數 */
        String job = jso.getString("job");
        String account = jso.getString("account");
        String password = jso.getString("password");

        /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
        if (account.isEmpty() || password.isEmpty() ) {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }
        /** 透過DoctorHelper物件的checkDuplicate()檢查該醫師帳號是否有重複 */
        else if (lh.checkLogin(job, account, password)) {

            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            int hmr_id = 0;
            JSONObject resp = new JSONObject();
            hmr_id = lh.getIDByAccount(account);
            resp.put("status", "200");
            resp.put("message", "成功登入！id=" + hmr_id);

            Cookie ck = new Cookie("doctor_id",String.valueOf(hmr_id));
            response.addCookie(ck);

            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        } else {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'403\', \"message\": \'帳號或密碼不符！\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }
    }
}
