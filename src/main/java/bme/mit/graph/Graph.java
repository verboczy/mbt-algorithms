package bme.mit.graph;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a directed graph. A G(V,E) graph is a set of nodes (V), and edges (E), 
 * where edges (elements of E) are connected to the nodes (elements of V).
 * @author verboczy
 *
 */
public class Graph {
	
	// the set of nodes
	private Set<Node> nodes;
	
	// the set of edges
	private Set<Edge> edges;
	
	// Source is a node, which does not have any incoming edge. 
	private boolean hasSource;
	
	// Sink is a node, which does not have any outgoing edge.
	private boolean hasSink;
	
	
	public Graph() {
		nodes = new HashSet<>();
		edges = new HashSet<>();
		
		// An initial graph does not have any nodes, so it does not has a sink, nor a source.
		hasSink = false;
		hasSource = false;
	}
	
	/**
	 * Add a node to the set of nodes.
	 * @param node - the node to be added
	 */
	public void addNode(Node node) {
		nodes.add(node);
	}
	
	/**
	 * Add an edge to the set of edges.
	 * It updates the start node's edge list.
	 * It recomputes the sinks, sources and the polarities. 
	 * @param edge - the edge to be added
	 */
	public void addEdge(Edge edge) {
		edges.add(edge);
		
		// refresh edge list of the start node
		Node node = edge.getStartNode();
		node.getEdges().add(edge);
		
		refreshSource();
		refreshSink();
		
		countPolarity();
	}
	
	// Refresh the field "hasSource".
	public void refreshSource() {
		Set<Node> localNodes = new HashSet<>();
		for (Edge edge : edges) {
			localNodes.add(edge.getEndNode());
		}
		
		// localNodes.equals(nodes) is equal to this
		if (localNodes.size() < nodes.size()) {
			// There is at least 1 node which cannot be reached from others, so it is a source. 
			// (if the graph is connected)
			hasSource = true;
		}
		else {
			hasSource = false;
		}
	}
	
	// Refresh the field "hasSink".
	public void refreshSink() {
		hasSink = false;
		for (Node node : nodes) {
			// If there is a node which does not have an outgoing edge, than it is a sink. 
			// (if the graph is connected)
			if (node.getEdges().isEmpty()) {
				hasSink = true;
			}
		}
	}

	// Sets the polarity of all node.
	public void countPolarity() {
		for (Node node : nodes) {
			int polarity = -node.getEdges().size();
			for (Edge edge : edges) {
				if (edge.getEndNode().equals(node)) {
					polarity++;
				}
			}
			node.setPolarity(polarity);
		}
	}
	
	// Prints the nodes and edges to the console.
	public void printMyself() {
		for (Node node : nodes) {
			node.printMyself();
		}
		for (Edge edge : edges) {
			edge.printMyself();
		}		
	}
	
	// sink
	public boolean getHasSink() {
		return hasSink;
	}
	
	// source
	public boolean getHasSource() {
		return hasSource;
	}
	
	// nodes
	public Set<Node> getNodes() {
		return nodes;
	}
	
	// edges
	public Set<Edge> getEdges() {
		return edges;
	}		

}
