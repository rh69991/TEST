package coupon.service.impl;

import coupon.dao.CouponDAO;
import coupon.dao.impl.CouponDAOImpl;
import coupon.service.CouponService;
import coupon.vo.Coupon;

import java.util.List;

public class CouponServiceImpl implements CouponService {

    private CouponDAO dao;

    public CouponServiceImpl() {
        dao = new CouponDAOImpl();
    }

    @Override
    public boolean addCoupon(Coupon coupon) {
        return dao.insert(coupon) > 0;
    }

    @Override
    public boolean removeCoupon(Integer couponId) {
        return dao.deleteById(couponId) > 0;
    }

    @Override
    public boolean updateCoupon(Coupon coupon) {
        return dao.updateById(coupon) > 0;
    }

    @Override
    public List<Coupon> listAllCoupon() {
        return dao.selectAll();
    }
}
