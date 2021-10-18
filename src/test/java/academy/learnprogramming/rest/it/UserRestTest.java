package academy.learnprogramming.rest.it;

import academy.learnprogramming.entity.Todo;
import academy.learnprogramming.entity.User;
import academy.learnprogramming.rest.TodoConfig;
import academy.learnprogramming.service.TodoService;
import jakarta.inject.Inject;
import jakarta.json.JsonArray;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@ExtendWith(ArquillianExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRestTest {

    @Inject
    private User user;

    @Inject
    private Todo todo;

    private static final Logger logger = LoggerFactory.getLogger(UserRestTest.class);

    private Client client;

    private WebTarget webTarget;

    private static String jwt;

    @ArquillianResource
    private URL base;

    @Deployment
    public static WebArchive createDeployment() {

        logger.info("UserRestTest::createDeployment()");

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
                .addPackage(TodoConfig.class.getPackage())
                .addAsResource("persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @BeforeEach
    public void setUp() throws Exception {
        logger.info("UserRestTest::setUp()");
        client = ClientBuilder.newBuilder().connectTimeout(7, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS).build();
    }

    @AfterEach
    public void tearDown() throws Exception {
        logger.info("UserRestTest::tearDown()");
        client.close();
    }

    @Test
    @Order(1)
    void saveUser() throws Exception {
        logger.info("UserRestTest::saveUser()");
        user.setEmail("bla@bla.com");
        user.setFullName("Donald Trump");
        user.setPassword("jDTrumpf@41");
        logger.info("initialUser {}", user);

        webTarget = client.target(URI.create(new URL(base, "api/v1/user/create").toExternalForm()));

        Response response = webTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(user));

        User savedUser = response.readEntity(User.class);
        logger.info("savedUser {}", savedUser);
    }

    @Test
    @Order(2)
    void login() throws Exception {
        logger.info("UserRestTest::login()");
        user.setEmail("bla@bla.com");
        user.setFullName("Donald Trump");
        user.setPassword("jDTrumpf@41");

        webTarget = client.target(URI.create(new URL(base, "api/v1/user/login").toExternalForm()));

        MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
        formData.putSingle("email", user.getEmail());
        formData.putSingle("password", user.getPassword());

        Response response = webTarget
                .request()
                .post(Entity.form(formData));

        jwt = (String) response.getHeaders().get(HttpHeaders.AUTHORIZATION).stream().findFirst().orElse(null);

        System.err.println(jwt);
    }

    @Test
    @Order(3)
    public void todoCreate() throws Exception {
        logger.info("UserRestTest::todoCreate()");

        todo.setTask("Master Jakarta EE 9");
        todo.setDueDate(LocalDate.of(2021, 12, 5));

        webTarget = client.target(URI.create(new URL(base, "api/v1/todo/new").toExternalForm()));

        Response response = webTarget
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, jwt)
                .post(Entity.json(todo));

        Todo createdTodo = response.readEntity(Todo.class);

        logger.info("UserRestTest::todoCreate() result {}", createdTodo);
    }

    @Test
    @Order(4)
    public void todoList() throws Exception {
        logger.info("UserRestTest::todoList()");

        webTarget = client.target(URI.create(new URL(base, "api/v1/todo/list").toExternalForm()));

        JsonArray jsonArray = webTarget
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, jwt)
                .get(JsonArray.class);

        logger.info("UserRestTest::todoList() result {}", jsonArray);
    }
}