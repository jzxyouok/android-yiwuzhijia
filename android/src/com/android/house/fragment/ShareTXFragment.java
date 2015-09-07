package com.android.house.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.BeeFramework.fragment.BaseFragment;
import com.funmi.house.R;

public class ShareTXFragment extends BaseFragment {
	private View view;

	private Button share;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_share_tx, null);
		share = (Button) view.findViewById(R.id.share_tx_weibo_btn);
		share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		return view;
	}
}
