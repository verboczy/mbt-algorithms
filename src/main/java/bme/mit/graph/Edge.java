package bme.mit.graph;

import bme.mit.helper.Logger;

/**
 * Represents an edge in the graph.
 * @author verboczy
 *
 */
public class Edge {
	
	// the node, which the edge start from
	private Node startNode;
	
	// the node, which the edge goes into
	private Node endNode;
	
	// the input label of the edge
	private String inputLabel;
	
	// the output label of the edge
	private String outputLabel;
	
	// how many times we have been on the edge
	private int visitedCount;
	
	
	public Edge() {
		setVisitedCount(0);
		inputLabel = "no label";
	}
	
	// Prints the name of the start node, end node and the label to the log file.
	public void printMyself() {
		Logger.getInstance().logMessage("Edge.java: edge - from: " + this.startNode.getName() + 
				", to: " + this.endNode.getName() + ", input: " + this.inputLabel + ", output: " + this.outputLabel);
	}
	
	// startNode
	public Node getStartNode() {
		return startNode;
	}
	
	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}
	
	// endNode
	public Node getEndNode() {
		return endNode;
	}
	
	public void setEndNode(Node endNode) {
		this.endNode = endNode;
	}
	
	// input label
	public String getInputLabel() {
		return inputLabel;
	}
	
	public void setInputLabel(String label) {
		this.inputLabel = label;
	}
	
	// output label
	public String getOutputLabel() {
		return outputLabel;
	}
	
	public void setOutputLabel(String label) {
		this.outputLabel = label;
	}

	// visitedCount
	public int getVisitedCount() {
		return visitedCount;
	}

	public void setVisitedCount(int visitedCount) {
		this.visitedCount = visitedCount;
	}

}
