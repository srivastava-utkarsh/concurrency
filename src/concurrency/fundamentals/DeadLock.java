package concurrency.fundamentals;

import java.util.Random;

public class DeadLock {
    public void mainFunction(){
        Message message = new Message();
        new Thread(new Writer(message)).start();
        new Thread(new Reader(message)).start();
    }
}

/**
 * this read or write methods will create deadlock as first thread to gain access
 * of read or write method will loop indefinitely and never release the lock of message
 * object. This situation is called deadlock.
 * 
 */
class Message{
    private String message;
    private boolean empty = true;
    
    public synchronized String read(){
        while (empty) {
        }
        empty = true;
        return message;
    }
    
    public synchronized void write(String message){
        while (!empty) {
        }
        empty = false;
        this.message = message;
    }
}

class Writer implements Runnable{
    private Message message;

    public Writer(Message message) {
        this.message = message;
    }
    
    @Override
    public void run() {
        String[] messages = {
            "Humpty dumpty sat on the wall",
            "Humpty dumpty had a great fall",
            "All the king's horses and all the king's men",
            "could'nt put Humpty back again"
        };
        
        Random random = new Random();
        for (int i = 0; i < messages.length; i++) {
            message.write(messages[i]);
            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {
            }
        }
        message.write("Finished");
    }
}

class Reader implements Runnable{
    private Message message;

    public Reader(Message message) {
        this.message = message;
    }
    
    @Override
    public void run() {
        Random random = new Random();
        for (String latestMsg = message.read() ; !latestMsg.equals("Finished") ; latestMsg = message.read()) {
            System.out.println(latestMsg);
            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {
            }
        }
    }

}