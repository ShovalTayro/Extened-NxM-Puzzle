
public class Node {

	Node parent;
	private String direction = "";
	private int cost;
	private int age = 0;
	private Integer[][] state;
	private Integer[][] goalState;
	private boolean out;

	/**
	 * Constructor 
	 * @param state - the current state of the board
	 * @param goalState - the goal state of the board
	 * @param cost - cost of this node
	 * @param age - the age of the node
	 * @param dir - direction: R, U, L and D
	 **/
	public Node(Integer[][] state, Integer[][] goalState, int cost, int age, String direction) {
		this.state = state;
		this.goalState = goalState;
		this.cost = cost;
		this.age = age;
		this.direction = direction;
	}
	
	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public void setDirection(String direction) {
		this.direction+=direction;
	}

	public String getDirection() {
		return this.direction;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Integer[][] getState() {
		return state;
	}


	public boolean getOut() {
		return out;
	}

	public void setOut(boolean out) {
		this.out = out;
	}

	/**
	 * F(x)= h(x) + g(x)
	 * h(n) - the heuristic function
	 * g(n) - the cost till this node
	 * @return grade of the node
	 **/
	public double f() {
		return h() + findCost();
	}

	/**
	 * the heuristic function - sum of all 'Manhattan dist' for each number on the gameBoard
	 * @return grade of the node
	 **/
	public double h() {
		double result = 0;
		for(int i = 0; i < getState().length; i++) {
			for(int j = 0; j < getState()[0].length; j++) {
				if(this.cost==5) result += (manhattan(i,j))*3;
				else result += (manhattan(i, j))*2;
			}
		}
		return result;
	}

	/**
	 * Manhattan distance = |x1 - x2| + |y1 - y2|
	 * @param x1 - the row of the block
	 * @param y1 - the column of the block
	 * @return Manhattan distance
	 **/
	private int manhattan(int x1 , int y1) {	
		Integer number = this.state[x1][y1];
		if(number == null) return 0;
		int x2 = 0;
		int y2 = 0;
		int dist =0;
		for(int i = 0; i < state.length; i++) {
			for(int j = 0; j < state[i].length; j++) {
				if(goalState[i][j] == number && number != null) {
					x2 = i;
					y2 = j;
					// |x1 - x2| + |y1 - y2|
					dist =  Math.abs(x1 - x2) +  Math.abs(y1 - y2);
					return dist;
				}	
			}
		}
		return 0;
	}
	/**
	 * check if this state node is the goal state of the board
	 * @return true if this is the goal, else false
	 */
	public boolean isGoal() {
		for(int i=0; i<state.length;i++) {
			for(int j=0;j<state[i].length;j++) {
				if(state[i][j]!=goalState[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * calc the path from the start till this node
	 * @return the path to the node
	 **/
	public String path() {
		if(this.parent==null) return "";
		return this.parent.path()+"-"+this.direction;
	}

	/**
	 * find the cost from the first state till the current node
	 * @param n - the current node
	 * @return the cost from the start till this node
	 **/
	public int findCost() {
		if (this.parent==null) return 0;
		else return this.cost+this.parent.findCost();
	}

	public String toString() {
		String string = "";
		for(int i = 0; i < state.length; i++) {
			for(int j = 0; j < state[i].length; j++) {
				if((i == state.length-1) && (j == state[i].length-1)) {
					if(state[i][j] == null) string += "0";
					else string += "" + Integer.toString(state[i][j]);
				}
				else {
					if(state[i][j] == null) string += "0,";
					else string += "" + Integer.toString(state[i][j]) + ",";
				}
			}
		}
		return string;
	}

}
