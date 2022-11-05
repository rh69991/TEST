package memberCoupon.dao;

import memberCoupon.vo.MemberCouponVO;

import java.util.List;

public interface MemberCouponDAOInterface {

    public void insert(MemberCouponVO member_couponVO);
    public void update(MemberCouponVO member_couponVO);
    public void delete(Integer coupon_id);
    public MemberCouponVO findByPrimaryKey(Integer coupon_id);
    public List<MemberCouponVO> getAll();

}
