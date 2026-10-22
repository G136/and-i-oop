package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    protected final void ensureVertex(int vertex) {
        if (!getVertices().contains(vertex)) {
            throw new IllegalArgumentException("Unknown vertex: " + vertex);
        }
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

    @Override
    public final int hashCode() {
        return Objects.hash(getVertices(), getEdges());
    }

    @Override
    public String toString() {
        List<Integer> sortedVertices = new ArrayList<>(getVertices());
        sortedVertices.sort(Integer::compareTo);

        List<String> sortedEdges = new ArrayList<>();
        for (Edge edge : getEdges()) {
            sortedEdges.add(edge.toString());
        }
        sortedEdges.sort(String::compareTo);

        return "Graph{vertices=" + sortedVertices
                + ", edges=" + sortedEdges
                + '}';
    }

    @Override
    public final void readFromFile(Path path) throws IOException {
        clear();

        for (String rawLine : Files.readAllLines(path)) {
            String line = rawLine.trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }

            String[] parts = line.split("\\s+");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid edge line: " + line);
            }

            int[] intPair = null;
            try {
                intPair = new int[]{
                        Integer.parseInt(parts[0].trim()),
                        Integer.parseInt(parts[1].trim())
                };
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid edge line: " + line);
            }

            addVertex(intPair[0]);
            addVertex(intPair[1]);

            Edge edge = new Edge(intPair[0], intPair[1]);
            if (!addEdge(edge.from(), edge.to())) {
                throw new IllegalArgumentException("Duplicate edge: " + edge);
            }
        }
    }

    @Override
    public final List<Integer> topologicalSort() {
        Map<Integer, Integer> indegree = new HashMap<>();
        for (Integer vertex : getVertices()) {
            indegree.put(vertex, 0);
        }
        for (Edge edge : getEdges()) {
            indegree.computeIfPresent(edge.to(), (k, v) -> v + 1);
        }

        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (Map.Entry<Integer, Integer> entry : indegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        List<Integer> result = new ArrayList<>();
        Map<Integer, Integer> mutableIndegree = new HashMap<>(indegree);

        while (!queue.isEmpty()) {
            Integer vertex = queue.poll();
            result.add(vertex);
            for (Integer neighbor : getNeighbors(vertex)) {
                Integer newValue = mutableIndegree.get(neighbor);
                if (newValue == null) {
                    continue;
                }
                newValue = newValue - 1;
                mutableIndegree.put(neighbor, newValue);
                if (newValue == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (result.size() != getVertices().size()) {
            throw new IllegalStateException("Graph contains a cycle; topological sort is impossible");
        }

        return List.copyOf(result);
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


