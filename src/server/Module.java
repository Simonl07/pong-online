package server;

public class Module {
	public static final String CHARSET = "UTF-8";
	
	public static final String SPLIT_REGEX = " ";// message split regex
	
	// send by client
	public static final String SET_ID = "SetId";
	public static final String MATCH_BEGIN = "MatchBegin";
	public static final String MATCH_CANCEL = "MatchCancel";
	public static final String GAME_START = "GameStart";
	public static final String GAME_END = "GameEnd";
	
	// send by server
	public static final String FIND_COMPETITOR = "FindCompetitor";
	
	public static String initGame() {
		// TODO game information
		return "";
	}
}
