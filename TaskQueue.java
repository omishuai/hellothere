package com.company;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TaskQueue {


    Lock lock;
    Condition condition;
    PriorityQueue<Task> queue;

    TaskQueue() {
        lock = new ReentrantLock();
        condition = lock.newCondition();
        queue = new PriorityQueue<>(new Comparator<Task>(){
            @Override
            public int compare(Task a, Task b) {
                return (int)(a.executionTime - b.executionTime);
            }
        });
    }


    public Task take() {
        // ensure one consumer at a time
        lock.lock();
        try {
            while (true) {

                if (!queue.isEmpty()) {
                    // then take from the queue;
                    Task task = queue.poll();
                    long delay = task.getDelay();
                    if (delay > 0) {
                        try {
                            condition.awaitNanos(delay);
                            return task;
                        }  catch (InterruptedException e) {
                            // some issue/interrupt happened, then let it go and try next time
                            return null;
                        }
                    }
                    return task;
                }
                // if empty, then just wait
                try {
                    condition.await();
                } catch (Exception e) {
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean put(Task task) {

        lock.lock();
        try {
            queue.offer(task);
            condition.signal();
            return true;
        } finally {
            lock.unlock();
        }
    }

}
