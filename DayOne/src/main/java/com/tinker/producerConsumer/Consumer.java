package com.tinker.producerConsumer;

import java.util.concurrent.BlockingQueue;

/**
 * Implementation of a simple consumer which picks tasks objects from blocking queue for processing.
 */
public class Consumer implements Runnable {
    private BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String task = queue.take();
                String processed = "processed " + task + " by " + Thread.currentThread().getName() + " at " + System.currentTimeMillis();
                System.out.println(processed);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}
