package com.pubstack.homework.ws.boxed;

import com.pubstack.homework.ws.boxed.bean.DataSourceUnit;
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

@SpringBootTest
class BoxedApplicationTests {

	@Test
	public void mergeTest() throws Exception {
		URI uri = Thread.currentThread().getContextClassLoader().getSystemResource("file.json").toURI();
		Path path =  Paths.get(uri).getParent();

		Map<String, HashMap<String, DataSourceUnit>> dataMap = DataLoader.load(path);



		Aggregation agg = new Aggregation();
		agg.setTarget("TO");
		agg.setCount("BE");
		MergeObject mo = new MergeObject();
		mo.addAggregateObjectItem(agg);


	}

}
