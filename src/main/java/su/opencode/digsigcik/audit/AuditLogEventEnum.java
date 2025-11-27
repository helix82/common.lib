package su.opencode.digsigcik.audit;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuditLogEventEnum {

    NA(AuditLogTopicEnum.NA, "N/A"),

    USER_LOGIN(AuditLogTopicEnum.AUTH, "Пользователь вошел в ПЭП"),
    USER_LOGOUT(AuditLogTopicEnum.AUTH, "Пользователь вышел из ПЭП"),

    CA_CERT_SAVE(AuditLogTopicEnum.CA_CERTS, "Загрузка сведений о сертификатах УЦ в ПЭП"),

    TRUST_CERTS_UPLOAD(AuditLogTopicEnum.TRUST_CERTS, "Добавление сертификата доверенного УЦ"),
    TRUST_CERTS_DELETE(AuditLogTopicEnum.TRUST_CERTS, "Удаление сертификата доверенного УЦ"),

    USER_CERTS_ORDER_ADDED(AuditLogTopicEnum.USER_CERT, "Добавление заявления на выпуск СКП ЭП"),
    USER_CERTS_ORDER_DELETED(AuditLogTopicEnum.USER_CERT, "Удаление заявления на выпуск СКП ЭП"),

    USER_CERTS_REVOCATION(AuditLogTopicEnum.USER_CERT, "Запрос на отзыв СКП ЭП"),
    USER_CERTS_REVOCATION_CANCELED(AuditLogTopicEnum.USER_CERT, "Отменён запрос на отзыв СКП ЭП"),

    USER_CERTS_UPLOAD(AuditLogTopicEnum.USER_CERT, "Добавление сертификата пользователя"),
    USER_CERTS_DELETE(AuditLogTopicEnum.USER_CERT, "Удаление связи с сертификатом пользователя"),

    SIMPLE_SIGN_CREATE(AuditLogTopicEnum.SIMPLE_SIGN, "Создание простой ЭП"),
    SIMPLE_SIGN_UPDATE(AuditLogTopicEnum.SIMPLE_SIGN, "Обновление простой ЭП"),
    SIMPLE_SIGN_DELETE(AuditLogTopicEnum.SIMPLE_SIGN, "Удаление простой ЭП"),
    SIMPLE_SIGN_SIGNING(AuditLogTopicEnum.SIMPLE_SIGN, "Формирование ЭП с помощью ПЭП"),

    CRL_USING(AuditLogTopicEnum.CRL, "Использование СОС для сверки сертификатов"),
    CRL_UPLOAD(AuditLogTopicEnum.CRL, "Добавление СОС"),
    CRL_DELETE(AuditLogTopicEnum.CRL, "Удаление СОС"),

    DIG_SIGN_CREATE(AuditLogTopicEnum.GENERAL_SIGN, "Формирование ЭП с помощью КЭП или НЭП"),
    DIG_SIGN_VERIFY(AuditLogTopicEnum.GENERAL_SIGN, "Выполнена проверка ЭП"),

    TLS_SERV_PROVIDER_UPLOAD(AuditLogTopicEnum.TLS_SERV_PROVIDER, "Загрузка справочника поставщика TLS-сертификата"),
    TLS_SERV_PROVIDER_UPDATE(AuditLogTopicEnum.TLS_SERV_PROVIDER, "Обновление справочника поставщика TLS-сертификата"),
    TLS_SERV_PROVIDER_DELETE(AuditLogTopicEnum.TLS_SERV_PROVIDER, "Удаление справочника поставщика TLS-сертификата"),

    TLS_CA_CERTS_UPLOAD(AuditLogTopicEnum.TLS_CA_CERTS, "Загрузка корневого или промежуточного TLS-сертификата"),
    TLS_CA_CERTS_CREATE(AuditLogTopicEnum.TLS_CA_CERTS, "Создание корневого или промежуточного TLS-сертификата"),
    TLS_CA_CERTS_DELETE(AuditLogTopicEnum.TLS_CA_CERTS, "Удаление корневого или промежуточного TLS-сертификата"),

    @Deprecated(since = "правки по журналируемым событиям от 5 нобяря 2024 года")
    TLS_SERV_CERTS_UPLOAD(AuditLogTopicEnum.TLS_SERVER_CERTS, "Загрузка серверного сертификата TLS"),

    TLS_SERV_CERTS_STEP_CA_CREATE(AuditLogTopicEnum.TLS_SERVER_CERTS, "Создание TLS-сертификата внутри ПЭПа"),
    TLS_SERV_CERTS_FK_CREATE(AuditLogTopicEnum.TLS_SERVER_CERTS, "Создание TLS-сертификата из УЦ Федерального Казначейства"),
    TLS_SERV_CERTS_NUC_CREATE(AuditLogTopicEnum.TLS_SERVER_CERTS, "Создание TLS-сертификата из НУЦ Минцифры"),
    TLS_SERV_CERTS_OTHER_CA_CREATE(AuditLogTopicEnum.TLS_SERVER_CERTS, "Создание TLS-сертификата от остальных поставщиков"),

    TLS_SERV_CERTS_DELETE(AuditLogTopicEnum.TLS_SERVER_CERTS, "Удаление серверного сертификата TLS"),
    TLS_SERV_CERTS_REISSUE(AuditLogTopicEnum.TLS_SERVER_CERTS, "Перевыпуск серверного сертификата TLS"),
    TLS_SERV_CERTS_REVOKE(AuditLogTopicEnum.TLS_SERVER_CERTS, "Отзыв серверного сертификата TLS"),

    TLS_CERTS_ORDER_STEP_CA(AuditLogTopicEnum.TLS_CERTS_ORDER, "Создание заявления на выпуск TLS-сертификата внутри ПЭПа"),
    TLS_CERTS_ORDER_FK(AuditLogTopicEnum.TLS_CERTS_ORDER, "Создание заявления на выпуск TLS-сертификата для УЦ Федерального Казначейства"),
    TLS_CERTS_ORDER_NUC(AuditLogTopicEnum.TLS_CERTS_ORDER, "Создание заявления на выпуск TLS-сертификата для НУЦ Минцифры"),
    TLS_CERTS_ORDER_OTHER_CA(AuditLogTopicEnum.TLS_CERTS_ORDER, "Создание заявления на выпуск TLS-сертификата для остальных поставщиков"),
    TLS_CERTS_ORDER_DEL(AuditLogTopicEnum.TLS_CERTS_ORDER, "Удаление заявления на выпуск TLS-сертификата"),

    @Deprecated(since = "правки по журналируемым событиям от 5 нобяря 2024 года")
    TLS_CERTS_ORDER_UPLOAD(AuditLogTopicEnum.TLS_CERTS_ORDER, "Загрузка серверного сертификата TLS"),
    @Deprecated(since = "правки по журналируемым событиям от 5 нобяря 2024 года")
    TLS_CERTS_ORDER_DELETE(AuditLogTopicEnum.TLS_CERTS_ORDER, "Отзыв серверного сертификата TLS"),
    @Deprecated(since = "правки по журналируемым событиям от 5 нобяря 2024 года")
    TLS_CERTS_ORDER_CERT_RELEASE(AuditLogTopicEnum.TLS_CERTS_ORDER, "Выпуск серверного сертификата TLS"),

    M4D_CREATE(AuditLogTopicEnum.M4D, "Добавление МЧД"),
    M4D_UPDATE(AuditLogTopicEnum.M4D, "Обновление МЧД"),
    M4D_DELETE(AuditLogTopicEnum.M4D, "Удаление МЧД"),
    M4D_SIGNATURE(AuditLogTopicEnum.M4D, "Загрузка подписи МЧД"),
    M4D_REQUEST_SEARCH_FIND_RR_CREATE(AuditLogTopicEnum.M4D, "Добавление запроса на поиск МЧД в РР МЧД"),
    M4D_REQUEST_SEARCH_FIND_RR_DELETE(AuditLogTopicEnum.M4D, "Удаление запроса на поиск МЧД в РР МЧД"),
    M4D_REQUEST_SEARCH_FIND_RR_UPDATE_STATUS(AuditLogTopicEnum.M4D, "Обновление статуса МЧД в запросе РР МЧД"),
    M4D_CHECK_IN_PEP(AuditLogTopicEnum.M4D, "Проверка МЧД в ПЭП"),
    M4D_CHECK_IN_REQUEST_SEARCH(AuditLogTopicEnum.M4D, "Проверка МЧД в РР МЧД"),

    M4D_RECALL_CREATE(AuditLogTopicEnum.M4D, "Добавление заявления на отзыв МЧД"),
    M4D_RECALL_DELETE(AuditLogTopicEnum.M4D, "Удаление заявления на отзыв МЧД"),
    M4D_RECALL_SIGNATURE(AuditLogTopicEnum.M4D, "Загрузка подписи заявления на отзыв МЧД"),

    M4D_CREDENTIAL_CREATE(AuditLogTopicEnum.M4D_CREDENTIALS, "Добавление полномочия"),
    M4D_CREDENTIAL_UPDATE(AuditLogTopicEnum.M4D_CREDENTIALS, "Обновление полномочия"),
    M4D_CREDENTIAL_DELETE(AuditLogTopicEnum.M4D_CREDENTIALS, "Удаление полномочия из справочника"),
    M4D_CREDENTIAL_UPLOAD(AuditLogTopicEnum.M4D_CREDENTIALS, "Загрузка справочника полномочий"),

    USER_PERMISSIONS_CREATE(AuditLogTopicEnum.USER_PERMISSIONS, "Добавление полномочий пользователя"),
    USER_PERMISSIONS_UPDATE(AuditLogTopicEnum.USER_PERMISSIONS, "Обновление полномочий пользователя"),
    USER_PERMISSIONS_DELETE(AuditLogTopicEnum.USER_PERMISSIONS, "Удаление полномочий пользователя"),

    CONTAINER_VERIFY(AuditLogTopicEnum.CONTAINER, "Проверка контейнера"),
    CONTAINER_UPDATE(AuditLogTopicEnum.CONTAINER, "Обновление контейнера"),

    // Ивенты скриптов на КриптоПро
    INSTALL_ROOT_CA(AuditLogTopicEnum.CRYPTO_PRO_SCRIPTS, "Установка корневых сертификатов из ПЭП в КриптоПро"),
    INSTALL_CA(AuditLogTopicEnum.CRYPTO_PRO_SCRIPTS, "Установка промежуточных сертификатов из ПЭП в КриптоПро"),
    INSTALL_TSL(AuditLogTopicEnum.CRYPTO_PRO_SCRIPTS, "Установка tsl.xml из ПЭП в КриптоПро"),
    INSTALL_CRL(AuditLogTopicEnum.CRYPTO_PRO_SCRIPTS, "Установка списка СОС из ПЭП в КриптоПро"),

    //HealthCheck
    HEALTH_M4D(AuditLogTopicEnum.M4D, "Статус здоровья БД сервиса M4D"),
    HEALTH_SYNAPI(AuditLogTopicEnum.GENERAL_SIGN, "Статус здоровья БД сервиса Synapi"),
    HEALTH_REFBOOK(AuditLogTopicEnum.TEMPLATE, "Статус здоровья БД сервиса Ref-book"),
    HEALTH_PVV_ADAPTER(AuditLogTopicEnum.NA, "Статус здоровья БД сервиса Pvv-adapter"),
    HEALTH_TLS(AuditLogTopicEnum.TLS_CERTS_ORDER, "Статус здоровья БД сервиса TLS"),

    HEALTH_M4D_SERVICE(AuditLogTopicEnum.M4D, "Статус сервиса M4D"),
    HEALTH_SYNAPI_SERVICE(AuditLogTopicEnum.GENERAL_SIGN, "Статус сервиса Synapi"),
    HEALTH_REFBOOK_SERVICE(AuditLogTopicEnum.TEMPLATE, "Статус сервиса Ref-book"),
    HEALTH_PVV_ADAPTER_SERVICE(AuditLogTopicEnum.NA, "Статус сервиса Pvv-adapter"),
    HEALTH_TLS_SERVICE(AuditLogTopicEnum.TLS_CERTS_ORDER, "Статус сервиса TLS"),

    HEALTH_M4D_DISC_SPACE(AuditLogTopicEnum.M4D, "Статус вычислительных ресурсов сервиса M4D"),
    HEALTH_SYNAPI_DISC_SPACE(AuditLogTopicEnum.GENERAL_SIGN, "Статус вычислительных ресурсов сервиса Synapi"),
    HEALTH_REFBOOK_DISC_SPACE(AuditLogTopicEnum.TEMPLATE, "Статус вычислительных ресурсов сервиса Ref-book"),
    HEALTH_PVV_ADAPTER_DISC_SPACE(AuditLogTopicEnum.NA, "Статус вычислительных ресурсов сервиса Pvv-adapter"),
    HEALTH_TLS_DISC_SPACE(AuditLogTopicEnum.TLS_CERTS_ORDER, "Статус вычислительных ресурсов сервиса TLS"),

    ORDER_FK_UL_CREATE(AuditLogTopicEnum.ORDER_FK, "Добавление заявления ЮЛ в УЦ ФК"),
    ORDER_FK_UL_NO_FIO_CREATE(AuditLogTopicEnum.ORDER_FK, "Добавление заявления ЮЛ без ФИО в УЦ ФК"),
    ORDER_FK_DL_CREATE(AuditLogTopicEnum.ORDER_FK, "Добавление заявления Должностного лица в УЦ ФК"),
    ORDER_FK_UPDATE(AuditLogTopicEnum.ORDER_FK, "Обновление заявления в УЦ ФК"),
    ORDER_FK_DELETE(AuditLogTopicEnum.ORDER_FK, "Удаление заявления в УЦ ФК"),

    TRUSTED_PERSON_FK_CREATE(AuditLogTopicEnum.TRUSTED_PERSON_FK, "Добавление доверенного лица УЦ ФК"),
    TRUSTED_PERSON_FK_UPDATE(AuditLogTopicEnum.TRUSTED_PERSON_FK, "Обновление доверенного лица УЦ ФК"),
    TRUSTED_PERSON_FK_DELETE(AuditLogTopicEnum.TRUSTED_PERSON_FK, "Удаление доверенного лица УЦ ФК"),

    REGISTRATION_USER_CREATE(AuditLogTopicEnum.REGISTRATION_USER, "Добавление заявления на регистрацию пользователя в УЦ ГАС 'Выборы'"),
    REGISTRATION_USER_UPDATE(AuditLogTopicEnum.REGISTRATION_USER, "Обновление заявления на регистрацию пользователя в УЦ ГАС 'Выборы'"),
    REGISTRATION_USER_DELETE(AuditLogTopicEnum.REGISTRATION_USER, "Удаление заявления на регистрацию пользователя в УЦ ГАС 'Выборы'"),

    SERVICE_PROVISION_CREATE(AuditLogTopicEnum.SERVICE_PROVISION, "Добавление заявления в УЦ ГАС 'Выборы'"),
    SERVICE_PROVISION_UPDATE(AuditLogTopicEnum.SERVICE_PROVISION, "Обновление заявления в УЦ ГАС 'Выборы'"),
    SERVICE_PROVISION_DELETE(AuditLogTopicEnum.SERVICE_PROVISION, "Удаление заявления в УЦ ГАС 'Выборы'"),

    ORGANIZATION_CREATE(AuditLogTopicEnum.ORGANIZATION, "Добавление организации"),
    ORGANIZATION_UPDATE(AuditLogTopicEnum.ORGANIZATION, "Обновление организации"),
    ORGANIZATION_DELETE(AuditLogTopicEnum.ORGANIZATION, "Удаление организации"),

    TOFK_CREATE(AuditLogTopicEnum.TOFK, "Добавление ТОФК"),
    TOFK_UPDATE(AuditLogTopicEnum.TOFK, "Обновление ТОФК"),
    TOFK_DELETE(AuditLogTopicEnum.TOFK, "Удаление ТОФК"),

    PRINCIPAL_CREATE(AuditLogTopicEnum.PRINCIPAL, "Добавление ЕИО организации"),
    PRINCIPAL_UPDATE(AuditLogTopicEnum.PRINCIPAL, "Обновление ЕИО организации"),
    PRINCIPAL_DELETE(AuditLogTopicEnum.PRINCIPAL, "Удаление ЕИО организации"),

    // TODO helix82 Добавить журналирование изменений настроек синхронизации объектов ИОК и применения сертификатов, ключей и МЧД

    // =================================================================================================================
    // Experimental (согласовать с Аналитикой)
    // =================================================================================================================

    CLOUD_PIN_CODE_UPDATE(AuditLogTopicEnum.USER_CERT, "Смена пин-кода в облачном крипто-провайдере");


    private final AuditLogTopicEnum topic;        // Общая тема журналируемого события
    private final String description;             // Опциональное описание (пока нигде не используется)
}
