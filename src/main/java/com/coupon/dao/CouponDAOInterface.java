package coupon.dao;

import coupon.vo.CouponVO;

import java.util.List;

public interface CouponDAOInterface {

    public void insert(CouponVO couponVO);

    public void update(CouponVO couponVO);

    public void delete(Integer couponId);

    public CouponVO findByPrimaryKey(Integer couponId);

    public List<CouponVO> getAll();

}
