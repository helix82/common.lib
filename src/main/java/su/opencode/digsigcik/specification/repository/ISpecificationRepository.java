package su.opencode.digsigcik.specification.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Расширение над обычным репозиторием с поддержкой пейджинации, фильтрации, сортировки, поиска по полям.
 *
 * @param <T> сущность в JPA
 */
@NoRepositoryBean
public interface ISpecificationRepository<T> extends
        PagingAndSortingRepository<T, Long>,
        JpaSpecificationExecutor<T>
{ }
