package com.graction.developer.lumi.Util.Thread;

/**
 * Created by Graction06 on 2018-01-11.
 */

public class ThreadManager {
    private ThreadStart threadStart;
    private ThreadComplete threadComplete;
    private boolean isComplete;
    private long sleepTime = 200;
    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            threadStart.start();
        }
    });

    public ThreadManager(ThreadStart threadStart, ThreadComplete threadComplete) {
        this.threadStart = threadStart;
        this.threadComplete = threadComplete;
    }

    public <T> T run() throws InterruptedException {
        isComplete = false;
        thread.start();
        while(!isComplete){
            Thread.sleep(sleepTime);
        }
        return threadComplete.complete();
    }

    public void threadComplete(){
        isComplete = true;
    }

    public interface ThreadStart {
        void start();
    }

    public interface ThreadComplete {
        <T> T complete();
    }
}
