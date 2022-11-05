package com.itemPhotos.model;

import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;

import org.json.JSONArray;

public class ItemPhotosService {
	private ItemPhotosInterface dao;

	public ItemPhotosService() {
		dao = new ItemPhotosDAO();
	}

	public byte[] getPhoto(Integer itemId) {
		List<ItemPhotosVO> list = dao.getPhoto(itemId);
		return list == null || list.size() == 0 ? null : list.get(0).getIpPhoto();
	}

	//測試
	public List<ItemPhotosVO> getPhotos(Integer itemId) {
		List<ItemPhotosVO> list = dao.getPhoto(itemId);
		return list == null || list.size() == 0 ? null : list;
	}


//	測試
//	public List<byte[]> getPhotos(Integer item_id){
//		List<byte[]> list = dao.getPhotos(item_id);
//		return list == null || list.size() == 0 ? null : list;
//
//	}

	public JSONArray getAllPhoto(Integer itemId){
		JSONArray jsonArray = dao.getAllPhoto(itemId);
		return jsonArray;

	}

	public String getPhotoJson(Integer itemId) {
		List<ItemPhotosVO> list = dao.getPhoto(itemId);
		Encoder encoder=Base64.getEncoder();
		return list == null || list.size() == 0 ? null : encoder.encodeToString(list.get(0).getIpPhoto());
	}
	public boolean deletePhoto(Integer ipId) {
		return dao.deletePhoto(ipId)>0;

	}
}
