package academy.learn.programming.sessionbeans;

import academy.learn.programming.annotations.Logged;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

@Stateless
public class AuditedService {

    private final Logger logger = LoggerFactory.getLogger(AuditedService.class);

    @PostConstruct
    private void init() {
        logger.warn("AuditedService::init annotated by @PostConstruct");
    }

    @Logged
    public void auditedMethod() {
        logger.info("AuditedService::auditedMethod call");
    }
}
