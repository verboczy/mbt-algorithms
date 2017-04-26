package bme.mit.algorithms;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bme.mit.graph.Graph;
import bme.mit.graph.GraphLoader;
import bme.mit.helper.TestHelper;

@RunWith(Parameterized.class)
public class AlgorithmParameterizedTest {
	
	private static GraphLoader graphLoader = new GraphLoader();
	private static Algorithm myAlgorithm = new MyAlgorithm();
	private static Algorithm randomAlgorithm = new RandomAlgorithm();
	private String initName;
	private String fileName;
	private TestHelper helper;
	private Logger log = LoggerFactory.getLogger(AlgorithmParameterizedTest.class);
	
	public AlgorithmParameterizedTest(String init, String file) {
		initName = init;
		fileName = file;
		helper = new TestHelper();		
	}
	
	@Parameters(name = "{1}")
	public static List<Object[]> data() {
		return Arrays.asList(new Object[][] {			
			{"s1", "graphNotConnected.gv"},
			{"s1", "graphNotConnectedWithEdges.gv"},
			{"s1", "graphConnectedButCannotTraverse.gv"},
			{"s1", "graphK3.gv"},
			{"s1", "graphK4.gv"},
			{"s1", "graphTriangleWithOneParallelEdge.gv"},
			{"s1", "graphWithSource.gv"},
			{"s1", "graphWithSink.gv"}
		});
	}
	
	@Test
	public void testMyTraverseNodesFromGivenNode() {
		String path = myAlgorithm.getAllNodeVisitedFromGivenNode(graphLoader.loadGraph(fileName), initName);
		log.info("Visit nodes with myAlgorithm from a given node: " + path);
		assertEquals(true, helper.checkNodePath(graphLoader.loadGraph(fileName), path));
	}
	
	@Test
	public void testRandomTraverseNodesFromGivenNode() {
		String path = randomAlgorithm.getAllNodeVisitedFromGivenNode(graphLoader.loadGraph(fileName), initName);		
		log.info("Visit nodes with randomAlgorithm from a given node: " + path);
		assertEquals(true, helper.checkNodePath(graphLoader.loadGraph(fileName), path));
	}
	
	@Test
	public void testMyTraverseNodesFromRandomNode() {
		String path = myAlgorithm.getAllNodeVisited(graphLoader.loadGraph(fileName));	
		log.info("Visit nodes with myAlgorithm from a random node: " + path);
		assertEquals(true, helper.checkNodePath(graphLoader.loadGraph(fileName), path));
	}
	
	@Test
	public void testRandomTraverseNodesFromRandomNode() {
		String path = randomAlgorithm.getAllNodeVisited(graphLoader.loadGraph(fileName));
		log.info("Visit nodes with randomAlgorithm from a random node: " + path);
		assertEquals(true, helper.checkNodePath(graphLoader.loadGraph(fileName), path));
	}
	
	@Test
	public void testMyTraverseEdgesFromGivenNode() {
		String path = myAlgorithm.getAllEdgeVisitedFromGivenNode(graphLoader.loadGraph(fileName), initName);
		log.info("Visit edges with myAlgorithm from a given node: " + path);
		assertEquals(true, helper.checkEdgePath(graphLoader.loadGraph(fileName), path));
	}
	
	@Test
	public void testRandomTraverseEdgesFromGivenNode() {
		String path = randomAlgorithm.getAllEdgeVisitedFromGivenNode(graphLoader.loadGraph(fileName), initName);
		log.info("Visit edges with randomAlgorithm from a given node: " + path);
		assertEquals(true, helper.checkEdgePath(graphLoader.loadGraph(fileName), path));
	}
	
	@Test
	public void testMyTraverseEdgesFromRandomNode() {
		String path = myAlgorithm.getAllEdgeVisited(graphLoader.loadGraph(fileName));
		log.info("Visit edges with myAlgorithm from a random node: " + path);
		assertEquals(true, helper.checkEdgePath(graphLoader.loadGraph(fileName), path));
	}
	
	@Test
	public void testRandomTraverseEdgesFromRandomNode() {
		String path = randomAlgorithm.getAllEdgeVisited(graphLoader.loadGraph(fileName));
		log.info("Visit edges with randomAlgorithm from a random node: " + path);
		assertEquals(true, helper.checkEdgePath(graphLoader.loadGraph(fileName), path));
	}
	
	
	@Test
	public void testEulerCircle() {
		Graph graph = graphLoader.loadGraph(fileName);
		String path = myAlgorithm.eulerCircle(graph, initName);
		log.info("Euler circle: " + path);
		assertEquals(true, helper.checkEulerCircle(graphLoader.loadGraph(fileName), graph, path));
	}	
	
	/*
	@Test
	public void testDeBruijnSequence() {
		
	}
	*/
}
