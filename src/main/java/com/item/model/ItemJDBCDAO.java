package com.item.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

public class ItemJDBCDAO implements ItemDAOInterface {
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/BA_REI?serverTimezone=Asia/Taipei";
    String userid = "root";
    String passwd = "P@ssw0rd";

    private static final String INSERT_STMT = "INSERT INTO item (itemt_id,item_name,item_content,item_price,item_amount,item_status,item_date,item_enddate) VALUES (?, ?, ?, ?, ?, ?,?,?)";
    private static final String GET_ALL_STMT = "SELECT item_id,itemt_id,item_name,item_content,item_price,item_amount,item_status,item_date,item_enddate FROM item order by item_id";
    private static final String GET_ONE_STMT = "SELECT item_id,itemt_id,item_name,item_content,item_price,item_amount,item_status,item_date,item_enddate FROM item where item_id = ?";
    private static final String DELETE = "DELETE FROM item where item_id = ?";
    private static final String UPDATE = "UPDATE item set itemt_id=?,item_name=?,item_content=?,item_price=?,item_amount=?,item_status=?,item_date=?,item_enddate=? where item_id = ?";

    @Override
    public Integer insert(ItemVO itemVO) {

        try (Connection con = DriverManager.getConnection(url, userid, passwd);
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
        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement pstmt = con.prepareStatement(UPDATE)) {
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
        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement pstmt = con.prepareStatement(DELETE)) {
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

        try (Connection con = DriverManager.getConnection(url, userid, passwd);
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

        try (Connection con = DriverManager.getConnection(url, userid, passwd);
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
                list.add(itemVO);
            }

        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
            // Clean up JDBC resources
        }
        return list;
    }

    public static void main(String[] args) {
        ItemJDBCDAO dao = new ItemJDBCDAO();

        // 新增
//		ItemVO itemVO1=new ItemVO();
//		itemVO1.setItemt_id(1);
//		itemVO1.setItem_name("狗飼料A");
//		itemVO1.setItem_content("營養呦");
//		itemVO1.setItem_price(200);
//		itemVO1.setItem_amount(2);
//		itemVO1.setItem_status(0);
//		itemVO1.setItem_date(Date.valueOf("2022-12-12"));
//		itemVO1.setItem_enddate(Date.valueOf("2023-12-12"));
//
//		dao.insert(itemVO1);
//
        // 修改
//		ItemVO itemVO2 = new ItemVO();
//		itemVO2.setItemt_id(1);
//		itemVO2.setItem_name("狗飼料");
//		itemVO2.setItem_content("營養好吸收");
//		itemVO2.setItem_price(10000);
//		itemVO2.setItem_amount(2);
//		itemVO2.setItem_status(0);
//		itemVO2.setItem_date(Date.valueOf("2022-12-12"));
//		itemVO2.setItem_enddate(Date.valueOf("2023-12-12"));
//		itemVO2.setItem_id(3);
//
//		dao.update(itemVO2);

        // 刪除
//		dao.delete(3);

        // 查詢
//		ItemVO item=dao.findByPrimaryKey(2);
//		System.out.print(item.getItem_id() + ",");
//		System.out.print(item.getItemt_id() + ",");
//		System.out.print(item.getItem_name() + ",");
//
//		System.out.print(item.getItem_content() + ",");
//		System.out.print(item.getItem_price() + ",");
//		System.out.print(item.getItem_amount() + ",");
//		System.out.print(item.getItem_status() + ",");
//		System.out.print(item.getItem_date()+ ",");
//		System.out.print(item.getItem_enddate());
//		System.out.println("---------------------");

        // 查詢全部
        List<ItemVO> list = dao.getAll();
        for (ItemVO item : list) {
            System.out.print(item.getItemId() + ",");
            System.out.print(item.getItemtId() + ",");
            System.out.print(item.getItemName() + ",");

            System.out.print(item.getItemContent() + ",");
            System.out.print(item.getItemPrice() + ",");
            System.out.print(item.getItemAmount() + ",");
            System.out.print(item.getItemStatus() + ",");
            System.out.print(item.getItemDate() + ",");
            System.out.print(item.getItemEnddate());
            System.out.println("---------------------");
        }
    }

    @Override
    public JSONArray getAllJS() {
        // TODO Auto-generated method stub
        return null;
    }
}
