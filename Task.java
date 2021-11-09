package com.company;

import java.util.Date;

class Task {

    Date date;
    long executionTime;
    Task(int delay) {
        date = new Date();
        this.executionTime = date.getTime() + delay;
    }

    long getDelay() {
        return executionTime - date.getTime();
    }

    long getExecutionTime() {
        return executionTime;
    }
}