package jpa.practice.relationship.decorator.decorator;

import jpa.practice.relationship.decorator.plan.MobilePlan;
import org.springframework.stereotype.Component;

@Component
public class InternationalCallDecorator extends MobilePlanDecorator {
    public InternationalCallDecorator(MobilePlan mobilePlan) {
        super(mobilePlan);
    }

    @Override
    public String getDescription() {
        return mobilePlan.getDescription() + " + International Call";
    }

    @Override
    public double cost() {
        return mobilePlan.cost() + 10;
    }

    @Override
    public DecoratorType getType() {
        return DecoratorType.INTERNATIONAL_CALL;
    }
}
