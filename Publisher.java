package com.company;

import java.util.Random;

class Publisher implements Runnable{

    Random rand;
    TaskQueue q;

    Publisher(TaskQueue q) {
        this.q = q;
        rand = new Random();
    }

    @Override
    public void run() {

        while (true) {
            int delay = rand.nextInt(50000);
            Task task = new Task(delay);

            q.put(task);

            // avoid pushing too often
            try {
                Thread.sleep(rand.nextInt(20000) + 2000);
            } catch (Exception ex){

            }
        }
    }
}