
class Concurrency {
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



class Consumer implements Runnable{

	TaskQueue q;
	Publisher(TaskQueue q) {
		this.q = q;
	}

	@Override
	public void run() {
		while (true) {
			Task t = q.pollTask();
			System.out.println("executing task with " + t.delay + "  at time" + t.executionTime);
		}
	}
}

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

			q.enqueue(task);

			// avoid pushing too often
			Thread.sleep(rand.nextInt(20000));
		}	
	}
}

class TaskQueue {

	public synchronized Task pollTask() {

	}

	public synchronized void enqueue(Task t) {

	}
}

class Task {
	int delay;
	int executionTime;
	Task(int delay) {
		this.delay = delay;
		this.executionTime = now() + delay;
	}

	int getDelay() {
		return delay;
	}

	int executionTime() {
		return executionTime;
	}
}
