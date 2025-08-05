package su.opencode.digsigcik.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import su.opencode.digsigcik.model.TestModel;
import su.opencode.digsigcik.specification.repository.ISpecificationRepository;

import java.util.List;

@Repository
public interface TestRepository extends ISpecificationRepository<TestModel> {

    /*
     * Вердикт - не стоит перегружать findAll при использовании ISpecificationRepository.
     * Возможные последствия: невозможность получить все энтити при отправке "нулевого запроса" (см. testNullSpecification_shouldBeOk), ничего не возвращает.
     *
     * Возможно, что дело в реализации SpecificationBuilder и это ещё можно исправить.
     *
     * @Override
     * @Query("SELECT tm from TestModel tm where tm.success=false")
     * List<TestModel> findAll();
     *
     */


    @Query("SELECT tm from TestModel tm where tm.success=false")
    List<TestModel> find_success_is_false();

    List<TestModel> findAllBySuccessFalse();

    @Query("SELECT count(tm.id) from TestModel tm where tm.login=?1 and tm.success=false AND tm.inputData IS NULL")
    Integer count_by_login(String login);

    // TODO: протестировать сложные запросы
//    @Query("SELECT tm.event FROM TestModel tm WHERE tm.thumbprint = :thumbprint AND tm.cloudId = :cloudId and tm.deleted=false")
//    Optional<Long> findCloudIdByThumbprintAndCloudId(@Param("thumbprint") String thumbprint, @Param("cloudId") Long cloudId);

}
