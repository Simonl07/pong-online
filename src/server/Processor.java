package server;

import java.io.IOException;
import java.net.Socket;

import server.matchmaking.MatchMaker;

public class Processor implements Runnable {

	private Socket socket;

	public Processor(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			Request request = new Request(socket.getInputStream());
			Response response = new Response(socket.getOutputStream());
			handle(request, response);
			if (!socket.isClosed()) {
				socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void handle(Request request, Response response) {
		String content = request.read();
		String id = null;
		MatchMaker match = MatchMaker.getInstance();
		while (content != null) {
			String[] contentPart = content.split(Module.SPLIT_REGEX);
			switch (contentPart[0]) {
			case Module.SET_ID:
				if (id == null) {
					id = contentPart[1];
				}
				break;
			case Module.MATCH_BEGIN:
				match.joinWaitList(id, response);
				break;
			case Module.MATCH_CANCEL:
				match.cancelToPlay(id);
				break;
			case Module.GAME_START:
				gameCommunicate(request, match.getCompetitorResponse(id));
				break;
			}
			content = request.read();
		}
	}
	
	private void gameCommunicate(Request self, Response comp) {
		String content = self.read();
		while (content != null && !content.equals(Module.GAME_END)) {
			comp.write(content);
			// TODO 3 kinds of report and keep tracking in server
			// TODO new rounds
			
			content = self.read();
		}
	}

}
