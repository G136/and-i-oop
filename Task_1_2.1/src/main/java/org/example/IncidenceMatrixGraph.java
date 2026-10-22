package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Directed graph backed by an incidence matrix. */
public class IncidenceMatrixGraph extends AbstractGraph {
    /**
     * Vertex order used as row indices inside each incidence column.
     */
    private final List<Integer> vertices = new ArrayList<>();

    /**
     * Edge list aligned with {@link #columns} by index.
     */
    private final List<Edge> edges = new ArrayList<>();

    /**
     * Incidence columns, one per edge.
     * -1 for source, 1 for target, and 2 for a self-loop
     */
    private final List<List<Integer>> columns = new ArrayList<>();

    @Override
    protected boolean doAddVertex(int vertex) {
        vertices.add(vertex);
        for (List<Integer> column : columns) {
            column.add(0);
        }
        return true;
    }

    @Override
    protected boolean doRemoveVertex(int vertex) {
        int index = vertices.indexOf(vertex);
        if (index < 0) {
            return false;
        }
        vertices.remove(index);
        for (int i = columns.size() - 1; i >= 0; i--) {
            if (columns.get(i).get(index) != 0) {
                columns.remove(i);
                edges.remove(i);
            }
        }
        for (List<Integer> column : columns) {
            column.remove(index);
        }
        return true;
    }

    @Override
    protected boolean doAddEdge(Edge edge) {
        int fromIndex = vertices.indexOf(edge.from());
        int toIndex = vertices.indexOf(edge.to());
        if (edges.contains(edge)) {
            return false;
        }
        List<Integer> column = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            column.add(0);
        }
        if (fromIndex == toIndex) {
            column.set(fromIndex, 2);
        } else {
            column.set(fromIndex, -1);
            column.set(toIndex, 1);
        }
        edges.add(edge);
        columns.add(column);
        return true;
    }

    @Override
    protected boolean doRemoveEdge(Edge edge) {
        int index = edges.indexOf(edge);
        if (index < 0) {
            return false;
        }
        edges.remove(index);
        columns.remove(index);
        return true;
    }

    @Override
    protected boolean doHasEdge(Edge edge) {
        return edges.contains(edge);
    }

    @Override
    protected Set<Edge> doGetEdges() {
        return new HashSet<>(edges);
    }

    @Override
    protected Set<Integer> doGetNeighbors(int vertex) {
        int vertexIndex = vertices.indexOf(vertex);
        assert (vertexIndex >= 0);

        HashSet<Integer> neighbors = new HashSet<>();
        for (int i = 0; i < edges.size(); i++) {
            Edge edge = edges.get(i);
            List<Integer> column = columns.get(i);
            if (column.get(vertexIndex) == -1 || column.get(vertexIndex) == 2) {
                neighbors.add(edge.to());
            }
        }
        return neighbors;
    }

    @Override
    protected void doClear() {
        vertices.clear();
        edges.clear();
        columns.clear();
    }
}


