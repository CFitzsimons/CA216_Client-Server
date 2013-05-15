CA216_Client-Server
===================

Run client with the following command:

    .\ChatClient Username
    
Compile Server with:

    javac ChatServer.java
    
And run it with:

    java ChatServer

 
 
Design
======
 
Firstly we made a few assumptions during the project, these are as follows:

1. There is no requirement to limit character input from the client.
2. There is no requirement to limit the number of clients connecting to the server at once.
3. An assumption that we do not need to edit or provide addition details for the ChatApplet or related files(policy/chat.html).
4. That additional clients will be setup and we do not have to provide them.
5. There is no requirement to bound the buffer.

Our ChatServer operates as follows:

1. The server listens on port 7777
2. When available, the server accepts a new connection from the port.
3. This connection is saved and sent to the MessageConsumer class which trackes all clients
4. A tracking variable for the number of connections is incremented.
5. Create and start a new thread with the socket that handles taking input from the client.

Our ServerThread operates as such:

1. A new instance of the object is created with the passed in socket.
2. A BufferedReader is constructed and told to listen for input.
3. Upon first executing, the Server waits for a message from the client with the client's name, which it stores.
4. When input is recieved the Thread sends it to the MessageConsumer for further handling.
5. If a SocketException is thrown we know that the socket in question must be unreadable so we exit the thread (return).
6. Before exiting we decrement the global tracking variable.

Our MessageConsumer operates as follows:

1. Takes a Buffer and constructs a new object with the passed in parameter.
2. Sockets can be added to the Consumer, which grants it knowledge of which sockets to output to.
3. MessageConsumer constantly attempts to remove messages from the buffer and send them out to all known clients.
4. When a new message is found, the Consumer cycles through the arraylist of all known sockets and outputs to them.
5. If the Stream cannot be opened then we assume that the socket is no longer available and remove it from the arrayList.
Note: We chose to use an ArrayList here as it permits faster random access then a LinkedList.

Our UnboundedBuffer class contains two primary methods:

Insert(String)

This places a String object into the buffer, if the input is null no placement happens. The method will also notify all waiting threads that there is data available.

remove()

Removes the first element inserted into the buffer, as the buffer operates on a first in, first out basis.

Notes:
- The buffer uses a LinkedList to store its data as we do not require random access.
- Both methods are synchronized to disallow concurrency issues.


Potential Problems
===================

1. Using a static variable provides an easy way of tracking the amount of connections, however, our implementation has a potential error.  If you close multiple chat windows simultaneously then the chat counter may decrements out of order but will still produce the same end result.