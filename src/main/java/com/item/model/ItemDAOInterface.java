package com.item.model;

import java.util.List;

import org.json.JSONArray;


public interface ItemDAOInterface {
     public Integer insert(ItemVO itemVO);
     public void update(ItemVO itemVO);
     public void delete(Integer itemId);
     public ItemVO findByPrimaryKey(Integer itemId);
     public List<ItemVO> getAll();
     public JSONArray getAllJS();
}
