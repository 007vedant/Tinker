package com.tinker.producerConsumer;

import java.util.concurrent.BlockingQueue;


/**
 * Implementation of a simple producer which adds tasks objects to blocking queue.
 */
public class Producer implements Runnable {
    private BlockingQueue<String> queue;
    private Integer counter = 0;

    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            counter += 1;
            long time = System.currentTimeMillis();
            String task = "Task " + counter + " added at " + time;
            try {
                this.queue.put(task);
            } catch (InterruptedException ie) {
                System.out.println("Producer was interrupted!");
            }

            pause(10000);
        }
    }

    private void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}
