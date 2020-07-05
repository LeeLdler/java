package bean;

import java.io.Serializable;

public class Message implements Serializable{
	/*
	 * 1.ÎÄ×ÖĞÅÏ¢
	 */
	private int type;
	private String message;
	private String date;
	private int isMy;
	private int id;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getIsMy() {
		return isMy;
	}
	public void setIsMy(int isMy) {
		this.isMy = isMy;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
