package sample;

import java.io.*;
import java.util.*;
class Graph {
    private LinkedList<Node> nodes = new LinkedList<>();
    private Dijkstra adj = new Dijkstra(true);

    String addNode(double x,double y,String name){
        Node temp = new Node(x,y,name);
        nodes.add(temp);
        return ("Node Added Successfully");
    }
    Node getNode(String from){
        for(Node i:nodes){
            if(i.name.equals(from)){
                return i;
            }
        }
        return null;
    }
    String SearchNode(String node) {
        for(Node i:nodes){
            if(i.name.equals(node)){
                return ("X coordinate:"+i.x+"\n"+"Y coordinate:"+i.y);
            }
        }
        return ("Node not Found");
    }

    String DeleteNode(String node){
        for(Node n:nodes){
            if(n.name.equals(node)){
//                nodes.remove(n);
                adj.DeleteNo(n);
                nodes.remove(n);
                return  "Node Deleted";
            }
        }
        return "Node Doesn't exist";
    }

    String ModifyNode(String node,double x,double y){
        for(Node i:nodes){
            if(i.name.equals(node)){
                i.x = x;
                i.y = y;
                return ("Node Modified");
            }
        }
        return ("Node not Found");
    }

    String addEdge(String from,String to,double weight){
        Node fromNode=null,toNode=null;
        for (Node i :nodes) {
            if(i.name.equals(from)){
                fromNode = i;
            }
            if(i.name.equals(to)){
                toNode = i;
            }
        }
        if(fromNode == null)
            return ("Form node not found");
        else if(toNode == null)
            return ("To node not Found");
        else {
            adj.addEdge(fromNode, toNode, weight);
            return ("Edge added Successfully");
        }
    }

    String SearchEdge(String from,String to){
        Node fromNode=null,toNode=null;
        for (Node i :nodes) {
            if(i.name.equals(from)){
                fromNode = i;
            }
            if(i.name.equals(to)){
                toNode = i;
            }
        }
        if(fromNode == null || toNode == null) {
            return ("Edge Not Found");
        }
        else
        {
            if(adj.hasEdge(fromNode, toNode)){
                return ("Edge Found \n"+"Weight is "+adj.Weight(fromNode,toNode));
            }
            else
                return ("Edge Not Found");
        }
    }

    String ModifyEdge(String from,String to,double weight){
        Node fromNode=null,toNode=null;
        for (Node i :nodes) {
            if(i.name.equals(from)){
                fromNode = i;
            }
            if(i.name.equals(to)){
                toNode = i;
            }
        }
        if(fromNode == null || toNode == null)
            return ("Edge not Found");
        else {
            adj.ModifyEdgeWeight(fromNode, toNode,weight);
            return ("Edge Modified Successfully");
        }
    }
    String DeleteEdge(String from,String to){
        Node fromNode=null,toNode=null;
        for (Node i :nodes) {
            if(i.name.equals(from)){
                fromNode = i;
            }
            if(i.name.equals(to)){
                toNode = i;
            }
        }
        if(fromNode == null || toNode == null){
            return ("Edge not Found");
        }
        else if(fromNode == toNode){
            return ("Both Nodes are same!");
        }
        else {

            if(adj.DeleteEd(fromNode, toNode)){
                return ("Edge deleted");
            }
            else
                return ("Edge Not Found");
        }
    }

    String getPath(String from,String to){
        String output;
        Node fromNode=null,toNode=null;
        for (Node i :nodes) {
            if(i.name.equals(from)){
                fromNode = i;
            }
            if(i.name.equals(to)){
                toNode =i;
            }
        }
        output = adj.DijkstraShortestPath(fromNode,toNode);
        adj.resetNodesVisited();
        return output;
    }

    String importFrom(String path){
        FileReader fr;
        try{
            fr = new FileReader(path);
        }
        catch (FileNotFoundException fe){
            return ("File not Found");
        }
        Scanner myReader = new Scanner(fr);
        while (myReader.hasNextLine()) {
            int no_of_nodes;
            try {
                no_of_nodes = Integer.parseInt(myReader.next());
            }catch (NumberFormatException e){
                return "Invalid Input";
            }
            for(int i=0;i<no_of_nodes;i++){
                String name = myReader.next();
                double x,y;
                try {
                    x = Double.parseDouble(myReader.next());
                    y = Double.parseDouble(myReader.next());
                }catch (NumberFormatException e){
                    return "Invalid Input";
                }
                addNode(x,y,name);
            }
            int no_of_edges;
            try {
                no_of_edges = Integer.parseInt(myReader.next());
            }catch (NumberFormatException e){
                return "Invalid Input";
            }
            for(int i=0;i<no_of_edges;i++){
                String from = myReader.next();
                String to = myReader.next();
                double weight;
                try {
                    weight = Double.parseDouble(myReader.next());
                }catch (NumberFormatException e){
                    return "Invalid Input";
                }
                addEdge(from,to,weight);
            }
        }
        myReader.close();
        return "Values Imported";
    }
    String exportTo(String path) throws IOException {

        FileWriter fw;
        ArrayList<Edge> edgeArrayList = new ArrayList<>();
        adj.copyEdge(edgeArrayList);

        try{
            fw = new FileWriter(path);
        } catch (IOException e) {
            return "File not Found";
        }
        fw.write(nodes.size()+"");
        fw.write("\n");
        for(Node n : nodes){
            fw.write(n.x+" "+n.y+" "+n.name+"\n");
        }
        fw.write(edgeArrayList.size()+"");
        fw.write("\n");
        for(Edge e :edgeArrayList){
            fw.write(e.source.name+" "+e.destination.name+" "+e.weight+"\n");
        }
        fw.close();
        return "File Exported";
    }

    LinkedList<Node> getNodes(){
        return nodes;
    }
    Dijkstra getAdj(){
        return adj;
    }

    Stack<Node> getNodePath(String from,String to){
        Node fromNode=null,toNode=null;
        for (Node i :nodes) {
            if(i.name.equals(from)){
                fromNode = i;
            }
            if(i.name.equals(to)){
                toNode = i;
            }
        }
        return adj.animatePath(fromNode,toNode);
    }

}
