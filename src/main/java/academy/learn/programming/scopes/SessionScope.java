package academy.learn.programming.scopes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@SessionScoped
public class SessionScope implements Serializable {

    private static final long serialVersionUID = 440804699990999L;

    private final Logger logger = LoggerFactory.getLogger(SessionScope.class);

    public SessionScope() {
        System.err.println("SessionScope::new");
    }

    @PostConstruct
    private void init() {
        logger.info("SessionScope::init");
    }

    @PreDestroy
    private void destroy() {
        logger.info("SessionScope::destroy");
    }

    public int getHashCode() {
        System.err.println("SessionScope::hashCode");
        return this.hashCode();
    }
}
