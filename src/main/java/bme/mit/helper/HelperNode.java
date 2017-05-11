package bme.mit.helper;

import java.util.ArrayList;
import java.util.List;

import bme.mit.graph.Node;

public class HelperNode {

	private List<Node> nodes = new ArrayList<>();
	private List<HelperEdge> edges = new ArrayList<>();
	
	private boolean visited = false;
	
	private StringBuilder builder = new StringBuilder("");

	
	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	

	public List<HelperEdge> getEdges() {
		return edges;
	}

	public void setEdges(List<HelperEdge> edges) {
		this.edges = edges;
	}
	

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	

	public StringBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(StringBuilder builder) {
		this.builder = builder;
	}
		
}
