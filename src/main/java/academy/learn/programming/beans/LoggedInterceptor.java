package academy.learn.programming.beans;

import academy.learn.programming.annotations.Logged;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.time.LocalDate;

@Logged
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class LoggedInterceptor {

    private final Logger logger = LoggerFactory.getLogger(LoggedInterceptor.class);
    private final String user = "Morgan";

    @PostConstruct
    private void init() {
        logger.warn("LoggedInterceptor::init annotated by @PostConstruct");
    }

    //This method will be called and passed invocation context by container
    @AroundInvoke
    public Object logMethodCall(InvocationContext context) throws Exception {
        //Log for example user who called method and time
        logger.info("User {} invoked {} method at {}", user, context.getMethod().getName(), LocalDate.now());
        return context.proceed();
    }
}
