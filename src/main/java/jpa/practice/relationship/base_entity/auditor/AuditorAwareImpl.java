package jpa.practice.relationship.base_entity.auditor;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        // SecurityContextHolder.getContext().getAuthentication().getName()
        return Optional.of(Arrays.asList("제작자1", "관리자2", "유저3").get(new Random().nextInt(3)));
    }
}