import java.net.Socket;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.IOException;

class MessageConsumer extends Thread{

    private BuffyTheVampireSlayer btvs;
    private ArrayList<Socket> socketList;
    private ArrayList<PrintWriter> writerList;
    
    MessageConsumer(BuffyTheVampireSlayer btvs){
        this.btvs = btvs;
        socketList = new ArrayList<>();
        writerList = new ArrayList<>();
    }
    
    MessageConsumer(BuffyTheVampireSlayer btvs, Socket startingSocket){
        this(btvs);
        addSocket(startingSocket);
    }
    
    public void run(){
        while(true){
            try{
                sendMessage(btvs.remove());
            }catch(InterruptedException e){
                //Don't handle
            }
        
        }
    }
    //Takes a string and outputs it to all preinitalized PrintWriters
    private void sendMessage(String toSend){
    
        for(int i = 0; i < writerList.size(); i++){
            writerList.get(i).write(toSend);       
        }
    }
    //Adds a potential client to the array
    // - Creates a printwriter associated with the socket.
    // - Creates/adds nothing if input is null
    public void addSocket(Socket toAdd){
        if(toAdd == null)
            return;
        try{
            socketList.add(toAdd);
            writerList.add(new PrintWriter(toAdd.getOutputStream(), true));
        }catch(IOException e){
            e.printStackTrace();
            System.err.println("Error getting socket output stream");
        }
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