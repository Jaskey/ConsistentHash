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


