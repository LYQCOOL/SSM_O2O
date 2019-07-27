package com.swpu.o2o.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.swpu.o2o.entity.LocalAuth;

public interface LocalAuthDao {
	/**
	 * 通过账号和密码查询用户，登录时使用
	 * 
	 * @param username
	 * @param password
	 * @return LocalAuth
	 */
	LocalAuth queryLocalByUserNameAndPwd(@Param("username") String username, @Param("password") String password);

	/**
	 * 通过用户Id查询用户
	 * 
	 * @param userId
	 * @return LocalAuth
	 */
	LocalAuth queryLocalByUserId(@Param("userId") long userId);

	/**
	 * 添加平台账号
	 * 
	 * @param localAuth
	 * @return
	 */
	int insertLocalAuth(LocalAuth localAuth);
	/**
	 * 通过userId，username，password更改密码
	 * @param userId
	 * @param userName
	 * @param password
	 * @return
	 */
	int updateLocalAuth(@Param("userId") long userId, @Param("userName") String userName,
			@Param("password") String password,@Param("newPassword") String newPassword,@Param("lastEditTime")Date lastEditTime);
	
}
