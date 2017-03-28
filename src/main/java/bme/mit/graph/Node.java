package bme.mit.graph;

import java.util.ArrayList;
import java.util.List;

import bme.mit.helper.Logger;

/**
 * Represents a node in a graph.
 * @author verboczy
 *
 */
public class Node {
	
	// the name of the node
	private String name;
	
	// how many times we have visited the node
	private int visitedCount;
	
	// list of the outgoing edges
	private List<Edge> edges;
	
	// the number of incoming edge - the number of outgoing edges
	private int polarity;		
	

	public Node() {
		visitedCount = 0;
		edges = new ArrayList<>();
		polarity = 0;
	}
	
	public Node(String name) {
		this.name = name;
		visitedCount = 0;
		edges = new ArrayList<>();
		polarity = 0;
	}
	
	public String getOutput(String input) {
		for (Edge edge : edges) {
			if (edge.getInputLabel().equals(input)) {
				return edge.getOutputLabel();
			}
		}
		return "";
	}
	
	public Node getNextNode(String input) {
		for (Edge edge : edges) {
			if (edge.getInputLabel().equals(input)) {
				return edge.getEndNode();
			}
		}
		return this;
	}
	
	// Prints the name of the node to the log file.
	public void printMyself() {
		Logger.getInstance().logMessage("Node.java: node: " + name);
	}
	
	// name
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	// visitedCount	
	public int getVisitedCount() {
		return visitedCount;
	}

	public void setVisitedCount(int visitedCount) {
		this.visitedCount = visitedCount;
	}

	// edges
	public List<Edge> getEdges() {
		return edges;
	}

	// polarity
	public int getPolarity() {
		return polarity;
	}

	public void setPolarity(int polarity) {
		this.polarity = polarity;
	}

}
