package concurrency.fundamentals;

public class ThreadSleep {
    public void mainFunction(){
        System.out.println("Hello from main thread");
        AnotherThread anotherThread = new AnotherThread();
        anotherThread.setName("== Another thread ==");
        anotherThread.start();
        
        new Thread(()->System.out.println("Hello from anonymous thread")).start();
        new Thread(()->{
            System.out.println("Hello from anonymous thread two and about to join anotherthread");
            try {
                // we're joining this anonymous class with anotherThread class instance, which means anonymous class
                // will wait till anotherThread has completed. There is overloaded constructor of join() method which
                // takes time as param and if anotherThread is not completed in param time then anonymous class will
                // resume and continue execution.
                anotherThread.join();
                System.out.println("anotherthread join method completed and resuming anonymous 2nd class");
            } catch (InterruptedException ex) {
                System.out.println("anotherthread inside 2nd anonymous class interrupted");
            }
        }).start();
//        anotherThread.interrupt();
        
        System.out.println("Hello again from main thread");
    }
}
