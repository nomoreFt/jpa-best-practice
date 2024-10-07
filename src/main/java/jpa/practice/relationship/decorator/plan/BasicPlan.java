package jpa.practice.relationship.decorator.plan;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class BasicPlan implements MobilePlan {
    @Override
    public String getDescription() {
        return "Basic Plan";
    }

    @Override
    public double cost() {
        return 10;
    }
}
