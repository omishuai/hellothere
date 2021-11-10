package com.company;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
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
                return (int)(a.getDelay() - b.getDelay());
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
                    Task task = queue.peek();

                    long delay = task.getDelay();
                    if (delay > 0) {
                        try {

                            //releasing the lock
                            condition.await(delay, TimeUnit.MILLISECONDS);

                            // here, either producer inserted something or delay has been exhausted
                            // either case, account for newest task in the queue
                            continue;
                        } catch (InterruptedException e) {
                            // some issue/interrupt happened, then let it go and try next time
                            return null;
                        }
                    }
                    return queue.poll();
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
