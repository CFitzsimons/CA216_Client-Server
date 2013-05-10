import java.net.Socket;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.IOException;

class MessageConsumer extends Thread{

    private UnboundedBuffer ub;
    private ArrayList<Socket> socketList;
    private ArrayList<PrintWriter> writerList;
    
    MessageConsumer(UnboundedBuffer ub){
        this.ub = ub;
        socketList = new ArrayList<>();
        writerList = new ArrayList<>();
    }
    
    MessageConsumer(UnboundedBuffer ub, Socket startingSocket){
        this(ub);
        addSocket(startingSocket);
    }
    
    public void run(){
        
        while(true){
            String input;
            try{
                if((input = ub.remove()) != null)
                    sendMessage(input);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    
    //Takes a string and outputs it to all preinitalized PrintWriters
    private void sendMessage(String toSend){

        for(int i = 0; i < socketList.size(); i++){
            try{
                PrintWriter tmp = new PrintWriter(socketList.get(i).getOutputStream(), true);  
                tmp.println(toSend);

            }catch(IOException e){
                socketList.remove(i);
            }
        }
    }
    
    //Adds a potential client to the array
    // - Creates a printwriter associated with the socket.
    // - Creates/adds nothing if input is null
    public void addSocket(Socket toAdd){
        if(toAdd == null)
            return;
        socketList.add(toAdd);
            //writerList.add(new PrintWriter(toAdd.getOutputStream(), true));

    }
    
    //Removes a socket and it's related PrintWriter
    public void removeSocket(int index){
        //Check if the index is in bounds
        if(index >= socketList.size() || index < 0)
            return;
        socketList.remove(index);
        writerList.get(index).close();
        writerList.remove(index);
    }
}