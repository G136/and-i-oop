package org.example;

import java.util.Objects;

/**
 * Immutable directed edge from {@code from} to {@code to}.
 *
 * @param from source vertex
 * @param to target vertex
 */
public record Edge(int from, int to) {

    /**
     * Edges are equal when both endpoints are equal and runtime type matches.
     */
    @Override
    public boolean equals(Object obj) {
        return this.getClass() == obj.getClass()
                && Objects.equals(this.from, ((Edge) obj).from)
                && Objects.equals(this.to, ((Edge) obj).to);
    }

    /**
     * Returns a compact textual form: {@code from -> to}.
     */
    @Override
    public String toString() {
        return from + " -> " + to;
    }
}

