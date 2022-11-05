package memberCoupon.vo;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "member_coupon", schema = "ba_rei", catalog = "")
@IdClass(MemberCouponPK.class)
public class MemberCoupon {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "COUPON_ID")
    private int couponId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "MEM_ID")
    private int memId;
    @Basic
    @Column(name = "MCPN_GETTIME")
    private Timestamp mcpnGettime;
    @Basic
    @Column(name = "MCPN_USE")
    private byte mcpnUse;

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

    public Timestamp getMcpnGettime() {
        return mcpnGettime;
    }

    public void setMcpnGettime(Timestamp mcpnGettime) {
        this.mcpnGettime = mcpnGettime;
    }

    public byte getMcpnUse() {
        return mcpnUse;
    }

    public void setMcpnUse(byte mcpnUse) {
        this.mcpnUse = mcpnUse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberCoupon that = (MemberCoupon) o;
        return couponId == that.couponId && memId == that.memId && mcpnUse == that.mcpnUse && Objects.equals(mcpnGettime, that.mcpnGettime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(couponId, memId, mcpnGettime, mcpnUse);
    }
}
