package com.company;

class Consumer implements Runnable{

    TaskQueue q;
    Consumer(TaskQueue q) {
        this.q = q;
    }

    @Override
    public void run() {
        while (true) {
            Task t = q.take();
            System.out.println("executing task with scheduled time " + t.executionTime);
        }
    }
}
