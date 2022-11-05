package orderBuy.controller;

import com.item.model.ItemService;
import com.item.model.ItemVO;
import com.memberCoupon.service.impl.MemberCouponService;
import commodityDetails.service.impl.CommodityDetailsService;
import commodityDetails.vo.CommodityDetailsVO;
import coupon.service.impl.CouponService;
import coupon.vo.CouponVO;
import memberCoupon.vo.MemberCouponVO;
import orderBuy.service.impl.OrderBuyService;
import orderBuy.vo.OrderBuyVO;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.out;

@WebServlet(name = "OrderBuyServlet", value = "/OrderBuyServlet")
public class OrderBuyServlet extends HttpServlet {
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

        String action = req.getParameter("action");

        if ("new_Order".equals(action)) {

            Integer memberId = null;
            memberId = Integer.valueOf(req.getParameter("memberId"));

            Integer couponId = null;
            couponId = Integer.valueOf(req.getParameter("couponId"));


            Integer orderPaying = null;
            orderPaying = Integer.valueOf(req.getParameter("orderPaying"));

            Integer orderSend = null;
            orderSend = Integer.valueOf(req.getParameter("orderSend"));

            String orderOther = req.getParameter("orderOther");

            String receiverName = req.getParameter("receiverName");

            String receiverAddress = req.getParameter("receiverAddress");

            String receiverPhone = req.getParameter("receiverPhone");

            JSONArray jsonArr;
            String dataArr = req.getParameter("dataArr");

            Map<Integer, Integer> items = new HashMap<Integer, Integer>();
            Map<Integer, Integer> orderItems = new HashMap<Integer, Integer>();
            Map<Integer, Integer> itemPrices = new HashMap<Integer, Integer>();
            Map<Integer, String> itemNames = new HashMap<Integer, String>();

            ItemService itemSvc = new ItemService();
//            ItemVO itemVO;

            Double originalPrice = 0.0;
            Integer discountPrice;
            Double finalPrice = null;

            CouponService couponSvc = new CouponService();
            CouponVO couponVO = couponSvc.getOneCoupon(couponId);
            discountPrice = couponVO.getCouponVal();

            try {
                jsonArr = new JSONArray(dataArr);
                for (int i = 0; i < jsonArr.length(); i++) {

                    JSONObject tmp = (JSONObject) jsonArr.get(i);

                    Integer itemId = tmp.getInt("itemId");

                    Integer cdAmount = tmp.getInt("cdAmount");
                    ItemVO itemVO = itemSvc.getItem(itemId);

                    Integer itemPrice = itemVO.getItemPrice();
                    String itemName = itemVO.getItemName();

                    items.put(itemId, itemId);
                    orderItems.put(itemId, cdAmount);
                    itemPrices.put(itemId, itemPrice);
                    itemNames.put(itemId, itemName);

                    /**
                     * 計算訂單金額
                     * @param itemId: 商品編號
                     * @param itemPrice: 商品價格
                     * @param cdAmount: 商品數量
                     * @param originalPrice: 原始總價
                     * @param discountPrice: 折扣價格
                     * @param finalPrice: 最終價格
                     */
                    originalPrice += itemPrice * cdAmount;
                }

            } catch (Exception e) {
                out.println("ERROR: " + e.getMessage());
            }
            try {
                finalPrice = originalPrice - discountPrice;
            } catch (Exception e) {
                out.println("ERROR: " + e.getMessage());
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化時間
            String time = sdf.format(new java.util.Date());
            java.util.Date timeDate = null; // 取得現在時間
            try {
               timeDate = sdf.parse(time);
            } catch (ParseException e) {
                out.println("ERROR: " + e.getMessage());
                e.printStackTrace();
            }
            assert timeDate != null;
            java.sql.Date currentTime = new java.sql.Date(timeDate.getTime());


            OrderBuyVO orderBuyVO = new OrderBuyVO();
            orderBuyVO.setMemId(memberId);
            orderBuyVO.setOriginalPrice(originalPrice);
            orderBuyVO.setDiscountPrice(Double.valueOf(discountPrice));
            orderBuyVO.setFinalPrice(finalPrice);
            orderBuyVO.setOrderDate(currentTime);
            orderBuyVO.setOrderPaying(orderPaying);
            orderBuyVO.setOrderSend(orderSend);
            orderBuyVO.setOrderStatus(0);
            orderBuyVO.setOrderOther(orderOther);
            orderBuyVO.setTrackingNum("BAREI-000000000");
            orderBuyVO.setReceiverName(receiverName);
            orderBuyVO.setReceiverAddress(receiverAddress);
            orderBuyVO.setReceiverPhone(receiverPhone);

            // 新增商品訂單
            OrderBuyService orderBuySvc = new OrderBuyService();
            orderBuyVO = orderBuySvc.newOrder(memberId, originalPrice, Double.valueOf(discountPrice), finalPrice, java.sql.Date.valueOf(String.valueOf(currentTime)), orderPaying, orderSend, 0, orderOther, "BAREI-000000000", receiverName, receiverAddress, receiverPhone);
            Integer orderId = orderBuyVO.getOrderId();

            MemberCouponService memberCouponSvc = new MemberCouponService();
            MemberCouponVO memberCouponVO = memberCouponSvc.getOwnCoupon(memberId, couponId);
            // 將優惠券切換為 1: 已使用
            memberCouponVO.setMcpnUse(1);
            memberCouponVO = memberCouponSvc.updateUse(1, couponId);

            Integer itemId;
            String itemName;
            Integer itemPrice;
            Integer cdAmount;

            CommodityDetailsVO commodityDetailsVO = new CommodityDetailsVO();

            for (Integer i : itemNames.keySet()) {

                itemId = items.get(i);
                itemName = itemNames.get(i);
                itemPrice = itemPrices.get(i);
                cdAmount = orderItems.get(i);

                commodityDetailsVO.setOrderId(orderId);
                commodityDetailsVO.setItemId(itemId);
                commodityDetailsVO.setItemName(itemName);
                commodityDetailsVO.setCdAmount(cdAmount);
                commodityDetailsVO.setItemPrice(Double.valueOf(itemPrice));

                CommodityDetailsService commodityDetailsSvc = new CommodityDetailsService();
                commodityDetailsVO = commodityDetailsSvc.newOrderDetail(orderId, itemId, itemName, cdAmount, Double.valueOf(itemPrice));

            }

        }
    }
}
