package com.android.house.events;

import com.android.house.model.HouseModel;

public class OnFilterItemClickedEvent {
	private HouseModel.SEARCH_TYPE type;
	private int filterId;
	
	public OnFilterItemClickedEvent(HouseModel.SEARCH_TYPE type,int id)
	{
		this.type=type;
		this.filterId=id;
	}

	public HouseModel.SEARCH_TYPE getType() {
		return type;
	}


	public int getFilterId() {
		return filterId;
	}

}
