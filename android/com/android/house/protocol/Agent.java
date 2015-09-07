package com.android.house.protocol;

import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name="AGENT")
public class Agent {
	
	@Column(name="agent_id")
	private int id;
	@Column(name="agent_name")
	private String agent_name;
	
	@Column(name="agent_tel")
	private String agent_tel;
	@Column(name="agent_score")
	private String agent_score;
	
	
	
	
	
}
