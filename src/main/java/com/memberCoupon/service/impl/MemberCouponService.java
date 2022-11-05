package com.memberCoupon.service.impl;

import com.memberCoupon.dao.impl.MemberCouponJNDIDAO;
import memberCoupon.vo.MemberCouponVO;

import java.sql.*;
import java.util.List;

import static common.Common.*;

public class MemberCouponService {

    private MemberCouponJNDIDAO dao;

    public MemberCouponService(){
        dao = new MemberCouponJNDIDAO();
    }

    public MemberCouponVO getOwnCoupon(Integer memId, Integer couponId) {

        MemberCouponVO memberCouponVO = null;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("select COUPON_ID, MEM_ID, MCPN_GETTIME, MCPN_USE from MEMBER_COUPON where MEM_ID = ? and COUPON_ID = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            pstmt.setInt(1, memId);
            pstmt.setInt(2, couponId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                memberCouponVO = new MemberCouponVO();
                memberCouponVO.setCouponId(rs.getInt("coupon_id"));
                memberCouponVO.setMemId(rs.getInt("mem_id"));
                memberCouponVO.setMcpnGettime(rs.getDate("mcpn_gettime"));
                memberCouponVO.setMcpnUse(rs.getInt("mcpn_use"));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return memberCouponVO;
    }

    public MemberCouponVO updateUse(Integer couponId,Integer mcpnUse) {


        MemberCouponVO memberCouponVO = new MemberCouponVO();

        memberCouponVO.setMcpnUse(mcpnUse);
        memberCouponVO.setCouponId(couponId);
        dao.update(memberCouponVO);

        return  memberCouponVO;
    }

    public List<MemberCouponVO> getAll(){
       return dao.getAll();
    }

}
