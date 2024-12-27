package com.example.mockserver;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import org.mockserver.mock.Expectation;
import org.mockserver.model.HttpRequest;

import com.example.springboot.file.FileUtils;

public class MockVendorMethod extends BaseMock {

	public MockVendorMethod() {
		super();
	}

	public static void main(String[] args) {
		MockVendorMethod mvm = new MockVendorMethod();
		mvm.mockRestSoapResp();

	}

	private void mockRestSoapResp() {
		String authPath = "/pm2test/rest/soap-resp";
		if (isRemove) {
			removeExpectation(authPath);
		}
		String authResp = FileUtils.loadTemplate("/template/mock_server/vendor_method/RestSoapResp.xml");
		HttpRequest authRequest = request().withMethod("GET").withPath(authPath);
		// Check if the expectation already exists
		Expectation[] existingExpectations = mockServerClient.retrieveActiveExpectations(authRequest);
		if (existingExpectations.length == 0) {
			mockServerClient.when(authRequest).respond(response().withStatusCode(200).withBody(authResp));
			System.out.println("add /pm2test/rest/soap-resp expectation success!");
		} else {
			System.out.println("/pm2test/rest/soap-resp expectation exist!");
		}
	}
}
