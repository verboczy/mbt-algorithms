package bme.mit.helper;

import java.util.ArrayList;
import java.util.List;

import bme.mit.graph.Node;

public class HelperNode {

	public List<Node> nodes = new ArrayList<>();
	public List<HelperEdge> edges = new ArrayList<>();
	
	public boolean visited = false;
	
	public StringBuilder builder = new StringBuilder("");
	
	public String getName() {
		StringBuilder asd = new StringBuilder("");
		for (Node node : nodes) {
			asd.append(node.getName());
		}
		
		return asd.toString();
	}
	
	public void print() {
		System.out.println("name: " + getName());
	}
	
}
