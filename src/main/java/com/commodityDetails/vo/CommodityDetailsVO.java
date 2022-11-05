package commodityDetails.vo;

import java.io.Serializable;

public class CommodityDetailsVO implements Serializable {

    private Integer orderId;
    private Integer itemId;
    private String itemName;
    private Integer cdAmount;
    private Double itemPrice;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getCdAmount() {
        return cdAmount;
    }

    public void setCdAmount(Integer cdAmount) {
        this.cdAmount = cdAmount;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }
}
