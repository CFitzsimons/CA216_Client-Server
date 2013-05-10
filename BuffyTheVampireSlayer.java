import java.util.LinkedList;

class BuffyTheVampireSlayer{
    private LinkedList<String> messageList;
    private int numMessages;

    public BuffyTheVampireSlayer(){
        messageList = new LinkedList<>();
        numMessages = 0;
    
    }
    //Inserts message into the buffer
    // - Becuase list is unbounded does not need to check if it's full
    // - Notify's all waiting theads that information is available
    public synchronized void insert(String message){
        messageList.addFirst(message);
        numMessages++;
        notifyAll();
        //System.out.println(message);
    }
    
    
    //Removes the first message placed into the buffer
    // - Will wait if buffer is empty
    // - Notifys waiting threads when buffer has space available 
    //The latter condition might not apply because this is a LinkedList and therefore
    //unbounded.
    public synchronized String remove() throws InterruptedException{
        while(numMessages <= 0){
            wait();
        }
        numMessages--;
        String temp = messageList.removeLast();
        //System.out.println(temp);
        //notifyAll();
        return temp;
    }
    
}
