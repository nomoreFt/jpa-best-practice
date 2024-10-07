package jpa.practice.relationship.decorator.decorator;

import jpa.practice.relationship.decorator.plan.MobilePlan;

public abstract class MobilePlanDecorator implements MobilePlan {
    protected MobilePlan mobilePlan;

    public MobilePlanDecorator() {}

    public MobilePlanDecorator(MobilePlan mobilePlan){
        this.mobilePlan = mobilePlan;
    }

    @Override
    public String getDescription() {
        return mobilePlan.getDescription();
    }

    @Override
    public double cost() {
        return mobilePlan.cost();
    }

    public abstract DecoratorType getType();

    public MobilePlan apply(MobilePlan mobilePlan) {
        this.mobilePlan = mobilePlan;
        return this;
    }
}
