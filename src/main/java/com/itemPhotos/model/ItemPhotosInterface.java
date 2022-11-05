package com.itemPhotos.model;

import java.util.List;

import org.json.JSONArray;

public interface ItemPhotosInterface {
	public void insert(ItemPhotosVO itemPhotosVO);

	public void update(ItemPhotosVO itemPhotosVO);

	public void delete(Integer itemId);

	public ItemPhotosVO findByPrimaryKey(Integer ipId);

	public JSONArray getAllPhoto(Integer itemId);

	public List<ItemPhotosVO> getPhoto(Integer itemId);

	public String getPhotoJson(Integer itemId);
	public int deletePhoto(Integer ipId);
//	public List<byte[]> getPhotos(Integer item_id);
}
