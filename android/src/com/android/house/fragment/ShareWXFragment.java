package com.android.house.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.BeeFramework.fragment.BaseFragment;
import com.android.house.share.wx.WXConstants;
import com.funmi.house.R;
import com.tencent.mm.sdk.constants.ConstantsAPI.WXApp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX.Req;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class ShareWXFragment extends BaseFragment{
	private View view;
	
	private Button share;
	
	 private IWXAPI api;   
	
	 
	 private static int SUPPROT_FREND=0x21020001;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_share_weixin, null);
		api=WXAPIFactory.createWXAPI(getActivity(), WXConstants.APP_ID, true);
		api.registerApp(WXConstants.APP_ID);
		share = (Button)view.findViewById(R.id.share_weixin_btn);
		share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				WXTextObject textObject=new WXTextObject();
				textObject.text="买房就找易屋之家";
				
				WXMediaMessage msg=new WXMediaMessage();
				msg.mediaObject=textObject;
				msg.description="科技改变房产交易";
				SendMessageToWX.Req req=new Req();
				if(api.getWXAppSupportAPI()>SUPPROT_FREND)
				{
					req.scene=Req.WXSceneTimeline;
					req.message=msg;
					api.sendReq(req);
				}
				else
				{
					Toast.makeText(getActivity(), "手机微信版本过低", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		return view;
	}
}
