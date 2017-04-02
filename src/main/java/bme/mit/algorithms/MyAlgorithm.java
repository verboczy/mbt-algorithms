package bme.mit.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bme.mit.graph.Edge;
import bme.mit.graph.Graph;
import bme.mit.graph.Node;

/**
 * It contains my algorithms for traversing a graph.
 * @author verboczy
 *
 */
public class MyAlgorithm extends Algorithm {
	
	Logger log = LoggerFactory.getLogger(MyAlgorithm.class);
	
	/**
	 * Not implemented yet.
	 */
	@Override
	public void traverse(Graph graph) {
		log.debug("traverse(Graph graph) - unimplemented method.");
	}

	/**
	 * Not implemented yet.
	 */
	@Override
	public void traverse(Graph graph, Node initial) {
		log.debug("traverse(Graph graph, Node initial) - unimplemented method.");
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
			log.info("all nodes have been visited.");
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
			log.info("all edges have been visited.");
			super.reset(graph);
		}
		else {
			traverseEdges(graph, minVisited.getEndNode());
		}		
	}

}
