//name and count of clients added,
//work for single client, possibly not multiple

import java.net.*;
import java.io.*;

// One thread per connection, this is it
class ServerThread extends Thread {

    // The socket passed from the creator
    private Socket socket = null;
	private UnboundedBuffer ub = null;
	private String name;
	

    public ServerThread(Socket socket, UnboundedBuffer ub) {
    	this.socket = socket;
		this.ub = ub;
    }

 // Handle the connection
    public void run() {
        int value = 1;

        while(true){
            try {
                
                String input;

                BufferedReader socketIn = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
                // Send a message to the client
                
                try{
                    if((input = socketIn.readLine()) != null){
                        if(value == 1) {
                            //socketOut.println("Echo: " + input);
                            ub.insert(input + " is connected");
                            //socketOut.println(input+" is connected");
                            name = input;
                            value--;
                        }
                        else{
                            ub.insert(name + ": " + input);
                            //socketOut.println(name+":" + input);
                        }
                    }
                    // Close things
                }catch(SocketException e){
                    //Do not handle exception and terminate thread
                    
                    Server.numConnections--;
                    System.out.println("Number of connections: " + Server.numConnections);
                    return;
                }

            } catch (IOException e) {
               e.printStackTrace();
                //break;
            }
        }
    }
    
}

// The server
public class ChatServer {
    static int numConnections = 0;
    public static void main(String[] args) throws IOException {

        
        ServerSocket serverSocket = null;
        UnboundedBuffer ub = new UnboundedBuffer();
        MessageConsumer messageTaker = new MessageConsumer(ub);
        try {

	    // Listen on on port 7777
            serverSocket = new ServerSocket(7777);
        } catch (IOException e) {

            System.err.println("Could not listen on port: 7777");
            System.exit(-1);

        }
        System.out.println("Sucessfully connected to port: 7777");
		messageTaker.start();
	// Loop forever
        while (true) {

	    /*
	     * Several things going on with this line of code:
	     * 1. Accept a connection (returns a new socket)
	     * 2. Create a new thread of type ServerThread
	     * 3. Call start on the new thread
	     */
            System.out.println("Number of connections: " + numConnections);
			Socket temp = serverSocket.accept();
			messageTaker.addSocket(temp);
			new ServerThread(temp, ub).start();
            numConnections++;
            
            //new ServerThread(serverSocket.accept()).start();
        }
    }
}