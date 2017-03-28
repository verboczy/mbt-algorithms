package bme.mit.sequences;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import bme.mit.graph.GraphLoader;

@RunWith(Parameterized.class)
public class SequenceParameterizedTest {
		
	@Parameters(name = "{2}")
	public static List<Object[]> data() {
		return Arrays.asList(new Object[][] {
			{"ba", "", "gThreeNodesSixEdges.gv"},
			{"b", "", "gFourNodesEightEdges.gv"},
			{"ca", "", "gFourNodesEightEdgesABC.gv"},
			{"cd", "", "gFourNodesEightEdgesABCD.gv"},
		});
	}	
	
	private static GraphLoader graphLoader = new GraphLoader();
	private static Sequence homingSequence = new HomingSequence();
	private static Sequence syncSequence = new SynchronizingSequence();
	private String homingExpected;
	private String syncExpected;
	private String fileName;
		
	public SequenceParameterizedTest(String expHoming, String expSync, String file) {
		homingExpected = expHoming;
		syncExpected = expSync;
		fileName = file;
	}
		
	@Test
	public void testHoming() {
		Assert.assertEquals(homingExpected, homingSequence.getSequence(graphLoader.loadGraph(fileName)));
	}
	
	@Test
	public void testSynchronizing() {
		Assert.assertEquals(syncExpected, syncSequence.getSequence(graphLoader.loadGraph(fileName)));
	}
	
}
