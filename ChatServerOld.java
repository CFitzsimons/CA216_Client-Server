//Creates a Thead that handles connection on a specific socket
//- Takes in a buffer that handles outputting to all clients
//- Takes in a socket that will be dealt with
class ServerThread extends Thread {

    //Define null socket and buffer
    private Socket socket = null;
	private UnboundedBuffer ub = null;
	private String name;
	
    //Default constructor
    public ServerThread(Socket socket, UnboundedBuffer ub) {
    	this.socket = socket;
		this.ub = ub;
    }
    
    //Handle the connection
    public void run() {
       
        boolean isFirstRun = true;
        while(true){
            try {
                
                String input;
                
                //Opens up a BufferedStream from the socket
                BufferedReader socketIn = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
                
                //Attempt to read from socket
                try{
                    if((input = socketIn.readLine()) != null){
                        if(isFirstRun) {
                            ub.insert(input + " is connected");
                            name = input;
                            isFirstRun = false;
                        }
                        else{
                            ub.insert(name + ": " + input);
                        }
                    }
                    // Close things
                }catch(SocketException e){
                    //Do not handle exception and terminate thread
                    //Note: If we get to this point, it is assumed
                    //      that the socket is closed. (client termination)
                    
                    ChatServer.numConnections--;
                    System.out.println("Number of connections: " + ChatServer.numConnections);
                    //Send the disconnection message to all clients
                    ub.insert(name + " is disconnecting...");
                    //break out of the loop
                    break;
                }

            } catch (IOException e) {
               e.printStackTrace();
            }
        }
    }
    
}

//The server itself
//- Handles taking in sockets and creating relevent threads.
//- inializes an unbounded buffer
//- Opens connections on port 7777
public class ChatServer {
    //Static variable to track number of connections
    public static int numConnections = 0;
    
    public static void main(String[] args) throws IOException {      
        //General declarations and instantiations
        ServerSocket serverSocket = null;
        UnboundedBuffer ub = new UnboundedBuffer();
        MessageConsumer messageTaker = new MessageConsumer(ub);
        
        try {
            //Attempt to listen on port 7777
            serverSocket = new ServerSocket(7777);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 7777");
            //Exit with opcode 1
            System.exit(1);
        }
        System.out.println("Sucessfully connected to port: 7777");
		messageTaker.start();
        
        while (true) {
            //Print out the inital number of connections
            System.out.println("Number of connections: " + ChatServer.numConnections);
            
            //Accept a new connection
			Socket temp = serverSocket.accept();
            
            //Send the socket to the consumer
			messageTaker.addSocket(temp);
            
            //Start a thread with the socket
			new ServerThread(temp, ub).start();
            
            //Increment number of connections
            ChatServer.numConnections++;
        }
    }
}