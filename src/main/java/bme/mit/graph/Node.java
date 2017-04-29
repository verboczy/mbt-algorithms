package bme.mit.graph;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a node in a graph.
 * @author verboczy
 *
 */
public class Node {
	
	Logger log = LoggerFactory.getLogger(Node.class);
	
	// the name of the node
	private String name;
	
	// how many times we have visited the node
	private int visitedCount;
	
	// list of the outgoing edges
	private List<Edge> edges;
	
	// the number of incoming edge - the number of outgoing edges
	private int polarity;		
	
	
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
	
	// name
	public String getName() {
		return name;
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
