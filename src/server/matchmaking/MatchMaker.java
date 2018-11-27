package server.matchmaking;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import server.Module;
import server.Request;
import server.Response;

public class MatchMaker {
	private static final int MAX_PLAYER = 10000;

	class Worker implements Runnable {
		@Override
		public void run() {
			while (running) {
				synchronized (waitList) {
					if (waitList.size() < 2) {
						try {
							waitList.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						int p1 = waitList.poll();
						int p2 = waitList.poll();
						startGame(p1, p2);
					}
				}
			}
		}

		private void startGame(int p1, int p2) {
			//TODO
			Response res1 = selfMap.get(p1);
			Response res2 = selfMap.get(p2);
			gameMap.put(p1, res2);
			gameMap.put(p2, res1);
			// TODO give inform to players
			selfMap.remove(p1);
			selfMap.remove(p2);
		}
	}

	private BlockingQueue<Integer> waitList;
	private boolean running;
	private Worker work = new Worker();
	private HashMap<Integer, Response> selfMap;
	private HashMap<Integer, Response> gameMap;

	private MatchMaker() {
		waitList = new LinkedBlockingQueue<Integer>();
		running = true;
		selfMap = new HashMap<>();
		gameMap = new HashMap<>();
		new Thread(work).start();
	}

	// singleton
	private static class Instance {
		private final static MatchMaker instance = new MatchMaker();
	}

	public static MatchMaker getInstance() {
		return Instance.instance;
	}

	public synchronized boolean cancelToPlay(String id) {
		synchronized (waitList) {
			selfMap.remove(id);
			return waitList.remove(id);
		}
	}

	public Response getCompetitorResponse(String id) {
		return gameMap.get(id);
	}

	public synchronized boolean joinWaitList(Integer id, Response response) {
		if (waitList.size() > 0) {
			waitList.notifyAll();
		}
		selfMap.put(id, response);
		return waitList.offer(id);
	}

	private synchronized int generateId() {
		int id = (int) (Math.random() * MAX_PLAYER);
		while (waitList.contains(id)) {
			id = (int) (Math.random() * MAX_PLAYER);
		}
		return id;
	}

}
