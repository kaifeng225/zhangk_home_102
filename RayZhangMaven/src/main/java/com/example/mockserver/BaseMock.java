package com.example.mockserver;

import static org.mockserver.model.HttpRequest.request;

import org.mockserver.client.MockServerClient;

public class BaseMock {
	protected MockServerClient mockServerClient = null;
	protected boolean isRemove = true;
	protected BaseMock() {
		mockServerClient = getMockServerClient();
	}

	protected void removeExpectation(String path) {
		mockServerClient.clear(request().withPath(path) // Specify the path of the expectation to remove
		);
		System.out.println("remove expectation " + path);
	}

	private MockServerClient getMockServerClient() {
		return new MockServerClient(MockServerConstants.HOST_NAME, MockServerConstants.HOST_PORT);
	}
}
