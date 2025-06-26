package su.opencode.digsigcik.specification;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FilterOperator {

    // TODO: not, or, and

    GREATER(">"),
    GREATER_OR_EQUAL(">="),

    LESS("<"),
    LESS_OR_EQUAL("<="),

    EQUAL("="),
    LIKE("~"),

    BETWEEN("between"),

    IN("in");

    private final String alias;
}

