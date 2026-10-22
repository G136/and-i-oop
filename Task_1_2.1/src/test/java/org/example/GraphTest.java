package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class GraphTest {
    @TempDir
    Path tempDir;

    @Test
    void differentRepresentationsAreEqual() {
        Graph list = new AdjacencyListGraph();
        Graph matrix = new AdjacencyMatrixGraph();
        Graph incidence = new IncidenceMatrixGraph();

        for (Graph graph : List.of(list, matrix, incidence)) {
            graph.addVertex(1);
            graph.addVertex(2);
            graph.addVertex(3);
            graph.addEdge(1, 2);
            graph.addEdge(1, 3);
        }

        assertEquals(list, matrix);
        assertEquals(matrix, incidence);
        assertEquals(list, incidence);
        assertEquals(list.hashCode(), matrix.hashCode());
    }

    @Test
    void neighborsAndRemovalWork() {
        Graph graph = new AdjacencyListGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);

        assertEquals(Set.of(2, 3), graph.getNeighbors(1));
        assertTrue(graph.removeEdge(1, 2));
        assertFalse(graph.hasEdge(1, 2));
        assertTrue(graph.removeVertex(3));
        assertFalse(graph.hasVertex(3));
    }

    @Test
    void matrixAndIncidenceMatchList() {
        Graph graph = new AdjacencyMatrixGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);

        assertEquals(Set.of(1, 2, 3), graph.getVertices());
        assertEquals(Set.of(new Edge(1, 2), new Edge(1, 3)), graph.getEdges());
    }

    @Test
    void readsFromFile() throws IOException {
        Path file = tempDir.resolve("graph.txt");
        Files.writeString(file, "1 2\n1 3\n2 4\n3 4\n");

        Graph graph = new AdjacencyListGraph();
        graph.readFromFile(file);

        assertEquals(Set.of(1, 2, 3, 4), graph.getVertices());
        assertEquals(
                Set.of(new Edge(1, 2), new Edge(1, 3), new Edge(2, 4), new Edge(3, 4)),
                graph.getEdges()
        );
        assertEquals(List.of(1, 2, 3, 4), graph.topologicalSort());
    }

    @Test
    void topologicalSortRejectsCycles() {
        Graph graph = new AdjacencyListGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 1);

        assertThrows(IllegalStateException.class, graph::topologicalSort);
    }
}

