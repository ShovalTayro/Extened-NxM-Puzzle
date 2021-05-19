import java.io.BufferedReader;
import java.io.File;  
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Ex1 {
	static boolean time = true;
	static boolean open = true;
	static String algorithm = "";
	static Integer[][] gameBoard = null;
	static Integer[][] goal_state = null;
	static int row = 0;
	static int col = 0;

	public static void main(String[] args) throws IOException {
		
		File input = new File("input.txt");
		BufferedReader reader = new BufferedReader(new FileReader(input));
		//line #1 - is the algorithm
		algorithm = reader.readLine(); 
		//line #2 - with time or not
		time = (reader.readLine().equals("no time") ? false : true);
		//line #3 - open or no open
		open = (reader.readLine().equals("no open") ? false : true);
		//line #4 - size of the game board NxM
		String[] size = reader.readLine().split("x", 2);
		row = Integer.parseInt(size[0]);
		col = Integer.parseInt(size[1]);
		//line #5 and on.. - the first state
		gameBoard = new Integer[row][col];
		for(int i = 0; i < row; i++) {
			String[] numbers = reader.readLine().split(",");
			for(int j = 0; j < col; j++) {
				if(!numbers[j].equals("_")) gameBoard[i][j] = Integer.parseInt(numbers[j]);
			}
		}
		//goal state
		if (reader.readLine().equals("Goal state:")){
			goal_state = new Integer[row][col];
			for(int i = 0; i < row; i++) {
				String[] numbers = reader.readLine().split(",");
				for(int j = 0; j < col; j++) {
					if(!numbers[j].equals("_")) goal_state[i][j] = Integer.parseInt(numbers[j]);
				}
			}
		} 
		reader.close();
		start();
	}
	/**
	 * start the game
	 * @throws IOException
	 */
	public static void start() throws IOException {
		long start_time = 0;
		long end_time = 0;
		float algo_time = 0;
		String path = "";
		Node start=new Node(gameBoard, goal_state,0,0, null);
		//Board p1=new Board(p,goal_state); 
		switch(algorithm) {
		case "BFS":
			BFS BFS = new BFS();
			start_time = System.currentTimeMillis();
			path = BFS.play(start, goal_state,open);
			end_time =  System.currentTimeMillis();
			algo_time = (end_time - start_time) / 1000F;
			output(path, time, algo_time);
			break;

		case "DFID": 
			DFID DFID = new DFID();
			start_time = System.currentTimeMillis();
			path = DFID.play(start, goal_state,open);
			end_time =  System.currentTimeMillis();
			algo_time = (end_time - start_time) / 1000F;
			output(path, time, algo_time);
			break;

		case "A*":
			A_Star A_Star = new A_Star();
			start_time = System.currentTimeMillis();
			path = A_Star.play(start, goal_state,open);
			end_time =  System.currentTimeMillis();
			algo_time = (end_time - start_time) / 1000F;	
			output(path, time, algo_time);
			break;

		case "IDA*":
			IDA_Star IDA_Star = new IDA_Star();
			start_time = System.currentTimeMillis();
			path = IDA_Star.play(start, goal_state,open);
			end_time =  System.currentTimeMillis();
			algo_time = (end_time - start_time) / 1000F;
			output(path, time, algo_time);
			break;

		case "DFBnB":
			DFBnB DFBnB = new DFBnB();
			start_time = System.currentTimeMillis();
			path = DFBnB.play(start, goal_state,open);
			end_time =  System.currentTimeMillis();
			algo_time = (end_time - start_time) / 1000F;
			output(path, time, algo_time);
			break;
		}
	}

	/**
	 * this function create the output file
	 * @param path - the path to goal state
	 * @param time - true if we want to print the time, else false
	 * @param algo_time - the time that take to the algorithm
	 **/
	public static void output(String path, boolean time, float algo_time) {
		File file = new File("output.txt");
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		path = path.substring(1, path.length());
		writer.println(path);
		if(time)
			writer.println(algo_time+" seconds");
		writer.close();
	}
}