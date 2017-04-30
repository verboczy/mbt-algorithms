package bme.mit.helper;

import org.junit.Test;

import bme.mit.algorithms.Algorithm;
import bme.mit.algorithms.MyAlgorithm;
import bme.mit.graph.Graph;
import bme.mit.graph.GraphLoader;
import bme.mit.sequences.SynchronizingSequence;

public class TestsForCoverage {

	private final static String GRAPH = "graphK3.gv";
	private GraphLoader graphLoader = new GraphLoader();
	private Algorithm algo = new MyAlgorithm();
	/*
	@Test
	public void testAlgorithmNodeByName() {
		algo.getAllNodeVisitedFromGivenNode(graphLoader.loadGraph(GRAPH), "dummy");
		//assertEquals("abc", algo.getAllNodeVisitedFromGivenNode(graphLoader.loadGraph(GRAPH), "dummy"));
	}
	
	@Test
	public void testMakeDual() {
		algo.makeDual(graphLoader.loadGraph(GRAPH));
	}
	
	
	@Test
	public void testCreateGraph() {
		graphLoader.loadGraph("nosuchfile.gv");
	}
	
	@Test
	public void testLoadEdge() {
		graphLoader.loadGraph("testBadGraph.gv");
	}
	
	@Test
	public void testComputeCorres() {
		Graph original = graphLoader.loadGraph("aaa.gv");
		SynchronizingSequence seq = new SynchronizingSequence();
		seq.computeCorrespondingMachine(original);
		seq.printMachine();
	}
	*/
	@Test
	public void testSync() {
		Graph original = graphLoader.loadGraph("aaa.gv");
		SynchronizingSequence seq = new SynchronizingSequence();
		System.out.println(seq.getSequence(original));
		//seq.computeCorrespondingMachine(original);
		//seq.printMachine();
	}
	
}
