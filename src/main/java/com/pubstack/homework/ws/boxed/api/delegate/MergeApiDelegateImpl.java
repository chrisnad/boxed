package com.pubstack.homework.ws.boxed.api.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pubstack.homework.ws.boxed.api.MergeApiDelegate;
import com.pubstack.homework.ws.boxed.bean.UnitDataSource;
import com.pubstack.homework.ws.boxed.bean.utils.DataLoader;
import com.pubstack.homework.ws.boxed.model.Aggregation;
import com.pubstack.homework.ws.boxed.model.FullPath;
import com.pubstack.homework.ws.boxed.model.MergeObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
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
            try {
                new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(new File(path +
                        "/" + path.getFileName() + "-agg.json"), mo.getAggregateObject());
            } catch (IOException e) {
                e.printStackTrace();
            }
            re = new ResponseEntity<>(mo, HttpStatus.OK);
        }
        return re;
    }
}
