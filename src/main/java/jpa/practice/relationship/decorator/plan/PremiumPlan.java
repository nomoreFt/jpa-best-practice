package jpa.practice.relationship.decorator.plan;

import org.springframework.stereotype.Component;

@Component("premiumPlan")
public class PremiumPlan implements MobilePlan {
    @Override
    public String getDescription() {
        return "Premium Plan";
    }

    @Override
    public double cost() {
        return 20;
    }
}
