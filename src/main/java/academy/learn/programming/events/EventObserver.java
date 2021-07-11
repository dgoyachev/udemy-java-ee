package academy.learn.programming.events;

import academy.learn.programming.annotations.Admin;
import academy.learn.programming.annotations.PopularStand;
import org.slf4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;
import javax.enterprise.event.Reception;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import java.io.Serializable;

@RequestScoped
public class EventObserver implements Serializable {

    @Inject
    private Logger logger;

    void plainEvent(@Observes  EventData eventData) {
        //Persist in database, send to another application outside your app
        //Essentially you can do whatever you want with the event data here.
        //We will just log it
        logger.info("User {} logged in at {}. Logged from plain event observer",
                eventData.getEmail(), eventData.getLoginTime());
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage());
        }
    }

    void userLoggedIn(@Observes @PopularStand EventData eventData) {
        //Persist in database, send to another application outside your app
        //Essentially you can do whatever you want with the event data here.
        //We will just log it
        logger.info("User {} logged in at {}. Logged from qualified observer",
                eventData.getEmail(), eventData.getLoginTime());
//        try {
//            Thread.sleep(6000);
//        } catch (InterruptedException e) {
//            logger.log(Level.SEVERE, null, e);
//        }
    }

    void asyncObserver(@ObservesAsync @PopularStand EventData eventData) {
        //Persist in database, send to another application outside your app
        //Essentially you can do whatever you want with the event data here.
        //We will just log it
        logger.info("User {} logged in at {}. Logged from async observer",
                eventData.getEmail(), eventData.getLoginTime());
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage());
        }
    }

    void conditionalObserver(@Observes(notifyObserver = Reception.IF_EXISTS,
            during = TransactionPhase.IN_PROGRESS) @Admin EventData eventData) {
        logger.info("The CEO {} logged in at {}. Logged from conditional observer",
                eventData.getEmail(), eventData.getLoginTime());
    }
}
