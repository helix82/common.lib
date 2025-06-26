package su.opencode.digsigcik.specification.service;

import org.springframework.data.domain.Pageable;
import su.opencode.digsigcik.specification.SpecificationBuilder;
import su.opencode.digsigcik.specification.SpecificationRequest;
import su.opencode.digsigcik.specification.SpecificationResponse;
import su.opencode.digsigcik.specification.repository.ISpecificationRepository;

import java.util.Optional;

public interface SpecificationService {

    // TODO: consider static (Данияр)
    <T> SpecificationResponse<T> getUsingSpecification(ISpecificationRepository<T> repository, SpecificationRequest request, Class<T> clazz) throws IllegalArgumentException;

    <T> Optional<Pageable> pageable(SpecificationRequest request, SpecificationBuilder<T> builder);
}
