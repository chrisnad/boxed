package com.pubstack.homework.ws.boxed.api.delegate;

import com.pubstack.homework.ws.boxed.api.MergeApiDelegate;
import com.pubstack.homework.ws.boxed.bean.DataSourceUnit;
import com.pubstack.homework.ws.boxed.bean.utils.DataLoader;
import com.pubstack.homework.ws.boxed.model.Aggregation;
import com.pubstack.homework.ws.boxed.model.FullPath;
import com.pubstack.homework.ws.boxed.model.MergeObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class MergeApiDelegateImpl extends ApiDelegate implements MergeApiDelegate {

    /**
     * The log.
     */
    Logger log = LoggerFactory.getLogger(MergeApiDelegateImpl.class);


    @Override
    public ResponseEntity<MergeObject> merge(FullPath strPath) {
        ResponseEntity<MergeObject> re = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Path path = Paths.get(strPath.getPathString());
        if (Files.exists(path)) {
            Map<String, HashMap<String, DataSourceUnit>> dataMap = DataLoader.load(path);



            Aggregation agg = new Aggregation();
            agg.setTarget("TO");
            agg.setCount("BE");
            MergeObject mo = new MergeObject();
            mo.addAggregateObjectItem(agg);
            re = new ResponseEntity<>(mo, HttpStatus.OK);
        }
        return re;
    }
}
