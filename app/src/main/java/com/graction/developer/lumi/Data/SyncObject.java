package com.graction.developer.lumi.Data;

import android.util.Log;

import java.util.LinkedList;

import retrofit2.Call;

/**
 * Created by Graction06 on 2018-01-19.
 */

public class SyncObject {
    private static final SyncObject instance = new SyncObject();
    private LinkedList<SyncItem> list = new LinkedList<>();
    private SyncItem now;

    public static SyncObject getInstance() {
        return instance;
    }

    public void addAction(OnSyncAction action, int id) throws InterruptedException {
        list.offer(new SyncItem(new Thread(() -> action.onStart()), id));
        start();
    }

    public void addAction(Thread thread, int id) throws InterruptedException {
        list.offer(new SyncItem(thread, id));
        start();
    }

    public void addAction(Call call, int id) throws InterruptedException {
        list.offer(new SyncItem(call, id));
        start();
    }

    private void start() throws InterruptedException {
        now = list.poll();
        if (now != null) {
            Log.i(getClass().getName(), "#################### id : " + now.getId());
            if (now.isThread()) {
                Thread thread = now.getThread();
                thread.start();
                thread.join();
                while (thread.isAlive()) {
                    Thread.sleep(200);
                    Log.i(getClass().getName(), "#################### loading id : " + now.getId() + " : " + thread.isAlive());
                    Log.i(getClass().getName(), "#################### loading getState : "  + (thread.getState() == Thread.State.TERMINATED));
                }
            } else {
                Call call = now.getCall();
                Log.i(getClass().getName(), "#################### loading id : " + now.getId());
                Log.i(getClass().getName(), "#################### loading isExecuted : " + call.isExecuted());
                Log.i(getClass().getName(), "#################### loading isCanceled : " + call.isCanceled());
            }

            start();
        }
    }

    public void end(int id) throws InterruptedException {
        if (now.getId() == id)
            start();
    }

    public interface OnSyncAction {
        void onStart();
    }

    public class SyncItem {
        private Thread thread;
        private Call call;
        private int id;
        private boolean isThread = false;

        public SyncItem(Thread thread, int id) {
            this.thread = thread;
            this.id = id;
            this.isThread = true;
        }

        public SyncItem(Call call, int id) {
            this.call = call;
            this.id = id;
        }

        public Thread getThread() {
            return thread;
        }

        public void setThread(Thread thread) {
            this.thread = thread;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Call getCall() {
            return call;
        }

        public void setCall(Call call) {
            this.call = call;
        }

        public boolean isThread() {
            return isThread;
        }
    }
}
