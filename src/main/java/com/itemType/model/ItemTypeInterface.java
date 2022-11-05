package com.itemType.model;

import java.util.List;

public interface ItemTypeInterface {

    public void insert(ItemTypeVO item_typeVO);
    public void update(ItemTypeVO item_typeVO);
    public void delete(Integer itemt_id);
    public ItemTypeVO findByPrimaryKey(Integer itemt_id);
    public List<ItemTypeVO> getAll();
    //萬用複合查詢(傳入參數型態Map)(回傳 List)
//        public List<EmpVO> getAll(Map<String, String[]> map);

}
