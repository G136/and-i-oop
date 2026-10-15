package org.example;

import java.util.*;

public abstract class AbstractGraph implements Graph {
    protected final HashSet<Integer> vertices = new HashSet<>();

    @Override
    public final boolean addVertex(int vertex) {
        if (vertices.contains(vertex)) {
            return false;
        }
        vertices.add(vertex);
        return doAddVertex(vertex);
    }

    @Override
    public final boolean removeVertex(int vertex) {
        if (!vertices.contains(vertex)) {
            return false;
        }
        if (!doRemoveVertex(vertex)) {
            return false;
        }
        vertices.remove(vertex);
        return true;
    }

    @Override
    public final boolean addEdge(int from, int to) {
        Edge edge = new Edge(from, to);
        ensureVertex(edge.from());
        ensureVertex(edge.to());
        return doAddEdge(edge);
    }

    @Override
    public final boolean removeEdge(int from, int to) {
        Edge edge = new Edge(from, to);
        if (!hasEdge(edge.from(), edge.to())) {
            return false;
        }
        return doRemoveEdge(edge);
    }

    @Override
    public boolean hasVertex(int vertex) {
        return vertices.contains(vertex);
    }

    @Override
    public final boolean hasEdge(int from, int to) {
        Edge edge = new Edge(from, to);
        if (!hasVertex(edge.from()) || !hasVertex(edge.to())) {
            return false;
        }
        return doHasEdge(edge);
    }

    @Override
    public final Set<Integer> getVertices() {
        return Collections.unmodifiableSet(vertices);
    }

    @Override
    public final Set<Edge> getEdges() {
        return Collections.unmodifiableSet(doGetEdges());
    }

    @Override
    public final Set<Integer> getNeighbors(int vertex) {
        ensureVertex(vertex);
        return Collections.unmodifiableSet(doGetNeighbors(vertex));
    }

    @Override
    public final void clear() {
        vertices.clear();
        doClear();
    }

    @Override
    public final boolean equals(Object obj) {
        return obj instanceof Graph
                && Objects.equals(getVertices(), ((Graph) obj).getVertices())
                && Objects.equals(getEdges(), ((Graph) obj).getEdges());
    }

    protected final void ensureVertex(int vertex) {
        if (!vertices.contains(vertex)) {
            throw new IllegalArgumentException("Unknown vertex: " + vertex);
        }
    }

    protected abstract boolean doAddVertex(int vertex);

    protected abstract boolean doRemoveVertex(int vertex);

    protected abstract boolean doAddEdge(Edge edge);

    protected abstract boolean doRemoveEdge(Edge edge);

    protected abstract boolean doHasEdge(Edge edge);

    protected abstract Set<Edge> doGetEdges();

    protected abstract Set<Integer> doGetNeighbors(int vertex);

    protected abstract void doClear();
}


