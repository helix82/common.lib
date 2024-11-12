package su.opencode.digsigcik.dtos.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleEnum {
    ROLE_ADMIN_TLS("Администратор TLS"),
    ROLE_ADMIN_IOK("Администратор ИОК"),
    ROLE_ADMIN_PEP("Администратор ПЭП"),
    ROLE_USER_PEP("Пользователь ПЭП");

    private final String name;
}
