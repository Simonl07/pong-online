package server.matchmaking;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import server.Module;
import server.Request;
import server.Response;

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
					// TODO put synchronized here or outside of if statement???
					synchronized (waitList) {
						String p1 = waitList.poll();
						String p2 = waitList.poll();
						startGame(p1, p2);
					}
				}
			}
		}
		
		private void startGame(String p1, String p2) {
			Response res1 = selfMap.get(p1);
			Response res2 = selfMap.get(p2);
			gameMap.put(p1, res2);
			gameMap.put(p2, res1);
			res1.write(Module.FIND_COMPETITOR + Module.SPLIT_REGEX + p2);
			res2.write(Module.FIND_COMPETITOR + Module.SPLIT_REGEX + p1);
			selfMap.remove(p1);
			selfMap.remove(p2);
		}
	}

	private BlockingQueue<String> waitList;
	private boolean running;
	private Worker work = new Worker();
	private HashMap<String, Response> selfMap;
	private HashMap<String, Response> gameMap;
	
	private MatchMaker() {
		waitList = new LinkedBlockingQueue<String>();
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
	
	
	public boolean cancelToPlay(String id) {
		synchronized (waitList) {
			selfMap.remove(id);
			return waitList.remove(id);
		}
	}
	
	public Response getCompetitorResponse(String id) {
		return gameMap.get(id);
	}

	public boolean joinWaitList(String id, Response response) {
		if (waitList.size() > 0) {
			waitList.notifyAll();
		}
		selfMap.put(id, response);
		return waitList.offer(id);
	}

}
