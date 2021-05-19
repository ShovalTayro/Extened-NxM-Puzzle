
import java.util.Hashtable;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Map.Entry;

public class A_Star {
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
		//make priority queue and hash_table
		Hashtable<String,Node> open_list = new Hashtable<>();
		Hashtable<String,Node> closed_list = new Hashtable<>();
		PriorityQueue<Node> l = new PriorityQueue<Node>(5, new NodeComparator());

		l.add(start);
		open_list.put(start.toString(),start);
		// while l is not empty
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
			//remove front
			Node front = l.poll();
			open_list.remove(front.toString());
			// if goal return path
			if(front.isGoal()) {
				l.clear();
				return front.path() + "\nNum: "+counter+"\nCost: " +front.findCost();			
			}
			//closed_list.add(n)
			closed_list.put(front.toString(), front);
			List<Node> operators = Board.findAllOperators(front, goalState);

			//for each allowed operator on n
			for (Node operator : operators) {

				//if operator in closed_list and not in l
				if(!closed_list.containsKey(operator.toString()) && !open_list.containsKey(operator.toString()) ) {
					counter++;	
					operator.setParent(front);
					open_list.put(operator.toString(), operator);
					l.add(operator);
				}
				//if operator in l with higher path cost
				else if(l.contains(operator)) {
					Node temp = open_list.get(operator.toString());
					//replace the node in l with operator
					if(operator.f() < temp.f()) {
						open_list.remove(operator.toString());
						open_list.put(operator.toString(), operator);
						l.remove(open_list.get(operator.toString()));
						l.add(temp);
					}
				}
			}
		}
		//if there is no solution
		return " no path \nNum: "+counter;
	}
}
