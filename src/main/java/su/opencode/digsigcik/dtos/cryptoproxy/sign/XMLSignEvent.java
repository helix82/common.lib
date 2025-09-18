package su.opencode.digsigcik.dtos.cryptoproxy.sign;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.opencode.digsigcik.dtos.enums.XmlDigSignType;

/**
 * <b>Запрос на подпись типа XADES</b>
 *
 * <p>
 *     Из коробки поддерживает <u>прикреплённое</u> подписание в форматах Enveloped и Enveloping. Для остальных случаев
 *     надо создавать (на Ключе) новый кастомный шаблон.
 * </p>
 * <p>
 *     Признак <u>откреплённости</u>, по видимому, игнорируется. Результат всегда будет один и тот же - XML,
 *     с зашитыми в нем данными о подписи.
 * </p>
 *
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
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude
public class XMLSignEvent {

    @JsonProperty(value = "ContentBase64")
    private String content;

    @JsonProperty(value = "UserLogin")
    private String userLogin;

    @JsonProperty("CertificateId")
    private long certificateId;

    @JsonProperty("CertPinCode")
    private String pinCode;

    @JsonProperty(value = "IsContentHash")
    private boolean hash;

    @JsonProperty(value = "CreateDetachedSign")
    private boolean detached;

    @JsonProperty(value = "XmlDigSignType")
    private XmlDigSignType signType;
}
