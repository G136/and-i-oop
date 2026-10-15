package org.example;

import java.util.Objects;

public record Edge(int from, int to) {

    @Override
    public boolean equals(Object obj) {
        return this.getClass() == obj.getClass()
                && Objects.equals(this.from, ((Edge) obj).from)
                && Objects.equals(this.to, ((Edge) obj).to);
    }

    @Override
    public String toString() {
        return from + " -> " + to;
    }
}

