package webroot.webcli.app;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import webroot.websrv.WebcliApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebcliApplication.class)
@ActiveProfiles("test")
public abstract class BaseTest {

    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

}
