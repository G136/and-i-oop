package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Base implementation of {@link Graph} that keeps common validation and algorithm logic.
 *
 * <p>Concrete subclasses are responsible for storage details through {@code do*} hooks,
 * while this class enforces the public contract from {@link Graph}.</p>
 */
public abstract class AbstractGraph implements Graph {
    /**
     * Canonical set of known vertices used for public membership checks.
     */
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

    /**
     * Verifies that a vertex exists in the graph.
     *
     * @param vertex vertex value
     * @throws IllegalArgumentException when the vertex is absent
     */
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

    /**
     * Graphs are equal when their vertex and edge sets are equal, regardless of representation.
     */
    @Override
    public final boolean equals(Object obj) {
        return obj instanceof Graph
                && Objects.equals(getVertices(), ((Graph) obj).getVertices())
                && Objects.equals(getEdges(), ((Graph) obj).getEdges());
    }

    /**
     * Hash code derived from vertex and edge sets to match {@link #equals(Object)}.
     */
    @Override
    public final int hashCode() {
        return Objects.hash(getVertices(), getEdges());
    }

    /**
     * Returns a deterministic string representation with sorted vertices and edges.
     */
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
            throw new IllegalStateException(
                    "Graph contains a cycle; topological sort is impossible"
            );
        }

        return List.copyOf(result);
    }

    /**
     * Adds a new vertex to internal state.
     *
     * <p>Called only after the vertex is inserted into {@link #vertices}
     * and known to be unique.</p>
     *
     * @param vertex vertex value
     * @return {@code true} if internal state update succeeded
     */
    protected abstract boolean doAddVertex(int vertex);

    /**
     * Removes a vertex from internal state along with all incident edges.
     *
     * <p>Called only for an existing vertex.</p>
     *
     * @param vertex vertex value
     * @return {@code true} if internal state update succeeded
     */
    protected abstract boolean doRemoveVertex(int vertex);

    /**
     * Adds a directed edge to internal state.
     *
     * <p>Called only when both endpoint vertices are known to exist.</p>
     *
     * @param edge edge to add
     * @return {@code true} if edge was new, {@code false} if it already existed
     */
    protected abstract boolean doAddEdge(Edge edge);

    /**
     * Removes a directed edge from internal state.
     *
     * <p>Called only when the edge is known to exist.</p>
     *
     * @param edge edge to remove
     * @return {@code true} if edge was removed
     */
    protected abstract boolean doRemoveEdge(Edge edge);

    /**
     * Checks whether an edge exists in internal state.
     *
     * <p>Called only when both endpoint vertices are known to exist.</p>
     *
     * @param edge edge to check
     * @return {@code true} if present, {@code false} otherwise
     */
    protected abstract boolean doHasEdge(Edge edge);

    /**
     * Returns current edges from internal state.
     *
     * @return mutable or immutable set of edges; caller wraps it as unmodifiable
     */
    protected abstract Set<Edge> doGetEdges();

    /**
     * Returns outgoing neighbors for an existing vertex.
     *
     * <p>Called only for known vertices.</p>
     *
     * @param vertex source vertex
     * @return neighbor set; caller wraps it as unmodifiable
     */
    protected abstract Set<Integer> doGetNeighbors(int vertex);

    /**
     * Clears all subclass-specific internal state.
     */
    protected abstract void doClear();
}


