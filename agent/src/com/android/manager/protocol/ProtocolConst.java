package com.android.manager.protocol;

public class ProtocolConst { 
	public static String WEB_DIR="http://121.41.13.145/yiwuzhijia/web/app_dev.php/";
	public static String PIC_DIR="http://121.41.13.145/yiwuzhijia/web/";
	
	public static String GET_MY_CUSTOMER_LIST="m/agent/getMyCustomerList"; //经纪人端获取我的客户
	
	public static String GET_FAIL_RECORD_DETAIL="m/agent/getFailRecordDetail";//经纪人获取无效客户端
	
	public static String ADD_BUSINESS_RECORD="m/agent/addBusinessRecord";//经纪人新增陪同记录
	
	public static String GET_VALIDDATE_CODE="m/base/getValidateCode";//获取验证码
	public static String DO_RECEIVECUSTOMER="m/agent/receiveUser";                          //处理接待客户
	
	public static String REGIST_USER="m/base/validateCode";
	
	public static String ADD_REC_CUSTOMER="m/agent/addRecCustomer";     //推荐客户
	public static String GET_CLIENT_RECORD="m/agent/getBusinessRecordListByUser";//拿客户陪同记录
	public static String GET_RECORD_DETAIL="m/agent/getRecordDetail";    //获取陪同记录详情
	
	//账户操作
	public static String GETHISTORY="m/account/getHistory";              //获取提现收入列表
	public static String ASK_WITHDRAWAL="m/account/askWithdrawCash";     //提现请求
	public static String BIND_ACCOUNT="m/account/bindAccount";           //绑定账户
	public static String GET_MASTER_VALUE="m/base/getMasterValue"; //获取公司信息
	

	//lbs
	public static String GET_CITY_LIST="m/lbs/getCityList";          //获取城市列表
	public static String GET_AREA_LIST="m/lbs/getAreaList";          //获取区域列表
	public static String GET_CURRENT_CITY="m/lbs/getCurrentCity";    //获取当前城市
	public static String UPDATE_USERLBS="m/lbs/updateUserLbs";       //更新当前位置
	public static String GET_HOUSElIST_BYCITY="m/lbs/getHouseListByCity"; //根据城市ID获取楼盘列表
	public static String GET_AGENTLIST_BYGPS="m/lbs/getHouseListByGPS";  //根据GPS选择附近经纪人
	public static String GET_FREECARLIST_BYGPS="m/user/getFreeCarListByGps"; //根据GPS选择附近的免费专车
	
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
		
		public static String UPLOAD_PIC="m/base/uploadPic";
		
		public static String GET_RANK_LIST="m/agent/getRankList";      //获取排名
	
		public static String CHANGE_CUSTOMER_RELATION="m/agent/changeRelationStatus";//更改用户看房状态
		public static String ADD_CLIENT_RECORD="m/agent/addBusinessRecord";//新增陪同记录
		
		public static String GET_HOUSE_LIST_BYAREA="m/user/searchHouseList";//根据区域id获取房源
		
		public static String SEARCH_MY_CUSTOMER="m/agent/searchMyCustomer";     //搜索我的客户
		
		public static String GET_HOUSEAGENT_INFO="m/user/getHouseAgentInfo"; //获取楼盘推荐经纪人信息
}
