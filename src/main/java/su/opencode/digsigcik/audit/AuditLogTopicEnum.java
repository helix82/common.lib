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

    CRL("Списки отозванных сертификатов СЭП"),
    EXPIRED("Проверка сертификатов в СОС"),

    TLS_SERV_PROVIDER("Поставщик TLS"),
    TLS_CA_CERTS("Корневые и промежуточные сертификаты TLS"),
    TLS_SERVER_CERTS("Серверные сертификаты TLS"),
    TLS_CERTS_ORDER("Заявление на выпуск сертификата TLS"),

    M4D("Управление МЧД"),
    M4D_CREDENTIALS("Справочник полномочий"),
    SIMPLE_SIGN("Простая подпись ПИН-кодом"),
    GENERAL_SIGN("Подпись с использованием СКП ЭП"),
    VERIFY("Проверка контейнера");


    private final String description;
}
