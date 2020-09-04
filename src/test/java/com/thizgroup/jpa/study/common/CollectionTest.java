package com.thizgroup.jpa.study.common;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.*;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import java.util.*;

public class CollectionTest {

    @Test
    public void test1(){
        // 普通Collection的创建
        List<String> list = Lists.newArrayList();
        Set<String> set = Sets.newHashSet();
        Map<String, String> map = Maps.newHashMap();

// 不变Collection的创建
        ImmutableList<String> iList = ImmutableList.of("a", "b", "c");
        ImmutableSet<String> iSet = ImmutableSet.of("e1", "e2");
        ImmutableMap<String, String> iMap = ImmutableMap.of("k1", "v1", "k2", "v2");

        iList.add("d");
    }

    @Test
    public void test2(){
        Map<String,List<Integer>> map = new HashMap<String,List<Integer>>();
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        map.put("aa", list);
        System.out.println(map.get("aa"));//[1, 2]

        Multimap<String,Integer> map1 = ArrayListMultimap.create();
        map1.put("aa", 1);
        map1.put("aa", 2);
        System.out.println(map1.get("aa"));  //[1, 2]
    }

    @Test
    public void test3(){
        BiMap<String, String> biMap = HashBiMap.create();
        biMap.put("a","1");
        if(!biMap.containsKey("b") && !biMap.containsValue("1")){
            biMap.put("b","1");
        }
    }

    @Test
    public void test4(){
        //use guava
        List<String> list = new ArrayList<String>();
        list.add("aa");
        list.add("bb");
        list.add("cc");

        String join = Joiner.on(",").join(list);
        System.out.println(join);
    }

    @Test
    public void test5(){
        Map<String, Integer> map = Maps.newHashMap();
        map.put("xiaoming", 12);
        map.put("xiaohong",13);
        String join = Joiner.on("&").withKeyValueSeparator("=").join(map);
        System.out.println(join);
    }

    @Test
    public void test6(){
        String str = "1-2-3-4-5-6";
        List<String> strings = Splitter.on("-").splitToList(str);
        System.out.println(ArrayUtils.toString(strings));
    }

    @Test
    public void test7(){
        String str = "1-2-3-4-  5-  6   ";
        List<String> strings = Splitter.on("-").omitEmptyStrings().trimResults().splitToList(str);
        System.out.println(strings);
    }

    @Test
    public void test8(){
        String str = "xiaoming=11&xiaohong=23";
        Map<String, String> stringMap = Splitter.on("&").withKeyValueSeparator("=").split(str);
        System.out.println(stringMap);
    }

    @Test
    public void test9(){
        String input = "aa.dd,,ff,,.";
        List<String> strings = Splitter.onPattern("[.|,]").splitToList(input);
        System.out.println(strings);
    }

    @Test
    public void test10(){
        HashSet setA = Sets.newHashSet(1, 2, 3, 4, 5);
        HashSet setB = Sets.newHashSet(4, 5, 6, 7, 8);
        Sets.SetView union = Sets.union(setA, setB);
        System.out.println(union);
        Sets.SetView difference = Sets.difference(setA, setB);
        System.out.println(difference);
        Sets.SetView difference1 = Sets.difference(setB, setA);
        System.out.println(difference1);
        Sets.SetView intersection = Sets.intersection(setA, setB);
        System.out.println(intersection);
        Set copy = intersection.copyInto(new HashSet());
        System.out.println(copy);
    }

    @Test
    public void test11(){
        HashMap<String, Integer> mapA = Maps.newHashMap();
        mapA.put("a", 1);mapA.put("b", 2);mapA.put("c", 3);

        HashMap<String, Integer> mapB = Maps.newHashMap();
        mapB.put("b", 20);mapB.put("c", 3);mapB.put("d", 4);

        MapDifference<String, Integer> difference = Maps.difference(mapA, mapB);
        System.out.println(difference.entriesDiffering());
    }

}
