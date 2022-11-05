package coupon.service.impl;

import coupon.dao.impl.CouponJNDIDAO;
import coupon.vo.CouponVO;

import java.sql.Date;
import java.util.List;

public class CouponService  {

    CouponJNDIDAO dao;
//    private CouponDAOInterface dao;

    public CouponService(){
        dao = new CouponJNDIDAO();
    }

    public CouponVO addCoupon(String coupon_nar, Integer coupon_val, Date receive_start, Date receive_over, Date use_start, Date use_over, Integer minimum){


        CouponVO couponVO = new CouponVO();

        couponVO.setCouponNar(coupon_nar);
        couponVO.setCouponVal(coupon_val);
        couponVO.setReceiveStart(receive_start);
        couponVO.setReceiveOver(receive_over);
        couponVO.setUseStart(use_start);
        couponVO.setUseOver(use_over);
        couponVO.setMinimum(minimum);
        dao.insert(couponVO);

        return couponVO;

    }
    public CouponVO updateCoupon(String coupon_nar, Integer coupon_val, Date receive_start, Date receive_over, Date use_start, Date use_over, Integer minimum , Integer coupon_id){

        CouponVO couponVO = new CouponVO();

        couponVO.setCouponNar(coupon_nar);
        couponVO.setCouponVal(coupon_val);
        couponVO.setReceiveStart(receive_start);
        couponVO.setReceiveOver(receive_over);
        couponVO.setUseStart(use_start);
        couponVO.setUseOver(use_over);
        couponVO.setMinimum(minimum);
        couponVO.setCouponId(coupon_id);
        dao.update(couponVO);

        return couponVO;

    }
    public void deleteCoupon(Integer coupon_id) {
        dao.delete(coupon_id);
    }

    public List<CouponVO> getAll() {
        return dao.getAll();
    }

    public CouponVO getOneCoupon(Integer couponId) {
        return dao.findByPrimaryKey(couponId);
    }

}
