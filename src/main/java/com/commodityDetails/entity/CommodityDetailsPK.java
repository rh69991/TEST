package commodityDetails.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

public class CommodityDetailsPK implements Serializable {
    @Column(name = "ORDER_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    @Column(name = "ITEM_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommodityDetailsPK that = (CommodityDetailsPK) o;
        return orderId == that.orderId && itemId == that.itemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, itemId);
    }

    @Entity
    @Table(name = "commodity_details", schema = "ba_rei", catalog = "")
    @IdClass(CommodityDetailsPK.class)
    public static class CommodityDetails {
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id
        @Column(name = "ORDER_ID")
        private int orderId;
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id
        @Column(name = "ITEM_ID")
        private int itemId;
        @Basic
        @Column(name = "ITEM_NAME")
        private String itemName;
        @Basic
        @Column(name = "CD_AMOUNT")
        private int cdAmount;
        @Basic
        @Column(name = "ITEM_PRICE")
        private int itemPrice;

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getItemId() {
            return itemId;
        }

        public void setItemId(int itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public int getCdAmount() {
            return cdAmount;
        }

        public void setCdAmount(int cdAmount) {
            this.cdAmount = cdAmount;
        }

        public int getItemPrice() {
            return itemPrice;
        }

        public void setItemPrice(int itemPrice) {
            this.itemPrice = itemPrice;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CommodityDetails that = (CommodityDetails) o;
            return orderId == that.orderId && itemId == that.itemId && cdAmount == that.cdAmount && itemPrice == that.itemPrice && Objects.equals(itemName, that.itemName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(orderId, itemId, itemName, cdAmount, itemPrice);
        }
    }
}
