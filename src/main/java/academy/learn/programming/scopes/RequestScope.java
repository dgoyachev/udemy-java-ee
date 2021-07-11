package academy.learn.programming.scopes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class RequestScope {

    private final Logger logger = LoggerFactory.getLogger(RequestScope.class);

    public RequestScope() {
        System.err.println("RequestScope::new");
    }

    @PostConstruct
    private void init() {
        System.err.println("RequestScope::init");
        logger.info("RequestScope::init");
    }

    @PreDestroy
    private void destroy() {
        logger.info("RequestScope::destroy");
        System.err.println("RequestScope::destroy");
    }

    public int getHashCode() {
        System.err.println("RequestScope::hashCode");
        return this.hashCode();
    }
}
