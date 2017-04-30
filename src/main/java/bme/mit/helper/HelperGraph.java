package bme.mit.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import bme.mit.graph.Node;

public class HelperGraph {

	public List<HelperNode> nodes = new ArrayList<>();
	public List<HelperEdge> edges = new ArrayList<>();
	
	public void print() {
		for (HelperNode helperNode : nodes) {
			helperNode.print();
		}
		
		System.out.println(edges.size());
		
		for (HelperEdge helperEdge : edges) {
			helperEdge.print();
		}
		
	}
	
}
