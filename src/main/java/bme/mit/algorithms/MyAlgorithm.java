package bme.mit.algorithms;

import bme.mit.graph.Edge;
import bme.mit.graph.Graph;
import bme.mit.graph.Node;

/**
 * It contains my algorithms for traversing a graph.
 * @author verboczy
 *
 */
public class MyAlgorithm extends Algorithm {
	
	private StringBuilder pathBuilder;
	private boolean cannotTraverse = false;
	
		
	@Override
	public String getAllNodeVisited(Graph graph) {
		pathBuilder = new StringBuilder("");
		
		Node initNode = getRandomNode(graph);
		stepCount = 0;
		
		traverseNodes(graph, initNode);
		
		if (cannotTraverse) {
			return "-";
		}
		
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
		
		if (cannotTraverse) {
			return "-";
		}
		
		return pathBuilder.toString();
	}

	@Override
	public String getAllNodeVisitedFromGivenNode(Graph graph, String init) {
		pathBuilder = new StringBuilder("");
		
		Node initial = getNodeByName(graph, init);
		stepCount = 0;
		traverseNodes(graph, initial);
		
		if (cannotTraverse) {
			return "-";
		}
		
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
		if (cannotTraverse) {
			return "-";
		}
		
		return pathBuilder.toString();
	}		
	
	/**
	 * Visits all nodes from the initial node.
	 */
	public void traverseNodes(Graph graph, Node node) {		
		if (stepCount >= TIMEOUT) {
			cannotTraverse = true;
			return;
		}
		pathBuilder.append(node.getName());
		pathBuilder.append(";");
		node.setVisitedCount(node.getVisitedCount() + 1);
		nodeSet.add(node);
		
		if (node.getEdges().isEmpty()) {
			cannotTraverse = !checkAllNodeVisited(graph);
			return;
		}
		Edge activeEdge = node.getEdges().get(0);
		Node minVisited = activeEdge.getEndNode();		
		for (Edge edge : node.getEdges()) {
			if (edge.getEndNode().getVisitedCount() < minVisited.getVisitedCount()) {
				minVisited = edge.getEndNode();
			}
		}
		
		if (nodeSet.equals(graph.getNodes())) {
			//super.reset(graph);
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
		if (initial.getEdges().isEmpty() || stepCount >= TIMEOUT) {
			cannotTraverse = (stepCount >= TIMEOUT);
			cannotTraverse = !checkAllEdgeVisited(graph);
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
		pathBuilder.append(minVisited.getInputLabel());
		pathBuilder.append(";");
		
		if (edgeSet.equals(graph.getEdges())) {
			//super.reset(graph);
		}
		else {
			stepCount++;
			traverseEdges(graph, minVisited.getEndNode());
		}		
	}

}
