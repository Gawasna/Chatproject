package com.chatproject.controller;

public class SeenMessage {
   private String content;
   private boolean isSeen;
   public SeenMessage(String content) {
	   this.content=content;
	   this.isSeen=false;   
   }
   public void readMessage() {
	   this.isSeen=true;
   }
   public void displayMessage() {
	   System.out.println("Nội dung tin nhắn: "+this.content);
	   if(this.isSeen) {
		   System.out.println("Tin nhắn đã xem");
	   }else {
		   System.out.println("Tin nhắn chưa xem");
	   }
   }
}

