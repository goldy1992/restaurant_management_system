package com.mike.client;

import com.mike.message.Request.LeaveRequest;
import com.mike.message.Request.RegisterClientRequest;
import com.mike.message.Request.TableStatusRequest;
import com.mike.message.Table;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * Created by michaelg on 09/12/2015.
 */
public class MessageSender {

	@Autowired
	SendGateway sendGateway;

	public void setSendGateway(SendGateway sendGateway) { this.sendGateway = sendGateway; }

	public boolean sendTableStatusRequest(ArrayList<Integer> tables) {
		TableStatusRequest request = new TableStatusRequest(tables);
		sendGateway.send(request);
		return true;
	}

	public final boolean registerClient(RegisterClientRequest.ClientType type) {
		RegisterClientRequest rKitchenReq = new RegisterClientRequest(type);
		sendGateway.send(rKitchenReq);
		System.out.println("message sent");
		return true;
	} // registerClient

	public final boolean leaveRequest() {
		LeaveRequest leaveRequest = new LeaveRequest();
		sendGateway.send(leaveRequest);
		return true;
	} // registerClient
}
