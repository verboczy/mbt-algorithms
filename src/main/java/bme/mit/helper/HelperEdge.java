package bme.mit.helper;

import java.util.List;

import bme.mit.graph.Node;

public class HelperEdge {

	public String input;
	public HelperNode startNode = new HelperNode();
	public HelperNode endNode = new HelperNode();
	
	public void print() {
		System.out.println("Start: " + startNode.getName() + " input: " + input + " end: " + endNode.getName());
	}
	
}
