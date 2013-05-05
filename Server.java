//name and count of clients added,
//work for single client, possibly not multiple

import java.net.*;
import java.io.*;

// One thread per connection, this is it
class ServerThread extends Thread {

    // The socket passed from the creator
    private Socket socket = null;
	String name;
	int clientCount = 0;
	

    public ServerThread(Socket socket) {

    	this.socket = socket;

    }

    // Handle the connection
    public void run() {
		int value = 1;
		
		clientCount++;
		System.out.println("Clients = "+clientCount);

    while(true){
        try {
			
            String input;

            // Attach a printer to the socket's output stream
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream(),
                true);
            BufferedReader socketIn = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            // Send a message to the client
			
            if((input = socketIn.readLine()) != null){
				if(value == 1) {
                //socketOut.println("Echo: " + input);
				socketOut.println(input+" is connected");
				name = input;
				value++;
				}
				else {
				socketOut.println(name+":" + input);
				}
            }
            // Close things

        } catch (IOException e) {
			clientCount--;
			System.err.println("Clients = "+clientCount);
           // e.printStackTrace();
            break;
            }
         }//close true loop
        }
    
}

// The server
public class Server {

    public static void main(String[] args) throws IOException {

	// The server socket, connections arrive here
        ServerSocket serverSocket = null;

        try {

	    // Listen on on port 7777
            serverSocket = new ServerSocket(7777);

        } catch (IOException e) {

            System.err.println("Could not listen on port: 7777");
            System.exit(-1);

        }
        System.out.println("Sucessfully connected to port: 7777");
	// Loop forever
        while (true) {

	    /*
	     * Several things going on with this line of code:
	     * 1. Accept a connection (returns a new socket)
	     * 2. Create a new thread of type ServerThread
	     * 3. Call start on the new thread
	     */
            new ServerThread(serverSocket.accept()).start();
        }
    }
}