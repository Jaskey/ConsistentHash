# Consistent Hash Implementation in Java

Reference http://www.codeproject.com/Articles/56138/Consistent-hashing

## Get Started

        //initialize 4 service node
        MyServiceNode node1 = new MyServiceNode("IDC1","127.0.0.1",8080);
        MyServiceNode node2 = new MyServiceNode("IDC1","127.0.0.1",8081);
        MyServiceNode node3 = new MyServiceNode("IDC1","127.0.0.1",8082);
        MyServiceNode node4 = new MyServiceNode("IDC1","127.0.0.1",8084);

        //hash them to hash ring
        ConsistentHashRouter<MyServiceNode> consistentHashRouter = new ConsistentHashRouter<>(Arrays.asList(node1,node2,node3,node4),10);//10 virtual node

        String requestIp = "192.168.0.1";
        System.out.println(requestIp + " is route to " + consistentHashRouter.routeNode(requestIp));

  check sameple of `MyServiceNode.java` for more details



## Developer Doc

### Node

Any class that implements `Node` can be mapped to `ConsistentHashRouter`.

### VirtualNode

Your custom `Node` represents a real physical node, which supports numbers of virtual nodes , the replicas of physical node.

When adding new `Node` to the `ConsistentHashRouter`, you can specify how many virtual nodes should be replicated.

### HashFunction

By default , `ConsistentHashRouter` will use MD5 to hash a node, you may specify your custom hash function by implementing `HashFunction`


----------------------------------------------------------------------------------------------

# Java 实现的一致性哈希 

原理参考 http://www.codeproject.com/Articles/56138/Consistent-hashing

## 快速开始

        //初始化4个服务节点
        MyServiceNode node1 = new MyServiceNode("IDC1","127.0.0.1",8080);
        MyServiceNode node2 = new MyServiceNode("IDC1","127.0.0.1",8081);
        MyServiceNode node3 = new MyServiceNode("IDC1","127.0.0.1",8082);
        MyServiceNode node4 = new MyServiceNode("IDC1","127.0.0.1",8084);

        //把节点哈希到哈希环中
        ConsistentHashRouter<MyServiceNode> consistentHashRouter = new ConsistentHashRouter<>(Arrays.asList(node1,node2,node3,node4),10);//10 virtual node

        String requestIp = "192.168.0.1";
        System.out.println(requestIp + " is route to " + consistentHashRouter.routeNode(requestIp));

  更多的使用的实例请参看： `MyServiceNode.java` for more details



## 开发者文档

### Node（节点）

任意一个实现了`Node`接口的类的对象都可以映射到`ConsistentHashRouter`中

### VirtualNode（虚拟节点）

自定义的 `Node`代表一个物理的节点，这些物理节点支持指定一些虚拟节点作为物理节点的复制（replicas）。当需要增加`Node` 到 `ConsistentHashRouter`时，你可以指定这个节点需要复制有多少个虚拟节点。


### HashFunction（哈希函数）

默认情况下， `ConsistentHashRouter` 会使用MD5去哈希一个节点，你也可以通过去实现`HashFunction`指定自己的自定义哈希函数



