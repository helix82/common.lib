package su.opencode.digsigcik.specification;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import su.opencode.digsigcik.config.SpecificationRequestTestConfig;
import su.opencode.digsigcik.model.TestModel;
import su.opencode.digsigcik.model.TestModelFactory;
import su.opencode.digsigcik.repository.TestRepository;
import su.opencode.digsigcik.specification.model.Filter;
import su.opencode.digsigcik.specification.model.Order;
import su.opencode.digsigcik.specification.model.Pagination;
import su.opencode.digsigcik.specification.model.Search;
import su.opencode.digsigcik.specification.service.SpecificationService;
import su.opencode.digsigcik.specification.service.SpecificationServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@DataJpaTest(
//        properties = {
//                "spring.liquibase.enabled=false",
//                "spring.jpa.properties.hibernate.default_schema=PUBLIC",
//                "spring.batch.jdbc.initialize-schema=ALWAYS",
//                "spring.jpa.hibernate.ddl-auto=create",
//                "spring.jpa.properties.hibernate.show_sql=false",
//                "logging.level.root=FATAL"
//        }
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = SpecificationRequestTestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SpecificationRequestTest {

        // TODO: проверка разных операторов (Данияр)
        // TODO: проверка самых разных комбинаций (Данияр)
        // TODO: PaginationMapperUtil, конвертирующий запрос в Specification (Данияр)
        // TODO: включить принудительное прохождение тестов

        @Autowired
        private TestRepository repository;

        private SpecificationService service;

//        @Deprecated
//        @Test
//        public void test() {
//                Assertions.assertTrue(true);
//                repository.save(TestModelFactory.of("login", 2L));
//                Assertions.assertTrue(repository.findAll().iterator().hasNext());
//        }

        @Test
        public void testNullSpecification_shouldBeOk() {
                given();
                whenEmptySpecification();
                thenResponseContains(1, 145);
        }

        @Test
        public void testSimpleAcquiring_Asc_shouldBeOk() {
                given();
                whenAsc(0, 10);
                thenResponseContains(1, 10);
                whenAsc(1, 10);
                thenResponseContains(11, 20);
                whenAsc(14, 10);
                thenResponseContains(141, 145);
        }

        @Test
        public void testBeginBoundaries_Asc_shouldBeStart() {
                given();
                whenAsc(-10, 10);
                thenResponseContains(1, 10);
        }

        @Test
        public void testEndBoundaries_Asc_shouldBeNone() {
                given();
                whenAsc(15, 10);
                thenResponseContainsNone();
        }

        @Test
        public void testSimpleAcquiring_Desc_shouldBeOk() {
                given();
                whenDesc(0, 10);
                thenResponseContains(136, 145);
                whenDesc(1, 10);
                thenResponseContains(126, 135);
                whenDesc(14, 10);
                thenResponseContains(1, 5);
        }

        @Test
        public void testBeginBoundaries_Desc_shouldBeStart() {
                given();
                whenDesc(-10, 10);
                thenResponseContains(136, 145);
        }

        @Test
        public void testEndBoundaries_Desc_shouldBeNone() {
                given();
                whenDesc(15, 10);
                thenResponseContainsNone();
        }

        // PageSize

        @Test
        public void testPageSize_Big_shouldBeHalf() {
                given();
                whenAsc(0, 100);
                thenResponseContains(1, 100);
                whenAsc(1, 100);
                thenResponseContains(101, 145);
        }

        @Test
        public void testPageSize_VeryBig_shouldBeAll() {
                given();
                whenAsc(0, 1000);
                thenResponseContains(1, 145);
                whenAsc(1, 1000);
                thenResponseContainsNone();
        }

        @Test
        public void testPageSize_Big_Desc_shouldBeHalf() {
                given();
                whenDesc(0, 100);
                thenResponseContains(46, 145);
                whenDesc(1, 100);
                thenResponseContains(1, 45);
        }

        @Test
        public void testPageSize_VeryBig_Desc_shouldBeAll() {
                given();
                whenDesc(0, 1000);
                thenResponseContains(1, 145);
                whenDesc(1, 1000);
                thenResponseContainsNone();
        }

        // Filter

        @Test
        public void testFilter_shouldBe10() {
                givenWith10_WithLogin("Daniyar");
                when(0, 12, new Order(Sort.Direction.ASC, "id"), Filter.builder().fieldName("login").operator(FilterOperator.EQUAL).values(List.of("Daniyar")).build());
                thenResponseContainsOnly(10, "Daniyar");
                when(0, 12, new Order(Sort.Direction.ASC, "id"), Filter.builder().fieldName("login").operator(FilterOperator.LIKE).values(List.of("Dan")).build());
                thenResponseContainsOnly(10, "Daniyar");
        }

        // TODO
        // Search

        @Test
        public void testSearch_shouldBe10() {
                givenWith10_WithLogin("Daniyar");
                when(0, 12, new Order(Sort.Direction.ASC, "id"), null, new Search("ani", List.of("login", "id")));
                thenResponseContainsOnly(10, "Daniyar");
        }

        @Test
        public void testWrongFieldSearching_shouldThrow() {
                // Такое поведение сейчас: если указать в полях поиска несуществующее поле сущности - выйдет ошибка

                givenWith10_WithLogin("ivanov");

                // или InvalidDataAccessApiUsageException
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                        this.response = service.getUsingSpecification(repository, new SpecificationRequest(
                                Pagination.builder().itemsPerPage(10).page(0).build(),
                                Collections.singletonList(new Order(Sort.Direction.ASC, "createdAt")),
                                Collections.emptyList(),
                                new Search("ivanov", List.of("event", "inputData", "resultData"))
                        ), TestModel.class);
                });
        }

        @Disabled("см. TestRepository")
        @Test
        public void testJpqlCompatibility_OverrideFindAll_shouldFindAll() {
                given();
//                Assertions.assertEquals(145, repository.findAll().size());
        }

        @Test
        public void testJpqlCompatibility_EqualsField_shouldFilter() {
//                @Query("SELECT tm from TestModel tm where tm.success=false")
                givenWith10_variations_withStatusFalse();
                Assertions.assertEquals(10, repository.find_success_is_false().size());
//                List<TestModel> findAllBySuccessFalse();
                Assertions.assertEquals(10, repository.findAllBySuccessFalse().size());
        }


        @Test
        public void testJpqlCompatibility_ComplexJpqlQuery_shouldFilter() {
                givenWith10_WithLogin("petrov");
                //        @Query("SELECT count(tm.id) from TestModel tm where tm.login=?1 and tm.success=false AND tm.inputData IS NULL")
                Assertions.assertEquals(10, repository.count_by_login("petrov"));
        }

        // =================================================================================================================
        // =================================================================================================================
        // =================================================================================================================


        private int REPO_SIZE = 0;

        void given() {
                REPO_SIZE = 145;

                repository.saveAll(
                        TestModelFactory.ofRange(Stream.iterate(0L, f -> f <= 145, f -> f+1))
                );

                service = new SpecificationServiceImpl();
        }

        void givenWith10_WithLogin(String login) {
                REPO_SIZE = 145;

                repository.saveAll(
                        TestModelFactory.ofRange("login1", Stream.iterate(0L, f -> f <= 135, f -> f+1))
                );
                repository.saveAll(
                        TestModelFactory.ofRange(login, Stream.iterate(136L, f -> f <= 145, f -> f+1), false)
                );

                service = new SpecificationServiceImpl();
        }

        void givenWith10_variations_withStatusFalse() {
                given();

                for (int i = REPO_SIZE / 2 ; i < REPO_SIZE / 2 + 10; i++)
                        repository.findById((long) i).orElseThrow().setSuccess(false);

                service = new SpecificationServiceImpl();
        }

        // TODO: добавить тестирование мульти-фильтра (составного фильтра)

        SpecificationResponse<TestModel> response;

        void whenAsc(int pageSelected, int pageSize) {
                when(pageSelected, pageSize, new Order(Sort.Direction.ASC, "id"));
        }

        void whenDesc(int pageSelected, int pageSize) {
                when(pageSelected, pageSize, new Order(Sort.Direction.DESC, "id"));
        }

        void when(int pageSelected, int pageSize, Order order) {
                when(pageSelected, pageSize, order, null);
        }

        void when(int pageSelected, int pageSize, Order order, Filter filter) {
                when(pageSelected, pageSize, order, filter, null);
        }

        void when(int pageSelected, int pageSize, Order order, Filter filter, Search search) {
//                this.response = service.getAllLogs(new SpecificationRequest(
                this.response = service.getUsingSpecification(repository, new SpecificationRequest(
                        Pagination.builder().itemsPerPage(pageSize).page(pageSelected).build(),
                        Collections.singletonList(order),
                        Objects.isNull(filter) ? Collections.emptyList() : Collections.singletonList(filter),
                        Objects.isNull(search) ? new Search("", List.of("login", "event", "inputData", "resultData")) : search
                ), TestModel.class);
        }

        void whenEmptySpecification() {
//                this.response = service.getAllLogs(new SpecificationRequest(
                this.response = service.getUsingSpecification(repository, new SpecificationRequest(
                        null,
                        null,
                        null,
                        null
                ), TestModel.class);
        }

        void thenResponseContains(long idFrom, long idTo) {
                var ordered = response.getData().stream().map(TestModel::getId).sorted().collect(Collectors.toList());

                Assertions.assertEquals(idTo - idFrom + 1, ordered.size(), "Actual content: " + ordered);
                ordered.forEach(id -> Assertions.assertTrue(id >= idFrom && id <= idTo, "Actual content: " + ordered));
        }

        void thenResponseContainsNone() {
                var ordered = response.getData().stream().map(TestModel::getId).sorted().collect(Collectors.toList());
                Assertions.assertEquals(0, ordered.size(), "Actual content: " + ordered);
        }

        void thenResponseContainsOnly(int size, String login) {
                var ordered = response.getData().stream().map(TestModel::getLogin).sorted().collect(Collectors.toList());

                Assertions.assertEquals(size, ordered.size(), "Actual content: " + ordered);
                ordered.forEach(l -> Assertions.assertEquals(login, l, "Actual content: " + ordered));
        }

}
