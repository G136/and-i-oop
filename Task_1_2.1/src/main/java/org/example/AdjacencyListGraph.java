package org.example;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AdjacencyListGraph extends AbstractGraph {
    private final Map<Integer, HashSet<Integer>> adjacency = new HashMap<>();

    @Override
    protected boolean doAddVertex(int vertex) {
        adjacency.put(vertex, new HashSet<>());
        return true;
    }

    @Override
    protected boolean doRemoveVertex(int vertex) {
        if (!adjacency.containsKey(vertex)) {
            return false;
        }
        adjacency.remove(vertex);
        for (Set<Integer> neighbors : adjacency.values()) {
            neighbors.remove(vertex);
        }
        return true;
    }

    @Override
    protected boolean doAddEdge(Edge edge) {
        HashSet<Integer> neighbors = adjacency.get(edge.from());
        return neighbors.add(edge.to());
    }

    @Override
    protected boolean doRemoveEdge(Edge edge) {
        HashSet<Integer> neighbors = adjacency.get(edge.from());
        return neighbors.remove(edge.to());
    }

    @Override
    protected boolean doHasEdge(Edge edge) {
        HashSet<Integer> neighbors = adjacency.get(edge.from());
        return neighbors != null && neighbors.contains(edge.to());
    }

    @Override
    protected Set<Edge> doGetEdges() {
        HashSet<Edge> edges = new HashSet<>();
        for (Map.Entry<Integer, HashSet<Integer>> entry : adjacency.entrySet()) {
            Integer from = entry.getKey();
            for (Integer to : entry.getValue()) {
                edges.add(new Edge(from, to));
            }
        }
        return edges;
    }

    @Override
    protected Set<Integer> doGetNeighbors(int vertex) {
        ensureVertex(vertex);
        HashSet<Integer> neighbors = adjacency.get(vertex);
        return new HashSet<>(neighbors);
    }

    @Override
    protected void doClear() {
        adjacency.clear();
    }
}

