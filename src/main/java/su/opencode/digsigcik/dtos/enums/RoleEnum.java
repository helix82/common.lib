package su.opencode.digsigcik.dtos.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleEnum {
    ROLE_ADMIN_TLS("Администратор TLS"),
    ROLE_ADMIN_IOK("Администратор ИОК"),
    ROLE_ADMIN_SEP("Администратор СЭП"),
    ROLE_USER_SEP("Пользователь СЭП"),
    ROLE_TEST_1("Тестовая роль 1"),
    ROLE_TEST_2("Тестовая роль 2");

    private final String name;
}
