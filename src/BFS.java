import java.util.ArrayDeque;
import java.util.Hashtable;
import java.util.List;
import java.util.Queue;
import java.util.Map.Entry;

public class BFS {
	int iteration = 1;
	int counter = 1;

	/**
	 * start the algorithm on the game board
	 * @param start - the start node
	 * @param goalState - the goal state 
	 * @param open - true if we want to print the 'open list' after each iteration
	 * @return result - result of the algorithm
	 **/
	public String play(Node start, Integer[][] goalState, boolean open ) {
		//make queue
		Hashtable<String,Node> open_list = new Hashtable<>();
		Hashtable<String,Node> closed_list = new Hashtable<>();
		Queue<Node> l = new ArrayDeque<Node>(); 

		l.add(start);
		open_list.put(start.toString(), start);

		//while queue not empty
		while (!l.isEmpty()) {
			if(open) {
				System.out.println("\niteration #" + iteration++);
				int count = 0;
				for (Entry<String, Node> entry : open_list.entrySet()) {
					String key = entry.getKey();
					Node value = entry.getValue();
					System.out.println (count +"-  Key: " + key + " Value: " + value.getDirection());
					count++;
				}
			}
			// remove front
			Node front = l.poll();
			open_list.remove(front.toString());
			closed_list.put(front.toString(),front);
			//allowed operators on n
			List<Node> operators = Board.findAllOperators(front ,goalState);
			
			//for each allowed operator on n 
			for (Node operator : operators) {
				operator.setParent(front);                   
				if(!open_list.containsKey(operator.toString()) && !closed_list.containsKey(operator.toString())) {
					counter++;
					//if goal return path
					if(operator.isGoal()) {
						l.clear();
						open_list.clear();
						return operator.path() + "\nNum: "+counter+"\nCost: " +operator.findCost();
					}
					l.add(operator);
					open_list.put(operator.toString(),operator);
				}
			}
		}
		iteration =1;
		//if there is no solution
		return " no path \nNum: "+counter;
	}
}
