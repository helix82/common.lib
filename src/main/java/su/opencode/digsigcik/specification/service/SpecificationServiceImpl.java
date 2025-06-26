package su.opencode.digsigcik.specification.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import su.opencode.digsigcik.specification.SpecificationBuilder;
import su.opencode.digsigcik.specification.SpecificationRequest;
import su.opencode.digsigcik.specification.SpecificationResponse;
import su.opencode.digsigcik.specification.repository.ISpecificationRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SpecificationServiceImpl implements SpecificationService {

    @Override
    public <T> SpecificationResponse<T> getUsingSpecification(ISpecificationRepository<T> repository, SpecificationRequest request, Class<T> clazz) throws IllegalArgumentException {

        var specBuilder = SpecificationBuilder.of(clazz);
        Page<T> page = Page.empty();
        List<T> list = List.of();
        Specification<T> filters = null;

        if (request.hasFilters())
            filters = specBuilder.customFilter(request.getFilters());

        if (request.hasSearch()) {
            var search = specBuilder.customSearch(request.getSearch());
            filters = Objects.isNull(filters) ? search : filters.and(search);
        }

        Optional<Pageable> pageable = pageable(request, specBuilder);

        if ( Objects.nonNull(filters) && pageable.isPresent() )
            page = repository.findAll(
                    filters,
                    pageable.get()
            );
        else if ( Objects.nonNull(filters) && request.hasSorting() )
            list = repository.findAll(
                    filters,
                    specBuilder.justSort(request.getSortBy())
            );
        else if (Objects.nonNull(filters))
            list = repository.findAll(
                    filters
            );
        else if ( pageable.isPresent() )
            page = repository.findAll(pageable.get());
        else if ( request.hasSorting() )
            list = (List<T>) repository.findAll(
                    specBuilder.justSort(request.getSortBy())
            );
        else
            list = (List<T>) repository.findAll();

        return pageable.isPresent()
                ? SpecificationBuilder.response(page, request.getFilters())
                : SpecificationBuilder.response(list, request.getFilters());
    }

    @Override
    public <T> Optional<Pageable> pageable(SpecificationRequest request, SpecificationBuilder<T> builder) {
        if (request.hasSorting() && request.hasPagination())
            return Optional.of( builder.pagableOf(request.getPagination(), request.getSortBy()) );
        else if (request.hasPagination())
            return Optional.of( builder.justPagination(request.getPagination()) );

        return Optional.empty();
    }
}
