package com.swpu.o2o.web.frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swpu.o2o.dto.ShopExecution;
import com.swpu.o2o.entity.Area;
import com.swpu.o2o.entity.HeadLine;
import com.swpu.o2o.entity.Shop;
import com.swpu.o2o.entity.ShopCategory;
import com.swpu.o2o.service.AreaService;
import com.swpu.o2o.service.HeadLineService;
import com.swpu.o2o.service.ShopCategoryService;
import com.swpu.o2o.service.ShopService;
import com.swpu.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class MainPageController {
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private HeadLineService headLineService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private ShopService shopService;

	/**
	 * 初始化前端展示系统的主页信息，包括获取一级店铺类别列表及头条列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listmainpageinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listMainPageInfo() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		try {
			// 获取一级店铺类别列表（即parent_id为空）
			shopCategoryList = shopCategoryService.getShopCategoeyList(null);
			modelMap.put("shopCategoryList", shopCategoryList);

		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		List<HeadLine> headLineList = new ArrayList<HeadLine>();
		try {
			// 获取状态为可用的（1）头条列表
			HeadLine headLineCondition = new HeadLine();
			headLineCondition.setEnableStatus(1);
			headLineList = headLineService.getHeadLineList(headLineCondition);
			modelMap.put("headLineList", headLineList);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		modelMap.put("success", true);
		return modelMap;
	}

	@RequestMapping(value = "/listshopspageinfo", method = RequestMethod.GET)
	@ResponseBody
	/**
	 * 获取一级商铺类别或者父类为某个类别的二级店铺
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, Object> listShopsPageinfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 尝试从前端获取商店类别的父类parentId
		long parentId = HttpServletRequestUtil.getLong(request, "parentId");
		List<ShopCategory> shopCategoryList = null;
		if (parentId != -1) {
			// 如果parentId存在，则取出一级ShopCategory下的二级ShopCategory列表
			try {
				ShopCategory shopCategoryCondition = new ShopCategory();
				ShopCategory parentshopCategory = new ShopCategory();
				parentshopCategory.setShopCategoryId(parentId);
				shopCategoryCondition.setParent(parentshopCategory);
				shopCategoryList = shopCategoryService.getShopCategoeyList(shopCategoryCondition);

			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;

			}
		} else {
			try {
				// 如果parentId不存在，则取出所有一级ShopCategory（用户在首页选择的是全部商店列表）
				shopCategoryList = shopCategoryService.getShopCategoeyList(null);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;

			}

		}
		modelMap.put("shopCategoryList", shopCategoryList);
		List<Area> areaList = null;
		try {
			// 获取区域信息
			areaList = areaService.getAreaList();
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;

	}

	@RequestMapping(value = "/listshops", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShops(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		// 获取每一页的最大条数
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		// 非空判断
		if ((pageIndex > -1) && (pageSize > -1)) {
			// 试着获取一级类别Id
			long parentId = HttpServletRequestUtil.getLong(request, "parentId");
			// 试着获取特定二级类别Id
			long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
			// 试着获取区域Id
			int areaId = HttpServletRequestUtil.getInt(request, "areaId");
			// 试着获取模糊查询的名字
			String shopName = HttpServletRequestUtil.getString(request, "shopName");
			// 获取组合之后的查询条件
			Shop shopCondition = compactShop4ConditionSearch(parentId, shopCategoryId, areaId, shopName);
			// 根据查询条件和分页信息获取店铺列表，并返回总数
			ShopExecution se = shopService.getShopList(shopCondition, pageIndex, pageSize);
			modelMap.put("shopList", se.getShoplist());
			modelMap.put("count", se.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageIndex or pageSize");
		}
		return modelMap;

	}

	private Shop compactShop4ConditionSearch(long parentId, long shopCategoryId, int areaId, String shopName) {
		// 生成店铺组合搜索的方法
		Shop shopCondition = new Shop();
		if (parentId != -1L) {
			// 查询某个一级ShopCategory下的所有二级ShopCategory里面的店铺列表
			ShopCategory childCategory = new ShopCategory();
			ShopCategory parentCategory = new ShopCategory();
			parentCategory.setShopCategoryId(parentId);
			childCategory.setParent(parentCategory);
			shopCondition.setShopCategory(childCategory);

		}
		if (shopCategoryId != -1L) {
			// 查询某个二级ShopCategory下面的店铺列表
			ShopCategory shopCategory = new ShopCategory();
			shopCategory.setShopCategoryId(shopCategoryId);
			shopCondition.setShopCategory(shopCategory);
		}
		if (areaId != -1L) {
			// 查询位于某个区域下的店铺列表
			Area area = new Area();
			area.setAreaID(areaId);
			shopCondition.setArea(area);

		}
		if(shopName!=null){
			//查询名字包含shopName的店铺列表
			shopCondition.setShopName(shopName);
		}
		//前端返回的都要是状态为1（审核成功的店铺）
		shopCondition.setEnableStatus(1);
		return shopCondition;
	}
}
