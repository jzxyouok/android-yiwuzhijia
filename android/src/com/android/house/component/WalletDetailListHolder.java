package com.android.house.component;

import android.widget.TextView;

public class WalletDetailListHolder {
	public TextView time;
	public TextView thing;
	public TextView money;
	public TextView status;

	public void setWalletInfo(String time, int thing, String money,String status) {
		this.time.setText(time);

		this.money.setText(money);
		
		this.status.setText(status);

		switch (thing) {
		case 1:
			this.thing.setText("注册返现");
			break;
		case 2:
			this.thing.setText("购房返现");
			break;
		case 3:
			this.thing.setText("购房返现");
			break;
		case 4:
			this.thing.setText("提现");
			break;

		default:
			break;
		}

	}
}