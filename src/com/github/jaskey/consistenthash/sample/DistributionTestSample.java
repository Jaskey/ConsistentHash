/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jaskey.consistenthash.sample;

import com.github.jaskey.consistenthash.ConsistentHashRouter;
import com.github.jaskey.consistenthash.Node;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * a test sample to test the hash function routing distribution, by default ConsistentHashRouter will use a inner MD5 hash algorithm
 */
public class DistributionTestSample{

    public static void main(String[] args) {
        //initialize 4 service node
        MyServiceNode node1 = new MyServiceNode("IDC1", "10.8.1.11", 8080);
        MyServiceNode node2 = new MyServiceNode("IDC1", "10.8.3.99", 8080);
        MyServiceNode node3 = new MyServiceNode("IDC1", "10.9.11.105", 8080);
        MyServiceNode node4 = new MyServiceNode("IDC1", "10.10.9.210", 8080);

        //hash them to hash ring.
        // 1. By default a MD5 hash function will be used, you can modify a little if you want to test your own hash funtion
        // 2. Another factor which is will influence distribution is the numbers of virtual nodes, you can change this factor , below, we use 20 virtual nodes for each physical node.
        ConsistentHashRouter<MyServiceNode> consistentHashRouter = new ConsistentHashRouter<>(Arrays.asList(node1,node2,node3,node4),20);//20 virtual node

        List<String> requestIps = new ArrayList<>();
        for(int i = 0; i < 100000; i++) {
            requestIps.add(getRandomIp());
        }


        System.out.println("==========output distribution result==========");
        System.out.println(goRoute(consistentHashRouter, requestIps.toArray(new String[requestIps.size()])).toString());


    }

    private static TreeMap<String, AtomicInteger> goRoute(ConsistentHashRouter<MyServiceNode> consistentHashRouter , String ... requestIps){
        TreeMap<String, AtomicInteger> res = new TreeMap<>();
        for (String requestIp: requestIps) {
            MyServiceNode mynode  = consistentHashRouter.routeNode(requestIp);
            res.putIfAbsent(mynode.getKey(), new AtomicInteger());
            res.get(mynode.getKey()).incrementAndGet();
            System.out.println(requestIp + " is routed to " + mynode);
        }
        return res;
    }

    private static String getRandomIp() {
        int[][] range = {{607649792, 608174079},// 36.56.0.0-36.63.255.255
                {1038614528, 1039007743},// 61.232.0.0-61.237.255.255
                {1783627776, 1784676351},// 106.80.0.0-106.95.255.255
                {2035023872, 2035154943},// 121.76.0.0-121.77.255.255
                {2078801920, 2079064063},// 123.232.0.0-123.235.255.255
                {-1950089216, -1948778497},// 139.196.0.0-139.215.255.255
                {-1425539072, -1425014785},// 171.8.0.0-171.15.255.255
                {-1236271104, -1235419137},// 182.80.0.0-182.92.255.255
                {-770113536, -768606209},// 210.25.0.0-210.47.255.255
                {-569376768, -564133889}, // 222.16.0.0-222.95.255.255
        };

        Random rdint = new Random();
        int index = rdint.nextInt(10);
        String ip = num2ip(range[index][0] + new Random().nextInt(range[index][1] - range[index][0]));
        return ip;
    }

    private static String num2ip(int ip) {
        int[] b = new int[4];
        String x = "";

        b[0] = (int) ((ip >> 24) & 0xff);
        b[1] = (int) ((ip >> 16) & 0xff);
        b[2] = (int) ((ip >> 8) & 0xff);
        b[3] = (int) (ip & 0xff);
        x = Integer.toString(b[0]) + "." + Integer.toString(b[1]) + "." + Integer.toString(b[2]) + "." + Integer.toString(b[3]);

        return x;
    }
}
