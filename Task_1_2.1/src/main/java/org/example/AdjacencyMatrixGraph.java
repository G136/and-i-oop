package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdjacencyMatrixGraph extends AbstractGraph {
    private final List<Integer> vertices = new ArrayList<>();
    private final List<List<Boolean>> matrix = new ArrayList<>();

    @Override
    protected boolean doAddVertex(int vertex) {
        vertices.add(vertex);
        for (List<Boolean> row : matrix) {
            row.add(false);
        }
        List<Boolean> newRow = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            newRow.add(false);
        }
        matrix.add(newRow);
        return true;
    }

    @Override
    protected boolean doRemoveVertex(int vertex) {
        int index = vertices.indexOf(vertex);
        if (index < 0) {
            return false;
        }
        vertices.remove(index);
        matrix.remove(index);
        for (List<Boolean> row : matrix) {
            row.remove(index);
        }
        return true;
    }

    @Override
    protected boolean doAddEdge(Edge edge) {
        int fromIndex = vertices.indexOf(edge.from());
        int toIndex = vertices.indexOf(edge.to());

        boolean thereWasNot = !matrix.get(fromIndex).get(toIndex);
        matrix.get(fromIndex).set(toIndex, true);
        return thereWasNot;
    }

    @Override
    protected boolean doRemoveEdge(Edge edge) {
        int fromIndex = vertices.indexOf(edge.from());
        int toIndex = vertices.indexOf(edge.to());

        boolean thereWas = matrix.get(fromIndex).get(toIndex);
        matrix.get(fromIndex).set(toIndex, false);
        return thereWas;
    }

    @Override
    protected boolean doHasEdge(Edge edge) {
        int fromIndex = vertices.indexOf(edge.from());
        int toIndex = vertices.indexOf(edge.to());
        return fromIndex >= 0 && toIndex >= 0 && matrix.get(fromIndex).get(toIndex);
    }

    @Override
    protected Set<Edge> doGetEdges() {
        HashSet<Edge> edges = new HashSet<>();
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                if (!matrix.get(i).get(j)) {
                    continue;
                }
                Integer from = vertices.get(i);
                Integer to = vertices.get(j);
                edges.add(new Edge(from, to));
            }
        }
        return edges;
    }

    @Override
    protected Set<Integer> doGetNeighbors(int vertex) {
        int index = vertices.indexOf(vertex);
        assert(index >= 0);

        HashSet<Integer> neighbors = new HashSet<>();
        for (int j = 0; j < vertices.size(); j++) {
            if (matrix.get(index).get(j)) {
                neighbors.add(vertices.get(j));
            }
        }
        return neighbors;
    }

    @Override
    protected void doClear() {
        vertices.clear();
        matrix.clear();
    }
}

