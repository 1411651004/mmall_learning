package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

public interface IUserService {

    /**
     * 登陆接口
     * @param username
     * @param password
     * @return
     */
    ServerResponse<User> login(String username, String password);

    /**
     * 注册接口
     * @param user
     * @return
     */
    ServerResponse<String> register(User user);

    /**
     * 校验接口
     * 注册时你输入完用户名或者邮箱，点击下一个text匡时检查用户名或者邮箱是否已存在
     * @param str
     * @param type
     * @return
     */
    ServerResponse<String> checkValid(String str,String type);

    /**
     * 找回密码的密保接口
     * @param username
     * @return
     */
    ServerResponse<String> selectQuestion(String username);

    /**
     * 使用本地缓存检查问题答案的接口,并返回给前台一个token
     * @param username
     * @param question
     * @param answer
     * @return
     */
    ServerResponse<String> checkAnswer(String username,String question,String answer);

    /**
     *重置密码接口，通过比对前后台的token是否一致来修改密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);

    /**
     * 修改密码接口，根据旧密码修改密码
     * @param passwordOld
     * @param passwordNew
     * @param user
     * @return
     */
    ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user);

    /**
     * 跟新个人信息接口，若成功把更新的信息存到session中
     * @param user
     * @return
     */
    ServerResponse<User> update_information(User user);

    /**
     * 获取个人信息接口，将密码置为空
     * @param userId
     * @return
     */
    ServerResponse<User> get_information(Integer userId);

    /**
     * 校验是否是管理员接口
     * @param user
     * @return
     */
    ServerResponse checkAdminRole(User user);
}
