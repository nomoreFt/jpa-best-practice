package jpa.practice.relationship.inheritance_message_module.domain.dispatcher.service;

import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.entity.Dispatcher;
import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.service.strategy.MessageSendStrategy;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class MessageSendService {

    private final List<MessageSendStrategy> messageSendStrategies;
    private final Map<Class<? extends Dispatcher>, MessageSendStrategy> messageSenderMap = new HashMap<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(10); // 스레드 풀 크기 설정

    // AtomicInteger로 동시성 문제를 방지하며 카운트
    private final AtomicInteger successCount = new AtomicInteger(0);
    private final AtomicInteger failureCount = new AtomicInteger(0);

    public MessageSendService(List<MessageSendStrategy> messageSendStrategies) {
        this.messageSendStrategies = messageSendStrategies;
        messageSendStrategies.forEach(sender -> messageSenderMap.put(sender.ofType(), sender));
    }

    // 비동기적으로 메시지를 발송하고 성공/실패를 카운팅
    public CompletableFuture<Void> processMessage(Dispatcher dispatcher) {
        return CompletableFuture.runAsync(() -> {
            MessageSendStrategy sender = messageSenderMap.get(dispatcher.getClass());
            if (sender != null) {
                sender.send(dispatcher);
            } else {
                throw new IllegalArgumentException("No message sender found for " + dispatcher.getClass().getSimpleName());
            }
        }, executorService).thenRun(() -> {
            // 성공 시 카운팅
            successCount.incrementAndGet();
        }).exceptionally(ex -> {
            // 실패 시 카운팅 및 예외 처리
            failureCount.incrementAndGet();
            System.err.println("Failed to send message for " + dispatcher.getClass().getSimpleName() + ": " + ex.getMessage());
            return null;
        });
    }

    // 메시지 리스트를 처리하며 성공/실패를 카운팅

    public CompletableFuture<Void> processMessages(List<Dispatcher> dispatchers) {
        // 각각의 메시지 발송 작업을 비동기로 실행
        List<CompletableFuture<Void>> futures = dispatchers.stream()
                .map(this::processMessage)
                .toList();

        // 모든 작업이 완료된 후 카운팅 결과 출력
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenRun(() -> {
                    System.out.println("All messages processed.");
                    System.out.println("Success count: " + successCount.get());
                    System.out.println("Failure count: " + failureCount.get());
                });
    }
}