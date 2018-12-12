package server;

public class Driver {

	public static void main(String[] args) {
		Server s = new Server(8000);
		s.startup();
	}

}
