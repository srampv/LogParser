package com.seo;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class LogParser {

    List<JsonData> dataObjects = new ArrayList<>();

    public List<JsonData> getDataObjects() {
        return dataObjects.stream().filter(e -> e.getPh() != null).collect(Collectors.toList());
    }

    public void setDataObjects(List<JsonData> dataObjects) {
        this.dataObjects = dataObjects;
    }

//    public static void main(String[] args) throws IOException {
//
//        Map<String, Set<JsonData>> list = new LogParser().processFile("/Users/aavsp/json-data.txt");
//
//        list.forEach((u, v) -> {
//            System.out.println(((JsonData) v.stream().toArray()[0]).getNm().split("\\.")[1] + ":" + v.size());
//        });
//
//
//    }

    public Map<String, Set<JsonData>> processFile(String fileNameWithPath) throws IOException {
        List<String> lines = readFile(fileNameWithPath);
        for (String data : lines) {
            JsonData mapper = new ObjectMapper().readValue(data, JsonData.class);
            System.out.println(mapper.getBg());
            System.out.println(mapper.getPh());
            dataObjects.add(mapper);
        }
        setDataObjects(dataObjects);
        Map<String, Set<JsonData>> map = getDataObjects().stream().collect(Collectors.groupingBy(JsonData::getPh, Collectors.toSet()));
        System.out.println(map);
        return map;
    }

    public List<String> readFile(String fileNameWithPath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileNameWithPath));
        return lines;
    }
}

