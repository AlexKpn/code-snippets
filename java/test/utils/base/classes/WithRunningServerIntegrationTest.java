package java.test.utils.base.classes;

import com.akelius.university.secret.code.test.util.constant.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@AutoConfigureObservability(tracing = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WithRunningServerIntegrationTest extends AbstractIntegrationTest {

    @LocalServerPort
    private int port;
    @Value("${server.servlet.context-path}")
    private String serverContextPath;

    @Autowired
    protected TestRestTemplate testRestTemplate;
    
    protected URI prepareUri(final String basePath, final String path) {
        return UriComponentsBuilder.newInstance()
                .scheme(WebConstants.HTTP)
                .host(WebConstants.LOCALHOST)
                .port(port)
                .path(serverContextPath)
                .path(basePath)
                .path(path)
                .build()
                .toUri();
    }

}
