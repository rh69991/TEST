package commodityDetails.service.impl;

import commodityDetails.dao.impl.CommodityDetailsJNDIDAO;
import commodityDetails.vo.CommodityDetailsVO;

import java.util.List;

public class CommodityDetailsService {

    private CommodityDetailsJNDIDAO dao;

    public CommodityDetailsService() {
        dao = new CommodityDetailsJNDIDAO();
    }

    public CommodityDetailsVO newOrderDetail(Integer orderId, Integer itemId, String itemName, Integer cdAmount, Double itemPrice) {

        CommodityDetailsVO commodityDetailsVO = new CommodityDetailsVO();

        commodityDetailsVO.setOrderId(orderId);
        commodityDetailsVO.setItemId(itemId);
        commodityDetailsVO.setItemName(itemName);
        commodityDetailsVO.setCdAmount(cdAmount);
        commodityDetailsVO.setItemPrice(itemPrice);
        dao.insert(commodityDetailsVO);

        return commodityDetailsVO;

    }

    public CommodityDetailsVO reviseDetail(Integer orderId, Integer itemId, String itemName, Integer cdAmount, Double itemPrice) {

        CommodityDetailsVO commodityDetailsVO = new CommodityDetailsVO();


        commodityDetailsVO.setItemId(itemId);
        commodityDetailsVO.setItemName(itemName);
        commodityDetailsVO.setCdAmount(cdAmount);
        commodityDetailsVO.setItemPrice(itemPrice);
        commodityDetailsVO.setOrderId(orderId);
        dao.update(commodityDetailsVO);

        return commodityDetailsVO;
    }

    public void deleteCommodityDetail(Integer orderId){
        dao.delete(orderId);
    }

    public List<CommodityDetailsVO> getDetail(Integer orderId){
        return dao.getOneDetail(orderId);
    }

    public List<CommodityDetailsVO> getAll(){
            return dao.getAllDetail();
}

}
