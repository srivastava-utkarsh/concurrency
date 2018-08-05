package concurrency.fundamentals;

public class ThreadSleep {
    public void mainFunction(){
        System.out.println("Hello from main thread");
        AnotherThread anotherThread = new AnotherThread();
        anotherThread.setName("== Another thread ==");
        anotherThread.start();
        
        new Thread(()->System.out.println("Hello from anonymous thread")).start();
        new Thread(()->System.out.println("Hello from anonymous thread two")).start();
        System.out.println("Hello again from main thread");
    }
}
