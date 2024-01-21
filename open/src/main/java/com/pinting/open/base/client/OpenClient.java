package com.pinting.open.base.client;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.response.Response;

public interface OpenClient {

	public Response execute(Request req);

}