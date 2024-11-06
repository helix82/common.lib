package su.opencode.digsigcik.audit;

import com.google.gson.ExclusionStrategy;

public abstract class FieldOnlyExclusionStrategy implements ExclusionStrategy {

    /**
     * Не работает, как ожидается -- блокирует проверку индивидуальных полей. Не подходит для запрещающего по-умолчанию подхода.
     * @return всегда false (так надо)
     */
    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }

}
