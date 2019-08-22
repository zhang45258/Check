package com.entity;

public class Zhijian {
	  private int channelId;
	    private String text;
	    private int emotionValue;
	    private int speechRate;
	    
		public int getEmotionValue() {
			return emotionValue;
		}
		public void setEmotionValue(int emotionValue) {
			this.emotionValue = emotionValue;
		}
		public int getSpeechRate() {
			return speechRate;
		}
		public void setSpeechRate(int speechRate) {
			this.speechRate = speechRate;
		}
		public int getChannelId() {
			return channelId;
		}
		public void setChannelId(int channelId) {
			this.channelId = channelId;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}


}
