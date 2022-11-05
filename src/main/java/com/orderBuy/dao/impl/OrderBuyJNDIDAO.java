package orderBuy.dao.impl;

import orderBuy.dao.OrderBuyDAOInterface;
import orderBuy.vo.OrderBuyVO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderBuyJNDIDAO implements OrderBuyDAOInterface {

    private static final String INSERT_STMT =
            "insert into ORDER_BUY (MEM_ID ,ORIGINAL_PRICE, DISCOUNT_PRICE, FINAL_PRICE, ORDER_DATE, ORDER_PAYING, ORDER_SEND, ORDER_STATUS, ORDER_OTHER, TRACKING_NUM, RECEIVER_NAME, RECEIVER_ADDRESS, RECEIVER_PHONE) value (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE =
            "update ORDER_BUY set ORIGINAL_PRICE= ?, DISCOUNT_PRICE= ?, FINAL_PRICE= ?, ORDER_DATE= ?, ORDER_PAYING= ?, ORDER_SEND= ?, ORDER_STATUS= ?, ORDER_OTHER= ?, TRACKING_NUM= ?, RECEIVER_NAME= ?, RECEIVER_ADDRESS= ?, RECEIVER_PHONE= ?, PICKUP_TIME= ? where ORDER_ID= ?";
    private static final String DELETE =
            "delete from ORDER_BUY where ORDER_ID = ?";
    private static final String GET_ONE_STMT =
            "select ORDER_ID, MEM_ID ,ORIGINAL_PRICE, DISCOUNT_PRICE, FINAL_PRICE, ORDER_DATE, ORDER_PAYING, ORDER_SEND, ORDER_STATUS, ORDER_OTHER, TRACKING_NUM, RECEIVER_NAME, RECEIVER_ADDRESS, RECEIVER_PHONE, PICKUP_TIME from ORDER_BUY where ORDER_ID = ?";
    private static final String GET_ALL_STMT =
            "select ORDER_ID, MEM_ID ,ORIGINAL_PRICE, DISCOUNT_PRICE, FINAL_PRICE, ORDER_DATE, ORDER_PAYING, ORDER_SEND, ORDER_STATUS, ORDER_OTHER, TRACKING_NUM, RECEIVER_NAME, RECEIVER_ADDRESS, RECEIVER_PHONE, PICKUP_TIME from ORDER_BUY order by ORDER_ID";

    private static DataSource ds = null;

    static {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/hikariCP-BaRei");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(OrderBuyVO orderBuyVO) {

        ResultSet rs = null;

        Integer orderId = null;
        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_STMT, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, orderBuyVO.getMemId());
            pstmt.setDouble(2, orderBuyVO.getOriginalPrice());
            pstmt.setDouble(3, orderBuyVO.getDiscountPrice());
            pstmt.setDouble(4, orderBuyVO.getFinalPrice());
            pstmt.setDate(5, orderBuyVO.getOrderDate());
            pstmt.setInt(6, orderBuyVO.getOrderPaying());
            pstmt.setInt(7, orderBuyVO.getOrderSend());
            pstmt.setInt(8, orderBuyVO.getOrderStatus());
            pstmt.setString(9, orderBuyVO.getOrderOther());
            pstmt.setString(10, orderBuyVO.getTrackingNum());
            pstmt.setString(11, orderBuyVO.getReceiverName());
            pstmt.setString(12, orderBuyVO.getReceiverAddress());
            pstmt.setString(13, orderBuyVO.getReceiverPhone());

            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            rs.next();
            orderId = rs.getInt(1);
            orderBuyVO.setOrderId(orderId);

            // Handle any DRIVER errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
            // Clean up JDBC resources
        }
    }

    @Override
    public void update(OrderBuyVO orderBuyVO) {

        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE)) {

            pstmt.setDouble(1, orderBuyVO.getOriginalPrice());
            pstmt.setDouble(2, orderBuyVO.getDiscountPrice());
            pstmt.setDouble(3, orderBuyVO.getFinalPrice());
            pstmt.setDate(4, orderBuyVO.getOrderDate());
            pstmt.setInt(5, orderBuyVO.getOrderPaying());
            pstmt.setInt(6, orderBuyVO.getOrderSend());
            pstmt.setInt(7, orderBuyVO.getOrderStatus());
            pstmt.setString(8, orderBuyVO.getOrderOther());
            pstmt.setString(9, orderBuyVO.getTrackingNum());
            pstmt.setString(10, orderBuyVO.getReceiverName());
            pstmt.setString(11, orderBuyVO.getReceiverAddress());
            pstmt.setString(12, orderBuyVO.getReceiverPhone());
            pstmt.setDate(13, orderBuyVO.getPickupTime());
            pstmt.setInt(14, orderBuyVO.getOrderId());

            pstmt.executeUpdate();

            // Handle any DRIVER errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
            // Clean up JDBC resources
        }
    }

    @Override
    public void delete(Integer orderId) {

        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE)) {

            pstmt.setInt(1, orderId);

            pstmt.executeUpdate();

            // Handle any DRIVER errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
            // Clean up JDBC resources
        }
    }

    @Override
    public OrderBuyVO findByPrimaryKey(Integer orderId) {

        OrderBuyVO orderBuyVO = null;
        ResultSet rs = null;

        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(GET_ONE_STMT, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            pstmt.setInt(1, orderId);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                orderBuyVO = new OrderBuyVO();
                orderBuyVO.setOrderId(rs.getInt("order_id"));
                orderBuyVO.setMemId(rs.getInt("mem_id"));
                orderBuyVO.setOriginalPrice(rs.getDouble("original_price"));
                orderBuyVO.setDiscountPrice(rs.getDouble("discount_price"));
                orderBuyVO.setFinalPrice(rs.getDouble("final_price"));
                orderBuyVO.setOrderDate(rs.getDate("order_date"));
                orderBuyVO.setOrderPaying(rs.getInt("order_paying"));
                orderBuyVO.setOrderSend(rs.getInt("order_send"));
                orderBuyVO.setOrderStatus(rs.getInt("order_status"));
                orderBuyVO.setOrderOther(rs.getString("order_other"));
                orderBuyVO.setTrackingNum(rs.getString("tracking_num"));
                orderBuyVO.setReceiverName(rs.getString("receiver_name"));
                orderBuyVO.setReceiverAddress(rs.getString("receiver_address"));
                orderBuyVO.setReceiverPhone(rs.getString("receiver_phone"));
                orderBuyVO.setPickupTime(rs.getDate("pickup_time"));
            }
            // Handle any DRIVER errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
            // Clean up JDBC resources
        }
        return orderBuyVO;
    }


    @Override
    public List<OrderBuyVO> getAll() {

        List<OrderBuyVO> list = new ArrayList<OrderBuyVO>();
        OrderBuyVO orderBuyVO = null;

        ResultSet rs = null;

        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(GET_ALL_STMT, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            rs = pstmt.executeQuery();

            while (rs.next()) {

                orderBuyVO = new OrderBuyVO();
                orderBuyVO.setOrderId(rs.getInt("order_id"));
                orderBuyVO.setMemId(rs.getInt("mem_id"));
                orderBuyVO.setOriginalPrice(rs.getDouble("original_price"));
                orderBuyVO.setDiscountPrice(rs.getDouble("discount_price"));
                orderBuyVO.setFinalPrice(rs.getDouble("final_price"));
                orderBuyVO.setOrderDate(rs.getDate("order_date"));
                orderBuyVO.setOrderPaying(rs.getInt("order_paying"));
                orderBuyVO.setOrderSend(rs.getInt("order_send"));
                orderBuyVO.setOrderStatus(rs.getInt("order_status"));
                orderBuyVO.setOrderOther(rs.getString("order_other"));
                orderBuyVO.setTrackingNum(rs.getString("tracking_num"));
                orderBuyVO.setReceiverName(rs.getString("receiver_name"));
                orderBuyVO.setReceiverAddress(rs.getString("receiver_address"));
                orderBuyVO.setReceiverPhone(rs.getString("receiver_phone"));
                orderBuyVO.setPickupTime(rs.getDate("pickup_time"));
                ;
                list.add(orderBuyVO);
            }

            // Handle any DRIVER errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
            // Clean up JDBC resources
        }
        return list;
    }
}
