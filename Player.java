public abstract class Player{
	protected int position;
	protected int moveTypeNum;
	protected int nRound;
	protected String playerName;
	
	public Player() {
		this("Unknown");
	}
	
	public Player(String name){ //temporal cohesion- initialize vars
		position = 0;
		moveTypeNum = 1;
		playerName = name;
		nRound = 0;
	}
	
	public String getName(){ //functional cohesion- one function
		return playerName; 
	}
	
	public int getPosition() {//functional cohesion- one function
		return position;
	}
	//communicational cohesion- helps alegorithem and uses the same data
	public void advance(int topPosition, int priorPlayerPosition) {
		if(moveTypeNum == 3) {
			moveType3(priorPlayerPosition);
		}
		else if(moveTypeNum == 2) {
			moveType2();
		}
		else {
			moveType1(topPosition);
		}	
		if(position < 0) {
			position = 0;
		}
	}
	//functional cohesion- one function
	public void moveType1(int topPosition){ //requires topPosition
		int advanceNum = rollDice()  + Math.abs((topPosition - position ) / 2);
		if(advanceNum <= 2)
			advanceNum = advanceNum * -1;
		position += advanceNum;
		return;
	}
	//functional cohesion- one function
	public void moveType2() { //
		int advanceNum = rollDice()*3;
		position += advanceNum;
		return;
	}
	//functional cohesion- one function
	public void moveType3(int lastPlayerPosition) { //requires lastPlayerPosition
		int advanceNum = rollDice() + Math.abs((position - lastPlayerPosition)/2);
		if(advanceNum >= 3) 
			advanceNum = advanceNum * -1;
		position += advanceNum;
		return;
	}
	//functional cohesion- one function
	public int rollDice() {
		return (int)(Math.random()*6 + 1);
	}
	public abstract void play(int t, int p);
}


class RacingGamePlayer extends Player{
	
	public RacingGamePlayer() {//temporal cohesion- initialize vars
		super();
	}
	
	public RacingGamePlayer(String name){//temporal cohesion- initialize vars
		super(name);
	}

	@Override
	public void play(int topPosition, int priorPlayerPosition) {//communicational cohesion- helps alegorithem and uses the same data
		if(nRound == 0) {
			nRound = (int) (Math.random()*3 + 3); // n = 3 through 5
			moveTypeNum = (int) (Math.random()*4 + 1); // moveTypeNum = 1 through 3
		}
		
		advance(topPosition, priorPlayerPosition);
		nRound--;
	}

}
