package com.reeo.book_network;

public record ColumnSpec(
    String name,
    boolean nullable,
    boolean unique,
    boolean updateable,
    boolean insertable
) {
}
