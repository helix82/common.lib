package su.opencode.digsigcik.dtos.cryptoproxy.enhance;

/**
 * <a href="https://dss.cryptopro.ru/docs/svs/devguide_nix/enums.html#VerifyParams">This</a>
 */
public final class EnhanceType {

    /**
     * Усиления с CAdES-BES до CAdES-T, CAdES-XLT1
     */
    public static final String CAdES = "2";

    /**
     * Усиление простой подписи CMS до CAdES-BES
     */
    public static final String CMStoBES = "6";

}
