package bme.mit.sequences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import bme.mit.graph.Edge;
import bme.mit.graph.Graph;
import bme.mit.graph.Node;
import bme.mit.helper.HelperEdge;
import bme.mit.helper.HelperGraph;
import bme.mit.helper.HelperNode;

public class SynchronizingSequence implements Sequence {

	private HelperGraph correspondingMachine = new HelperGraph();
	private Set<String> inputs = new HashSet<>();

	// TODO
	private void setInputSet(Graph graph) {
		Set<Edge> edges = graph.getEdges();
		for (Edge edge : edges) {
			inputs.add(edge.getInputLabel());
		}
	}

	public void printMachine() {
		correspondingMachine.print();
	}

	public void computeCorrespondingMachine(Graph original) {
		setInputSet(original);
		List<Node> nodes = toArrayList(original.getNodes());

		// Add nodes to the graph
		for (int i = 0; i < nodes.size(); i++) {
			HelperNode elem = new HelperNode();
			elem.nodes.add(nodes.get(i));
			correspondingMachine.nodes.add(elem);
			for (int j = i + 1; j < nodes.size(); j++) {
				HelperNode elems = new HelperNode();
				elems.nodes.add(nodes.get(i));
				elems.nodes.add(nodes.get(j));
				correspondingMachine.nodes.add(elems);
			}
		}

		for (HelperNode node : correspondingMachine.nodes) {
			for (String input : inputs) {
				HelperNode next = new HelperNode();
				List<Node> nextNodes = new ArrayList<>();
				for (Node simpleNode : node.nodes) {
					if (!nextNodes.contains(simpleNode.getNextNode(input))) {
						nextNodes.add(simpleNode.getNextNode(input));
					}
				}
				next.nodes = nextNodes;
				HelperEdge edge = new HelperEdge();
				edge.input = input;
				edge.startNode = node;
				edge.endNode = getNodeByElements(nextNodes);
				node.edges.add(edge);
				correspondingMachine.edges.add(edge);
			}
		}
	}

	private HelperNode getNodeByElements(List<Node> paramNode) {
		for (HelperNode nodeList : correspondingMachine.nodes) {
			if (nodeList.nodes.size() == paramNode.size() && nodeList.nodes.contains(paramNode.get(0))) {
				if (paramNode.size() == 2) {
					if (nodeList.nodes.contains(paramNode.get(1))) {
						return nodeList;
					}
				} else {
					return nodeList;
				}
			}
		}

		return null;
	}

	private List<Node> delta(List<Node> inputStates, String seq) {
		System.out.println("delta: " + seq);

		List<Node> result = new ArrayList<>();

		String[] seqElems = seq.split(";");

		for (Node state : inputStates) {
			System.out.println("rootstate: " + state.getName());
			Node temp = state;
			for (int i = 1; i < seqElems.length; i++) {
				System.out.println("temp1: " + temp.getName());
				temp = temp.getNextNode(seqElems[i]);
				System.out.println("temp2: " + temp.getName());
			}
			if (!result.contains(temp)) {
				result.add(temp);
				//System.out.println(temp.getName());
				
			}
		}

		return result;
	}

	private <T> List<T> toArrayList(Set<T> set) {

		List<T> result = new ArrayList<>();
		Iterator<T> iter = set.iterator();

		while (iter.hasNext()) {
			result.add(iter.next());
		}

		return result;
	}

	@Override
	public String getSequence(Graph graph) {

		computeCorrespondingMachine(graph);

		List<Node> nodes = toArrayList(graph.getNodes());

		StringBuilder x = new StringBuilder("");

		List<Node> finalStates = delta(nodes, x.toString());
		while (finalStates.size() > 1) {
			Node s = finalStates.get(0);
			Node t = finalStates.get(1);

			String y = mergingSequence(s, t);
			//System.out.println(y);

			if ("-".equals(y)) {
				return y; // Failure
			}
			
			x.append(y);
			finalStates = delta(nodes, x.toString());
		}

		return x.toString();
	}

	// BFS in the corresponding machine
	private String mergingSequence(Node s, Node t) {
		clearGraph();
		
		List<Node> rList = new ArrayList<>();
		rList.add(s);
		rList.add(t);
		HelperNode root = getNodeByElements(rList);
		//System.out.println("root: ");
		root.print();
		
		Queue<HelperNode> queue = new LinkedList<>();
		queue.add(root);
		root.visited = true;
		while (!queue.isEmpty()) {
			HelperNode node = queue.remove();	
			node.print();
			List<HelperNode> unvisitedChildren = getUnvisitedChildren(node);
			for (HelperNode unvisited : unvisitedChildren) {
				String in = getInput(node, unvisited);
				unvisited.builder.append(node.builder.toString()).append(";").append(in);
				//System.out.println("unvisited seq: " + unvisited.builder.toString());
				if (isSingleton(unvisited)) {
					return unvisited.builder.toString();
				}
				
				unvisited.visited = true;
				queue.add(unvisited);
			}
		}

		return "-";
	}
	
	private void clearGraph() {
		for (HelperNode node : correspondingMachine.nodes) {
			node.builder = new StringBuilder("");
			node.visited = false;
		}
	}
	
	private String getInput(HelperNode from, HelperNode to) {
		for (HelperEdge edge : from.edges) {
			if (edge.endNode.equals(to)) {
				return edge.input;
			}
		}
		return "";
	}
	
	private boolean isSingleton(HelperNode node) {
		return node.nodes.size() == 1;
	}
	
	private List<HelperNode> getUnvisitedChildren(HelperNode node) {
		List<HelperNode> unvisited = new ArrayList<>();
		for (HelperEdge edge : node.edges) {
			if (!edge.endNode.visited) {
				unvisited.add(edge.endNode);
			}
		}
		return unvisited;
	}

}
