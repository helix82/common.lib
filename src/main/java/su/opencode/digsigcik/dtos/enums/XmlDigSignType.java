package su.opencode.digsigcik.dtos.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <b>Тип подписи XADES</b>
 * <p>
 *     Подробнее читай в
 *     <a href="https://dss.cryptopro.ru/docs/articles/rest/signserver/samples.html#подпись-в-формате-xmldsig">
 *         документации к эндпоинту подписи XADES
 *         </a>
 * </p>
 * <p>
 *      <b>Дата обновления: </b> 18 Sep 2025
 * </p>
 */
@RequiredArgsConstructor
public enum XmlDigSignType {

    ENVELOPING("XMLEnveloping"),
    ENVELOPED("XMLEnveloped"),
    TEMPLATE("XMLTemplate");

    @Getter
    private final String dssApiType;
}
