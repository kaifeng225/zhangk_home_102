package com.example.mockserver;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import org.mockserver.client.MockServerClient;
import org.mockserver.mock.Expectation;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;

import com.example.springboot.file.FileUtils;
import com.google.common.collect.Lists;

public class MockAccela extends BaseMock {
	private static final String ACCESS_TOKEN = "abcdefg";
	private static final String RECORD_ID = "CONCORD-DUB20-00000-003UM";
	private static final String TRANSACTION_ID = "1209950";
	private static final Header CONTENT_TYPE = new Header("Content-Type",
			"application/x-www-form-urlencoded; charset=utf-8");
	private static final Header API_KEY = new Header("api_key", "asdasda6hk82s8z6f2b5qfhk");
	private static final Header AUTHORIZATION = new Header("Authorization", ACCESS_TOKEN);

	public MockAccela() {
		super();
	}

	public static void main(String[] args) {
		MockAccela ma = new MockAccela();
		ma.mockAuth();
		ma.mockQuoteSearch();
		ma.mockQuote();
		ma.mockSubmitInit();
		ma.mockSubmitCommit();
		ma.mockRefund();

	}

	private void mockAuth() {
		String authPath = "/pm2test/concord/accela/auth/oauth2/token";
		if (isRemove) {
			removeExpectation(authPath);
		}
		String authResp = FileUtils.loadTemplate("/template/mock_server/accela/Auth.json").replace("${accessToken}",
				ACCESS_TOKEN);
		HttpRequest authRequest = request().withMethod("POST").withPath(authPath);
		// Check if the expectation already exists
		Expectation[] existingExpectations = mockServerClient.retrieveActiveExpectations(authRequest);
		if (existingExpectations.length == 0) {
			mockServerClient.when(authRequest).respond(response().withStatusCode(200).withBody(authResp));
			System.out.println("add AUTH expectation success!");
		} else {
			System.out.println("AUTH expectation exist!");
		}
	}

	private void mockQuoteSearch() {
		String searchPath = "/pm2test/concord/accela/apis/v4/search/records";
		if (isRemove) {
			removeExpectation(searchPath);
		}
		String searchResp = FileUtils.loadTemplate("/template/mock_server/accela/Quote_Search.json")
				.replace("${recordId}", RECORD_ID);
		HttpRequest searchRequest = request().withMethod("POST").withPath(searchPath);
		// Check if the expectation already exists
		Expectation[] existingExpectations = mockServerClient.retrieveActiveExpectations(searchRequest);
		if (existingExpectations.length == 0) {
			mockServerClient.when(searchRequest).respond(response().withStatusCode(200).withBody(searchResp));
			System.out.println("add Search expectation success!");
		} else {
			System.out.println("Search expectation exist!");
		}
	}

	private void mockQuote() {
		String quotePath = "/pm2test/concord/accela/apis/v4/records/" + RECORD_ID + "/fees";
		if (isRemove) {
			removeExpectation(quotePath);
		}
		String quoteResp = FileUtils.loadTemplate("/template/mock_server/accela/Quote.json");
		HttpRequest quoteRequest = request().withMethod("GET").withPath(quotePath);
		// Check if the expectation already exists
		Expectation[] existingExpectations = mockServerClient.retrieveActiveExpectations(quoteRequest);
		if (existingExpectations.length == 0) {
			mockServerClient.when(quoteRequest).respond(response().withStatusCode(200).withBody(quoteResp));
			System.out.println("add Quote expectation success!");
		} else {
			System.out.println("Quote expectation exist!");
		}
	}

	private void mockSubmitInit() {
		String initPath = "/pm2test/concord/accela/apis/v4/payments/initialize";
		if (isRemove) {
			removeExpectation(initPath);
		}
		String initResp = FileUtils.loadTemplate("/template/mock_server/accela/Submit_INIT.json")
				.replace("${transactionId}", TRANSACTION_ID);
		HttpRequest initRequest = request().withMethod("POST").withPath(initPath);
		// Check if the expectation already exists
		Expectation[] existingExpectations = mockServerClient.retrieveActiveExpectations(initRequest);
		if (existingExpectations.length == 0) {
			mockServerClient.when(initRequest).respond(response().withStatusCode(200).withBody(initResp));
			System.out.println("add SubmitInit expectation success!");
		} else {
			System.out.println("SubmitInit expectation exist!");
		}
	}

	private void mockSubmitCommit() {
		String submitPath = "/pm2test/concord/accela/apis/v4/payments/" + TRANSACTION_ID;
		if (isRemove) {
			removeExpectation(submitPath);
		}
		String submitResp = FileUtils.loadTemplate("/template/mock_server/accela/Submit.json");
		HttpRequest submitRequest = request().withMethod("PUT").withPath(submitPath);
		// Check if the expectation already exists
		Expectation[] existingExpectations = mockServerClient.retrieveActiveExpectations(submitRequest);
		if (existingExpectations.length == 0) {
			mockServerClient.when(submitRequest).respond(response().withStatusCode(200).withBody(submitResp));
			System.out.println("add Submit expectation success!");
		} else {
			System.out.println("Submit expectation exist!");
		}
	}

	private void mockRefund() {
		String refundPath = "/pm2test/concord/accela/apis/v4/payments/refund";
		if (isRemove) {
			removeExpectation(refundPath);
		}
		String refundResp = FileUtils.loadTemplate("/template/mock_server/accela/Refund.json");
		HttpRequest refundRequest = request().withMethod("GET").withPath(refundPath);
		// Check if the expectation already exists
		Expectation[] existingExpectations = mockServerClient.retrieveActiveExpectations(refundRequest);
		if (existingExpectations.length == 0) {
			mockServerClient.when(refundRequest).respond(response().withStatusCode(200).withBody(refundResp));
			System.out.println("add Refund expectation success!");
		} else {
			System.out.println("Refund expectation exist!");
		}
	}

}
