package com.pubstack.homework.ws.boxed.bean.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pubstack.homework.ws.boxed.bean.DataSourceUnit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataLoader {

    private DataLoader() {}

    public static Map<String, HashMap<String, DataSourceUnit>> load(Path path){
        List<DataSourceUnit> dataSourceList = loadFromSource(path);
        return toMap(dataSourceList);
    }

    private static List<DataSourceUnit> loadFromSource(Path path) {
        List<DataSourceUnit> dataSourceList = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(path)) {
            List<String> fileList = walk.map(Path::toString)
                    .filter(f -> f.endsWith(".json")).collect(Collectors.toList());

            final ObjectMapper mapper = new ObjectMapper();
            for (String pathToFile : fileList) {
                final String jsonStr = new String(Files.readAllBytes(Paths.get(pathToFile)));
                dataSourceList.add(mapper.readValue(jsonStr, DataSourceUnit.class).computeHierarchy());
            }
        } catch (IOException e) {
            // we dont throw this way .json files with bad formatting would just be ignored.
            e.printStackTrace();
        }
        return dataSourceList;
    }

    private static HashMap<String, HashMap<String, DataSourceUnit>> toMap (List<DataSourceUnit> dataSourceList){
        HashMap<String, List<DataSourceUnit>> timeMap = new HashMap<>();
        for (DataSourceUnit dataUnit : dataSourceList) {
            if (!timeMap.containsKey(dataUnit.getTime())) {
                List<DataSourceUnit> list = new ArrayList<>();
                list.add(dataUnit);
                timeMap.put(dataUnit.getTime(), list);
            } else {
                timeMap.get(dataUnit.getTime()).add(dataUnit);
            }
        }
        HashMap<String, HashMap<String, DataSourceUnit>> dataMap = new HashMap<>();
        for (String date : timeMap.keySet()) {
            HashMap<String, DataSourceUnit> targetMap = new HashMap<>();
            for (DataSourceUnit dataUnit : timeMap.get(date)) {
                if (!targetMap.containsKey(dataUnit.getTarget_path())) {
                    targetMap.put(dataUnit.getTarget_path(), dataUnit);
                } else {
                    targetMap.get(dataUnit.getTarget_path()).addCount(dataUnit.getCount());
                }
            }
            dataMap.put(date, targetMap);
        }
        return dataMap;
    }

}
