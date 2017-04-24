package bme.mit.algorithms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bme.mit.graph.Edge;
import bme.mit.graph.Graph;
import bme.mit.graph.Node;

/**
 * Algorithm is an abstract class, which has various graph traversing algorithms.
 * Some of them should be overloaded, but there are algorithms which is implementation 
 * independent, eg.: euler circle.
 * @author verboczy
 *
 */
public abstract class Algorithm {
	
	Logger log = LoggerFactory.getLogger(Algorithm.class);
	
	// a set of nodes
	protected Set<Node> nodeSet;
	// a set of edges
	protected Set<Edge> edgeSet;
	
	// a list of nodes, needed for the Euler circle algorithm
	protected List<Node> circle;
	// a stack of nodes, needed for the Euler circle algorithm
	protected Deque<Node> stack;
	
	public Algorithm() {
		nodeSet = new HashSet<>();
		edgeSet = new HashSet<>();
		
		circle = new ArrayList<>();
		stack = new ArrayDeque<>();
	}
	
	// the algorithms that should be overridden
	public abstract void traverseNodes(Graph graph, Node initial);
	public abstract void traverseEdges(Graph graph, Node initial);
	
	/**
	 * Reverses the "circle" list.
	 */
	private void reverseCircleList() {
		List<Node> reverse = new ArrayList<>();
		for (int i = 0; i < circle.size(); i++) {
			int j = circle.size() - i - 1;
			reverse.add(i, circle.get(j));
		}
		circle = reverse;
	}
	
	/**
	 * Shows an Euler circle in the given graph, starting from the init node.
	 * @param graph - the graph we want to visit
	 * @param initNode - the node, the Euler circle starts from
	 */
	public void eulerCircle(Graph graph, Node initNode) {
		stack.clear();
		circle.clear();
				
		eul(graph, initNode);
		
		reverseCircleList();		
		circle.add(0, initNode);
		
		for (Node node : circle) {
			node.printMyself();
		}				
	}
	
	/**
	 * Helper method to the Euler circle.
	 * I found the algorithm at "http://www.graph-magics.com/articles/euler.php"
	 * @param graph - a graph we want to visit
	 * @param current - a node where we are standing
	 */
	private void eul(Graph graph, Node current) {
		if (current.getEdges().isEmpty()) {			
			if (stack.isEmpty()) {	// think again the last step
				return;
			}
			circle.add(current);
			Node newCurrent = stack.pop();
			eul(graph, newCurrent);
		}
		else {
			stack.push(current);
			Edge removeEdge = current.getEdges().get(0);
			current.getEdges().remove(0);
			Node newCurrent = removeEdge.getEndNode();
			graph.getEdges().remove(removeEdge);
			eul(graph, newCurrent);
		}
	}	
	
	/**
	 * Sets the nodes and edges to unvisited.
	 * @param graph
	 */
	protected void reset(Graph graph) {
		for (Node node : graph.getNodes()) {
			node.setVisitedCount(0);
		}
		for (Edge edge : graph.getEdges()) {
			edge.setVisitedCount(0);
		}
		nodeSet.clear();
		edgeSet.clear();
	}
	
	/**
	 * Eulerizes the given graph. It means, it duplicates some edges to make 
	 * all node's polarity to be 0.
	 * @param original
	 */
	public void eulerizeGraph(Graph original) {
		// If the graph has a sink or a source, than it cannot be eulerized.
		if (original.getHasSink() || original.getHasSource()) {
			log.debug("The graph cannot be eulerized.");
			return;
		}
				
		original.countPolarity();	// Recount the polarity of all node.
		
		// If there is a node, which has positive polarity, than 
		Node positive = null;
		for (Node node : original.getNodes()) {
			// A node is positive if its polarity is greater than 0.
			if (node.getPolarity() > 0) {
				positive = node;
			}
		}
		
		if (positive == null) {
			// End. There was not any node having greater polarity than 0, so cannot be any having less than 0. So all must be 0.
			return;
		}
		
		Edge duplicatedEdge = new Edge();
		Edge oldEdge = positive.getEdges().get(0);
		Node min = oldEdge.getEndNode();
		
		duplicatedEdge.setStartNode(positive);
		duplicatedEdge.setEndNode(min);
		duplicatedEdge.setInputLabel(oldEdge.getInputLabel());
		
		for (Edge edge : positive.getEdges()) {
			if ((min.getPolarity() > edge.getEndNode().getPolarity()) 
				|| 
				// In case the two polarity equals, let's go the way where there are more possibilities.
				((min.getPolarity() == edge.getEndNode().getPolarity()) 
						&& (edge.getEndNode().getEdges().size() > min.getEdges().size()))) {				
				min = edge.getEndNode();
				duplicatedEdge.setEndNode(min);
				duplicatedEdge.setInputLabel(edge.getInputLabel());
			}
		}
		
		original.addEdge(duplicatedEdge);
		
		eulerizeGraph(original);	// recursive call
	}
	
	
	/**
	 * Makes the edge graph of the original graph.
	 * @param original - the graph we want to transform
	 * @return a new Graph, which is the edge graph of the original graph 
	 */
	public Graph makeDual(Graph original) {		
		Graph dual = new Graph();
		
		// Adds nodes to the dual graph. What is an edge in the original is a node in the dual.
		for (Edge edge : original.getEdges()) {
			Node node = new Node(edge.getInputLabel());
			dual.addNode(node);
		}
		
		// Adds edges to the dual graph.
		// An edge in the dual is a node and an incoming and an outgoing edge in the original graph.
		for (Edge edgeIncoming : original.getEdges()) {
			Node midNode = edgeIncoming.getEndNode();			
			String node1 = edgeIncoming.getStartNode().getName();
			String node2 = midNode.getName();
			
			// Searches for outgoing edges from the midNode. With every outgoing edge, there will be a new edge in the dual.
			for (Edge edgeOutgoing : midNode.getEdges()) {
				Edge dualEdge = new Edge();
				String node3 = edgeOutgoing.getEndNode().getName();
				dualEdge.setInputLabel(node1.concat(node2).concat(node3));
				
				for (Node dualNode : dual.getNodes()) {
					if (dualNode.getName().equals(edgeIncoming.getInputLabel())) {	
						dualEdge.setStartNode(dualNode);
					}
					if (dualNode.getName().equals(edgeOutgoing.getInputLabel())) {
						dualEdge.setEndNode(dualNode);
					}
				}
				dual.addEdge(dualEdge);
			}
		}
		
		return dual;
	}

}
