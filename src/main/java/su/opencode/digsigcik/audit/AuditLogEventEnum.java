package su.opencode.digsigcik.audit;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuditLogEventEnum {

    NA(AuditLogTopicEnum.NA, "N/A"),

    USER_LOGIN(AuditLogTopicEnum.AUTH, "Пользователь вошел в СЭП"),
    USER_LOGOUT(AuditLogTopicEnum.AUTH, "Пользователь вышел из СЭП"),

    @Deprecated(since = "Пока не ясна необходимость")
    TEMPLATE_UPLOAD(AuditLogTopicEnum.TEMPLATE, "Загрузка шаблона заявления в СЭП"),
    @Deprecated(since = "Пока не ясна необходимость")
    TEMPLATE_DELETE(AuditLogTopicEnum.TEMPLATE, "Удаление шаблона заявленияиз СЭП"),

    CA_CERT_UPLOAD(AuditLogTopicEnum.CA_CERTS, "Добавление сертификата аккредитованного УЦ"),
    CA_CERT_SAVE(AuditLogTopicEnum.CA_CERTS, "Загрузка сведений о сертификатах УЦ в СЭП"),
    CA_CERT_DELETED(AuditLogTopicEnum.CA_CERTS, "Удаление сертификата аккредитованного УЦ"),

    TRUST_CERTS_UPLOAD(AuditLogTopicEnum.TRUST_CERTS, "Добавление сертификата доверенного УЦ"),
    TRUST_CERTS_DELETE(AuditLogTopicEnum.TRUST_CERTS, "Удаление сертификата доверенного УЦ"),

    USER_CERTS_ORDER_ADDED(AuditLogTopicEnum.USER_CERT, "Добавление заявления на выпуск СКП ЭП"),
    USER_CERTS_ORDER_DELETED(AuditLogTopicEnum.USER_CERT, "Удаление заявления на выпуск СКП ЭП"),

    USER_CERTS_REVOCATION(AuditLogTopicEnum.USER_CERT, "Запрос на отзыв СКП ЭП"),
    USER_CERTS_REVOCATION_CANCELED(AuditLogTopicEnum.USER_CERT, "Отменён запрос на отзыв СКП ЭП"),

    USER_CERTS_UPLOAD(AuditLogTopicEnum.USER_CERT, "Добавление сертификата пользователя"),
    @Deprecated(since = "был дебаг, сейчас не нужно")
    USER_CERTS_PARSE(AuditLogTopicEnum.USER_CERT, "Считывание сертификата пользователя"),
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
    TLS_SERV_PROVIDER_DELETE(AuditLogTopicEnum.TLS_SERV_PROVIDER, "Загрузка справочника поставщика TLS-сертификата"),

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

    @Deprecated(since = "правки по журналируемым событиям от 5 нобяря 2024 года")
    TLS_CERTS_ORDER_UPLOAD(AuditLogTopicEnum.TLS_CERTS_ORDER, "Загрузка серверного сертификата TLS"),
    @Deprecated(since = "правки по журналируемым событиям от 5 нобяря 2024 года")
    TLS_CERTS_ORDER_DELETE(AuditLogTopicEnum.TLS_CERTS_ORDER, "Отзыв серверного сертификата TLS"),
    @Deprecated(since = "правки по журналируемым событиям от 5 нобяря 2024 года")
    TLS_CERTS_ORDER_CERT_RELEASE(AuditLogTopicEnum.TLS_CERTS_ORDER, "Выпуск серверного сертификата TLS"),

    M4D_CREATE(AuditLogTopicEnum.M4D, "Добавление МЧД"),
    M4D_UPDATE(AuditLogTopicEnum.M4D, "Обновление МЧД"),
    M4D_DELETE(AuditLogTopicEnum.M4D, "Удаление МЧД"),
    M4D_VERIFY(AuditLogTopicEnum.M4D, "Верификация контейнера МЧД"),
    M4D_SIGNATURE(AuditLogTopicEnum.M4D, "Загрузка подписи МЧД"),
    M4D_REQUEST_SEARCH_FIND_RR_CREATE(AuditLogTopicEnum.M4D, "Создание запроса на поиск МЧД в РР МЧД"),
    M4D_REQUEST_SEARCH_FIND_RR_DELETE(AuditLogTopicEnum.M4D, "Удаление запроса на поиск МЧД в РР МЧД"),
    M4D_REQUEST_SEARCH_FIND_RR_UPDATE_STATUS(AuditLogTopicEnum.M4D, "Обновление статуса МЧД в запросе РР МЧД"),
    M4D_CHECK_IN_PEP(AuditLogTopicEnum.M4D, "Поиск МЧД в ПЭП"),
    M4D_CHECK_IN_REQUEST_SEARCH(AuditLogTopicEnum.M4D, "Поиск ранее сформированного запроса в РР МЧД на проверку статуса МЧД"),

    M4D_RECALL_CREATE(AuditLogTopicEnum.M4D, "Добавление заявления на отзыв МЧД"),
    M4D_RECALL_UPDATE(AuditLogTopicEnum.M4D, "Обновление заявления на отзыв МЧД"),
    M4D_RECALL_DELETE(AuditLogTopicEnum.M4D, "Удаление заявления на отзыв МЧД"),
    M4D_RECALL_SIGNATURE(AuditLogTopicEnum.M4D, "Загрузка подписи заявления на отзыв МЧД"),

    M4D_CREDENTIAL_CREATE(AuditLogTopicEnum.M4D_CREDENTIALS, "Создание полномочия"),
    M4D_CREDENTIAL_UPDATE(AuditLogTopicEnum.M4D_CREDENTIALS, "Обновление полномочия"),
    M4D_CREDENTIAL_DELETE(AuditLogTopicEnum.M4D_CREDENTIALS, "Удаление полномочия из справочника"),

    CONTAINER_VERIFY(AuditLogTopicEnum.VERIFY, "Проверка контейнера"),

    // =================================================================================================================
    // Experimental (согласовать с Аналитикой)
    // =================================================================================================================

    CLOUD_PIN_CODE_UPDATE(AuditLogTopicEnum.USER_CERT, "Смена пин-кода в облачном крипто-провайдере");


    private AuditLogTopicEnum topic;        // Общая тема журналируемого события
    private String description;             // Опциональное описание (пока нигде не используется)
}
