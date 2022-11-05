package memberCoupon.controller;

import coupon.service.impl.CouponService;
import coupon.vo.CouponVO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.List;

import static common.Common.*;
import static common.Common.PASSWORD;
import static java.lang.System.out;

@WebServlet(name = "MemberCouponServlet", value = "/MemberCouponServlet")
public class MemberCouponServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {


        try {
            PrintWriter pw = res.getWriter();
            JSONObject jsonObj = new JSONObject();
            JSONArray array = new JSONArray();

            CouponService couponSvc = new CouponService();
            List<CouponVO> list = couponSvc.getAll();

            for (int i = 0; i < list.size(); i++ ) {
                
            }

            pw.println(array);
            pw.close();

        } catch (JSONException e) {
            out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setCharacterEncoding("UTF-8");

        res.setContentType("text/html;charset=utf-8");
        /* 允許跨域主機地址 */
        res.setHeader("Access-Control-Allow-Origin", "*");
        /* 允許跨域 GET, POST, HEAD 等 */
        res.setHeader("Access-Control-Allow-Methods", "*");
        /* 重新預檢驗跨域的緩存時間 (s) */
        res.setHeader("Access-Control-Max-Age", "3600");
        /* 允許跨域的請求頭 */
        res.setHeader("Access-Control-Allow-Headers", "*");
        /* 是否攜帶 cookie */
        res.setHeader("Access-Control-Allow-Credentials", "true");

        String action = req.getParameter("action");

        if ("list_Own_Coupon".equals(action)) {

            Integer memId = null;
            memId = Integer.valueOf(req.getParameter("memberId"));

            try {
                Class.forName(DRIVER);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try (
                    Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                    PreparedStatement pstmt = conn.prepareStatement("select C.COUPON_VAL, C.COUPON_NAR, C.USE_START, C.USE_OVER, M.MCPN_GETTIME, M.MCPN_USE, C.MINIMUM from MEMBER_COUPON as M inner join COUPON as C on M.COUPON_ID = C.COUPON_ID where MEM_ID = ?")) {

                PrintWriter pw = res.getWriter();
                JSONArray items = new JSONArray();

                pstmt.setInt(1, memId);
                ResultSet rs = pstmt.executeQuery();
                // 取得列數
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                // 遍瀝 ResultSet
                while (rs.next()) {
                    JSONObject jsonObj = new JSONObject();
                    for (int i = 1; i <= columnCount; i++) {
                        String value = null;
                        String columnName = metaData.getColumnLabel(i);// 列名稱
                        if (rs.getString(columnName) != null && !rs.getString(columnName).equals("")) {
                            value = new String(rs.getBytes(columnName), StandardCharsets.UTF_8);

                        } else {
                            value = "";
                        }
                        jsonObj.put(columnName, value);
                    }
                    items.put(jsonObj);
                }
                pw.println(items);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
