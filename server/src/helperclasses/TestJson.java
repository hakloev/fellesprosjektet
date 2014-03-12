package helperclasses;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Employee;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by Håkon Ødegård Løvdal on 11/03/14.
 */
public class TestJson {

	public static void main(String[] args) {
		Employee employee = new Employee("Lars", "password", "truls");
		RequestTest request = new RequestTest("employee","get", employee);
		ObjectMapper mapper = new ObjectMapper();

		try {
			Writer strWriter = new StringWriter();
			mapper.writeValue(strWriter, request);
			String userDataJSON = strWriter.toString();
			System.out.println(userDataJSON);

			// Loevdal
			JsonNode root = mapper.readTree(userDataJSON);
			JsonNode fields = root.get("type");
			//System.out.println(fields);

			Request r = new Request(userDataJSON);
			Object o = r.parseJSON();
			System.out.println(o);
			System.out.println(o instanceof Employee);

		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
