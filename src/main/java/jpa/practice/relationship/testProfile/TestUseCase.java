package jpa.practice.relationship.testProfile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestUseCase {
    private final TestService testService;

    public void test() {
        testService.test();
    }
}
