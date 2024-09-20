package su.opencode.digsigcik.dtos.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleEnum {
    ROLE_ADMIN_TLS("Администратор TLS"),
    ROLE_ADMIN_IOK("Администратор ИОК"),
    ROLE_ADMIN_SEP("Администратор СЭП"),
    ROLE_USER_SEP("Пользователь СЭП");

    private final String name;
}
