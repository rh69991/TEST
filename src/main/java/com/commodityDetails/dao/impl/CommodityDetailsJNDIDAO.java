package com.commodityDetails.dao.impl;

import commodityDetails.dao.CommodityDetailsDAOInterface;
import commodityDetails.vo.CommodityDetailsVO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommodityDetailsJNDIDAO implements CommodityDetailsDAOInterface {

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
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void insert(CommodityDetailsVO commodityDetailsVO) {

        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_STMT)) {

            pstmt.setInt(1, commodityDetailsVO.getOrderId());
            pstmt.setInt(2, commodityDetailsVO.getItemId());
            pstmt.setString(3, commodityDetailsVO.getItemName());
            pstmt.setInt(4, commodityDetailsVO.getCdAmount());
            pstmt.setDouble(5, commodityDetailsVO.getItemPrice());

            pstmt.executeUpdate();

            // Handle any DRIVER errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
            // Clean up JDBC resources
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void update(CommodityDetailsVO commodityDetailsVO) {

        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE)) {


            pstmt.setInt(1, commodityDetailsVO.getOrderId());
            pstmt.setInt(2, commodityDetailsVO.getItemId());
            pstmt.setString(3, commodityDetailsVO.getItemName());
            pstmt.setInt(4, commodityDetailsVO.getCdAmount());
            pstmt.setDouble(5, commodityDetailsVO.getItemPrice());

            pstmt.executeUpdate();

            // Handle any DRIVER errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
            // Clean up JDBC resources
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
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
    @Transactional(readOnly = true)
    public CommodityDetailsVO findByOrderID(Integer orderId) {

        CommodityDetailsVO commodity_detailsVO = null;
        ResultSet rs = null;

        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(GET_ONE_STMT, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

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


            // Handle any DRIVER errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
            // Clean up JDBC resources
        }
        return commodity_detailsVO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommodityDetailsVO> getOneDetail(Integer orderId) {

        List<CommodityDetailsVO> list = new ArrayList<CommodityDetailsVO>();
        CommodityDetailsVO commodity_detailsVO = null;

        ResultSet rs = null;

        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(GET_ONE_STMT, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

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

            // Handle any DRIVER errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
            // Clean up JDBC resources
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommodityDetailsVO> getAllDetail() {

        List<CommodityDetailsVO> list = new ArrayList<CommodityDetailsVO>();
        CommodityDetailsVO commodity_detailsVO = null;

        ResultSet rs = null;

        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(GET_ALL_DETAILS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
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

             // Handle any DRIVER errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
            // Clean up JDBC resources
        }
        return list;
    }
}
