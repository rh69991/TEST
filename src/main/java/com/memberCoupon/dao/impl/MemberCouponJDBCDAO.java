package com.memberCoupon.dao.impl;

import memberCoupon.dao.MemberCouponDAOInterface;
import memberCoupon.vo.MemberCouponVO;

import static common.Common.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MemberCouponJDBCDAO implements MemberCouponDAOInterface {

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

    @Override
    public void insert(MemberCouponVO member_couponVO) {

         Connection conn = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(INSERT_STMT);

            pstmt.setInt(1, member_couponVO.getCouponId());
            pstmt.setInt(2, member_couponVO.getMemId());
            pstmt.setDate(3, member_couponVO.getMcpnGettime());
            pstmt.setInt(4, member_couponVO.getMcpnUse());

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
    public void update(MemberCouponVO member_couponVO) {

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(UPDATE);

            pstmt.setInt(1, member_couponVO.getMcpnUse());
            pstmt.setInt(2, member_couponVO.getCouponId());

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
    public void delete(Integer coupon_id) {

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(DELETE);

            pstmt.setInt(1, coupon_id);

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
    public MemberCouponVO findByPrimaryKey(Integer coupon_id) {

        MemberCouponVO member_couponVO = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(GET_ONE_STMT);

            pstmt.setInt(1, coupon_id);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                member_couponVO = new MemberCouponVO();
                member_couponVO.setCouponId(rs.getInt("coupon_id"));
                member_couponVO.setMemId(rs.getInt("mem_id"));
                member_couponVO.setMcpnGettime(rs.getDate("mcpn_gettime"));
                member_couponVO.setMcpnUse(rs.getInt("mcpn_use"));
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
        return member_couponVO;
    }

    @Override
    public List<MemberCouponVO> getAll() {

        List<MemberCouponVO> list = new ArrayList<MemberCouponVO>();
        MemberCouponVO member_couponVO = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                member_couponVO = new MemberCouponVO();
                member_couponVO.setCouponId(rs.getInt("coupon_id"));
                member_couponVO.setMemId(rs.getInt("mem_id"));
                member_couponVO.setMcpnGettime(rs.getDate("mcpn_gettime"));
                member_couponVO.setMcpnUse(rs.getInt("mcpn_use"));
                list.add(member_couponVO);
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
