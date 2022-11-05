package com.itemPhotos.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;

public class ItemPhotosDAO implements ItemPhotosInterface {

	private static final String INSERT_STMT = "INSERT INTO item_photos(item_id,ip_photo) values (?,?)";
	private static final String GET_ALL_STMT = "SELECT ip_id, ip_photo FROM item_photos where item_id=? order by ip_id;";
	private static final String GET_ONE_STMT = "SELECT ip_photo FROM item_photos where item_id = ?;";
	private static final String DELETE = "DELETE FROM item_photos where item_id = ?";
	private static final String UPDATE = "UPDATE item_photos set  ip_photo=? where item_id = ?";
	private static final String DELETE_PHOTO = "DELETE FROM item_photos where ip_id = ?";

	private static DataSource ds = null;

	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/hikariCP-BaRei");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(ItemPhotosVO itemPhotosVO) {
		try (Connection con = ds.getConnection(); PreparedStatement pstmt = con.prepareStatement(INSERT_STMT)) {
			pstmt.setInt(1, itemPhotosVO.getItemId());
			pstmt.setBytes(2, itemPhotosVO.getIpPhoto());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}
	}

	@Override
	public void update(ItemPhotosVO itemPhotosVO) {
		try (Connection con = ds.getConnection(); PreparedStatement pstmt = con.prepareStatement(UPDATE)) {

			pstmt.setBytes(1, itemPhotosVO.getIpPhoto());
			pstmt.setInt(2, itemPhotosVO.getItemId());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}

	}

	@Override
	public void delete(Integer itemId) {
		try (Connection con = ds.getConnection(); PreparedStatement pstmt = con.prepareStatement(DELETE)) {

			pstmt.setInt(1, itemId);

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}

	}

	@Override
	public ItemPhotosVO findByPrimaryKey(Integer ipId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray getAllPhoto(Integer itemId) {
		JSONArray jsonArray = new JSONArray();
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_ALL_STMT)) {
			pstmt.setInt(1, itemId);
			ResultSet rs = pstmt.executeQuery();

			Encoder encoder = Base64.getEncoder();
			while (rs.next()) {
				byte[] photo = rs.getBytes("IP_PHOTO");

				// 轉成Base64(文字檔）
				String photo64 = encoder.encodeToString(photo);

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("ipId",rs.getInt("IP_ID"));
				jsonObject.put("photo", photo64);
				jsonArray.put(jsonObject);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonArray;

	}


	@Override
	public List<ItemPhotosVO> getPhoto(Integer itemId) {
		try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(GET_ONE_STMT)) {
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

//	測試
//	@Override
//	public List<byte[]> getPhotos(Integer item_id) {try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(GET_ONE_STMT)) {
//		pstmt.setInt(1, item_id);
//		try (ResultSet rs = pstmt.executeQuery()) {
//			List<byte[]> list = new ArrayList<>();
//			while (rs.next()) {
//
//				list.add(rs.getBytes("IP_PHOTO"));
//
//			}
//			return list;
//		}
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//	return null;
//	}

	@Override
	public String getPhotoJson(Integer itemId) {

		Encoder encoder = Base64.getEncoder();
		String photo = null;
		try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(GET_ONE_STMT)) {

			pstmt.setInt(1, itemId);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				byte[] photoJs = rs.getBytes("IP_PHOTO");
				photo = encoder.encodeToString(photoJs);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return photo;

	}

	@Override
	public int deletePhoto(Integer ipId) {
		try(Connection con = ds.getConnection(); PreparedStatement pstmt = con.prepareStatement(DELETE_PHOTO)) {
			pstmt.setInt(1, ipId);
			return pstmt.executeUpdate();
		} catch (Exception e) {

			e.printStackTrace();
			return -1;
		}
	}


}
