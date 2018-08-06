package concurrency.fundamentals;

import concurrency.ThreadColor;

public class SynchronizationExample {
    public void mainFunction(){
        Countdown countdown = new Countdown();
        
        CountdownThread thread1 = new CountdownThread(countdown);
        CountdownThread thread2 = new CountdownThread(countdown);
        thread1.setName("Thread 1");
        thread2.setName("Thread 2");
        thread1.start();
        thread2.start();
    }
}

class Countdown{
    private int i;
    
    public void doCountdown(){
        String color;
        switch(Thread.currentThread().getName()){
            case "Thread 1":
                color = ThreadColor.ANSI_BLUE;
                break;
            case "Thread 2":
                color = ThreadColor.ANSI_PURPLE;
                break;
            default:
                color = ThreadColor.ANSI_GREEN;
        }
        /**
         * IMP!!!
         * 
         * we cannot use method local variables here as they're stored on stack
         * and each thread has it's own local copy of variables so there is no
         * RACE-CONDITION. Therefore we'll have to use variables which are shared
         * among both threads.
         */
        synchronized (this){
            for (int j = 0; j < 10; j++) {
                System.out.println(color + Thread.currentThread().getName() + " : i="+j);
            }
        }
    }
}

class CountdownThread extends Thread {
    private Countdown threadCountdown;

    public CountdownThread(Countdown countdown) {
        threadCountdown = countdown;
    }

    public void run() {
        threadCountdown.doCountdown();
    }
}