package academy.learn.programming.events;

import org.slf4j.Logger;

import javax.annotation.Priority;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.interceptor.Interceptor;

@RequestScoped
public class EventPriority {
    
    @Inject
    Logger logger;

    void greetingReceiver1(@Observes @Priority(Interceptor.Priority.APPLICATION + 200) String greeting) {
        logger.info("Greeting 1 with lower priority invoked with message {}", greeting);

    }

    //Higher priority
    void greetingReceiver2(@Observes @Priority(Interceptor.Priority.APPLICATION) String greeting) {
        logger.info("Greeting 2 with higher priority invoked with message {}", greeting);
    }    
}
