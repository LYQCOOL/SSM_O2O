package com.swpu.o2o.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.util.SystemPropertyUtils;

import com.swpu.o2o.dto.ImageHolder;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;;

public class ImageUtil {
	/**
	 * 处理用户传递过来的文件（文件流）
	 * 
	 * @param thumbnail：用户传过来的文件，targetAddr保存路径
	 */
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	//格式化时间
	private static final SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyyMMddHHmmss"); 
	//随机数
	private static final Random r=new Random();
	public static String generateThumbnail(ImageHolder thumbnail,String targetAddr){
		//图片可能重名，这里获取文件随机名
		String realFileName=getRandomFileName();
		//获取文件扩展名
		String extension=getExtension(thumbnail.getImageName());
		//路径可能不存在
		makDirPath(targetAddr);
		String relativeAddr=targetAddr+realFileName+extension;
		File dest=new File(PathUtil.getImageBasePath()+relativeAddr);
		//创建缩略图
		try{
			Thumbnails.of(thumbnail.getImage()).size(200,200).watermark(Positions.BOTTOM_RIGHT,
					ImageIO.read(new File(basePath ,"water.png")), 0.25f).outputQuality(0.8f).toFile(dest);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return relativeAddr;

	}
	/**
	 * 创建目标路径所涉及到的目录。即/home/LYQ/xiangze/xxx.jpg
	 * 那么home，LYQ,xiangze这三个目录都会自动创建
	 * @param targetAddr
	 */
	private static void makDirPath(String targetAddr) {
		String realFilePath=PathUtil.getImageBasePath()+targetAddr;
		File dirPath=new File(realFilePath); 
		if (!dirPath.exists()){
			//递归创建目录
			dirPath.mkdirs();
		}
	}
	/**
	 * 获取文件扩展名
	 * @return
	 */
	private static String getExtension(String fileName) {
		// 获取文件扩展名
//		String originalFileName=cFile.getName();
		//截取字符串，从最后一个点到最后
		return fileName.substring(fileName.lastIndexOf("."));
	}
	/**
	 * 随机生成图片名，当前年月日小时分钟秒数+五位随机数
	 * @return
	 */
	public static String getRandomFileName() {
		//获取随机5位数
		int rannum=r.nextInt(89999)+10000;
		String nowTimestr=sDateFormat.format(new Date());
		return nowTimestr+rannum;
	}

	public static void main(String[] args) {
		try {
			Thumbnails.of(new File("D:\\default.jpg")).size(200, 200)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath,"water.png")), 0.25f)
					.outputQuality(0.25f).toFile("D:\\3.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 如果storePath是文件路径就删除文件，如果是目录路径就删除目录下所有文件
	 * @param storePath
	 */
	public static void deleteFileOrPath(String storePath){
		File fileOrPath=new File(PathUtil.getImageBasePath()+storePath);
		if(fileOrPath.exists()){
			//如果是目录
			if(fileOrPath.isDirectory()){
				File files[]=fileOrPath.listFiles();
				for(int i=0;i<files.length;i++){
					files[i].delete();
				}
			}
			//删除文件或mu'lu
			fileOrPath.delete();
		}
		
	}
	/**
	 * 处理商品详情图，透明图，大小不一样
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateNormalImg(ImageHolder thumbnail,String targetAddr){
		//图片可能重名，这里获取文件随机名
		String realFileName=getRandomFileName();
		//获取文件扩展名
		String extension=getExtension(thumbnail.getImageName());
		//路径可能不存在
		makDirPath(targetAddr);
		String relativeAddr=targetAddr+realFileName+extension;
		File dest=new File(PathUtil.getImageBasePath()+relativeAddr);
		//创建缩略图
		try{
			Thumbnails.of(thumbnail.getImage()).size(337,640).watermark(Positions.BOTTOM_RIGHT,
					ImageIO.read(new File(basePath ,"water.png")), 0.25f).outputQuality(0.9f).toFile(dest);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return relativeAddr;

	}
}
