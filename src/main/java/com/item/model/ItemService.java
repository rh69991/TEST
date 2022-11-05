package com.item.model;

import java.sql.Date;
import java.util.List;

import org.json.JSONArray;

import com.itemPhotos.model.ItemPhotosDAO;
import com.itemPhotos.model.ItemPhotosVO;
import com.itemPhotos.model.ItemPhotosInterface;

public class ItemService {
    private ItemDAOInterface itemDao;
    private ItemPhotosInterface itemPhotoDao;

    public ItemService() {
        itemDao = new ItemDAO();
        itemPhotoDao = new ItemPhotosDAO();
    }

    public List<ItemVO> getAll() {
        return itemDao.getAll();
    }

    public ItemVO addItem(String itemName, String itemContent, Integer itemPrice, Integer itemAmount,
                          Date startDate, Date enddate, Integer itemStatus, Integer itemtId, List<byte[]> item_photo) {
        ItemVO itemVO = new ItemVO();
        itemVO.setItemName(itemName);
        itemVO.setItemContent(itemContent);
        itemVO.setItemPrice(itemPrice);
        itemVO.setItemAmount(itemAmount);
        itemVO.setItemDate(startDate);
        itemVO.setItemEnddate(enddate);
        itemVO.setItemStatus(itemStatus);
        itemVO.setItemtId(itemtId);

        Integer item_id = itemDao.insert(itemVO);

        for (byte[] photo : item_photo) {

            ItemPhotosVO itemPhotosVO = new ItemPhotosVO();
            itemPhotosVO.setItemId(item_id);
            itemPhotosVO.setIpPhoto(photo);
            itemPhotoDao.insert(itemPhotosVO);
        }
        return itemVO;
    }

    public void update(String itemName, String itemContent, Integer itemPrice, Integer itemAmount, Date startDate,
                       Date endDate, Integer itemStatus, Integer itemtType, List<byte[]> itemPhoto, Integer itemId) {
        ItemVO itemVO = new ItemVO();
        itemVO.setItemName(itemName);
        itemVO.setItemContent(itemContent);
        itemVO.setItemPrice(itemPrice);
        itemVO.setItemAmount(itemAmount);
        itemVO.setItemDate(startDate);
        itemVO.setItemEnddate(endDate);
        itemVO.setItemStatus(itemStatus);
        itemVO.setItemtId(itemtType);
        itemVO.setItemId(itemId);

        itemDao.update(itemVO);

        if (itemPhoto.size()!=0){
            for (byte[] photo : itemPhoto) {

                ItemPhotosVO itemPhotosVO = new ItemPhotosVO();
                itemPhotosVO.setItemId(itemId);
                itemPhotosVO.setIpPhoto(photo);
                itemPhotoDao.insert(itemPhotosVO);
            }
        }

    }

    public com.item.model.ItemVO getItem(Integer itemId) {

        return itemDao.findByPrimaryKey(itemId);
    }

    public void deleteItem(Integer itemId) {
        itemPhotoDao.delete(itemId);
        itemDao.delete(itemId);

    }

    public JSONArray getAllJs() {
        return itemDao.getAllJS();
    }

}
