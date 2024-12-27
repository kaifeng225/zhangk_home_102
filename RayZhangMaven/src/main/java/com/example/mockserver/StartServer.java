package com.example.mockserver;

import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class StartServer {

	public static void main(String[] args) {
		startByClientAndServer();
		addExpectationByMockServerClient();
//		removeExpectationByMockServerClient();

	}
	
	private static void startByClientAndServer() {
		ClientAndServer mockServer = startClientAndServer(MockServerConstants.HOST_PORT);
		mockServer.openUI();
	}
	
	private static void addExpectationByMockServerClient() {
		MockServerClient mockServerClient=	new MockServerClient(MockServerConstants.HOST_NAME, MockServerConstants.HOST_PORT);
		mockServerClient.when(
	        request()
	            .withMethod("POST")
	            .withPath("/login")
	    )
	    .respond(
	        response()
	            .withStatusCode(302)
	            .withCookie(
	                "sessionId", "2By8LOhBmaW5nZXJwcmludCIlMDAzMW"
	            )
	            .withHeader(
	                "Location", "https://www.mock-server.com"
	            )
	    );
		System.out.print("add expectation /login");
	
	}
	
	private static void removeExpectationByMockServerClient() {
		MockServerClient mockServerClient=	new MockServerClient("localhost", 80);
		mockServerClient.clear(
	            request()
	                .withPath("/login") // Specify the path of the expectation to remove
	        );
		System.out.print("remove expectation /login");
	}

}
