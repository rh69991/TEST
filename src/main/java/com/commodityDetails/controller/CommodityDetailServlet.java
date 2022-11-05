package commodityDetails.controller;

import orderBuy.service.impl.OrderBuyService;
import orderBuy.vo.OrderBuyVO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


@WebServlet(name = "CommodityDetailServlet", value = "/backend/commodityDetails/commodityDetails.do")
public class CommodityDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

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
        res.setHeader("content-type", "text/html;charset=utf-8");

        String action = req.getParameter("action");

        if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

            List<String> errorMsgs = new LinkedList<String>();
            // Store this set in the request scope, in case we need to
            // send the ErrorPage view.
            req.setAttribute("errorMsgs", errorMsgs);

            /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
            String str = req.getParameter("orderId");
            if (str == null || (str.trim()).length() == 0) {
                errorMsgs.add("請選擇欲查詢之訂單編號");
            }
            // Send the use back to the form, if there were errors
            if (!errorMsgs.isEmpty()) {
                RequestDispatcher failureView = req.getRequestDispatcher("/commodityDetails/selectCommodityDetails.jsp");
                failureView.forward(req, res);
                return;// 程式中斷
            }

            Integer orderId = null;
            try {
                orderId = Integer.valueOf(req.getParameter("orderId").trim());
            } catch (NumberFormatException e) {
                orderId = 0;
                errorMsgs.add("請選擇欲查詢之訂單編號");
            }

            if (!errorMsgs.isEmpty()) {
                RequestDispatcher failureView = req.getRequestDispatcher("/commodityDetails/selectCommodityDetails.jsp");
                failureView.forward(req, res);
                return;// 程式中斷
            }
            /*************************** 2.開始查詢並修改資料 ****************************************/
            OrderBuyService orderBuyService = new OrderBuyService();
            OrderBuyVO orderBuyVO = orderBuyService.getOneOrder(orderId);

            /*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
            req.setAttribute("orderBuyVO", orderBuyVO);
            req.setAttribute("orderId", orderId); // 資料庫取出的empVO物件,存入req
            String url = "/backend/commodityDetails/listCommodityDetails.jsp";
            RequestDispatcher successView = req.getRequestDispatcher(url);
            successView.forward(req, res);
        }

        if ("update_Order".equals(action)) { // 來自select_page.jsp的請求

            List<String> errorMsgs = new LinkedList<String>();
            // Store this set in the request scope, in case we need to
            // send the ErrorPage view.
            req.setAttribute("errorMsgs", errorMsgs);

            /*************************** 1.接收請求參數 ****************************************/
            Integer orderId = Integer.valueOf(req.getParameter("orderId"));
            Integer memId = Integer.valueOf(req.getParameter("memId"));
            Double originalPrice = Double.valueOf(req.getParameter("originalPrice"));
            Double discountPrice = Double.valueOf(req.getParameter("discountPrice"));
            Double finalPrice = Double.valueOf(req.getParameter("finalPrice"));
            java.sql.Date orderDate = java.sql.Date.valueOf(req.getParameter("orderDate"));
            Integer orderPaying = Integer.valueOf(req.getParameter("orderPaying"));
            Integer orderSend = Integer.valueOf(req.getParameter("orderSend"));
            Integer orderStatus = Integer.valueOf(req.getParameter("orderStatus"));
            String orderOther = req.getParameter("orderOther");
            String trackingNum = req.getParameter("trackingNum");

            if (trackingNum == null || trackingNum.trim().length() == 0){
                errorMsgs.add("物流編號: 為必要項目");
            }
            String receiverName = req.getParameter("receiverName");
            String receiverNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
            if (receiverName == null || receiverName.trim().length() == 0) {
                errorMsgs.add("收件人姓名: 請勿空白");
            } else if (!receiverName.trim().matches(receiverNameReg)) { // 以下練習正則(規)表示式(regular-expression)
                errorMsgs.add("收件人姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
            }
            String receiverAddress = req.getParameter("receiverAddress");
            String receiverPhone = req.getParameter("receiverPhone");
            String phoneRegex="0\\d{2,3}[-]?\\d{7,8}|0\\d{2,3}\\s?\\d{7,8}|13[0-9]\\d{8}|15[1089]\\d{8}";
            if (receiverPhone == null || receiverPhone.trim().length() == 0) {
                errorMsgs.add("連絡電話: 請勿空白");
            } else if (!receiverPhone.trim().matches(phoneRegex)) { // 以下練習正則(規)表示式(regular-expression)
                errorMsgs.add("連絡電話: 電話格式錯誤");
            }
            /*************************** 2.開始查詢並修改資料 ****************************************/
            OrderBuyService orderBuyService = new OrderBuyService();
            OrderBuyVO orderBuyVO = orderBuyService.getOneOrder(orderId);

            orderBuyVO.setMemId(memId);
            orderBuyVO.setOriginalPrice(originalPrice);
            orderBuyVO.setDiscountPrice(discountPrice);
            orderBuyVO.setFinalPrice(finalPrice);
            orderBuyVO.setOrderDate(orderDate);
            orderBuyVO.setOrderPaying(orderPaying);
            orderBuyVO.setOrderSend(orderSend);
            orderBuyVO.setOrderStatus(orderStatus);
            orderBuyVO.setOrderOther(orderOther);
            orderBuyVO.setTrackingNum(trackingNum);
            orderBuyVO.setReceiverName(receiverName);
            orderBuyVO.setReceiverAddress(receiverAddress);
            orderBuyVO.setReceiverPhone(receiverPhone);
            orderBuyVO.setOrderId(orderId);

            if (!errorMsgs.isEmpty()) {
                req.setAttribute("orderId", orderId);
                req.setAttribute("orderBuyVO", orderBuyVO);
//                res.getWriter().print("<script> function modalShow() { $('#myModal').click(); } </script>");
                RequestDispatcher failureView = req.getRequestDispatcher("/backend/commodityDetails/listCommodityDetails.jsp");
                failureView.forward(req, res);
                return; // 程式中斷
                }

             orderBuyVO = orderBuyService.updateOrder(originalPrice, discountPrice, finalPrice, orderDate, orderPaying, orderSend, orderStatus, trackingNum, receiverName, receiverAddress, receiverPhone, orderId);

            /*************************** 3.修改完成,準備轉交(Send the Success view) *************/
            req.setAttribute("orderBuyVO", orderBuyVO); // 資料庫取出的empVO物件,存入req
            String url = "/backend/commodityDetails/selectCommodityDetails.jsp";
            RequestDispatcher successView = req.getRequestDispatcher(url);
            successView.forward(req, res);
        }

    }
}
