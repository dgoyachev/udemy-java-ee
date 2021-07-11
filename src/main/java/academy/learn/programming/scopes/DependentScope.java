package academy.learn.programming.scopes;

import academy.learn.programming.annotations.Logged;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Serializable;

public class DependentScope implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(DependentScope.class);

    public DependentScope() {
        System.err.println("DependentScope::new");
    }

    @PostConstruct
    private void init() {
        logger.info("DependentScope::init");
    }

    @PreDestroy
    private void destroy() {
        logger.info("DependentScope::destroy");
    }

    @Logged
    public int getHashCode() {
        System.err.println("DependentScope::hashCode");
        return this.hashCode();
    }
}