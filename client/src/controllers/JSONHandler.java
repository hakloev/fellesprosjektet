package controllers;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by Torgeir on 11.03.14.
 */
public class JSONHandler {

    public String jsonParser(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String userDataJSON = null;


        try {

            Writer strWriter = new StringWriter();
            mapper.writeValue(strWriter, object);
            userDataJSON = strWriter.toString();
            System.out.println("Parsed: "+ userDataJSON + "\n");

        } catch (IOException e) {

            e.printStackTrace();

        }

        return userDataJSON;
    }



}
