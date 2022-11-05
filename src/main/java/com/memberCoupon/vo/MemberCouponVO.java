package memberCoupon.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class MemberCouponVO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "COUPON_ID")
    private Integer couponId;
    @Column(name = "MEM_ID")
    private Integer memId;
    @Column(name = "MCPN_GETTIME")
    private Date mcpnGettime;
    @Column(name = "MCPN_USE")
    private Integer mcpnUse;

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getMemId() {
        return memId;
    }

    public void setMemId(Integer memId) {
        this.memId = memId;
    }

    public Date getMcpnGettime() {
        return mcpnGettime;
    }

    public void setMcpnGettime(Date mcpnGettime) {
        this.mcpnGettime = mcpnGettime;
    }

    public Integer getMcpnUse() {
        return mcpnUse;
    }

    public void setMcpnUse(Integer mcpnUse) {
        this.mcpnUse = mcpnUse;
    }
}
