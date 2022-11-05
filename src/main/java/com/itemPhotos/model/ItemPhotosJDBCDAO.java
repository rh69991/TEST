package com.itemPhotos.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;


public class ItemPhotosJDBCDAO implements ItemPhotosInterface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/BA_REI?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "wendy1016";

	private static final String INSERT_STMT = "INSERT INTO itemPhotos(item_id,ip_photo) values (?,?)";
	private static final String GET_ALL_STMT = "SELECT ip_photo FROM itemPhotos where item_id=? order by ip_id;";
	private static final String GET_ONE_STMT = "SELECT ip_photo FROM item_photos where item_id = ?;";
	private static final String DELETE = "DELETE FROM itemPhotos where item_id = ?";
	private static final String UPDATE = "UPDATE itemPhotos set  ip_photo=? where item_id = ?";
	@Override
	public void insert(ItemPhotosVO itemPhotosVO) {
		// TODO Auto-generated method stub

	}
	@Override
	public void update(ItemPhotosVO itemPhotosVO) {
		// TODO Auto-generated method stub

	}
	@Override
	public void delete(Integer itemId) {
		// TODO Auto-generated method stub

	}
	@Override
	public ItemPhotosVO findByPrimaryKey(Integer ipId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public JSONArray getAllPhoto(Integer itemId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<ItemPhotosVO> getPhoto(Integer itemId) {
		try (
			Connection conn = DriverManager.getConnection(url , userid, passwd);
			PreparedStatement pstmt = conn.prepareStatement(GET_ONE_STMT)
		) {
			pstmt.setInt(1, itemId);
			try (ResultSet rs = pstmt.executeQuery()) {
				List<ItemPhotosVO> list = new ArrayList<>();
				while (rs.next()) {
					ItemPhotosVO vo = new ItemPhotosVO();
					vo.setIpPhoto(rs.getBytes("IP_PHOTO"));
					list.add(vo);
				}
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public String getPhotoJson(Integer itemId) {
		// TODO Auto-generated method stub
		return null;
	}
//	@Override
//	public List<byte[]> getPhotos(Integer item_id) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	@Override
	public int deletePhoto(Integer ipId) {
		// TODO Auto-generated method stub
		return -1;

	}
}
