package su.opencode.digsigcik.audit;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuditLogTopicEnum {

    NA("N/A"),

    TEMPLATE("Справочник УЦ"),
    AUTH("Аутентификация пользователя"),

    CA_CERTS("Сертификаты аккредитованных УЦ"),
    TRUST_CERTS("Сертификаты доверенных УЦ"),
    USER_CERT("СКП ЭП пользователей"),

    CRL("Списки отозванных сертификатов ПЭП"),
    EXPIRED("Проверка сертификатов в СОС"),

    TLS_SERV_PROVIDER("Поставщик TLS"),
    TLS_CA_CERTS("Корневые и промежуточные сертификаты TLS"),
    TLS_SERVER_CERTS("Серверные сертификаты TLS"),
    TLS_CERTS_ORDER("Заявление на выпуск сертификата TLS"),

    M4D("Управление МЧД"),
    M4D_CREDENTIALS("Справочник полномочий"),
    USER_PERMISSIONS("Полномочия пользователей"),
    SIMPLE_SIGN("Простая подпись ПИН-кодом"),
    GENERAL_SIGN("Подпись с использованием СКП ЭП"),
    VERIFY("Проверка контейнера"),

    CRYPTO_PRO_SCRIPTS("Скрипты КриптоПро"),

    TRUSTED_PERSON_FK("Доверенное лицо УЦ ФК"),
    ORDER_FK("Заявления в УЦ ФК"),
    REGISTRATION_USER("Регистрация пользователя в УЦ ГАС 'Выборы'"),
    SERVICE_PROVISION("Заявления в УЦ ГАС 'Выборы'"),
    ORGANIZATION("Справочник организаций"),
    TOFK("Справочник ТОФК"),
    PRINCIPAL("Справочник ЕИО организаций");


    private final String description;
}
