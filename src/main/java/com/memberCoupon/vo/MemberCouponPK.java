package memberCoupon.vo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class MemberCouponPK implements Serializable {
    @Column(name = "COUPON_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int couponId;
    @Column(name = "MEM_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memId;

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int getMemId() {
        return memId;
    }

    public void setMemId(int memId) {
        this.memId = memId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberCouponPK that = (MemberCouponPK) o;
        return couponId == that.couponId && memId == that.memId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(couponId, memId);
    }
}
