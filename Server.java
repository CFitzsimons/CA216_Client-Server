//name and count of clients added,
//work for single client, possibly not multiple

import java.net.*;
import java.io.*;

// One thread per connection, this is it
class ServerThread extends Thread {

    // The socket passed from the creator
    private Socket socket = null;
    private BuffyTheVampireSlayer btvs;
	private String name;
	int clientCount = 0;
	

    public ServerThread(Socket socket, BuffyTheVampireSlayer btvs) {
        this.btvs = btvs;
    	this.socket = socket;

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
                            btvs.insert(input + " is connected");
                            //socketOut.println(input+" is connected");
                            name = input;
                            value--;
                        }
                        else{
                            btvs.insert(name + ": " + input);
                            //socketOut.println(name+":" + input);
                        }
                    }
                    // Close things
                }catch(SocketException e){
                    //Do not handle exception and terminate thread
                    this.interrupt();
                }

            } catch (IOException e) {
               e.printStackTrace();
                //break;
            }
        }//close true loop
    }
    
}

// The server
public class Server {

    public static void main(String[] args) throws IOException {

	// The server socket, connections arrive here
        ServerSocket serverSocket = null;
        BuffyTheVampireSlayer btvs = new BuffyTheVampireSlayer();
        MessageConsumer messageTaker = new MessageConsumer(btvs);
        
        try {

	    // Listen on on port 7777
            serverSocket = new ServerSocket(7777);
            messageTaker.start();
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
            Socket tmpSocket = serverSocket.accept();
            messageTaker.addSocket(tmpSocket);
            new ServerThread(tmpSocket, btvs).start();
            
            //new ServerThread(serverSocket.accept()).start();
        }
    }
}