import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

public class DFID {
	int iteration = 1;
	int counter = 1;
	Node node_goal;

	/**
	 * start the algorithm on the game board
	 * @param start - the start node
	 * @param goalState - the goal state 
	 * @param open - true if we want to print the 'open list' after each iteration
	 * @return result - result of the algorithm
	 **/
	public String play(Node start, Integer[][] goalState, boolean open) {
		String result = "";
		//for depth 0 to infinite
		for(int depth = 0; depth < Integer.MAX_VALUE; depth++) {
			//make_hash_table
			Hashtable<String, Node>hashTable = new Hashtable<String, Node>();
			//limited_dfs
			result = limited_DFS(start,goalState,depth,hashTable);
			if(open) {
				System.out.println("\niteration #" + iteration++);
				int count = 0;
				for (Entry<String, Node> entry : hashTable.entrySet()) {
					String key = entry.getKey();
					Node value = entry.getValue();
					System.out.println (count +"-  Key: " + key + " Value: " + value.getDirection());
					count++;
				}
			}
			//if result != cutoff
			if (!result.equals("cutoff")) {
				depth = Integer.MAX_VALUE;
				return result + "\nNum: "+counter+"\nCost: " +node_goal.findCost();
			}
		}
		//if there is no solution
		return " no path \nNum: "+counter;
	}

	public String limited_DFS(Node n, Integer[][] goalState, int limit, Hashtable <String,Node> hashtable) {
		boolean isCutOff; 
		String result = "";
		//if goal
		if(n.isGoal()) {
			node_goal = n;
			counter++;
			return n.path();
		}
		//if limit = 0 then return cutoff
		if(limit==0) return "cutoff";
		else {
			counter++;
			//hash_table.insert(n)
			hashtable.put(n.toString(),n);
			isCutOff = false; 
			List<Node> operators = Board.findAllOperators(n,goalState);

			//for each allowed operator on n
			for (Node operator : operators) {
				//continue with the next operator
				if(hashtable.containsKey(operator.toString())) continue;
				operator.setParent(n);    
				//limited_dfs with limit-1
				result = limited_DFS(operator, goalState, limit-1, hashtable);
				// if result = cutoff -> true
				if(result.equals("cutoff")) isCutOff = true;
				// if result != fail
				else if(!result.equals("fail")) return result; 
			}
			//hash_table.remove(b)
			hashtable.remove(n.toString());
			// if isCutoff = true -> return cutoff
			if(isCutOff) return "cutoff";
			//return fail
			else return "fail";
		}
	}
}
