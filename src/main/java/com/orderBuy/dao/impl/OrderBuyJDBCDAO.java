package orderBuy.dao.impl;

import orderBuy.dao.OrderBuyDAOInterface;
import orderBuy.vo.OrderBuyVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static common.Common.*;
import static common.Common.PASSWORD;

public class OrderBuyJDBCDAO implements OrderBuyDAOInterface {

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

    @Override
    public void insert(OrderBuyVO orderBuyVO) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;

        Integer orderId= null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(INSERT_STMT);

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
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database DRIVER. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
            // Clean up JDBC resources
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }

    @Override
    public void update(OrderBuyVO order_buyVO) {

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(UPDATE);



            pstmt.setDouble(1, order_buyVO.getOriginalPrice());
            pstmt.setDouble(2, order_buyVO.getDiscountPrice());
            pstmt.setDouble(3, order_buyVO.getFinalPrice());
            pstmt.setDate(4, order_buyVO.getOrderDate());
            pstmt.setInt(5, order_buyVO.getOrderPaying());
            pstmt.setInt(6, order_buyVO.getOrderSend());
            pstmt.setInt(7, order_buyVO.getOrderStatus());
            pstmt.setString(8, order_buyVO.getOrderOther());
            pstmt.setString(9, order_buyVO.getTrackingNum());
            pstmt.setString(10, order_buyVO.getReceiverName());
            pstmt.setString(11, order_buyVO.getReceiverAddress());
            pstmt.setString(12, order_buyVO.getReceiverPhone());
            pstmt.setDate(13, order_buyVO.getPickupTime());
            pstmt.setInt(14,order_buyVO.getOrderId());

            pstmt.executeUpdate();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database DRIVER. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
            // Clean up JDBC resources
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }

    }

    @Override
    public void delete(Integer order_id) {

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(DELETE);

            pstmt.setInt(1, order_id);

            pstmt.executeUpdate();

            // Handle any driver errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
            // Clean up JDBC resources
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }

    }

    @Override
    public OrderBuyVO findByPrimaryKey(Integer order_id) {

        OrderBuyVO order_buyVO = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(GET_ONE_STMT);

            pstmt.setInt(1, order_id);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                order_buyVO = new OrderBuyVO();
                order_buyVO.setOrderId(rs.getInt("order_id"));
                order_buyVO.setMemId(rs.getInt("mem_id"));
                order_buyVO.setOriginalPrice(rs.getDouble("original_price"));
                order_buyVO.setDiscountPrice(rs.getDouble("discount_price"));
                order_buyVO.setFinalPrice(rs.getDouble("final_price"));
                order_buyVO.setOrderDate(rs.getDate("order_date"));
                order_buyVO.setOrderPaying(rs.getInt("order_paying"));
                order_buyVO.setOrderSend(rs.getInt("order_send"));
                order_buyVO.setOrderStatus(rs.getInt("order_status"));
                order_buyVO.setOrderOther(rs.getString("order_other"));
                order_buyVO.setTrackingNum(rs.getString("tracking_num"));
                order_buyVO.setReceiverName(rs.getString("receiver_name"));
                order_buyVO.setReceiverAddress(rs.getString("receiver_address"));
                order_buyVO.setReceiverPhone(rs.getString("receiver_phone"));
                order_buyVO.setPickupTime(rs.getDate("pickup_time"));
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database DRIVER. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
            // Clean up JDBC resources
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        return order_buyVO;
    }


    @Override
    public List<OrderBuyVO> getAll() {

        List<OrderBuyVO> list = new ArrayList<OrderBuyVO>();
        OrderBuyVO order_buyVO = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                order_buyVO = new OrderBuyVO();
                order_buyVO.setOrderId(rs.getInt("order_id"));
                order_buyVO.setMemId(rs.getInt("mem_id"));
                order_buyVO.setOriginalPrice(rs.getDouble("original_price"));
                order_buyVO.setDiscountPrice(rs.getDouble("discount_price"));
                order_buyVO.setFinalPrice(rs.getDouble("final_price"));
                order_buyVO.setOrderDate(rs.getDate("order_date"));
                order_buyVO.setOrderPaying(rs.getInt("order_paying"));
                order_buyVO.setOrderSend(rs.getInt("order_send"));
                order_buyVO.setOrderStatus(rs.getInt("order_status"));
                order_buyVO.setOrderOther(rs.getString("order_other"));
                order_buyVO.setTrackingNum(rs.getString("tracking_num"));
                order_buyVO.setReceiverName(rs.getString("receiver_name"));
                order_buyVO.setReceiverAddress(rs.getString("receiver_address"));
                order_buyVO.setReceiverPhone(rs.getString("receiver_phone"));
                order_buyVO.setPickupTime(rs.getDate("pickup_time"));
                ;
                list.add(order_buyVO);
            }

            // Handle any driver errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
            // Clean up JDBC resources
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        return list;
    }
}
