
import java.util.ArrayList;
import java.util.List;

public class Board {

	/**
	 * function that find all the allowed operators to this node 
	 * @param front - the node that we want to find his all allowed operators
	 * @param goalState - the goal state of the board
	 * @return list of all the allowed operators to this node 
	 */
	public static List<Node> findAllOperators(Node front, Integer[][] goalState) {	
		List<Node> operators = new ArrayList<Node>();
		Integer [][] state =new Integer [front.getState().length][front.getState()[0].length];
		copy(state, front.getState());

		List<Integer> blank_location = new ArrayList<Integer>();
		//find the blanks location
		for (int i = 0; i < front.getState().length; i++) {
			for (int j = 0; j < front.getState()[i].length; j++) {
				if (front.getState()[i][j] == null) {
					blank_location.add(i);
					blank_location.add(j);
				}
			}
		}
		int index = 0;
		while(index+1 < blank_location.size()) {
			int i = blank_location.get(index);
			int j = blank_location.get(index+1);
			//TWO BLANKS together -> move LEFT
			if(i+1 < front.getState().length && j+1 < front.getState()[0].length && front.getState()[i+1][j] == null ) {
				Node operator = moveLeft(front, state, goalState, true, i, j);
				if(front.getDirection() == null || !front.getDirection().equals(operator.getState()[i][j].toString()+ "&"+ operator.getState()[i+1][j].toString()+"R"))
					operators.add(operator);
			}
			//TWO BLANKS together -> move UP
			if(j+1 < front.getState()[i].length && i+1 < front.getState().length && front.getState()[i][j+1] == null) {
				Node operator = moveUp(front, state, goalState, true, i, j);
				if(front.getDirection() == null || !front.getDirection().equals(operator.getState()[i][j].toString()+ "&"+ operator.getState()[i][j+1].toString()+"D")) 
					operators.add(operator);
			}
			//TWO BLANKS together -> move RIGHT
			if(i+1 < front.getState().length && j > 0 && front.getState()[i+1][j] == null) {
				Node operator = moveRight(front, state, goalState, true, i, j);
				if(front.getDirection() == null || !front.getDirection().equals(operator.getState()[i][j].toString()+ "&"+ operator.getState()[i+1][j].toString()+"L")) 
					operators.add(operator);
			}
			//TWO BLANKS together -> move DOWN
			if(j+1 < front.getState()[i].length && i > 0 && front.getState()[i][j+1] == null) {
				Node operator = moveDown(front, state, goalState, true, i, j);
				if(front.getDirection() == null || !front.getDirection().equals(operator.getState()[i][j].toString()+ "&"+ operator.getState()[i][j+1].toString()+"U")) 
					operators.add(operator);
			}
			//ONE BLANK -> move LEFT
			if(j+1 < front.getState()[0].length && front.getState()[i][j+1] != null) {
				Node operator = moveLeft(front, state, goalState, false, i, j);
				if(front.getDirection() == null || !front.getDirection().equals(operator.getState()[i][j].toString()+"R"))
					operators.add(operator);
			}
			//ONE BLANK -> move UP
			if(i+1 < front.getState().length && front.getState()[i+1][j] != null) {
				Node operator = moveUp(front, state, goalState, false, i, j);
				if(front.getDirection() == null || !front.getDirection().equals(operator.getState()[i][j].toString()+"D")) 
					operators.add(operator);
			}
			//ONE BLANK -> move RIGHT
			if(j > 0 && front.getState()[i][j-1] != null) {
				Node operator = moveRight(front, state, goalState, false, i, j);
				if(front.getDirection() == null || !front.getDirection().equals(operator.getState()[i][j].toString()+"L")) 
					operators.add(operator);
			}
			//ONE BLANK -> move DOWN
			if(i > 0 && front.getState()[i-1][j] != null) {
				Node operator = moveDown(front, state, goalState, false, i, j);
				if(front.getDirection() == null || !front.getDirection().equals(operator.getState()[i][j].toString()+"U")) 
					operators.add(operator);
			}
			index +=2;
		}
		return operators;
	}

	/**
	 * this function move the block down in the board 
	 * @param front - the current goal
	 * @param curr_state - the current state of the board
	 * @param goalState - the goal state
	 * @param twoBlanks - true if there is 2 blanks in the board
	 * @param i - the row of the blank
	 * @param j - the col of the blank
	 * @return new node with the current state after the move of the block
	 **/
	public static Node moveDown(Node front, Integer[][] curr_state, Integer[][] goalState, boolean twoBlanks ,int i, int j) {
		Integer[][] new_state = new Integer[goalState.length][goalState[0].length];
		copy(new_state, curr_state);
		Node operator = null;
		//move 2 blocks together
		if(twoBlanks) {
			new_state[i][j] = curr_state[i-1][j];
			new_state[i][j+1] = curr_state[i-1][j+1];
			new_state[i-1][j] = null;
			new_state[i-1][j+1] = null;
			operator = new Node(new_state, goalState, 7, front.getAge()+1, new_state[i][j].toString() + "&" + new_state[i][j+1].toString() + "D");
		}
		//move one block
		else {
			new_state[i][j] = curr_state[i-1][j];
			new_state[i-1][j] = null;
			operator = new Node(new_state, goalState, 5, front.getAge()+1, new_state[i][j].toString() + "D");
		}
		return operator;
	}

	/**
	 * this function move the block up in the board 
	 * @param front - the current goal
	 * @param curr_state - the current state of the board
	 * @param goalState - the goal state
	 * @param twoBlanks - true if there is 2 blanks in the board
	 * @param i - the row of the blank
	 * @param j - the col of the blank
	 * @return new node with the current state after the move of the block
	 **/
	public static Node moveUp(Node front, Integer[][] curr_state,Integer[][] goalState, boolean twoBlanks ,int i, int j) {
		Integer[][] new_state = new Integer[goalState.length][goalState[0].length];
		copy(new_state, front.getState());
		Node operator = null;
		//move 2 blocks together
		if(twoBlanks) {
			new_state[i][j] = curr_state[i+1][j];
			new_state[i][j+1] = curr_state[i+1][j+1];
			new_state[i+1][j] = null;
			new_state[i+1][j+1] = null;
			operator = new Node(new_state, goalState, 7, front.getAge()+1, new_state[i][j].toString() + "&" + new_state[i][j+1].toString() + "U");
		}
		//move one block
		else {
			new_state[i][j] = curr_state[i+1][j];
			new_state[i+1][j] = null;
			operator = new Node(new_state, goalState, 5, front.getAge()+1, new_state[i][j].toString() + "U");
		}
		return operator;
	}

	/**
	 * this function move the block left in the board 
	 * @param front - the current goal
	 * @param curr_state - the current state of the board
	 * @param goalState - the goal state
	 * @param twoBlanks - true if there is 2 blanks in the board
	 * @param i - the row of the blank
	 * @param j - the col of the blank
	 * @return new node with the current state after the move of the block
	 **/
	public static Node moveLeft(Node front, Integer[][] curr_state,Integer[][] goalState, boolean twoBlanks ,int i, int j) {
		Integer[][] new_state = new Integer[goalState.length][goalState[0].length];
		copy(new_state, curr_state);
		Node operator = null;
		//move 2 blocks together
		if(twoBlanks) {
			new_state[i][j] = curr_state[i][j+1]; 
			new_state[i+1][j] = curr_state[i+1][j+1]; 
			new_state[i][j+1] = null; 
			new_state[i+1][j+1] = null; 
			operator = new Node(new_state, goalState, 6, front.getAge()+1, new_state[i][j].toString() + "&" + new_state[i+1][j].toString() + "L");
		}
		//move one block
		else {
			new_state[i][j] = curr_state[i][j+1]; 
			new_state[i][j+1] = null; 
			operator = new Node(new_state, goalState, 5, front.getAge()+1, new_state[i][j].toString() + "L");
		}
		return operator;
	}

	/**
	 * this function move the block right in the board 
	 * @param front - the current goal
	 * @param curr_state - the current state of the board
	 * @param goalState - the goal state
	 * @param twoBlanks - true if there is 2 blanks in the board
	 * @param i - the row of the blank
	 * @param j - the col of the blank
	 * @return new node with the current state after the move of the block
	 **/
	public static Node moveRight(Node front, Integer[][] curr_state,Integer[][] goalState, boolean twoBlanks ,int i, int j) {
		Integer[][] new_state = new Integer[goalState.length][goalState[0].length];
		copy(new_state, curr_state);
		Node operator = null;
		//move 2 blocks together
		if(twoBlanks) {
			new_state[i][j] = curr_state[i][j-1];
			new_state[i+1][j] = curr_state[i+1][j-1];
			new_state[i][j-1] = null;
			new_state[i+1][j-1] = null;
			operator = new Node(new_state, goalState, 6, front.getAge()+1, new_state[i][j].toString() + "&" + new_state[i+1][j].toString() + "R");
		}
		//move one block
		else {
			new_state[i][j] = curr_state[i][j-1];
			new_state[i][j-1] = null;
			operator = new Node(new_state, goalState, 5, front.getAge()+1, new_state[i][j].toString() + "R");
		}
		return operator;
	}

	/**
	 * deep copy of matrix
	 * @param a - the new matrix
	 * @param b - the matrix we want to copy
	 **/
	private static void copy(Integer[][] state, Integer[][] integers) {
		for(int i = 0; i < state.length; i++)
			for(int j = 0; j < state[i].length; j++)
				state[i][j] = integers[i][j];

	}
}
