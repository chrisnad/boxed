package com.pubstack.homework.ws.boxed;

import com.pubstack.homework.ws.boxed.bean.UnitDataSource;
import com.pubstack.homework.ws.boxed.bean.utils.DataLoader;
import com.pubstack.homework.ws.boxed.model.Aggregation;
import com.pubstack.homework.ws.boxed.model.MergeObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BoxedApplicationTests {

	@Test
	public void mergeTest() throws Exception {
		URI uri = Thread.currentThread().getContextClassLoader().getSystemResource("file.json").toURI();
		Path path =  Paths.get(uri).getParent();

		Map<String, HashMap<Long, UnitDataSource>> dataMap = DataLoader.load(path);

		MergeObject mo = new MergeObject();
		for (HashMap<Long, UnitDataSource> nodeMap : dataMap.values()){
			long h = 1;
			while (nodeMap.size()>0) {
				Aggregation agg = new Aggregation();
				agg.setTime(nodeMap.values().iterator().next().getTime());
				agg.setTarget(nodeMap.get(h).getTarget_path());
				agg.setCount(nodeMap.values().stream().mapToLong(UnitDataSource::getCount).sum());
				mo.addAggregateObjectItem(agg);
				nodeMap.remove(h);
				h++;
			}
		}
		assertTrue(mo.getAggregateObject().size() > 0);
	}

}
