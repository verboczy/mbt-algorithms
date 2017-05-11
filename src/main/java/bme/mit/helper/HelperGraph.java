package bme.mit.helper;

import java.util.ArrayList;
import java.util.List;

public class HelperGraph {

	private List<HelperNode> nodes = new ArrayList<>();
	private List<HelperEdge> edges = new ArrayList<>();
	
	
	public List<HelperNode> getNodes() {
		return nodes;
	}
	
	public void setNodes(List<HelperNode> nodes) {
		this.nodes = nodes;
	}
	
	
	public List<HelperEdge> getEdges() {
		return edges;
	}
	
	public void setEdges(List<HelperEdge> edges) {
		this.edges = edges;
	}
	
}
