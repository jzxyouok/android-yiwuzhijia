package com.android.house.protocol;

import android.R.string;

/**
 * 接口地址
 * @author shenlw
 *
 */
public class ProtocolConst {
    
//	public static String WEB_DIR="http://app.ewuzhijia.com/yiwuzhijia/web/app_dev.php";
	public static String WEB_DIR="http://121.41.13.145/yiwuzhijia/web/app_dev.php/";
	
	//基础部分
	public static String REGISTER="m/base/validateCode"; //注册
	public static String VALIDATECODE="m/base/getValidateCode";     //验证验证码，密码
	public static String LOGIN="m/user/login";                   //登录
	public static String PICUPLOAD="ajaxfile/picUpload?picUpLoad="; //图片上传
	public static String FORGETPWD="m/base/forgetPwd";       //忘记密码
	public static String RESETPWD="m/base/resetPwd";             //修改密码
	public static String LOGOUT="m/base/logout";                 //注销
	public static String GETMASTERVALUE="m/base/getMasterValue";  //获取设置值
	public static String SUGGESTION="m/base/addSuggestion";       //添加意见与建议
	public static String GET_VALIDATECODE="m/base/getValidateCode"; //获取注册短信验证码
	public static String GET_USERINFO="m/base/getUserInfo";         //获取个人信息
	public static String UPDATE_USERINFO="m/base/updateUserInfo";   //更改个人信息
	public static String GET_MASTER_VALUE="m/base/getMasterValue"; //获取公司信息
	
	
	//用户
	public static String GET_AGNETINFO="m/user/getAgentInfo";  //获取经纪人信息
	public static String GET_MYAGENTINFO="m/user/getMyAgentInfo"; //获取我的经纪人信息
	public static String VIEWEDHOUSELIST="m/user/getViewedHouseList"; //已经查看的楼盘列表
	public static String VIEWDHOUEINFO="m/user/getViewedHouseInfo";   //获取已经看的楼盘详情
	public static String SEARCH_HOUSE_AGENT="m/user/searchHouseAndAgentList"; //搜索楼盘和经纪人
	public static String SEARCH_HOUSE="m/user/searchHouseList";       //筛选楼盘
	public static String GET_HOUSEPIC_LIST="m/user/getHousePicList";  //获取楼盘图片信息
	public static String GET_HOUSEAGENT_INFO="m/user/getHouseAgentInfo"; //获取楼盘推荐经纪人信息
	public static String RESERVE_AGENT="m/user/reserveAgent";          //预约经纪人
	public static String COMMENT_AGNET="m/user/addAgentComment";    //评论经纪人
	public static String GET_AGENTCOMMENT="m/user/getAgentCommentList";//获取经纪人评论列表
	public static String CANCEL_AGENT="m/user/cancelAgent";           //解约经济人
	
	//lbs
	public static String GET_CITY_LIST="m/lbs/getCityList";          //获取城市列表
	public static String GET_AREA_LIST="m/lbs/getAreaList";          //获取区域列表
	public static String GET_CURRENT_CITY="m/lbs/getCurrentCity";    //获取当前城市
	public static String UPDATE_USERLBS="m/lbs/updateUserLbs";       //更新当前位置
	public static String GET_HOUSElIST_BYCITY="m/lbs/getHouseListByCity"; //根据城市ID获取楼盘列表
	public static String GET_AGENTLIST_BYGPS="m/user/getAgentListByGPS";  //根据GPS选择附近经纪人
	public static String GET_FREECARLIST_BYGPS="m/user/getFreeCarListByGps"; //根据GPS选择附近的免费专车
	
	//账户操作
	public static String GETHISTORY="m/account/getHistory";              //获取提现收入列表
	public static String ASK_WITHDRAWAL="m/account/askWithdrawCash";     //提现请求
	public static String BIND_ACCOUNT="m/account/bindAccount";           //绑定账户
	
	//公共
	public static String GET_COMPANY_INFO="m/base/getMasterValue ";//获取公司信息
	
	public static String GET_AGREEMENT_INFO = "m/user/getAgreementInfo";//获取合购房合同
	
}
