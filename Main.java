package com.company;

public class Main {

    public static void main(String[] args) {
        // create a delay queue
        TaskQueue queue = new TaskQueue();

        // create publisher and consumer
        int consumerCount = 5;
        int publisherCount = 4;
        for (int i = 0; i < publisherCount; i++) {
            new Thread(new Publisher(queue)).start();
        }

        for (int i = 0; i < consumerCount; i++) {
            new Thread(new Consumer(queue)).start();
        }
    }
}
