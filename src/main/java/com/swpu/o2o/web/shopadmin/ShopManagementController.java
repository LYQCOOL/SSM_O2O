package com.swpu.o2o.web.shopadmin;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swpu.o2o.dto.ImageHolder;
import com.swpu.o2o.dto.ShopExecution;
import com.swpu.o2o.entity.Area;
import com.swpu.o2o.entity.PersonInfo;
import com.swpu.o2o.entity.Shop;
import com.swpu.o2o.entity.ShopCategory;
import com.swpu.o2o.enums.ShopStateEnum;
import com.swpu.o2o.exceptions.ShopOperationException;
import com.swpu.o2o.service.AreaService;
import com.swpu.o2o.service.ShopCategoryService;
import com.swpu.o2o.service.ShopService;
import com.swpu.o2o.util.CodeUtil;
import com.swpu.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;

	// 用于重定向（为登录）
	@RequestMapping(value = "/getshopmanagementinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if (shopId <= 0) {
			Object currentShopObj = request.getSession().getAttribute("currentShop");
			if (currentShopObj == null) {
				modelMap.put("redirect", true);
				modelMap.put("url", "/o2o/shopadmin/shoplist");
			} else {
				Shop currentShop = (Shop) currentShopObj;
				modelMap.put("redirect", false);
				modelMap.put("shopId", currentShop.getShopId());
			}
		} else {
			Shop currentShop = new Shop();
			currentShop.setShopId(shopId);
			request.getSession().setAttribute("currentShop", currentShop);
			modelMap.put("redirect", false);
		}
		return modelMap;

	}

	@RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShoplist(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		PersonInfo user = new PersonInfo();
		user = (PersonInfo) request.getSession().getAttribute("user");
		try {
			Shop shopCondition = new Shop();
			shopCondition.setOwner(user);
			ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
			//列出店铺创建成功后，将店铺放入session中作为权限验证依据，该店铺只能操作自己的店铺
			request.getSession().setAttribute("shopList", se.getShoplist());
			modelMap.put("success", true);
			modelMap.put("shopList", se.getShoplist());
			modelMap.put("user", user);

		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}

	/**
	 * 根据shopId查看shop
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
	// 返回Json格式
	@ResponseBody
	private Map<String, Object> getShopById(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if (shopId > -1) {
			try {
				Shop shop = shopService.getByShopId(shopId);
				List<Area> arealist = areaService.getAreaList();
				modelMap.put("shop", shop);
				modelMap.put("areaList", arealist);
				modelMap.put("success", true);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopInitInfo() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			shopCategoryList = shopCategoryService.getShopCategoeyList(new ShopCategory());
			areaList = areaService.getAreaList();
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);

		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;

	}

	// 上传店铺消息
	@RequestMapping(value = "/registershop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registershop(HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 验证验证码
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;

		}
		// 1.接收并转换为相应的参数，包括店铺信息及图片信息
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		// jackson使用
		ObjectMapper mappper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mappper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		// 获取相关文件
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			// 是否有文件流，强制转换
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		// 2.注册店铺
		if (shop != null && shopImg != null) {
			// PersonInfo owner = new PersonInfo();
			// Session Todo
			PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
			// owner.setUserId(1L);
			shop.setOwner(owner);
			// File shopImgFile = new File(PathUtil.getImageBasePath() +
			// ImageUtil.getRandomFileName());
			// try {
			// shopImgFile.createNewFile();
			// } catch (IOException e) {
			// modelMap.put("success", false);
			// modelMap.put("errMsg", e.getMessage());
			// return modelMap;
			// }
			// try {
			// inputStreamToFile(shopImg.getInputStream(), shopImgFile);
			// } catch (IOException e) {
			// modelMap.put("success", false);
			// modelMap.put("errMsg", e.getMessage());
			// }
			ShopExecution se;
			try {
				// 这里为什么不直接传shopImg：因为CommonsMultipartFile这个类型在ut（Junit）测试时很难构造
				// 2.重构
				ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
				se = shopService.addShop(shop, imageHolder);
				if (se.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
					modelMap.put("Msg", "创建成功");
					// 该用户可以操作的列表,设置到session中
					// 设置unchecked，没有警告
					@SuppressWarnings("unchecked")
					List<Shop> shoplist = (List<Shop>) request.getSession().getAttribute("shoplist");
					if (shoplist == null || shoplist.size() == 0) {
						shoplist = new ArrayList<Shop>();

					}
					shoplist.add(se.getShop());
					request.getSession().setAttribute("shoplist", shoplist);

				}

				else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}

			} catch (ShopOperationException | IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap;

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;
		}
		// 3.返回结果

	}
	/*
	 * // 将文件流转换为文件(会产生很多垃圾文件，性能低) private static void
	 * inputStreamToFile(InputStream ins, File file) { FileOutputStream os =
	 * null; try { os = new FileOutputStream(file); int bytesRead = 0; byte[]
	 * buffer = new byte[1024]; while ((bytesRead = ins.read(buffer)) != -1) {
	 * os.write(buffer, 0, bytesRead);
	 * 
	 * } } catch (Exception e) { throw new
	 * RuntimeException("调用inputStreamToFile产生异常:" + e.getMessage()); } finally
	 * { try { if (os != null) { os.close(); } if (ins != null) { ins.close(); }
	 * } catch (IOException e) { throw new RuntimeException("关闭输入输出流异常:" +
	 * e.getMessage()); } } }
	 */

	/**
	 * 修改店铺信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyshop(HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 验证验证码
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;

		}
		// 1.接收并转换为相应的参数，包括店铺信息及图片信息
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		// jackson使用
		ObjectMapper mappper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mappper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		// 获取相关文件
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			// 是否有文件流，强制转换
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		}
		// 2.修改店铺
		if (shop != null && shop.getShopId() != null) {
			PersonInfo owner = new PersonInfo();
			owner.setUserId(1L);
			shop.setOwner(owner);
			ShopExecution se;
			try {
				// 这里为什么不直接传shopImg：因为CommonsMultipartFile这个类型在ut（Junit）测试时很难构造
				if (shopImg == null) {
					se = shopService.modifyShop(shop, null);

				} else {
					ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
					se = shopService.modifyShop(shop, imageHolder);
				}
				if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
					modelMap.put("Msg", "创建成功");
				}

				else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}

			} catch (ShopOperationException | IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap;

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺ID");
			return modelMap;
		}
		// 3.返回结果

	}

}
