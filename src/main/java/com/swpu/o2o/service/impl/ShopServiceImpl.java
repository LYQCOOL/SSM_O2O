package com.swpu.o2o.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swpu.o2o.dao.ShopDao;
import com.swpu.o2o.dto.ImageHolder;
import com.swpu.o2o.dto.ShopExecution;
import com.swpu.o2o.entity.Shop;
import com.swpu.o2o.enums.ShopStateEnum;
import com.swpu.o2o.exceptions.ShopOperationException;
import com.swpu.o2o.service.ShopService;
import com.swpu.o2o.util.ImageUtil;
import com.swpu.o2o.util.PageCalculator;
import com.swpu.o2o.util.PathUtil;

//实现店铺接口
@Service
public class ShopServiceImpl implements ShopService {
	@Autowired
	private ShopDao shopDao;

	@Override
	// 事务标签，支持事务
	@Transactional
	public ShopExecution addShop(Shop shop, ImageHolder thumbnail) {
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			//给店铺信息赋初始值
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			int effectedNum = shopDao.insertShop(shop);
			//添加店铺信息
			if (effectedNum <= 0) {
				// 为什么不用Exception，因为程序抛出ShopOperationException或者继承ShopOperationException事务才能得以终止
				// 并回滚，Exception不会
				throw new ShopOperationException("店铺创建失败");
			}
			else{
				if(thumbnail.getImage()!=null){
					//存储图片
					try{
						addShopImg(shop,thumbnail);
					}
					catch (Exception e){
						throw new ShopOperationException("addShopImg err:"+e.getMessage());
					}
					//更新店铺图片地址
					effectedNum=shopDao.updateShop(shop);
					if(effectedNum<=0){
						throw new ShopOperationException("更新图片地址失败");
 
					}
				}
			}
		} catch (Exception e) {
			throw new ShopOperationException("add Shop error:" + e.getMessage());

		}
		return new ShopExecution(ShopStateEnum.CHECK,shop);
		//return new ShopExecution(ShopStateEnum.NULL_SHOP);

	}

	private void addShopImg(Shop shop, ImageHolder thumbnail) {
		// TODO Auto-generated method stub
		String dest=PathUtil.getShopImagePath(shop.getShopId());
		String shopimgAddr=ImageUtil.generateThumbnail(thumbnail, dest);
		shop.setShopImg(shopimgAddr);
		
	}

	@Override
	public Shop getByShopId(long shopId) {
		return shopDao.queryByshopId(shopId);	
	}

	@Override
	public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException {
		if(shop==null || shop.getShopId()==null){
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		else{
			try{
			// 1.判断是否需要处理图片
			
			if(thumbnail.getImage()!=null&&thumbnail.getImageName()!=null&&!"".equals(thumbnail.getImageName())){
				Shop tempShop=shopDao.queryByshopId(shop.getShopId());
				if(tempShop.getShopImg()!=null){
					ImageUtil.deleteFileOrPath(tempShop.getShopImg());
				}
				addShopImg(shop, thumbnail);
			}
			//2.更新店铺信息
			shop.setLastEditTime(new Date());
			int effectedNum=shopDao.updateShop(shop);
			if(effectedNum<=0){
				return new ShopExecution(ShopStateEnum.INNER_ERROR);
			}
			else{
				shop=shopDao.queryByshopId(shop.getShopId());
				return new ShopExecution(ShopStateEnum.SUCCESS,shop);
			}
			}
			catch(Exception e){
				throw new ShopOperationException("modifyShop error:"+e.getMessage());
			}

		}
	}

	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		int rowIndex=PageCalculator.calculatorRowIndex(pageIndex, pageSize);
		List<Shop> shopList=shopDao.queryShopList(shopCondition, rowIndex, pageSize);
		int count=shopDao.queryShopCount(shopCondition);
		ShopExecution se=new ShopExecution();
		if(shopList!=null){
			se.setShoplist(shopList);
			se.setCount(count);
		}
		else{
			se.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		return se;
		
	}

}
