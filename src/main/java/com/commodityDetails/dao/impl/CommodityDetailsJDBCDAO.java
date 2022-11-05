package commodityDetails.dao.impl;

import commodityDetails.dao.CommodityDetailsDAOInterface;
import commodityDetails.vo.CommodityDetailsVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static common.Common.*;
import static common.Common.PASSWORD;

public class CommodityDetailsJDBCDAO implements CommodityDetailsDAOInterface {

    private static final String INSERT_STMT =
            "insert into COMMODITY_DETAILS (ORDER_ID, ITEM_ID , ITEM_NAME, CD_AMOUNT, ITEM_PRICE) value (?, ?, ?, ?, ?)";
    private static final String UPDATE =
            "update COMMODITY_DETAILS set ITEM_ID= ?, ITEM_NAME= ?, CD_AMOUNT= ?, ITEM_PRICE= ? where ORDER_ID= ?";
    private static final String DELETE =
            "delete from COMMODITY_DETAILS where ORDER_ID = ?";
    private static final String GET_ONE_STMT =
            "select ORDER_ID, ITEM_ID , ITEM_NAME, CD_AMOUNT, ITEM_PRICE from COMMODITY_DETAILS where ORDER_ID = ?";
    private static final String GET_ALL_DETAILS =
            "select ORDER_ID, ITEM_ID , ITEM_NAME, CD_AMOUNT, ITEM_PRICE from COMMODITY_DETAILS order by ITEM_ID";



    @Override
    public void insert(CommodityDetailsVO commodityDetailsVO) {

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(INSERT_STMT);

            pstmt.setInt(1, commodityDetailsVO.getOrderId());
            pstmt.setInt(2, commodityDetailsVO.getItemId());
            pstmt.setString(3, commodityDetailsVO.getItemName());
            pstmt.setInt(4, commodityDetailsVO.getCdAmount());
            pstmt.setDouble(5, commodityDetailsVO.getItemPrice());


            pstmt.executeUpdate();

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
    public void update(CommodityDetailsVO commodityDetailsVO) {

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(UPDATE);


            pstmt.setInt(2, commodityDetailsVO.getItemId());
            pstmt.setString(3, commodityDetailsVO.getItemName());
            pstmt.setInt(4, commodityDetailsVO.getCdAmount());
            pstmt.setDouble(5, commodityDetailsVO.getItemPrice());


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
    public void delete(Integer orderId) {

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(DELETE);

            pstmt.setInt(1, orderId);

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
    public CommodityDetailsVO findByOrderID(Integer orderId) {

        CommodityDetailsVO commodity_detailsVO = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(GET_ONE_STMT);

            pstmt.setInt(1, orderId);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                commodity_detailsVO = new CommodityDetailsVO();
                commodity_detailsVO.setItemId(rs.getInt("item_id"));
                commodity_detailsVO.setItemName(rs.getString("item_name"));
                commodity_detailsVO.setCdAmount(rs.getInt("cd_amount"));
                commodity_detailsVO.setItemPrice(rs.getDouble("item_price"));
                commodity_detailsVO.setOrderId(rs.getInt("order_id"));
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
        return commodity_detailsVO;
    }

    @Override
    public List<CommodityDetailsVO> getOneDetail(Integer orderId) {

        List<CommodityDetailsVO> list = new ArrayList<CommodityDetailsVO>();
        CommodityDetailsVO commodity_detailsVO = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(GET_ONE_STMT);

            pstmt.setInt(1, orderId);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                commodity_detailsVO = new CommodityDetailsVO();
                commodity_detailsVO.setOrderId(rs.getInt("order_id"));
                commodity_detailsVO.setItemId(rs.getInt("item_id"));
                commodity_detailsVO.setItemName(rs.getString("item_name"));
                commodity_detailsVO.setCdAmount(rs.getInt("cd_amount"));
                commodity_detailsVO.setItemPrice(rs.getDouble("item_price"));
                list.add(commodity_detailsVO);
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

    @Override
    public List<CommodityDetailsVO> getAllDetail() {

       List<CommodityDetailsVO> list = new ArrayList<CommodityDetailsVO>();
        CommodityDetailsVO commodity_detailsVO = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(GET_ALL_DETAILS);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                commodity_detailsVO = new CommodityDetailsVO();
                commodity_detailsVO.setOrderId(rs.getInt("order_id"));
                commodity_detailsVO.setItemId(rs.getInt("item_id"));
                commodity_detailsVO.setItemName(rs.getString("item_name"));
                commodity_detailsVO.setCdAmount(rs.getInt("cd_amount"));
                commodity_detailsVO.setItemPrice(rs.getDouble("item_price"));
                list.add(commodity_detailsVO);
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
