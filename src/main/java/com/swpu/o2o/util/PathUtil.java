package com.swpu.o2o.util;

public class PathUtil {
	private static String seperator=System.getProperty("file.separator");
	/*
	 * @return 返回项目图片的根路径
	 */
	public static String getImageBasePath(){
		String os=System.getProperty("os.name");
		String basePath="";
		if (os.toLowerCase().startsWith("win")){
			basePath="D:/Project/image/";
		}
		else{
			basePath="/home/LYQ/image";
		}
		basePath=basePath.replace("/", seperator);
		return basePath;
	}
	/**
	 * 
	 * @param shopId
	 * @return 返回项目图片的子路径
	 */
	public static String getShopImagePath(long shopId){
		String imagePath="/upload/item/shop/"+shopId+"/";
		return imagePath.replace("/",seperator);
	}
	public static void main(String[] args){
		System.out.println(getImageBasePath());
	}
}
