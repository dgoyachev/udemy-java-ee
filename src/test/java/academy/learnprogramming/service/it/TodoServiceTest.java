package academy.learnprogramming.service.it;

import academy.learnprogramming.entity.Todo;
import academy.learnprogramming.entity.User;
import academy.learnprogramming.rest.TodoConfig;
import academy.learnprogramming.service.TodoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ArquillianExtension.class)
public class TodoServiceTest {

    @Inject
    private User user;

    @Inject
    TodoService todoService;

    Logger logger;

    @Deployment
    public static WebArchive createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "java-ee.war")
                .addPackage(Todo.class.getPackage())
                .addPackage(TodoService.class.getPackage())
                .addAsLibraries(
                        new File("target/udemy-java-ee/WEB-INF/lib/jjwt-0.9.0.jar"),
                        new File("target/udemy-java-ee/WEB-INF/lib/shiro-cache-1.4.0.jar"),
                        new File("target/udemy-java-ee/WEB-INF/lib/shiro-config-core-1.4.0.jar"),
                        new File("target/udemy-java-ee/WEB-INF/lib/shiro-config-ogdl-1.4.0.jar"),
                        new File("target/udemy-java-ee/WEB-INF/lib/shiro-core-1.4.0.jar"),
                        new File("target/udemy-java-ee/WEB-INF/lib/shiro-crypto-cipher-1.4.0.jar"),
                        new File("target/udemy-java-ee/WEB-INF/lib/shiro-crypto-core-1.4.0.jar"),
                        new File("target/udemy-java-ee/WEB-INF/lib/shiro-crypto-hash-1.4.0.jar"),
                        new File("target/udemy-java-ee/WEB-INF/lib/shiro-event-1.4.0.jar"),
                        new File("target/udemy-java-ee/WEB-INF/lib/shiro-lang-1.4.0.jar")                )
                .addAsResource("persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @BeforeEach
    public void setUp() throws Exception {
        logger = LoggerFactory.getLogger(TodoServiceTest.class.getName());

        user.setEmail("bla@bla.com");
        user.setFullName("Donald Trump");
        user.setPassword("jDTrumpf@41");

        todoService.saveUser(user);
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    public void saveUser() {
        assertNotNull(user.getId());
        logger.info(user.getId().toString());

        assertNotEquals("The user password is not same as hashed", "jDTrumpf@41", user.getPassword());

        logger.info(user.getPassword());
    }

    @Test
    public void createTodo() {
        logger.info("TodoServiceTest::createTodo");
    }

    @Test
    public void updateTodo() {
    }

    @Test
    public void findToDoById() {
    }

    @Test
    public void getTodos() {
    }
}
