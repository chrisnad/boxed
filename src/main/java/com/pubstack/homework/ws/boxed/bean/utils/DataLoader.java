package com.pubstack.homework.ws.boxed.bean.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pubstack.homework.ws.boxed.bean.UnitDataSource;

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

    public static Map<String, HashMap<Long, UnitDataSource>> load(Path path){
        List<UnitDataSource> dataSourceList = loadFromSource(path);
        return toMap(dataSourceList);
    }

    private static List<UnitDataSource> loadFromSource(Path path) {
        List<UnitDataSource> dataSourceList = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(path)) {
            List<String> fileList = walk.map(Path::toString)
                    .filter(f -> f.endsWith(".json")).collect(Collectors.toList());

            final ObjectMapper mapper = new ObjectMapper();
            for (String pathToFile : fileList) {
                final String jsonStr = new String(Files.readAllBytes(Paths.get(pathToFile)));
                dataSourceList.add(mapper.readValue(jsonStr, UnitDataSource.class).computeHierarchy());
            }
        } catch (IOException e) {
            // we dont throw this way .json files with bad formatting would just be ignored.
            e.printStackTrace();
        }
        return dataSourceList;
    }

    private static HashMap<String, HashMap<Long, UnitDataSource>> toMap (List<UnitDataSource> dataSourceList){
        HashMap<String, List<UnitDataSource>> timeMap = new HashMap<>();
        for (UnitDataSource dataUnit : dataSourceList) {
            if (!timeMap.containsKey(dataUnit.getTime())) {
                List<UnitDataSource> list = new ArrayList<>();
                list.add(dataUnit);
                timeMap.put(dataUnit.getTime(), list);
            } else {
                timeMap.get(dataUnit.getTime()).add(dataUnit);
            }
        }
        HashMap<String, HashMap<Long, UnitDataSource>> dataMap = new HashMap<>();
        for (String date : timeMap.keySet()) {
            HashMap<Long, UnitDataSource> targetMap = new HashMap<>();
            for (UnitDataSource dataUnit : timeMap.get(date)) {
                if (!targetMap.containsKey(dataUnit.getHierarchy())) {
                    targetMap.put(dataUnit.getHierarchy(), dataUnit);
                } else {
                    targetMap.get(dataUnit.getHierarchy()).addCount(dataUnit.getCount());
                }
            }
            dataMap.put(date, targetMap);
        }
        return dataMap;
    }

}
