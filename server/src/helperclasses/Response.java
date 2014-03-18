package helperclasses;

/**
 * Created by Håkon Ødegård Løvdal on 11/03/14.
 *
 * Class for holding response string before it is sent back to client
 *
 */
public class Response {

	private final String response;

	public Response(String response) {
		this.response = response;
		System.out.println("Response.response: RESPONSE CREATED: " + Request.getTime());

	}

	public String getResponse() {
		return response;
	}

}
