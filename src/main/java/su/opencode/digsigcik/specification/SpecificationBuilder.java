package su.opencode.digsigcik.specification;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import su.opencode.digsigcik.specification.model.Filter;
import su.opencode.digsigcik.specification.model.Order;
import su.opencode.digsigcik.specification.model.Pagination;
import su.opencode.digsigcik.specification.model.Search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.*;

@Slf4j
public class SpecificationBuilder<T> {

    private final Class<?> clazz;
    private final Map<String, FilterDataType> types;

    private SpecificationBuilder(Class<?> clazz, Map<String, FilterDataType> types) {
        this.clazz = clazz;
        this.types = types;
    }

    public static <T> SpecificationBuilder<T> of(Class<T> clazz) {

        var classDescription = new HashMap<String, FilterDataType>();

        for (Field f : clazz.getDeclaredFields()) {
            if (f.getType().equals(Number.class) || f.getType().equals(Long.class) || f.getType().equals(Integer.class) || f.getType().equals(Double.class) || f.getType().equals(Float.class))
                classDescription.put(f.getName(), FilterDataType.NUMBER);
            else if (f.getType().equals(Temporal.class) || f.getType().equals(LocalDateTime.class) || f.getType().equals(ZonedDateTime.class))
                classDescription.put(f.getName(), FilterDataType.DATETIME_ISO);
            else
                classDescription.put(f.getName(), FilterDataType.STRING);
        }

        log.debug("Class {} was parsed for filter types: {}", clazz.getSimpleName(), classDescription);
        return new SpecificationBuilder<T>(clazz, classDescription);
    }

    /**
     * критерии, описанные в произвольной форме
     * TODO: составной оператор (Данияр)
     */
    public Specification<T> customFilter(List<Filter> filters) throws IllegalArgumentException {

        return (root, query, builder) -> {

            for (Filter filter : filters) {
                log.trace("Specs type of {} is {}", filter.getFieldName(), root.get(filter.getFieldName()).getJavaType().getSimpleName());
                return addon(builder, root.get(filter.getFieldName()), filter);
            }

            return null;
        };
    }

    // TODO: refactoring (Данияр)
    private Predicate addon(CriteriaBuilder builder, Path path, Filter filter) throws IllegalArgumentException {

        switch ( getTypeForFieldName(filter.getFieldName()) ) {
            case NUMBER:
                return addonNumber(builder, path, filter);
            case STRING:
                return addonString(builder, path, filter);
            case DATETIME_ISO:
                return addonDateTime(builder, path, filter);
            default:
                throw new IllegalArgumentException();
        }
    }

    private FilterDataType getTypeForFieldName(String fieldName) {
        return types.get(fieldName);
    }

    private static Predicate addonNumber(CriteriaBuilder builder, Path<Double> path, Filter filter) {

        List<Double> numbers = (List<Double>) filter.tryParseValues(FilterDataType.NUMBER);
        log.debug("Managing number filter: {}", filter);

        if ( filter.getOperator().equals(FilterOperator.GREATER) )
            return builder.greaterThan(path, numbers.get(0));
        else if ( filter.getOperator().equals(FilterOperator.GREATER_OR_EQUAL) )
            return builder.greaterThanOrEqualTo(path, numbers.get(0));
        else if ( filter.getOperator().equals(FilterOperator.LESS) )
            return builder.lessThan(path, numbers.get(0));
        else if ( filter.getOperator().equals(FilterOperator.LESS_OR_EQUAL) )
            return builder.lessThanOrEqualTo(path, numbers.get(0));
        else if ( filter.getOperator().equals(FilterOperator.EQUAL) )
            return builder.equal(path, numbers.get(0));
        else if ( filter.getOperator().equals(FilterOperator.BETWEEN) ) {
            if (numbers.get(0) == null)
                return builder.lessThanOrEqualTo(path, numbers.get(1));
            else if (numbers.get(1) == null)
                return builder.greaterThanOrEqualTo(path, numbers.get(0));
            else
                return builder.between(path, numbers.get(0), numbers.get(1));
        }
        else if ( filter.getOperator().equals(FilterOperator.IN) )
            return path.in(numbers);

        throw new IllegalArgumentException();
    }

    private static Predicate addonString(CriteriaBuilder builder, Path<String> path, Filter filter) {

        List<String> strings = (List<String>) filter.tryParseValues(FilterDataType.STRING);
        log.debug("Managing string filter: {}", filter);

        if ( filter.getOperator().equals(FilterOperator.GREATER) )
            return builder.greaterThan(path.as(String.class), strings.get(0));
        else if ( filter.getOperator().equals(FilterOperator.GREATER_OR_EQUAL) )
            return builder.greaterThanOrEqualTo(path.as(String.class), strings.get(0));
        else if ( filter.getOperator().equals(FilterOperator.LESS) )
            return builder.lessThan(path.as(String.class), strings.get(0));
        else if ( filter.getOperator().equals(FilterOperator.LESS_OR_EQUAL) )
            return builder.lessThanOrEqualTo(path.as(String.class), strings.get(0));
        else if ( filter.getOperator().equals(FilterOperator.EQUAL) )
            return builder.equal(path.as(String.class), strings.get(0));
        else if ( filter.getOperator().equals(FilterOperator.LIKE) )
            return simpleLikePredicate(path, builder, strings.get(0));
        else if ( filter.getOperator().equals(FilterOperator.IN) )
            return path.as(String.class).in(strings);

        throw new IllegalArgumentException();
    }

    private static Predicate addonDateTime(CriteriaBuilder builder, Path<LocalDateTime> path, Filter filter) {

        List<LocalDateTime> dates = (List<LocalDateTime>) filter.tryParseValues(FilterDataType.DATETIME_ISO);
        log.debug("Managing date filter: {}", filter);

        if ( filter.getOperator().equals(FilterOperator.GREATER) )
            return builder.greaterThan(path, dates.get(0));
        else if ( filter.getOperator().equals(FilterOperator.GREATER_OR_EQUAL) )
            return builder.greaterThanOrEqualTo(path, dates.get(0));
        else if ( filter.getOperator().equals(FilterOperator.LESS) )
            return builder.lessThan(path, dates.get(0));
        else if ( filter.getOperator().equals(FilterOperator.LESS_OR_EQUAL) )
            return builder.lessThanOrEqualTo(path, dates.get(0));
        else if ( filter.getOperator().equals(FilterOperator.EQUAL) )
            return builder.equal(path, dates.get(0));
            // TODO: consider `builder.between(root.get("dateStart"),date1, date2)`;
            //  но с ней непонятно пока, как без ошибок парсить пустые поля (Данияр)
        else if ( filter.getOperator().equals(FilterOperator.BETWEEN) ) {
            if (dates.get(0) == null)
                return builder.lessThanOrEqualTo(path, dates.get(1));
            else if (dates.get(1) == null)
                return builder.greaterThanOrEqualTo(path, dates.get(0));
            else
                return builder.between(path, dates.get(0), dates.get(1));
        }
        else if ( filter.getOperator().equals(FilterOperator.IN) )
            return path.in(dates);

        throw new IllegalArgumentException();
    }

    // TODO: mapping (Данияр)
    public Sort justSort(@NonNull List<Order> orders) {
        Order order = Objects.requireNonNull(orders).get(0);
        return Sort.by(order.getOrder(), order.getKey());
    }

    public PageRequest justPagination(@NonNull Pagination pagination) {
        return PageRequest
                .ofSize(pagination.getItemsPerPage())
                .withPage(pagination.getPage());
    }

    public PageRequest pagableOf(@NonNull Pagination pagination, @NonNull List<Order> orders) {
        return justPagination(pagination)
                .withSort( justSort(orders) );
    }

    public static Sort sortOf(Order order) {
        return Sort.by(order.getOrder(), order.getKey());
    }

    public static Order orderOf(Sort sort) {

        return sort.isUnsorted()
                ? new Order()
                : new Order(sort.iterator().next().getDirection(), sort.iterator().next().getProperty());
    }

    public static <T> SpecificationResponse<T> response(List<T> listAcquired, List<Filter> filtersApplied) {
        return response(new PageImpl<>(listAcquired), filtersApplied);
    }

    public static <T> SpecificationResponse<T> response(Page<T> pageAcquired, List<Filter> filtersApplied) {

        var p = Pagination.builder()
                .page(pageAcquired.getNumber())
                .itemsPerPage(pageAcquired.getSize())
                .pages(pageAcquired.getTotalPages())
                .count(pageAcquired.getTotalElements())
                .build();

        var o = orderOf(pageAcquired.getSort());

        return SpecificationResponse.<T>builder()
                .data( pageAcquired.getContent() )
                .pagination( p )
                .sortBy( List.of(o) )
                .filters( filtersApplied )
                .build();
    }

    // TODO: consider io.micrometer.core.instrument.search.Search (Данияр)
    public Specification<T> customSearch(Search search) {
        return (root, query, builder) -> {

            Iterator<String> iterator = search.getFields().iterator();
            Predicate predicate;

            // TODO: test null (Данияр)
            for (predicate = simpleLikePredicate(root.get(iterator.next()), builder, search.getTemplate());
                 iterator.hasNext();
                 predicate = builder.or(predicate, simpleLikePredicate(root.get(iterator.next()), builder, search.getTemplate()))
            ) ;

            return predicate;
        };
    }

    private static <T> Predicate simpleLikePredicate(Path<T> attributePath, CriteriaBuilder builder, String likePattern) {
        return builder.like(
                builder.lower(attributePath.as(String.class)),
                "%" + likePattern.toLowerCase() + "%"
        );
    }

}
