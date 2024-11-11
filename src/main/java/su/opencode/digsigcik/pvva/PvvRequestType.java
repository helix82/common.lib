package su.opencode.digsigcik.pvva;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PvvRequestType {

    M4D("синхронизация списка МЧД"),
    SEP("синхронизация списка выданных простых ПЭП"),
    CA("синхронизация списка сертификатов УЦ"),
    CRL("синхронизация СОС");

    private final String description;
}
