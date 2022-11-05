package coupon.vo;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Coupon {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "COUPON_ID")
    private int couponId;
    @Basic
    @Column(name = "COUPON_NAR")
    private String couponNar;
    @Basic
    @Column(name = "COUPON_VAL")
    private int couponVal;
    @Basic
    @Column(name = "RECEIVE_START")
    private Date receiveStart;
    @Basic
    @Column(name = "RECEIVE_OVER")
    private Date receiveOver;
    @Basic
    @Column(name = "USE_START")
    private Date useStart;
    @Basic
    @Column(name = "USE_OVER")
    private Date useOver;
    @Basic
    @Column(name = "MINIMUM")
    private int minimum;

    public Coupon() {
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public String getCouponNar() {
        return couponNar;
    }

    public void setCouponNar(String couponNar) {
        this.couponNar = couponNar;
    }

    public int getCouponVal() {
        return couponVal;
    }

    public void setCouponVal(int couponVal) {
        this.couponVal = couponVal;
    }

    public Date getReceiveStart() {
        return receiveStart;
    }

    public void setReceiveStart(Date receiveStart) {
        this.receiveStart = receiveStart;
    }

    public Date getReceiveOver() {
        return receiveOver;
    }

    public void setReceiveOver(Date receiveOver) {
        this.receiveOver = receiveOver;
    }

    public Date getUseStart() {
        return useStart;
    }

    public void setUseStart(Date useStart) {
        this.useStart = useStart;
    }

    public Date getUseOver() {
        return useOver;
    }

    public void setUseOver(Date useOver) {
        this.useOver = useOver;
    }

    public int getMinimum() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return couponId == coupon.couponId && couponVal == coupon.couponVal && minimum == coupon.minimum && Objects.equals(couponNar, coupon.couponNar) && Objects.equals(receiveStart, coupon.receiveStart) && Objects.equals(receiveOver, coupon.receiveOver) && Objects.equals(useStart, coupon.useStart) && Objects.equals(useOver, coupon.useOver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(couponId, couponNar, couponVal, receiveStart, receiveOver, useStart, useOver, minimum);
    }
}
