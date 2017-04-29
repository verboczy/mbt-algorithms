package bme.mit.sequences;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bme.mit.graph.GraphLoader;
import bme.mit.helper.TestHelper;

@RunWith(Parameterized.class)
public class SequenceParameterizedTest {

	
	private static GraphLoader graphLoader = new GraphLoader();
	private static Sequence homingSequence = new HomingSequence();
	private static Sequence syncSequence = new SynchronizingSequence();
	private String syncExpected;
	private String fileName;
	private TestHelper helper;
	private Logger log = LoggerFactory.getLogger(SequenceParameterizedTest.class);
	
	public SequenceParameterizedTest(String expSync, String file) {
		syncExpected = expSync;
		fileName = file;
		helper = new TestHelper();
	}
		
	@Parameters(name = "{1}")
	public static List<Object[]> data() {
		return Arrays.asList(new Object[][] {
			{"", "circleFourEdges.gv"},
			{"", "circleFourEdgesWithLoopsWithSameInput.gv"},
			{"", "circleFourEdgesWithLoopsWithDiffInput.gv"},
			{"", "circleTwoEdges.gv"},
			{"", "circleTwoEdgesWithLoops.gv"},
			{"", "homingExample.gv"},
			//{"", "pathOneEdge.gv"},
			//{"", "pathOneEdgeWithLoop.gv"},
			{"", "singletonNode.gv"},
			{"", "singletonNodeWithLoop.gv"}
		});
	}
		
	@Test
	public void testHoming() {
		String seq = homingSequence.getSequence(graphLoader.loadGraph(fileName));
		log.info(seq);
		Assert.assertEquals(true, helper.checkHomingSequence(graphLoader.loadGraph(fileName), seq));
	}
	
	@Test
	public void testSynchronizing() {
		Assert.assertEquals(syncExpected, syncSequence.getSequence(graphLoader.loadGraph(fileName)));
	}
	
}
