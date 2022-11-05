package com.item.model;

import java.sql.Date;

public class ItemVO implements java.io.Serializable {
   private Integer itemId;
   private Integer itemtId;
   private String itemName;
   private String itemContent;
   private Integer itemPrice;
   private Integer itemAmount;
   private Integer itemStatus;
   private Date itemDate;
   private Date itemEnddate;
   private byte[] photo;

   public byte[] getPhoto() {
      return photo;
   }

   public void setPhoto(byte[] photo) {
      this.photo = photo;
   }

   public Integer getItemId() {
      return itemId;
   }

   public void setItemId(Integer itemId) {
      this.itemId = itemId;
   }

   public Integer getItemtId() {
      return itemtId;
   }

   public void setItemtId(Integer itemtId) {
      this.itemtId = itemtId;
   }

   public String getItemName() {
      return itemName;
   }

   public void setItemName(String itemName) {
      this.itemName = itemName;
   }

   public String getItemContent() {
      return itemContent;
   }

   public void setItemContent(String itemContent) {
      this.itemContent = itemContent;
   }

   public Integer getItemPrice() {
      return itemPrice;
   }

   public void setItemPrice(Integer itemPrice) {
      this.itemPrice = itemPrice;
   }

   public Integer getItemAmount() {
      return itemAmount;
   }

   public void setItemAmount(Integer itemAmount) {
      this.itemAmount = itemAmount;
   }

   public Integer getItemStatus() {
      return itemStatus;
   }

   public void setItemStatus(Integer itemStatus) {
      this.itemStatus = itemStatus;
   }

   public Date getItemDate() {
      return itemDate;
   }

   public void setItemDate(Date itemDate) {
      this.itemDate = itemDate;
   }

   public Date getItemEnddate() {
      return itemEnddate;
   }

   public void setItemEnddate(Date itemEnddate) {
      this.itemEnddate = itemEnddate;
   }
}
