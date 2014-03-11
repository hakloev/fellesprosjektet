package controllers;
import java.io.IOException;
import java.net.Socket;
/**
 * Created by Torgeir on 11.03.14.
 */
public class SocketListener {

    Socket socket;

    public void closeSocket(){
        try{
            socket.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
