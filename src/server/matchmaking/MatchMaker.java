package server.matchmaking;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import server.GameProcessor;
import server.PlayerInfo;

public class MatchMaker {

	private BlockingQueue<PlayerInfo> waitList;
	private Thread matchThread;
	private volatile boolean shutdown;

	public MatchMaker() {
		this.waitList = new LinkedBlockingQueue<PlayerInfo>();
		this.shutdown = false;
		matchThread = new MatchThread();
		matchThread.start();
	}

	private class MatchThread extends Thread {

		@Override
		public void run() {
			while (!shutdown) {
				if (waitList.size() < 2) {
					synchronized (waitList) {
						try {
							waitList.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					PlayerInfo left = waitList.poll();
					PlayerInfo right = waitList.poll();
					GameProcessor game = new GameProcessor(left, right);
					new Thread(game).start();
				}

			}
		}
	}

	public boolean quit(PlayerInfo player) {
		synchronized (waitList) {
			return waitList.remove(player);
		}
	}

	public boolean join(PlayerInfo player) {
		synchronized (waitList) {
			if (waitList.size() > 0) {
				waitList.notifyAll();
			}
			return waitList.offer(player);
		}
	}

}
