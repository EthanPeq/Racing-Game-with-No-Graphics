import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class GameControl {
	protected IGameView view = new IOHandler();
	protected List<Player> players;
	String raceHistory;
	
	public GameControl(){//temporal cohesion- initialize vars
		players = new ArrayList<Player>();
		raceHistory = "";
		init();		
	}
	public void runGame(){ //Logical cohesion- performs a series of actions one at a time
	    do {
	    	int numRounds = 1;
			startGame();
	    	do{
	    		view.display("Round " + numRounds + ":");
	    		playRound();
	    		view.display(raceHistory);
	    		numRounds++;
	    	}while(moreRounds());   
	    	endGame();
	    } while ( ((char) view.getInput("Play again (t/f)" + "?")) == 't');	  
	}
	abstract void init();
	abstract void startGame();
	abstract void playRound();
	abstract void endGame();
	abstract boolean moreRounds();
	
}


interface IGameView{
	void getResult(String prompt);
	void display(String message);
	<T> T getInput(String msg);
}
