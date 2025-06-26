package su.opencode.digsigcik.repository;

import org.springframework.stereotype.Repository;
import su.opencode.digsigcik.model.TestModel;
import su.opencode.digsigcik.specification.repository.ISpecificationRepository;

@Repository
public interface TestRepository extends ISpecificationRepository<TestModel> {
}
