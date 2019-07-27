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

import com.swpu.o2o.dto.ProductExecution;
import com.swpu.o2o.entity.Product;
import com.swpu.o2o.entity.ProductCategory;
import com.swpu.o2o.entity.Shop;
import com.swpu.o2o.service.ProductCategoryService;
import com.swpu.o2o.service.ProductService;
import com.swpu.o2o.service.ShopService;
import com.swpu.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ShopDetailController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@RequestMapping(value="/listshopdetailpageinfo",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> listShopDetailPageInfo(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		//获取前台传过来的shopId
		long shopId=HttpServletRequestUtil.getLong(request, "shopId");
		Shop shop=null;
		List<ProductCategory> productCategoryList=new ArrayList<ProductCategory>();
		if(shopId!=-1){
			//获取店铺Id为shopId的店铺信息
			shop=shopService.getByShopId(shopId);
			//获取店铺下面的商品类别列表
			productCategoryList=productCategoryService.getproductCategoryList(shopId);
			modelMap.put("shop", shop);
			modelMap.put("productCategoryList", productCategoryList);
			modelMap.put("success", true);
		}
		else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
	}
	@RequestMapping(value="/listproductsbyshop",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> listProductsByShop(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		//获取页码
		int pageIndex=HttpServletRequestUtil.getInt(request, "pageIndex");
		//获取每一页最大条数
		int pageSize=HttpServletRequestUtil.getInt(request, "pageSize");
		//获取店铺Id
		long shopId=HttpServletRequestUtil.getLong(request, "shopId");
		//空值判断
		if((pageIndex>-1)&&(pageSize>-1)&&(shopId>-1)){
			//获取商品类别
			long productCategoryId=HttpServletRequestUtil.getLong(request, "productCategoryId");
			//尝试获取模糊查找的模糊名
			String productName=HttpServletRequestUtil.getString(request, "productName");
			//组合查询条件
			Product productCondition=compactProductCondition4Search(productCategoryId,productName);
			//按照传入的查询条件以及分页信息返回商品列表以及总数
			ProductExecution pe=productService.getProductList(productCondition, pageIndex, pageSize);
			modelMap.put("productList", pe.getProductList());
			modelMap.put("count", pe.getCount());
			modelMap.put("success", true);
		}
		else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageIndex or pageSize or shopId");
		}
		return modelMap;
	}
	/**
	 * 组合商品查询对象，封装到productCondition
	 * @param productCategoryId
	 * @param productName
	 * @return
	 */
	private Product compactProductCondition4Search(long productCategoryId, String productName) {
		// TODO Auto-generated method stub
		Product productCondition=new Product();
		if(productCategoryId!=-1){
			ProductCategory productCategory=new ProductCategory();
			productCategory.setProductCategoryId(productCategoryId);
			productCondition.setProductCategory(productCategory);	
		}
		if(productName!=null){
			productCondition.setProductName(productName);
		}
		//选出允许的状态（1）
		productCondition.setEnableStatus(1);
		return productCondition;
	}
}
