package su.opencode.digsigcik.dtos.cryptoproxy.sign;

/**
 * Объекты запросов к сервису подписи КриптоПро.
 */
abstract public class SignRequestBuilder {

    private SignRequestBuilder() { }

    public static PDFSignEvent.PDFSignEventBuilder pdfRequestBuilder() {
        return PDFSignEvent.builder();
    }

    public static XMLSignEvent.XMLSignEventBuilder xmlRequestBuilder() {
        return XMLSignEvent.builder();
    }

    public static CMSSignEvent.CMSSignEventBuilder cmsRequestBuilder() {
        return CMSSignEvent.builder();
    }

    public static GostSignEvent.GostSignEventBuilder gost3410RequestBuilder() {
        return GostSignEvent.builder();
    }

    public static CAdESSignEvent.CAdESSignEventBuilder CAdESBesRequestBuilder() {
        return CAdESSignEvent.builder().type(SignType.CAdES_BES);
    }

    public static CAdESSignEvent.CAdESSignEventBuilder CAdESTRequestBuilder() {
        return CAdESSignEvent.builder().type(SignType.CAdES_T);
    }

    public static CAdESSignEvent.CAdESSignEventBuilder CAdESXLT1RequestBuilder() {
        return CAdESSignEvent.builder().type(SignType.CAdES_XLT1);
    }
}
