package com.itemType.model;

import static common.Common.*;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemTypeJDBCDAO implements ItemTypeInterface {

    private static final String INSERT_STMT =
            "insert into ITEM_TYPE (ITEMT_NAME) value (?)";
    private static final String UPDATE =
            "update ITEM_TYPE set ITEMT_NAME= ? where ITEMT_ID= ?";
    private static final String DELETE =
            "delete from ITEM_TYPE where ITEMT_ID = ?";
    private static final String GET_ONE_STMT =
            "select ITEMT_ID, ITEMT_NAME from ITEM_TYPE where ITEMT_ID = ?";
    private static final String GET_ALL_STMT =
            "select ITEMT_ID, ITEMT_NAME from ITEM_TYPE order by ITEMT_ID";

    @Override
    public void insert(ItemTypeVO item_typeVO) {

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(INSERT_STMT);

            pstmt.setString(1, item_typeVO.getItemtName());

            pstmt.executeUpdate();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database DRIVER. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
            // Clean up JDBC resources
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }

    @Override
    public void update(ItemTypeVO item_typeVO) {

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(UPDATE);

            pstmt.setString(1, item_typeVO.getItemtName());

            pstmt.executeUpdate();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database DRIVER. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
            // Clean up JDBC resources
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }

    }

    @Override
    public void delete(Integer itemt_id) {

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(DELETE);

            pstmt.setInt(1, itemt_id);

            pstmt.executeUpdate();

            // Handle any driver errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
            // Clean up JDBC resources
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }

    @Override
    public ItemTypeVO findByPrimaryKey(Integer item_id) {

        ItemTypeVO item_typeVO = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(GET_ONE_STMT);

            pstmt.setInt(1, item_id);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                item_typeVO = new ItemTypeVO();
                item_typeVO.setItemtId(rs.getInt("itemt_id"));
                item_typeVO.setItemtName(rs.getString("itemt_name"));
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database DRIVER. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
            // Clean up JDBC resources
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        return item_typeVO;
    }

    @Override
    public List<ItemTypeVO> getAll() {

        List<ItemTypeVO> list = new ArrayList<ItemTypeVO>();
        ItemTypeVO item_typeVO = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                item_typeVO = new ItemTypeVO();
                item_typeVO.setItemtId(rs.getInt("itemt_id"));
                item_typeVO.setItemtName(rs.getString("itemt_name"));
                list.add(item_typeVO);
            }

            // Handle any driver errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
            // Clean up JDBC resources
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        return list;
    }
}
