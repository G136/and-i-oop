package org.example;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

/** Directed graph contract shared by all graph representations. */
public interface Graph {

    /**
     * Returns all vertices currently present in the graph.
     *
     * @return an unmodifiable set of vertices
     */
    Set<Integer> getVertices();

    /**
     * Checks whether a vertex exists.
     *
     * @param vertex vertex value
     * @return {@code true} if the vertex exists, otherwise {@code false}
     */
    boolean hasVertex(int vertex);

    /**
     * Adds a vertex if it is not already present.
     *
     * @param vertex vertex value
     * @return was it not already present?
     */
    boolean addVertex(int vertex);

    /**
     * Removes a vertex and all incident edges.
     *
     * @param vertex vertex value
     * @return was there such a vertex?
     */
    boolean removeVertex(int vertex);

    /**
     * Returns all outgoing neighbors of a vertex.
     *
     * @param vertex source vertex
     * @return an unmodifiable set of vertices reachable by one outgoing edge
     * @throws IllegalArgumentException when the vertex does not exist
     */
    Set<Integer> getNeighbors(int vertex);


    /**
     * Returns all edges currently present in the graph.
     *
     * @return an unmodifiable set of directed edges
     */
    Set<Edge> getEdges();

    /**
     * Checks whether a directed edge exists.
     *
     * @param from source vertex
     * @param to target vertex
     * @return {@code true} if the edge exists, otherwise {@code false}
     */
    boolean hasEdge(int from, int to);

    /**
     * Adds a directed edge.
     *
     * @param from source vertex
     * @param to target vertex
     * @return was a new edge added?
     * @throws IllegalArgumentException when at least one endpoint vertex does not exist
     */
    boolean addEdge(int from, int to);

    /**
     * Removes a directed edge.
     *
     * @param from source vertex
     * @param to target vertex
     * @return was there such an edge?
     */
    boolean removeEdge(int from, int to);


    /**
     * Removes all vertices and edges from the graph.
     */
    void clear();

    /**
     * Replaces current graph contents with edges loaded from a text file.
     *
     * <p>Before reading, the graph is cleared. Each non-empty, non-comment line must contain
     * exactly two integer vertex identifiers separated by whitespace and represents one
     * directed edge {@code from to}. Lines starting with {@code #} are ignored.</p>
     *
     * <p>Duplicate edges and malformed lines are invalid input.</p>
     *
     * @param path path to the input file
     * @throws IOException when file I/O fails
     * @throws IllegalArgumentException when input format is invalid or contains duplicate edges
     */
    void readFromFile(Path path) throws IOException;

    /**
     * Returns a topological order of vertices.
     *
     * <p>If multiple vertices are available to pick next, smaller vertex values are chosen first.
     * If the graph contains a cycle (including a self-loop), no topological order exists.</p>
     *
     * @return immutable list with vertices in topological order
     * @throws IllegalStateException when the graph contains a cycle
     */
    List<Integer> topologicalSort();
}

