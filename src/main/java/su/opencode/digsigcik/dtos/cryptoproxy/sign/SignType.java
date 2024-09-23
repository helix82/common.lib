package su.opencode.digsigcik.dtos.cryptoproxy.sign;

/**
 * Похожий енум
 * @see "ru.CryptoPro.CAdES.CAdESType"
 */
public enum SignType {

    /**
     * Подпись PDF-файла
     */
    PDF,

    /**
     * Подпись XML-файла
     */
    XMLDSig,

    /**
     * Простая подпись
     */
    CMS,

    /**
     * Подпись формата ГОСТ Р 34.10-2001/ГОСТ Р 34.10-2012.
     */
    GOST_3410,

    /**
     * Подпись CAdES-BES
     */
    CAdES_BES,

    /**
     * Подпись CAdES-T
     */
    CAdES_T,

    /**
     * Подпись CAdES-XLT1
     */
    CAdES_XLT1
}
