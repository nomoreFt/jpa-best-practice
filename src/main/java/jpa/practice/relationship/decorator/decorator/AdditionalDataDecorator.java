package jpa.practice.relationship.decorator.decorator;

import jpa.practice.relationship.decorator.plan.MobilePlan;
import org.springframework.stereotype.Component;

@Component
public class AdditionalDataDecorator extends MobilePlanDecorator {

    // 기본 생성자 제거하고, MobilePlan을 생성자 인자로 받음
    public AdditionalDataDecorator(MobilePlan mobilePlan) {
        super(mobilePlan);  // 부모 생성자 호출
    }

    @Override
    public String getDescription() {
        return mobilePlan.getDescription() + " + Additional Data";
    }

    @Override
    public double cost() {
        return mobilePlan.cost() + 5;
    }

    @Override
    public DecoratorType getType() {
        return DecoratorType.ADDITIONAL_DATA;
    }
}
