package com.commodityDetails.dao;

import commodityDetails.vo.CommodityDetailsVO;

import java.util.List;

public interface CommodityDetailsDAOInterface {

    public void insert(CommodityDetailsVO commodityDetailsVO);
    public void update(CommodityDetailsVO commodityDetailsVO);
    public void delete(Integer orderId);
    public CommodityDetailsVO findByOrderID(Integer orderId);
    public List<CommodityDetailsVO> getOneDetail(Integer orderId);
    public List<CommodityDetailsVO> getAllDetail();

}
