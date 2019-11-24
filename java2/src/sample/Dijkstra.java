package sample;


import java.util.*;

class Dijkstra {
    private Set<Node> nodes;
    private boolean directed;

    Dijkstra(boolean directed) {
        this.directed = directed;
        nodes = new HashSet<>();
    }
//
//    public void addNode(Node... n) {
//        // We're using a var arg method so we don't have to call
//        // addNode repeatedly
//        nodes.addAll(Arrays.asList(n));
//    }

    void addEdge(Node source, Node destination, double weight) {

        nodes.add(source);
        nodes.add(destination);

        // We're using addEdgeHelper to make sure we don't have duplicate edges
        addEdgeHelper(source, destination, weight);

        if (!directed && source != destination) {
            addEdgeHelper(destination, source, weight);
        }
    }

    void ModifyEdgeWeight(Node a, Node b, double weight) {

        for (Edge edge : a.edges) {
            if (edge.source == a && edge.destination == b) {
                // Update the value
                edge.weight = weight;
                return;
            }
        }
    }

    boolean DeleteEd(Node a, Node b) {
        for (Edge edge : a.edges) {
            if (edge.source == a && edge.destination == b) {
                // Update the value in case it's a different one now
                a.edges.remove(edge);
                return true;
            }
        }
        return false;
    }
    void DeleteNo(Node from){
        for(Node node : nodes){
            for (Edge edge : node.edges) {
//                System.out.println(edge.source.name+" "+edge.destination.name+" "+edge.weight);
                if (edge.source == from || edge.destination == from) {
                    node.edges.remove(edge);
//                    System.out.println(edge.source.name+" "+edge.destination.name+" "+edge.weight);
                }
            }
        }
        nodes.remove(from);
    }

    void copyEdge(ArrayList<Edge> edges){
        for(Node node : nodes){
            edges.addAll(node.edges);
        }
    }

    private void addEdgeHelper(Node a, Node b, double weight) {

        for (Edge edge : a.edges) {
            if (edge.source == a && edge.destination == b) {
                // Update the value in case it's a different one now
                edge.weight = weight;
                return;
            }
        }
        // If it hasn't been added already
        a.edges.add(new Edge(a, b, weight));
    }

    boolean hasEdge(Node source, Node destination) {
        LinkedList<Edge> edges = source.edges;
        for (Edge edge : edges) {
            if (edge.destination == destination) {
                return true;
            }
        }
        return false;
    }

    double Weight(Node source, Node destination) {
        LinkedList<Edge> edges = source.edges;
        for (Edge edge : edges) {
            if (edge.destination == destination) {
                return edge.weight;
            }
        }
        return 0d;
    }

    void resetNodesVisited() {
        for (Node node : nodes) {
            node.unvisited();
        }
    }
    String DijkstraShortestPath(Node start, Node end) {

        String output ="";
        HashMap<Node, Node> changedAt = new HashMap<>();
        changedAt.put(start, null);

        HashMap<Node, Double> shortestPathMap = new HashMap<>();

        for (Node node : nodes) {
            if (node == start)
                shortestPathMap.put(start, 0.0);
            else shortestPathMap.put(node, Double.POSITIVE_INFINITY);
        }

        for (Edge edge : start.edges) {
            shortestPathMap.put(edge.destination, edge.weight);
            changedAt.put(edge.destination, start);
        }

        start.visit();

        while (true) {
            Node currentNode = closestReachableUnvisited(shortestPathMap);

            if (currentNode == null) {
                return ("There isn't a path between " + start.name + " and " + end.name);
            }

            if (currentNode == end) {

                Node child = end;

                StringBuilder path = new StringBuilder(end.name);
                while (true) {
                    Node parent = changedAt.get(child);
                    if (parent == null) {
                        break;
                    }

                    path.insert(0, parent.name + "->");
                    child = parent;
                }
                output += path;
//                System.out.println(path);
//                output += ("\nThe path costs: " + shortestPathMap.get(end));
                return output;
            }
            currentNode.visit();

            for (Edge edge : currentNode.edges) {
                if (edge.destination.isVisited())
                    continue;

                if (shortestPathMap.get(currentNode)
                        + edge.weight
                        < shortestPathMap.get(edge.destination)) {
                    shortestPathMap.put(edge.destination,
                            shortestPathMap.get(currentNode) + edge.weight);
                    changedAt.put(edge.destination, currentNode);
                }
            }
        }
    }
    Stack<Node> animatePath(Node start, Node end) {

        Stack<Node> path = new Stack<>();
        HashMap<Node, Node> changedAt = new HashMap<>();
        changedAt.put(start, null);

        HashMap<Node, Double> shortestPathMap = new HashMap<>();

        for (Node node : nodes) {
            if (node == start)
                shortestPathMap.put(start, 0.0);
            else shortestPathMap.put(node, Double.POSITIVE_INFINITY);
        }

        for (Edge edge : start.edges) {
            shortestPathMap.put(edge.destination, edge.weight);
            changedAt.put(edge.destination, start);
        }

        start.visit();

        while (true) {
            Node currentNode = closestReachableUnvisited(shortestPathMap);

            if (currentNode == null) {
                return null;
            }

            if (currentNode == end) {

                Node child = end;
                path.push(child);
                while (true) {
                    Node parent = changedAt.get(child);
                    if (parent == null) {
                        break;
                    }

                    path.push(parent);
                    child = parent;
                }
                return path;
            }
            currentNode.visit();

            for (Edge edge : currentNode.edges) {
                if (edge.destination.isVisited())
                    continue;

                if (shortestPathMap.get(currentNode)
                        + edge.weight
                        < shortestPathMap.get(edge.destination)) {
                    shortestPathMap.put(edge.destination,
                            shortestPathMap.get(currentNode) + edge.weight);
                    changedAt.put(edge.destination, currentNode);
                }
            }
        }
    }
    private Node closestReachableUnvisited(HashMap<Node, Double> shortestPathMap) {

        double shortestDistance = Double.POSITIVE_INFINITY;
        Node closestReachableNode = null;
        for (Node node : nodes) {
            if (node.isVisited())
                continue;

            double currentDistance = shortestPathMap.get(node);
            if (currentDistance == Double.POSITIVE_INFINITY)
                continue;

            if (currentDistance < shortestDistance) {
                shortestDistance = currentDistance;
                closestReachableNode = node;
            }
        }
        return closestReachableNode;
    }

}
