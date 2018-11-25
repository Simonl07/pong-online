package server.matchmaking;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MatchMaker {

	class Worker implements Runnable {
		@Override
		public void run() {
			while (running) {
				if (waitList.size() < 2) {
					try {
						waitList.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					// TODO put it here or outside of if statement???
					synchronized (waitList) {
						Player p1 = waitList.poll();
						Player p2 = waitList.poll();
						// TODO start a game here
						
					}
				}
			}
		}
	}

	private BlockingQueue<Player> waitList;
	private boolean running;
	private Worker work = new Worker();

	public MatchMaker() {
		waitList = new LinkedBlockingQueue<Player>();
		running = true;
		new Thread(work).start();
	}

	public boolean cancelToPlay(Player player) {
		synchronized (waitList) {
			return waitList.remove(player);
		}
	}

	public boolean joinWaitList(Player player) {
		if (waitList.size() > 0) {
			waitList.notifyAll();
		}
		return waitList.offer(player);
	}

}
