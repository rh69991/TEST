package coupon.dao.impl;

import coupon.dao.CouponDAOInterface;
import coupon.vo.CouponVO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CouponJNDIDAO implements CouponDAOInterface {

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
    public void insert(CouponVO couponVO) {


        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_STMT)) {

            pstmt.setString(1, couponVO.getCouponNar());
            pstmt.setInt(2, couponVO.getCouponVal());
            pstmt.setDate(3, couponVO.getReceiveStart());
            pstmt.setDate(4, couponVO.getReceiveOver());
            pstmt.setDate(5, couponVO.getUseStart());
            pstmt.setDate(6, couponVO.getUseOver());
            pstmt.setInt(7, couponVO.getMinimum());

            pstmt.executeUpdate();

            // Handle any DRIVER errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
            // Clean up JDBC resources
        }
    }

    @Override
    public void update(CouponVO couponVO) {

        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE)) {


            pstmt.setString(1, couponVO.getCouponNar());
            pstmt.setInt(2, couponVO.getCouponVal());
            pstmt.setDate(3, couponVO.getReceiveStart());
            pstmt.setDate(4, couponVO.getReceiveOver());
            pstmt.setDate(5, couponVO.getUseStart());
            pstmt.setDate(6, couponVO.getUseOver());
            pstmt.setInt(7, couponVO.getMinimum());
            pstmt.setInt(8, couponVO.getCouponId());

            pstmt.executeUpdate();

            // Handle any DRIVER errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
            // Clean up JDBC resources
        }
    }

    @Override
    public void delete(Integer couponId) {

        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE)) {

            pstmt.setInt(1, couponId);

            pstmt.executeUpdate();

            // Handle any DRIVER errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
            // Clean up JDBC resources
        }
    }

    @Override
    public CouponVO findByPrimaryKey(Integer couponId) {

        CouponVO couponVO = null;
        ResultSet rs = null;

        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(GET_ONE_STMT, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

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

            // Handle any DRIVER errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
            // Clean up JDBC resources
        }
        return couponVO;
    }

    @Override
    public List<CouponVO> getAll() {

        List<CouponVO> list = new ArrayList<CouponVO>();
        CouponVO couponVO = null;

        ResultSet rs = null;

        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(GET_ALL_STMT)) {
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

            // Handle any DRIVER errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
            // Clean up JDBC resources
        }
        return list;
    }
}
