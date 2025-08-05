package su.opencode.digsigcik.model;

import su.opencode.digsigcik.audit.AuditLogEventEnum;
import su.opencode.digsigcik.audit.AuditSeverityEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestModelFactory {
    public static List<TestModel> ofRange(Stream<Long> ids) {
        return ofRange("login", ids);
    }

    public static List<TestModel> ofRange(String login, Stream<Long> ids) {
        List<TestModel> list = new ArrayList<>();

        ids.forEach(id -> {
            list.add(of(login, id));
        });

        return list;
    }

    public static List<TestModel> ofRange(String login, Stream<Long> ids, boolean success) {
        List<TestModel> list = new ArrayList<>();

        ids.forEach(id -> {
            list.add(of(login, id, success));
        });

        return list;
    }

    public static TestModel of(String login, Long id) {
        return of(login, id, Boolean.TRUE);
    }

    public static TestModel of(String login, Long id, boolean success) {
        return new TestModel(
                id,
                LocalDateTime.now(),
                login,
                AuditLogEventEnum.USER_CERTS_ORDER_ADDED,
                AuditSeverityEnum.INFO,
                success,
                null, "", 0l, 0l
        );
    }
}
