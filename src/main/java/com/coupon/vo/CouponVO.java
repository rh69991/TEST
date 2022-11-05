package coupon.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class CouponVO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "COUPON_ID")
    private Integer couponId;
    @Column(name = "COUPON_NAR")
    private String couponNar;
    @Column(name = "COUPON_VAL")
    private Integer couponVal;
    @Column(name = "RECEIVE_START")
    private Date receiveStart;
    @Column(name = "RECEIVE_OVER")
    private Date receiveOver;
    @Column(name = "USE_OVER")
    private Date useStart;
    @Column(name = "USE_OVER")
    private Date useOver;

    private Integer minimum;

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public String getCouponNar() {
        return couponNar;
    }

    public void setCouponNar(String couponNar) {
        this.couponNar = couponNar;
    }

    public Integer getCouponVal() {
        return couponVal;
    }

    public void setCouponVal(Integer couponVal) {
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

    public Integer getMinimum() {
        return minimum;
    }

    public void setMinimum(Integer minimum) {
        this.minimum = minimum;
    }
}
