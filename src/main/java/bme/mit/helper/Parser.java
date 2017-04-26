package bme.mit.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bme.mit.graph.Edge;
import bme.mit.graph.Graph;
import bme.mit.graph.Node;

/**
 * Creates a graph by parsing the .gv file.
 * 
 * @author verboczy
 *
 */
public class Parser {

	Logger log = LoggerFactory.getLogger(Parser.class);

	private Set<String> nodes;
	private Set<String> edges;

	public Parser() {
		nodes = new HashSet<>();
		edges = new HashSet<>();
	}

	/**
	 * Create a graph from the graph representation found in the file.
	 * 
	 * @param file
	 *            - the path to the file, which contains the graph
	 *            representation
	 * @return a new Graph
	 */
	public Graph createGraph(String file) {

		Graph graph = new Graph();

		try (BufferedReader bf = new BufferedReader(new FileReader(file))) {

			String line;
			// eg. line: A -> B a 1
			while ((line = bf.readLine()) != null) {

				String[] stringArray = line.split(" ");

				if (stringArray.length == 1) {
					nodes.add(stringArray[0]);
				} else {
					nodes.add(stringArray[0]);
					nodes.add(stringArray[2]);
					loadIntoEdge(stringArray);
				}
			}

		} catch (Exception e) {
			log.error("problem with the input graph file", e);
		}

		makeEdgeList(graph);

		return graph;
	}
	
	private void loadIntoEdge(String[] stringArray) {
		try {
			edges.add(stringArray[0] + " " + stringArray[2] + " " + stringArray[3] + " " + stringArray[4]);
		} catch (IndexOutOfBoundsException e) {
			log.error("could not add input or output to the edge: " + stringArray[0] + " " + stringArray[2],
					e);
			edges.add(stringArray[0] + " " + stringArray[2]);
		}
	}

	/**
	 * Sets the edge list of all node.
	 * 
	 * @param graph
	 */
	public void makeEdgeList(Graph graph) {

		for (String name : nodes) {
			Node node = new Node(name);
			graph.addNode(node);
		}

		for (String string : edges) {
			Edge edge = new Edge();

			String[] sArray = string.split(" ");
			for (Node node : graph.getNodes()) {
				if (node.getName().equals(sArray[0])) {
					edge.setStartNode(node);
				}
				if (node.getName().equals(sArray[1])) {
					edge.setEndNode(node);
				}
			}

			try {
				edge.setInputLabel(sArray[2]);
				edge.setOutputLabel(sArray[3]);
			} catch (IndexOutOfBoundsException e) {
				log.error("this edge does not have label.", e);
			}

			graph.addEdge(edge);
		}
	}

}
