import java.util.Hashtable;
import java.util.List;
import java.util.Stack;
import java.util.Map.Entry;

public class DFBnB {
	int iteration = 1;
	int counter = 1;
	Node goal_node;

	/**
	 * start the algorithm on the game board
	 * @param start - the start node
	 * @param goalState - the goal state 
	 * @param open - true if we want to print the 'open list' after each iteration
	 * @return result - result of the algorithm
	 **/
	public String play(Node start, Integer[][] goalState, boolean open ) {
		//make stack(start) and hash table(start)
		Stack<Node> l =new Stack<Node>();
		Hashtable<String, Node> open_list = new Hashtable<String, Node>();
		l.push(start);
		open_list.put(start.toString(), start);

		//threshold = infinite
		double threshold = Integer.MAX_VALUE;

		//while l is not empty
		while(!l.isEmpty()) {
			//l.remove_front(front)
			Node front = l.pop();
			//if from marked as "out" -> h.remove(front)
			if(front.getOut()) open_list.remove(front.toString());
			else {
				//mark front as "out" and l.insert(front)
				front.setOut(true);
				l.add(front);
				counter++;
				//apply all the allowed operators on front 
				List<Node> operators = Board.findAllOperators(front, goalState);
				for(int i = 0; i < operators.size() ;i++) {
					operators.get(i).setParent(front);
				}
				//sort the nodes in 'operators' according to their f value (increasing order)
				operators.sort(new NodeComparator());
				//for each node operator from 'operators' according to the sorted order 
				int i = 0;
				while( i < operators.size()) {
					//if f(front) >= threshold) -> remove front and all the node after it from 'operators'
					if(operators.get(i).f() >= threshold) 
						while(i < operators.size()) operators.remove(i);
					//else if h contains front'=front and front' is marked as "out" -> remove front from 'operators'
					else if(open_list.containsKey(operators.get(i).toString()) && open_list.get(operators.get(i).toString()).getOut()) operators.remove(i);
					//else if h contains front'=front and front' is not marked as "out"
					else if(open_list.containsKey(operators.get(i).toString()) && !open_list.get(operators.get(i).toString()).getOut()) {
						//if f(front') <= f(front) -> remove front' from 'operators'
						if(open_list.get(operators.get(i).toString()).f() <= operators.get(i).f()) operators.remove(i);
						else {
							//remove front' from l and h
							open_list.remove(operators.get(i).toString());
							l.remove(open_list.get(operators.get(i).toString()));
						}
					}
					//if goal(front)					
					else if(operators.get(i).isGoal()) {
						//threshold = f(front)
						threshold = operators.get(i).f();
						goal_node = operators.get(i);
						while(i < operators.size()) operators.remove(i);
					}
					else i++;
				}
				for(int j = operators.size()-1; j >= 0 ; j--) {
					l.add(operators.get(j));
					open_list.put(operators.get(j).toString(), operators.get(j));
				}
			}
			if(open) {
				iteration++;
				System.out.println("\niteration #" + iteration);
				int count = 0;
				for (Entry<String, Node> entry : open_list.entrySet()) {
					String key = entry.getKey();
					Node value = entry.getValue();
					System.out.println (count +"-  Key: " + key + " Value: " + value.getDirection());
					count++;
				}
			}
		}
		//if there is no solution
		if(goal_node == null)return " no path \nNum: "+counter;
		return goal_node.path() + "\nNum: "+counter+"\nCost: " +goal_node.findCost();
	}
}
