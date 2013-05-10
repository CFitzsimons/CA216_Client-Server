//name and count of clients added,
//work for single client, possibly not multiple

import java.net.*;
import java.io.*;

// One thread per connection, this is it
class ServerThread extends Thread {

    // The socket passed from the creator
    private Socket socket = null;
<<<<<<< HEAD
    private BuffyTheVampireSlayer btvs;
	private String name;
=======
	private BuffyTheVampireSlayer btvs = null;
	String name;
>>>>>>> 31bc6bcf0a2cf9dad87b1d49f00d6a5a43f075c6
	int clientCount = 0;
	

    public ServerThread(Socket socket, BuffyTheVampireSlayer btvs) {
<<<<<<< HEAD
        this.btvs = btvs;
=======

>>>>>>> 31bc6bcf0a2cf9dad87b1d49f00d6a5a43f075c6
    	this.socket = socket;
		this.btvs = btvs;

    }

 // Handle the connection
    public void run() {
<<<<<<< HEAD
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
=======
		int value = 1;
		
		clientCount++;
		System.out.println("Clients = "+clientCount);

    while(true){
        try {
			
            String input;

            // Attach a printer to the socket's output stream

            //PrintWriter socketOut = new PrintWriter(socket.getOutputStream(),
            //    true);
            BufferedReader socketIn = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            // Send a message to the client
			
            if((input = socketIn.readLine()) != null){
				if(value == 1) {
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
>>>>>>> 31bc6bcf0a2cf9dad87b1d49f00d6a5a43f075c6
            }
        }//close true loop
    }
    
}

// The server
public class Server {

    public static void main(String[] args) throws IOException {

	// The server socket, connections arrive here
        ServerSocket serverSocket = null;
<<<<<<< HEAD
        BuffyTheVampireSlayer btvs = new BuffyTheVampireSlayer();
        MessageConsumer messageTaker = new MessageConsumer(btvs);
        
=======
		BuffyTheVampireSlayer btvs = new BuffyTheVampireSlayer();
		MessageConsumer consumer = new MessageConsumer(btvs);

>>>>>>> 31bc6bcf0a2cf9dad87b1d49f00d6a5a43f075c6
        try {

	    // Listen on on port 7777
            serverSocket = new ServerSocket(7777);
            messageTaker.start();
        } catch (IOException e) {

            System.err.println("Could not listen on port: 7777");
            System.exit(-1);

        }
        System.out.println("Sucessfully connected to port: 7777");
		consumer.start();
	// Loop forever
        while (true) {

	    /*
	     * Several things going on with this line of code:
	     * 1. Accept a connection (returns a new socket)
	     * 2. Create a new thread of type ServerThread
	     * 3. Call start on the new thread
	     */
<<<<<<< HEAD
            Socket tmpSocket = serverSocket.accept();
            messageTaker.addSocket(tmpSocket);
            new ServerThread(tmpSocket, btvs).start();
            
=======
			Socket temp = new Socket();
			consumer.addSocket(temp);
			new ServerThread(temp, btvs).start();
>>>>>>> 31bc6bcf0a2cf9dad87b1d49f00d6a5a43f075c6
            //new ServerThread(serverSocket.accept()).start();
        }
    }
}