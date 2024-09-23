package su.opencode.digsigcik.dtos.cryptoproxy.verify;

/**
 * Объекты запросов к сервису verify КриптоПро.
 */
abstract public class VerifyRequestBuilder {

    private VerifyRequestBuilder() { }

    public static VerifyAttachedSignEvent.VerifyAttachedSignEventBuilder verifyAttachedSignRequestBuilder() {
        return VerifyAttachedSignEvent.builder();
    }

    public static VerifyDetachedSignEvent.VerifyDetachedSignEventBuilder verifyDetachedSignRequestBuilder() {
        return VerifyDetachedSignEvent.builder();
    }

}
