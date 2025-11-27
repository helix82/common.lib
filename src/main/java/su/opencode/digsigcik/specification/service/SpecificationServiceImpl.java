package su.opencode.digsigcik.specification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import su.opencode.digsigcik.specification.FilterOperator;
import su.opencode.digsigcik.specification.SpecificationBuilder;
import su.opencode.digsigcik.specification.SpecificationRequest;
import su.opencode.digsigcik.specification.SpecificationResponse;
import su.opencode.digsigcik.specification.model.Filter;
import su.opencode.digsigcik.specification.repository.ISpecificationRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Slf4j
public class SpecificationServiceImpl implements SpecificationService {

    @Override
    public <T> SpecificationResponse<T> getUsingSpecification(ISpecificationRepository<T> repository, SpecificationRequest request, Class<T> clazz) throws IllegalArgumentException {

        var specBuilder = SpecificationBuilder.of(clazz);

        Page<T> page = Page.empty();
        List<T> list = List.of();
        Specification<T> filters = null;

        if (request.hasFilters()) {
            filters = specBuilder.customFilter(request.getFilters());
        }

        if (request.hasSearch()) {
            Specification<T> search = specBuilder.customSearch(request.getSearch());
            filters = Objects.isNull(filters) ? search : filters.and(search);
        }

        Optional<Pageable> pageable = pageable(request, specBuilder);

        if ( Objects.nonNull(filters) && pageable.isPresent() ) {
            page = repository.findAll(
                    filters,
                    pageable.get()
            );
        }
        else if ( Objects.nonNull(filters) && request.hasSorting() ) {
            list = repository.findAll(
                    filters,
                    specBuilder.justSort(request.getSortBy())
            );
        }
        else if (Objects.nonNull(filters)) {
            list = repository.findAll(
                    filters
            );
        }
        else if ( pageable.isPresent() ) {
            page = repository.findAll(pageable.get());
        }
        else if ( request.hasSorting() ) {
            list = (List<T>) repository.findAll(
                    specBuilder.justSort(request.getSortBy())
            );
        }
        else {
            list = (List<T>) repository.findAll();
        }

        return pageable.isPresent()
                ? SpecificationBuilder.response(page, request.getFilters())
                : SpecificationBuilder.response(list, request.getFilters());
    }

    // TODO: на будущее, попробовать поиск в repository целиком через JpaSpecificationExecutor (Данияр)
    private static <T> Specification<T> empty() {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.conjunction();
            }
        };
    }

    @Override
    public <T> Optional<Pageable> pageable(SpecificationRequest request, SpecificationBuilder<T> builder) {
        if (request.hasSorting() && request.hasPagination())
            return Optional.of( builder.pagableOf(request.getPagination(), request.getSortBy()) );
        else if (request.hasPagination())
            return Optional.of( builder.justPagination(request.getPagination()) );

        return Optional.empty();
    }

    @Override
    public void onlyRelevant(SpecificationRequest request) {

        // TODO: request.getFilters() might be nullable | also test
        if (Objects.isNull(request.getFilters()))
            request.setFilters(new ArrayList<>());
    }

    @Override
    public void onlyLogin(SpecificationRequest request, String login) {

        // TODO: request.getFilters() might be nullable
        if (Objects.isNull(request.getFilters()))
            request.setFilters(new ArrayList<>());

        request.getFilters().removeIf(f -> f.getFieldName().equals("login"));
        request.getFilters().add(new Filter("login", FilterOperator.EQUAL, Collections.singletonList(login)));
    }
}
