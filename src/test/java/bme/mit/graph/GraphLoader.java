package bme.mit.graph;

import bme.mit.helper.Parser;

public class GraphLoader {

	
	/**
	 * Creates a new graph, given in a file.
	 * @param graphName - the file containing the graph
	 * @return a new graph
	 */
	public Graph loadGraph(String graphName) {
		String fileName = getFileName(graphName);
		
		Parser parser = new Parser();
		return parser.createGraph(fileName);		
	}
	
	/**
	 * The path of the graph representation can be given here.
	 * @return - the path to the graph
	 */
	private static String getFileName(String graphFileName) {
		// the folder of the workspace
		String userDir = System.getProperty("user.dir");
		// userDir = E:\Work\workspaces\java\graphalgorithms
		//System.out.println(userDir);
		// the graphs are in the resources folder
		String resourcesFolder = "\\src\\test\\resources\\";
		userDir = userDir.concat(resourcesFolder);
		
		// the name of the file containing the graph
		//String graphFileName = "gstateun.gv";			// The graph can be selected here!
		userDir = userDir.concat(graphFileName);

		return userDir;
	}
	
}
