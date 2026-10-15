package org.example;

public class Main {
    public static void main(String[] args) {
        Graph graph = new AdjacencyListGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);

        System.out.println(graph);
    }
}