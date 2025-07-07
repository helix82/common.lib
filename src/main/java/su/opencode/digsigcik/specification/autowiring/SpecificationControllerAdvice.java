package su.opencode.digsigcik.specification.autowiring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import su.opencode.digsigcik.Constants;
import su.opencode.digsigcik.specification.annotation.EnablePagination;
import su.opencode.digsigcik.specification.mapper.CaseInsensitiveConverter;

@Slf4j
@RestControllerAdvice(annotations = EnablePagination.class)
public class SpecificationControllerAdvice {

    // TODO: consider using Interceptors (Данияр)

    // =================================================================================================================
    // ====================================        Model Conversion Handling         ===================================
    // =================================================================================================================

    @InitBinder("specificationRequest")
    public void initBinder(WebDataBinder binder, WebRequest webRequest) {

        // Денис просил, чтобы можно было в запросе использовать "acs/desc" вместо "ACS/DESC"
        binder.registerCustomEditor(Sort.Direction.class, new CaseInsensitiveConverter<>(Sort.Direction.class));
    }


    // =================================================================================================================
    // =======================================        Exception Handling         =======================================
    // =================================================================================================================

    @ExceptionHandler({InvalidDataAccessApiUsageException.class, BindException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = Constants.CONTROLLER_AUDIT_PAGINATION_FORMAT_ERROR)
    public void handleSpecificationQueryFormatException(Exception ex) {

        // FIXME: не работает, т.к. common-starter перехватывает все Exception. Можно ли переопределить это в другом классе (в этом)?

        // Обработка исключений, связанных с пагинацией/фильтрацией/сортировкой запросов типа SpecificationRequest
        log.error("{}: {}", Constants.CONTROLLER_AUDIT_PAGINATION_FORMAT_ERROR, ex.getMessage());
    }
}
