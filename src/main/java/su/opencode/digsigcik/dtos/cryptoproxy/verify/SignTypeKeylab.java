package su.opencode.digsigcik.dtos.cryptoproxy.verify;

/**
 * <a href="https://dss.cryptopro.ru/docs/svs/devguide_nix/enums.html#VerifyParams">docs</a>
 */
public enum SignTypeKeylab {

    // 0 - Подпись документа в формате XMLDSig
    XMLDSig,

    // 1 - Электронная подпись с использованием алгоритма ГОСТ Р 34.10–2012
    GOST3410,

    // 2 - Подпись формата CAdES
    CAdES,

    // 3 - Подпись PDF документов
    PDF,

    // 4 - Подпись документов MS Word и Excel
    MSOffice,

    // 5 - Подпись формата CAdES-BES
    CMS
}
