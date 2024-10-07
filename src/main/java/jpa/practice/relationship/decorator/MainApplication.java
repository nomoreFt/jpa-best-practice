package jpa.practice.relationship.decorator;


import jpa.practice.relationship.decorator.decorator.DecoratorType;
import jpa.practice.relationship.decorator.plan.BasicPlan;
import jpa.practice.relationship.decorator.plan.MobilePlan;
import jpa.practice.relationship.decorator.plan.PremiumPlan;
import jpa.practice.relationship.decorator.service.MobilePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication implements CommandLineRunner {

    @Autowired
    private MobilePlanService mobilePlanService;

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // 기본 요금제에 데코레이터 적용
        MobilePlan basicPlan = new BasicPlan();
        MobilePlan decoratedBasicPlan = mobilePlanService.applyDecorators(basicPlan, DecoratorType.ADDITIONAL_DATA,
                DecoratorType.INTERNATIONAL_CALL);
        System.out.println(decoratedBasicPlan.getDescription() + " cost: $" + decoratedBasicPlan.cost());

        MobilePlan decoratedBasicPlan2 = mobilePlanService.applyDecorators(basicPlan, DecoratorType.ADDITIONAL_DATA);
        System.out.println(decoratedBasicPlan2.getDescription() + " cost: $" + decoratedBasicPlan2.cost());

        MobilePlan premiumPlan = new PremiumPlan();
        MobilePlan decoratedPremiumPlan = mobilePlanService.applyDecorators(premiumPlan, DecoratorType.ADDITIONAL_DATA);
        System.out.println(decoratedPremiumPlan.getDescription() + " cost: $" + decoratedPremiumPlan.cost());

        /**
         * Basic Plan + Additional Data + International Call cost: $25.0
         * Basic Plan + Additional Data cost: $15.0
         * Premium Plan + Additional Data cost: $25.0
         */
    }
}