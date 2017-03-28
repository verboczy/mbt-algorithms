package bme.mit.algorithms;

import bme.mit.graph.Edge;
import bme.mit.graph.Graph;
import bme.mit.graph.Node;
import bme.mit.helper.Logger;

/**
 * It contains my algorithms for traversing a graph.
 * @author verboczy
 *
 */
public class MyAlgorithm extends Algorithm {
	
	/**
	 * Not implemented yet.
	 */
	@Override
	public void traverse(Graph graph) {
		Logger.getInstance().logError("MyAlgorithm.java: traverse(Graph graph) - unimplemented method.");
	}

	/**
	 * Not implemented yet.
	 */
	@Override
	public void traverse(Graph graph, Node initial) {
		Logger.getInstance().logError("MyAlgorithm.java: traverse(Graph graph, Node initial) - unimplemented method.");
	}

	/**
	 * Visits all nodes from the initial node.
	 */
	@Override
	public void traverseNodes(Graph graph, Node node) {
		node.printMyself();		
		node.setVisitedCount(node.getVisitedCount() + 1);
		nodeSet.add(node);
		
		Edge activeEdge = node.getEdges().get(0);
		Node minVisited = activeEdge.getEndNode();		
		for (Edge edge : node.getEdges()) {
			if (edge.getEndNode().getVisitedCount() < minVisited.getVisitedCount()) {
				minVisited = edge.getEndNode();
				//activeEdge = edge;	// not used
			}
		}
		
		if (nodeSet.equals(graph.getNodes())) {
			Logger.getInstance().logMessage("MyAlgorithm.java: All nodes have been visited.");
			super.reset(graph);
		}
		else {
			traverseNodes(graph, minVisited);
		}
	}	

	/**
	 * Visits all edges from an initial node.
	 */
	@Override
	public void traverseEdges(Graph graph, Node initial) {
		Edge minVisited = initial.getEdges().get(0);
		for (Edge edge : initial.getEdges()) {
			if (edge.getVisitedCount() < minVisited.getVisitedCount()) {
				minVisited = edge;
			}
		}
		
		minVisited.setVisitedCount(minVisited.getVisitedCount() + 1);
		edgeSet.add(minVisited);
		minVisited.printMyself();
		
		if (edgeSet.equals(graph.getEdges())) {
			Logger.getInstance().logMessage("MyAlgorithm.java: All edges have been visited.");
			super.reset(graph);
		}
		else {
			traverseEdges(graph, minVisited.getEndNode());
		}		
	}

}
