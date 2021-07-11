package academy.learn.programming.beans;

import academy.learn.programming.annotations.Web;
import academy.learn.programming.scopes.ApplicationScope;
import academy.learn.programming.scopes.DependentScope;
import academy.learn.programming.scopes.RequestScope;
import academy.learn.programming.scopes.SessionScope;
import academy.learn.programming.sessionbeans.AuditedService;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

@Web
public class ScopesBean {

    private final RequestScope requestScope;
    private final ApplicationScope applicationScope;
    private final AuditedService auditedService;
    private final SessionScope sessionScope;
    private final DependentScope dependentScope;
    private final Logger logger;

    @Inject
    private ScopesBean(RequestScope requestScope, ApplicationScope applicationScope, AuditedService auditedService, SessionScope sessionScope, DependentScope dependentScope, Logger logger) {
        this.requestScope = requestScope;
        this.applicationScope = applicationScope;
        this.auditedService = auditedService;
        this.sessionScope = sessionScope;
        this.dependentScope = dependentScope;
        this.logger = logger;
    }

    @PostConstruct
    private void init() {
        auditedService.auditedMethod();
        logger.info("*******************************************");
        logger.info("Scopes bean called");
        logger.info("********************************************");
    }

    @PreDestroy
    private void kill() {
        logger.info("*******************************************");
        logger.info("Scopes bean gonna be killed :-( ");
        logger.info("********************************************");
    }

    public RequestScope getRequestScope() {
        return requestScope;
    }

    public ApplicationScope getApplicationScope() {
        return applicationScope;
    }

    public AuditedService getAuditedService() {
        return auditedService;
    }

    public SessionScope getSessionScope() {
        return sessionScope;
    }

    public DependentScope getDependentScope() {
        return dependentScope;
    }

    public int requestScopeHashCode() {
        System.err.println("ScopesBean::requestScopeHashCode");
        return requestScope.getHashCode();
    }

    public int applicationScopeHashCode() {
        return applicationScope.getHashCode();
    }

    public int sessionScopeHashCode() {
        return sessionScope.getHashCode();
    }

    public int dependentScopeHashCode() {
        System.err.println("ScopesBean::dependentScopeHashCode");
        return dependentScope.getHashCode();
    }
}
