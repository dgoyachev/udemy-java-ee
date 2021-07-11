package academy.learn.programming.scopes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;

@ApplicationScoped
public class ApplicationScope implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(ApplicationScope.class);

    public ApplicationScope() {
        System.err.println("ApplicationScope::new");
    }

    @PostConstruct
    private void init() {
        logger.info("ApplicationScope::init");
    }

    @PreDestroy
    private void destroy() {
        logger.info("ApplicationScope::destroy");
    }

    public int getHashCode() {
        System.err.println("ApplicationScope::hashCode");
        return this.hashCode();
    }
}