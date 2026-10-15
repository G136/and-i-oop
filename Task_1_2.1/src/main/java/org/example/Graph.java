package org.example;

import java.util.Set;

public interface Graph {

    Set<Integer> getVertices();

    boolean hasVertex(int vertex);

    boolean addVertex(int vertex);

    boolean removeVertex(int vertex);

    Set<Integer> getNeighbors(int vertex);


    Set<Edge> getEdges();

    boolean hasEdge(int from, int to);

    boolean addEdge(int from, int to);

    boolean removeEdge(int from, int to);


    void clear();
}

