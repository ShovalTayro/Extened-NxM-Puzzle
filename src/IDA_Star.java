import java.util.Hashtable;
import java.util.List;
import java.util.Stack;
import java.util.Map.Entry;

public class IDA_Star {
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
		//make stack and hash table
		Hashtable<String, Node> open_list = new Hashtable<String, Node>(); 
		Stack<Node> l = new Stack<>(); 
		double threshold = start.f();
		//while threshold != infinite
		while (threshold != Integer.MAX_VALUE){
			//min threshold  = infinite
			double min_threshold = Integer.MAX_VALUE;
			start.setOut(false);
			//l.insert(start) and h.insert(start)
			l.push(start);
			open_list.put(start.toString(), start);
			//while l in not empty
			while (!l.isEmpty()){
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
				Node front = l.pop();
				//if front is mark as out -> h.remove(front)
				if(front.getOut()) open_list.remove(front.toString());
				else{
					//mark front as "out" and l.insert(front)
					front.setOut(true);
					l.push(front);

					List<Node> operators = Board.findAllOperators(front, goalState);
					//for each allowed operator on front
					for (Node operator : operators){
						counter++;
						operator.setParent(front); 
						//if f(operator) > threshold
						if (operator.f() > threshold){
							//min threshold = min(min threshold, f(operator))
							min_threshold = Math.min(min_threshold, operator.f());
							continue;
						}
						//if h contain front'=front and front' marked as out -> continue
						if (open_list.containsKey(operator.toString()) && open_list.get(operator.toString()).getOut())continue;
						//if h contain front'=front and front' not marked as out
						if (open_list.containsKey(operator.toString()) && !open_list.get(operator.toString()).getOut()) {
							//if f(front') > f(front) 
							if (open_list.get(operator.toString()).f() > operator.f()) {
								//remove front' from l and h
								open_list.remove(operator.toString());
								l.remove(operator);
							}
							else continue;
						}
						//if goal return path
						if (operator.isGoal()) {
							l.clear();
							threshold = Integer.MAX_VALUE;
							return operator.path() + "\nNum: "+counter+"\nCost: "+ operator.findCost();
						}
						//l.insert(front) and h.insert(front)
						l.push(operator);
						open_list.put(operator.toString(),operator);
					}
				}
			}
			//threshold  = min threshold
			threshold = min_threshold;
		}
		//if there is no solution
		return " no path \nNum: "+counter;
	}
}
