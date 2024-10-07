package jpa.practice.relationship.testProfile;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
@Profile("!prod && !stg")
@Service("TestService")
public class TestServiceImplDev implements TestService{
    @Override
    public String test() {
        System.out.println("Dev Test Service");
        return "Dev Test Service";
    }
}
