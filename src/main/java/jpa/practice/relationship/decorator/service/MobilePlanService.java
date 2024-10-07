package jpa.practice.relationship.decorator.service;

import jakarta.annotation.PostConstruct;
import jpa.practice.relationship.decorator.decorator.DecoratorType;
import jpa.practice.relationship.decorator.decorator.MobilePlanDecorator;
import jpa.practice.relationship.decorator.plan.MobilePlan;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MobilePlanService {
    private final List<MobilePlanDecorator> mobilePlanDecorators;
    private final Map<DecoratorType, MobilePlanDecorator> decoratorMap = new HashMap<>();

    public MobilePlanService(List<MobilePlanDecorator> mobilePlanDecorators) {
        this.mobilePlanDecorators = mobilePlanDecorators;
        mobilePlanDecorators.forEach(decorator -> {
            decoratorMap.put(decorator.getType(), decorator);
        });
    }

    // Enum을 사용한 데코레이터 적용 메서드
    public MobilePlan applyDecorators(MobilePlan plan, DecoratorType... decorators) {
        MobilePlan decoratedPlan = plan;

        for (DecoratorType decoratorType : decorators) {
            MobilePlanDecorator decorator = decoratorMap.get(decoratorType);
            if (decorator != null) {
                decoratedPlan = decorator.apply(decoratedPlan);
            }
        }

        return decoratedPlan;
    }
}