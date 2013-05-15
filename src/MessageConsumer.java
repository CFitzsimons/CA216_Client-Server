import java.net.Socket;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.IOException;
//Takes a message and throws it out to all known sockets
//- Socket's are stored in an an ArrayList for random access
//- We chose to use an unbounded buffer (see design.txt)
class MessageConsumer extends Thread{

    private UnboundedBuffer ub;
    private ArrayList<Socket> socketList;
    
    //Standard constructor, builds Consumer with a known buffer
    MessageConsumer(UnboundedBuffer ub){
        this.ub = ub;
        socketList = new ArrayList<>();
    }
    //Overloaded constructor, also adds a socket
    MessageConsumer(UnboundedBuffer ub, Socket startingSocket){
        this(ub);
        addSocket(startingSocket);
    }
    
    //Start thread
    public void run(){
        
        while(true){
            String input;
            //Attempt to remove a message from buffer
            try{
                //Will wait until data is available
                if((input = ub.remove()) != null)
                    sendMessage(input);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    
    /* 
    * Takes a string and attempts to send it to all known sockets.
    * - Constructs a tempororay printwriter to output string.
    * - If we cannot open the output stream, then we must assume
    *   that the socket has been closed elsewhere and therefore
    *   we can remove it from the arraylist.
    */
    private void sendMessage(String toSend){

        for(int i = 0; i < socketList.size(); i++){
            try{
                PrintWriter tmp = new PrintWriter(socketList.get(i).getOutputStream(), true);  
                tmp.println(toSend);

            }catch(IOException e){//Potential error here?
                socketList.remove(i);
            }
        }
    }
    
    //Adds a potential client to the arraylist
    // - Creates/adds nothing if input is null
    public void addSocket(Socket toAdd){
        if(toAdd == null)
            return;
        socketList.add(toAdd);
    }
    
    //Removes a socket from the arrayList
    public void removeSocket(int index){
        //Check if the index is in bounds
        if(index >= socketList.size() || index < 0)
            return;
        socketList.remove(index);
    }
}