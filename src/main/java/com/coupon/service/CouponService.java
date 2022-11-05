package coupon.service;

import coupon.vo.Coupon;

import java.util.List;

public interface CouponService {

    boolean addCoupon(Coupon coupon);

    boolean removeCoupon(Integer couponId);

    boolean updateCoupon(Coupon coupon);

    List<Coupon> listAllCoupon();

}
