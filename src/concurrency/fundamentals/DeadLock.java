package concurrency.fundamentals;

import concurrency.ThreadColor;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
* producer-consumer demo
*/
public class DeadLock {
    public void mainFunction(){
        Message message = new Message();
        Thread t1 = new Thread(new Writer(message));
        Thread t2 = new Thread(new Reader(message));
        t1.setName("Writer");
        t2.setName("Reader");
        t1.start();
        t2.start();
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
        System.out.println(ThreadColor.ANSI_BLUE+"acquirig lock on Message.read() by thread :- "+Thread.currentThread().getName());
        while (empty) {
            try {
                System.out.println(ThreadColor.ANSI_RED+"waiting started on Message.read() by thread :- "+Thread.currentThread().getName());
                wait(); // if lock is released then 
            } catch (InterruptedException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        empty = true;
        notifyAll();
        System.out.println(ThreadColor.ANSI_GREEN+"lock released in Message.read() by thread :- "+Thread.currentThread().getName());
        return message;
    }
    
    public synchronized void write(String message){
        System.out.println(ThreadColor.ANSI_BLUE+"acquirig lock on Message.write() by thread :- "+Thread.currentThread().getName());
        while (!empty) {
            try {
                System.out.println(ThreadColor.ANSI_RED+"waiting started on Message.write() by thread :- "+Thread.currentThread().getName());
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        empty = false;
        this.message = message;
        System.out.println(ThreadColor.ANSI_GREEN+"lock released in Message.write() by thread :- "+Thread.currentThread().getName());
        notifyAll();
    }
}

class Writer implements Runnable{
    private Message message;

    public Writer(Message message) {
        this.message = message;
    }
    
    @Override
    public void run() {
        System.out.println("Writer thread started");
        
        String[] messages = {
            "Humpty dumpty sat on the wall",
            "Humpty dumpty had a great fall",
            "All the king's horses and all the king's men",
            "could'nt put Humpty back again"
        };
        
        Random random = new Random();
        for (int i = 0; i < messages.length; i++) {
            System.out.println(ThreadColor.ANSI_PURPLE+"going to write messages in Writer.class loop");
            message.write(messages[i]);
            try {
                System.out.println(ThreadColor.ANSI_PURPLE+"going to sleep in Writer.class loop");
                Thread.sleep(random.nextInt(2000));
                System.out.println(ThreadColor.ANSI_PURPLE+"wokeup in Writer.class loop");
                
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
        System.out.println("Reader thread started");
        Random random = new Random();
        for (String latestMsg = message.read() ; !latestMsg.equals("Finished") ; latestMsg = message.read()) {
            System.out.println(latestMsg);
            try {
                System.out.println(ThreadColor.ANSI_PURPLE+"going to sleep in Reader.class loop");
                Thread.sleep(random.nextInt(2000));
                System.out.println(ThreadColor.ANSI_PURPLE+"wokeup in Reader.class loop");
            } catch (InterruptedException e) {
            }
        }
    }

}
