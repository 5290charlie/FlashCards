package org.eclipse.wtp.fc;

public class Msg {
	public static final int ERROR = 0;
	public static final int SUCCESS = 1;
	public static final String eClass = "error";
	public static final String sClass = "success";
	
	private String myMsg;
	private int type;
	
	public Msg(String msg, int type) {
		this.myMsg = msg;
		this.type = type;
	}
	
	public String getMsg() {
		return myMsg;
	}
	
	public int getType() {
		return type;
	}
	
	public String findClass() {
		if (type == SUCCESS)
			return sClass;
		else
			return eClass;
	}
}
