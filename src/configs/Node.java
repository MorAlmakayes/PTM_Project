package configs;

import graph.Message;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node {
    private String name;
    private List<Node> edges;
    private Message msg;

    public Node(String name) {
        this.name = name;
        this.edges = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }
    public List<Node> getEdges() {
        return this.edges;
    }
    public Message getMsg() {
        return this.msg;
    }

    public void setMsg(Message m) {
        this.msg = m;
    }
    public void setEdges(List<Node> edges) {
        this.edges = edges;
    }
    public void setMessage(String s) {
        this.msg = new Message(s);
    }

    public void addEdge(Node n) {
        edges.add(n);
    }

    public boolean hasCycles() {
        Set<Node> visited = new HashSet<>();
        Set<Node> inStack = new HashSet<>();
        return dfsCheck(this, visited, inStack);
    }

    private boolean dfsCheck(Node current, Set<Node> visited, Set<Node> inStack) {
        if (!visited.contains(current)) {
            visited.add(current);
            inStack.add(current);

            for (Node neighbor : current.edges) {

                if (!visited.contains(neighbor) && dfsCheck(neighbor, visited, inStack)) {
                    return true;
                }

                else if (inStack.contains(neighbor)) {
                    return true;
                }
            }
        }
        inStack.remove(current);
        return false;
    }


}