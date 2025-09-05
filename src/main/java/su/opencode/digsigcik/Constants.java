package su.opencode.digsigcik;

public interface Constants {
    String XML_ELEMENT_CA = "УдостоверяющийЦентр";
    String XML_ELEMENT_ACC = "АккредитованныеУдостоверяющиеЦентры";
    String XML_ELEMENT_SHC = "ПрограммноАппаратныеКомплексы";
    String XML_ELEMENT_ACCREDITATION_STATUS = "СтатусАккредитации";
    String XML_ELEMENT_SH_SYSTEM = "ПрограммноАппаратныйКомплекс";
    String XML_ELEMENT_KEYS_AUTH_PERSONS = "КлючиУполномоченныхЛиц";
    String XML_ELEMENT_KEY = "Ключ";
    String XML_ELEMENT_REVIEW_LIST_ADDRESS = "АдресаСписковОтзыва";
    String XML_ELEMENT_CERTIFICATES = "Сертификаты";
    String XML_ELEMENT_ADDRESS = "Адрес";
    String XML_ELEMENT_CERTIFICATE_DATA = "ДанныеСертификата";
    String XML_ELEMENT_STATUS = "Статус";
    String XML_ELEMENT_ISSUED_TO = "КомуВыдан";
    String XML_ELEMENT_DATE_START = "ПериодДействияС";
    String XML_ELEMENT_DATE_END = "ПериодДействияДо";
    String XML_ELEMENT_CERT = "Данные";
    String XML_ELEMENT_IMPRINT = "Отпечаток";
    String XML_ELEMENT_SERIAL_NUMBER = "СерийныйНомер";
    String XML_ELEMENT_KEY_ID = "ИдентификаторКлюча";

    String SCHEMA_CA_TYPE_INPUT_EXAMPLE = "Вручную";
    String SCHEMA_CA_ACCREDITATION_STATUS_EXAMPLE = "Действует";

    String CA_IS_NULL = "CA is NULL";
    String CONTROLLER_CA_TYPE_INPUT_AUTO = "Автоматически";
    String CONTROLLER_CA_TYPE_INPUT_MANUALLY = "Вручную";
    String VALID_STATUS = "VALID";

    String CONTROLLER_AUDIT_PAGINATION_FORMAT_ERROR = "Получен неверный формат параметризованного запроса: не поддерживается оператор или поле фильтрации/поиска указано неверно";
    String HEALTH_STATUS_UNKNOWN = "Получен неопределённый статус";
}
