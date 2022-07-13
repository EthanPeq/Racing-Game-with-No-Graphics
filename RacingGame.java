import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

public class RacingGame {

	public static void main(String[] args) {
		GameControl controller= new RacingGameController();   
		controller.runGame();   

	}

}

class IOHandler implements IGameView{
	char input;
	private static char[] matches = new char[]{'f', 't'};

	@Override
	public void display(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	@Override
	public Character getInput(String msg) {
		boolean isCorrectInput = false;
		do {
            input = JOptionPane.showInputDialog(msg).charAt(0);
            input = Character.toLowerCase(input);
            for(int i = 0; i < matches.length; i++){
            	if (input == matches[i]) {   
            		return new Character(input);
            	}            	
            }
            System.out.print("Please respond with an expected character:  ");            
        } while (!isCorrectInput);    
		return null;
	}

	@Override
	public void getResult(String prompt) {
		// TODO Auto-generated method stub		
	}	
}

class RacingGameController extends GameControl{
	Player prior = null;
	int finish;	

	RacingGameController(){//temporal cohesion- initialize vars
		super();
	}
	
	@Override
	void init() {
		view.display("This program lets you play a Racing Game\n");
		int nPlayers = Integer.parseInt(JOptionPane.showInputDialog("Number of Computer Players:"));
		finish = Integer.parseInt(JOptionPane.showInputDialog("What index is the finish line?"));
		
	    for(int i = 1; i <= nPlayers; i++){
	    	players.add(new RacingGamePlayer("Player "+ i));
	    }  
	}

	@Override
	void startGame() {
		raceHistory = "Player \t";
		for(int i = 1; i <= players.size(); i++){
			raceHistory += "   " + i;
		}
		raceHistory += "\nPosition"; 	
	}

	@Override
	void playRound() {//Logical cohesion- performs a series of actions one at a time
		int topPosition = 0;
		raceHistory += "\n             ";
		for(int i = 0; i < players.size(); i++){
			Player current = players.get(i);
			if(current.getPosition() > finish) continue;
			prior = getPriorPlayer(i);
			if(prior == null) return;
			if(current.getPosition() > topPosition) {
				topPosition = current.getPosition();
			}
			int priorPlayerPosition = prior.getPosition();
			current.play(topPosition,priorPlayerPosition);
			raceHistory += "   " + current.getPosition();
		}	
	}

	@Override
	void endGame() {
		Player winner = new RacingGamePlayer();
		for(int i = 0; i < players.size(); i++){
			Player current = players.get(i);
			
	    	if (current.getPosition() > winner.getPosition()){
	    		winner = current;
	    	}
	    } 
		view.display("\nThe Winner is: " + winner.getName() + " with the position of: " + winner.getPosition());
		
	}

	@Override
	boolean moreRounds() {
		for(Player p : players){
	    	if (p.getPosition() >= finish){
	    		return false;
	    	}
	    } 
		return true;
	}
	
	private Player getPriorPlayer(int i){
		 Player prior = null;
		 if(i == 0){
			 int ind = players.size()-1;
			 if(ind > 0){
				 prior = players.get(ind);
			 }
		 }
		 else {
			 prior = players.get(i-1);
		 }
		 return prior;
	}	
}
