package com.android.manager.component;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

public class ClientEvaluationHolder {
	private TextView evaluateText;

	private TextView dressLevel;
	private TextView pickUpLevel;
	private TextView evaluateLevel;

	private RatingBar dress;
	private RatingBar pickUp;
	private RatingBar evaluate;
	
	public void initHolder(View view,int evaluateText,int dressLevel
			,int pickUpLevel,int evaluateLevel,int dress,int pickUp,int evaluate){
		this.evaluateText = (TextView)view.findViewById(evaluateText);
		this.dressLevel = (TextView)view.findViewById(dressLevel);
		this.pickUpLevel = (TextView)view.findViewById(pickUpLevel);
		this.evaluateLevel = (TextView)view.findViewById(evaluateLevel);
		
		this.dress = (RatingBar)view.findViewById(dress);
		this.pickUp = (RatingBar)view.findViewById(pickUp);
		this.evaluate = (RatingBar)view.findViewById(evaluate);
	}
	
	public void setHolder(String evaluateText,String dress,String pickUp,String evaluate
			,int dressLevel,int pickUpLevel,int evaluateLevel){
		this.evaluateText.setText(evaluateText);
		this.dressLevel.setText(dress);
		this.pickUpLevel.setText(pickUp);
		this.evaluateLevel.setText(evaluate);
		
		this.dress.setNumStars(dressLevel);
		this.pickUp.setNumStars(pickUpLevel);
		this.evaluate.setNumStars(evaluateLevel);
	}
}
