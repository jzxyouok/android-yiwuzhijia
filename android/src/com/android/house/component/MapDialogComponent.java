package com.android.house.component;

import com.android.house.protocol.House;
import com.funmi.house.R;

import android.view.View.OnClickListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MapDialogComponent extends View implements OnClickListener{
	private Context mContext;
	
	private View view;
	
	private Button submit;
	
	private TextView managerName;
	private TextView customerMale;
	private TextView customerFemale;
	
	private TextView houseName;
	private TextView housePrice;
	private TextView housePosition;
	
	private EditText customerName;
	private EditText customerPhone;
	
	private ImageView managerImg;
	
	private String sex;
	private String customerNameStr;
	private String customerPhoneStr;

	public MapDialogComponent(Context context) {
		super(context);
		mContext = context;
		
		initView();
	}

	private void initView(){
		view = LayoutInflater.from(mContext).inflate(R.layout.item_map_dialog_view, null);
		
		submit = (Button)view.findViewById(R.id.map_dialog_submit);
		
		managerName = (TextView)view.findViewById(R.id.map_dialog_manager_name);
		customerMale = (TextView)view.findViewById(R.id.map_dialog_customer_male);
		customerFemale = (TextView)view.findViewById(R.id.map_dialog_customer_female);
		
		houseName = (TextView)view.findViewById(R.id.map_dialog_house_name);
		housePrice = (TextView)view.findViewById(R.id.map_dialog_house_price);
		housePosition = (TextView)view.findViewById(R.id.map_dialog_house_pos);
		
		customerName = (EditText)view.findViewById(R.id.map_dialog_customer_name);
		customerPhone = (EditText)view.findViewById(R.id.map_dialog_customer_phone);
		
		managerImg = (ImageView)view.findViewById(R.id.map_dialog_manager_img);
		
		setClickListener();
	}
	
	private void setClickListener(){
		submit.setOnClickListener(this);
		customerMale.setOnClickListener(this);
		customerFemale.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.map_dialog_submit:
			customerNameStr = customerName.getText().toString();
			customerPhoneStr = customerPhone.getText().toString();
			
			break;
			
		case R.id.map_dialog_customer_male:
			sex = "男";
			break;
			
		case R.id.map_dialog_customer_female:
			sex = "女";
			break;
		}
	}
	
	
	public void setHouseInfo(House house)
	{
		houseName.setText(house.getName());
		housePrice.setText(house.getHouse_price()+"");
		housePosition.setText(house.getLocation_info()+":"+house.getArea_name());
	}
	public void setManagerName(String name){
		managerName.setText(name);
	}
	
	public void setManagetImg(Bitmap img){
		managerImg.setImageBitmap(img);
	}
	
	public void setHouseName(String name){
		houseName.setText(name);
	}
	
	public void setHousePrice(String price){
		housePrice.setText(price);
	}
	
	public void setHousePos(String pos){
		housePosition.setText(pos);
	}
	
	public String getSex(){
		return sex;
	}
	
	public View getView(){
		return view;
	}
	
	public String getCustomerName(){
		return customerNameStr;
	}
	
	public String getCustomerPhone(){
		return customerPhoneStr;
	}
}
