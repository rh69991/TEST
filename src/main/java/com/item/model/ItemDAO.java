package com.item.model;

import com.itemPhotos.model.ItemPhotosDAO;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO implements ItemDAOInterface {
	private static final String INSERT_STMT = "INSERT INTO item (itemt_id,item_name,item_content,item_price,item_amount,item_status,item_date,item_enddate) VALUES (?, ?, ?, ?, ?, ?,?,?)";
	private static final String GET_ALL_STMT = "SELECT item_id,itemt_id,item_name,item_content,item_price,item_amount,item_status,item_date,item_enddate FROM item order by item_id";
//	private static final String GET_ALL_STMT = "SELECT ip.ip_photo, i.item_id,i.itemt_id,i.item_name,i.item_content,i.item_price,i.item_amount,i.item_status,i.item_date, i.item_enddate FROM item i join itemPhotos ip on i.item_id = ip.item_id order by item_id";
	private static final String GET_ONE_STMT = "SELECT item_id,itemt_id,item_name,item_content,item_price,item_amount,item_status,item_date,item_enddate FROM item where item_id = ?";
	private static final String DELETE = "DELETE FROM item where item_id = ?";
	private static final String UPDATE = "UPDATE item set itemt_id=?,item_name=?,item_content=?,item_price=?,item_amount=?,item_status=?,item_date=?,item_enddate=? where item_id = ?";
	private static final String GET_ALL_STMT_JS = "SELECT item_id,itemt_id,item_name,item_content,item_price,item_amount,item_status,item_date,item_enddate FROM item order by item_id";
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
	public Integer insert(ItemVO itemVO) {

		try (Connection con = ds.getConnection();
				PreparedStatement pstmt = con.prepareStatement(INSERT_STMT, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setInt(1, itemVO.getItemtId());
			pstmt.setString(2, itemVO.getItemName());
			pstmt.setString(3, itemVO.getItemContent());
			pstmt.setInt(4, itemVO.getItemPrice());
			pstmt.setInt(5, itemVO.getItemAmount());
			pstmt.setInt(6, itemVO.getItemStatus());
			pstmt.setDate(7, itemVO.getItemDate());
			pstmt.setDate(8, itemVO.getItemEnddate());
			pstmt.executeUpdate();

			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}
		return null;
	}

	@Override
	public void update(ItemVO itemVO) {
		try (Connection con = ds.getConnection(); PreparedStatement pstmt = con.prepareStatement(UPDATE)) {
			pstmt.setInt(1, itemVO.getItemtId());
			pstmt.setString(2, itemVO.getItemName());
			pstmt.setString(3, itemVO.getItemContent());
			pstmt.setInt(4, itemVO.getItemPrice());
			pstmt.setInt(5, itemVO.getItemAmount());
			pstmt.setInt(6, itemVO.getItemStatus());
			pstmt.setDate(7, itemVO.getItemDate());
			pstmt.setDate(8, itemVO.getItemEnddate());
			pstmt.setInt(9, itemVO.getItemId());

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
	public ItemVO findByPrimaryKey(Integer itemId) {
		ItemVO itemVO = null;

		try (Connection con = ds.getConnection();
				PreparedStatement pstmt = con.prepareStatement(GET_ONE_STMT, ResultSet.TYPE_SCROLL_INSENSITIVE)) {
			pstmt.setInt(1, itemId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					itemVO = new ItemVO();
					itemVO.setItemId(rs.getInt("item_id"));
					itemVO.setItemtId(rs.getInt("itemt_id"));

					itemVO.setItemName(rs.getString("item_name"));
					itemVO.setItemContent(rs.getString("item_content"));
					itemVO.setItemPrice(rs.getInt("item_price"));

					itemVO.setItemAmount(rs.getInt("item_amount"));
					itemVO.setItemStatus(rs.getInt("item_status"));

					itemVO.setItemDate(rs.getDate("item_date"));
					itemVO.setItemEnddate(rs.getDate("item_enddate"));
				}
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}

		return itemVO;
	}

	@Override
	public List<ItemVO> getAll() {
		List<ItemVO> list = new ArrayList<ItemVO>();
		ItemVO itemVO = null;

		try (Connection con = ds.getConnection();
				PreparedStatement pstmt = con.prepareStatement(GET_ALL_STMT, ResultSet.TYPE_SCROLL_INSENSITIVE)) {

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				itemVO = new ItemVO();
				itemVO.setItemId(rs.getInt("item_id"));
				itemVO.setItemtId(rs.getInt("itemt_id"));

				itemVO.setItemName(rs.getString("item_name"));
				itemVO.setItemContent(rs.getString("item_content"));
				itemVO.setItemPrice(rs.getInt("item_price"));

				itemVO.setItemAmount(rs.getInt("item_amount"));
				itemVO.setItemStatus(rs.getInt("item_status"));

				itemVO.setItemDate(rs.getDate("item_date"));
				itemVO.setItemEnddate(rs.getDate("item_enddate"));
//				itemVO.setPhoto(rs.getBytes("ip_photo"));
				list.add(itemVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}
		return list;
	}

	@Override
	public JSONArray getAllJS() {
		JSONArray list = new JSONArray();

		try (Connection con = ds.getConnection();
		PreparedStatement pstmt = con.prepareStatement(GET_ALL_STMT_JS, ResultSet.TYPE_SCROLL_INSENSITIVE)) {
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				   JSONObject item = new JSONObject();
				   Integer id = rs.getInt("item_id");
				   ItemPhotosDAO dao =new ItemPhotosDAO();
				   String photo = dao.getPhotoJson(id);

					item.put("item_id", id);
					item.put("itemt_id", rs.getInt("ITEMT_ID"));
					item.put("item_name",rs.getString("ITEM_NAME"));
					item.put("item_price",rs.getInt("ITEM_PRICE"));
					item.put("item_photo",photo);
				   list.put(item);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
