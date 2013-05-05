import java.util.LinkedList;

class BuffyTheVampireSlayer{
    private LinkedList<String> messageList;
    private int numMessages;

    public BuffyTheVampireSlayer(){
        messageList = new LinkedList<>();
        numMessages = 0;
    
    }
    
    public synchronized void insert(String message){
    
    }
    
    
    //Removes the first message placed into the buffer
    // - Will wait if buffer is empty
    // - Notifys waiting threads when buffer has space available 
    //The latter condition might not apply because this is a LinkedList and therefore
    //unbounded.
    public synchronized String remove() throws InterruptedException{
        while(numMessages == 0){
            wait();
        }
        numMessages--;
        notifyAll();
        return messageList.removeLast();
    }
    
}