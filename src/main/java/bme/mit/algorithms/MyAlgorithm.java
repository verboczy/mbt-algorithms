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
		
	private Logger logger = LoggerFactory.getLogger(MyAlgorithm.class);
	
	private StringBuilder pathBuilder;
	
		
	@Override
	public String getAllNodeVisited(Graph graph) {
		pathBuilder = new StringBuilder("");
		
		Node initNode = getRandomNode(graph);
		stepCount = 0;
		
		traverseNodes(graph, initNode);
		
		return pathBuilder.toString();
	}

	@Override
	public String getAllEdgeVisited(Graph graph) {
		pathBuilder = new StringBuilder("");
		
		Node initNode = getRandomNode(graph);
		pathBuilder.append(initNode.getName());
		pathBuilder.append(";");
		
		stepCount = 0;
		
		traverseEdges(graph, initNode);
		
		return pathBuilder.toString();
	}

	@Override
	public String getAllNodeVisitedFromGivenNode(Graph graph, String init) {
		pathBuilder = new StringBuilder("");
		
		Node initial = getNodeByName(graph, init);
		stepCount = 0;
		traverseNodes(graph, initial);
		
		return pathBuilder.toString();
	}

	@Override
	public String getAllEdgeVisitedFromGivenNode(Graph graph, String init) {
		pathBuilder = new StringBuilder("");
		pathBuilder.append(init);
		pathBuilder.append(";");
		
		Node initial = getNodeByName(graph, init);		
		stepCount = 0;
		traverseEdges(graph, initial);
		
		return pathBuilder.toString();
	}		
	
	/**
	 * Visits all nodes from the initial node.
	 */
	public void traverseNodes(Graph graph, Node node) {		
		if (stepCount >= TIMEOUT) {
			return;
		}
		//node.printMyself();		
		pathBuilder.append(node.getName());
		pathBuilder.append(";");
		node.setVisitedCount(node.getVisitedCount() + 1);
		nodeSet.add(node);
		
		if (node.getEdges().size() == 0) {
			return;
		}
		Edge activeEdge = node.getEdges().get(0);
		Node minVisited = activeEdge.getEndNode();		
		for (Edge edge : node.getEdges()) {
			if (edge.getEndNode().getVisitedCount() < minVisited.getVisitedCount()) {
				minVisited = edge.getEndNode();
				//activeEdge = edge;	// not used
			}
		}
		
		if (nodeSet.equals(graph.getNodes())) {
			//logger.info("all nodes have been visited.");
			super.reset(graph);
		}
		else {
			stepCount++;
			traverseNodes(graph, minVisited);
		}
	}	

	/**
	 * Visits all edges from an initial node.
	 */
	public void traverseEdges(Graph graph, Node initial) {
		if (initial.getEdges().size() == 0 || stepCount >= TIMEOUT) {
			return;
		}
		Edge minVisited = initial.getEdges().get(0);
		for (Edge edge : initial.getEdges()) {
			if (edge.getVisitedCount() < minVisited.getVisitedCount()) {
				minVisited = edge;
			}
		}
		
		minVisited.setVisitedCount(minVisited.getVisitedCount() + 1);
		edgeSet.add(minVisited);
		//minVisited.printMyself();
		pathBuilder.append(minVisited.getInputLabel());
		pathBuilder.append(";");
		
		if (edgeSet.equals(graph.getEdges())) {
			//logger.info("all edges have been visited.");
			super.reset(graph);
		}
		else {
			stepCount++;
			traverseEdges(graph, minVisited.getEndNode());
		}		
	}

}
