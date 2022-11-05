package coupon.dao.impl;

import coupon.dao.CouponDAOInterface;
import coupon.vo.CouponVO;

import static common.Common.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CouponJDBCDAO implements CouponDAOInterface {

    private static final String INSERT_STMT =
            "insert into COUPON (COUPON_NAR, COUPON_VAL, RECEIVE_START, RECEIVE_OVER, USE_START, USE_OVER, MINIMUM) value (?, ?, ?, ?, ? ,?, ?)";
    private static final String UPDATE =
            "update COUPON set COUPON_NAR= ?, COUPON_VAL = ?, RECEIVE_START= ?, RECEIVE_OVER= ?, USE_START= ?, USE_OVER= ?, MINIMUM= ? where COUPON_ID= ?";
    private static final String DELETE =
            "delete from COUPON where COUPON_ID = ?";
    private static final String GET_ONE_STMT =
            "select COUPON_ID, COUPON_NAR, COUPON_VAL, RECEIVE_START, RECEIVE_OVER, USE_START, USE_OVER, MINIMUM from COUPON where COUPON_ID = ?";
    private static final String GET_ALL_STMT =
            "select COUPON_ID, COUPON_NAR, COUPON_VAL, RECEIVE_START, RECEIVE_OVER, USE_START, USE_OVER, MINIMUM from COUPON order by COUPON_ID";


    @Override
    public void insert(CouponVO couponVO) {

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(INSERT_STMT);

            pstmt.setString(1, couponVO.getCouponNar());
            pstmt.setInt(2, couponVO.getCouponVal());
            pstmt.setDate(3, couponVO.getReceiveStart());
            pstmt.setDate(4, couponVO.getReceiveOver());
            pstmt.setDate(5, couponVO.getUseStart());
            pstmt.setDate(6, couponVO.getUseOver());
            pstmt.setInt(7, couponVO.getMinimum());

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
    public void update(CouponVO couponVO) {

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(UPDATE);


            pstmt.setString(1, couponVO.getCouponNar());
            pstmt.setInt(2, couponVO.getCouponVal());
            pstmt.setDate(3, couponVO.getReceiveStart());
            pstmt.setDate(4, couponVO.getReceiveOver());
            pstmt.setDate(5, couponVO.getUseStart());
            pstmt.setDate(6, couponVO.getUseOver());
            pstmt.setInt(7, couponVO.getMinimum());
            pstmt.setInt(8, couponVO.getCouponId());

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
    public void delete(Integer couponId) {

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(DELETE);

            pstmt.setInt(1, couponId);

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
    public CouponVO findByPrimaryKey(Integer couponId) {

        CouponVO couponVO = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(GET_ONE_STMT);

            pstmt.setInt(1, couponId);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                couponVO = new CouponVO();
                couponVO.setCouponId(rs.getInt("coupon_id"));
                couponVO.setCouponNar(rs.getString("coupon_nar"));
                couponVO.setCouponVal(rs.getInt("coupon_val"));
                couponVO.setReceiveStart(rs.getDate("receive_start"));
                couponVO.setReceiveOver(rs.getDate("receive_over"));
                couponVO.setUseStart(rs.getDate("use_start"));
                couponVO.setUseOver(rs.getDate("use_over"));
                couponVO.setMinimum(rs.getInt("minimum"));
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
        return couponVO;
    }

    @Override
    public List<CouponVO> getAll() {

      List<CouponVO> list = new ArrayList<CouponVO>();
        CouponVO couponVO = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                couponVO = new CouponVO();
                couponVO.setCouponId(rs.getInt("coupon_id"));
                couponVO.setCouponNar(rs.getString("coupon_nar"));
                couponVO.setCouponVal(rs.getInt("coupon_val"));
                couponVO.setReceiveStart(rs.getDate("receive_start"));
                couponVO.setReceiveOver(rs.getDate("receive_over"));
                couponVO.setUseStart(rs.getDate("use_start"));
                couponVO.setUseOver(rs.getDate("use_over"));
                couponVO.setMinimum(rs.getInt("minimum"));
                list.add(couponVO);
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
