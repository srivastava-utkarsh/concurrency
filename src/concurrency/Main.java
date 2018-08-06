package concurrency;

import concurrency.fundamentals.SynchronizationExample;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        Thingie thingie1 = new Thingie();
        Thingie thingie2 = new Thingie();
        
        MyThread th1 = new MyThread(thingie1);
        MyThread th2 = new MyThread(thingie2);
        th1.setName("thread 1");
        th2.setName("thread 2");
        th1.start();
        th2.start();
        
    }
    
    
    public static class Thingie {
        private Date lastAccess;
        
        public synchronized void setLastAccess(Date date) {
            this.lastAccess = date;
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName()+"  : i = "+i);
            }
        }
    }
    public static class MyThread extends Thread {
        private Thingie thingie;
        public MyThread(Thingie thingie) {
            this.thingie = thingie;
        }
        public void run() {
            thingie.setLastAccess(new Date());
        }
    }
    
}
