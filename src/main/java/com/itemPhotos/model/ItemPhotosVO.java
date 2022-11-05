package com.itemPhotos.model;

public class ItemPhotosVO implements java.io.Serializable {
	private Integer ipId;
	private Integer itemId;
	private byte[] ipPhoto;

	public Integer getIpId() {
		return ipId;
	}

	public void setIpId(Integer ipId) {
		this.ipId = ipId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public byte[] getIpPhoto() {
		return ipPhoto;
	}

	public void setIpPhoto(byte[] ipPhoto) {
		this.ipPhoto = ipPhoto;
	}
}
