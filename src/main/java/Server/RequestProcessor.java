package Server;

import Base.HTTPResponse;

import java.net.Socket;


public class RequestProcessor implements Runnable {

    private Socket connection;

    public RequestProcessor(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        new HTTPResponse(connection).response();
    }
}
