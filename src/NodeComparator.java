import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {

	public NodeComparator () {
	}

	@Override
	public int compare(Node v1, Node v2) {
		if (v1.f() > v2.f()) 
			return 1; 

		else if (v1.f() < v2.f()) 
			return -1; 

		else if(v1.f() == v2.f()) { 
			//sort by the age of the node
			if(v1.getAge() > v2.getAge()) return 1;
			else if(v1.getAge() < v2.getAge()) return -1;
			else {	
				if (v2.getDirection().contains("L")) return 1;
				if (v1.getDirection().contains("L")) return -1;
				if (v2.getDirection().contains("U")) return 1;
				if (v1.getDirection().contains("U")) return -1;
				if (v2.getDirection().contains("R")) return 1;
				if (v1.getDirection().contains("R")) return -1;
				if (v2.getDirection().contains("D")) return 1;
				if (v1.getDirection().contains("D")) return -1;
			}
		}
		return 0;
	}
} 