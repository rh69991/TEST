package com.memberCoupon.dao.impl;

import memberCoupon.dao.MemberCouponDAOInterface;
import memberCoupon.vo.MemberCouponVO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MemberCouponJNDIDAO implements MemberCouponDAOInterface {

    private static final String INSERT_STMT =
            "insert into MEMBER_COUPON (COUPON_ID, MEM_ID, MCPN_GETTIME, MCPN_USE) value (?, ? ,?, ?)";
    private static final String UPDATE =
            "update MEMBER_COUPON set MCPN_USE= ? where COUPON_ID= ?";
    private static final String DELETE =
            "delete from MEMBER_COUPON where COUPON_ID = ?";
    private static final String GET_ONE_STMT =
            "select COUPON_ID, MEM_ID, MCPN_GETTIME, MCPN_USE from MEMBER_COUPON where COUPON_ID = ?";
    private static final String GET_ALL_STMT =
            "select COUPON_ID, MEM_ID, MCPN_GETTIME, MCPN_USE from MEMBER_COUPON order by COUPON_ID";


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
    public void insert(MemberCouponVO member_couponVO) {

        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_STMT)) {

            pstmt.setInt(1, member_couponVO.getCouponId());
            pstmt.setInt(2, member_couponVO.getMemId());
            pstmt.setDate(3, member_couponVO.getMcpnGettime());
            pstmt.setInt(4, member_couponVO.getMcpnUse());

            pstmt.executeUpdate();

            // Handle any DRIVER errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
            // Clean up JDBC resources
        }
    }

    @Override
    public void update(MemberCouponVO member_couponVO) {

        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE)) {


            pstmt.setInt(1, member_couponVO.getMcpnUse());
            pstmt.setInt(2, member_couponVO.getCouponId());

            pstmt.executeUpdate();
            // Handle any DRIVER errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
            // Clean up JDBC resources
        }
    }


    @Override
    public void delete(Integer coupon_id) {

        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE)) {

            pstmt.setInt(1, coupon_id);

            pstmt.executeUpdate();

            // Handle any driver errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
            // Clean up JDBC resources
        }
    }

    @Override
    public MemberCouponVO findByPrimaryKey(Integer coupon_id) {

        MemberCouponVO member_couponVO = null;
        ResultSet rs = null;

        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(GET_ONE_STMT)) {

            pstmt.setInt(1, coupon_id);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                member_couponVO = new MemberCouponVO();
                member_couponVO.setCouponId(rs.getInt("coupon_id"));
                member_couponVO.setMemId(rs.getInt("mem_id"));
                member_couponVO.setMcpnGettime(rs.getDate("mcpn_gettime"));
                member_couponVO.setMcpnUse(rs.getInt("mcpn_use"));
            }

            // Handle any DRIVER errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
            // Clean up JDBC resources
        }
        return member_couponVO;
    }

    @Override
    public List<MemberCouponVO> getAll() {

        List<MemberCouponVO> list = new ArrayList<MemberCouponVO>();
        MemberCouponVO member_couponVO = null;

        ResultSet rs = null;

        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(GET_ALL_STMT)) {
            rs = pstmt.executeQuery();

            while (rs.next()) {

                member_couponVO = new MemberCouponVO();
                member_couponVO.setCouponId(rs.getInt("coupon_id"));
                member_couponVO.setMemId(rs.getInt("mem_id"));
                member_couponVO.setMcpnGettime(rs.getDate("mcpn_gettime"));
                member_couponVO.setMcpnUse(rs.getInt("mcpn_use"));
                list.add(member_couponVO);
            }

            // Handle any DRIVER errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
            // Clean up JDBC resources
        }
        return list;

    }
}
