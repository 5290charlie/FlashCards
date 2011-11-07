package org.eclipse.wtp.fc;

import java.util.*;

public class MsgList {
	
	private List<Msg> msgs = new ArrayList<Msg>();
	
	public void addMsg(String msg, int type) {
		Msg newMsg = new Msg(msg, type);
		msgs.add(newMsg);
	}
	
	public List<Msg> fetchAll() {
		return msgs;
	}
	
	public int numMsgs() {
		return msgs.size();
	}
}
