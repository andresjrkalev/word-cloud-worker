package ee.word.cloud.worker.receiver;

import ee.word.cloud.worker.model.Message;
import ee.word.cloud.worker.model.Occurrence;
import ee.word.cloud.worker.repository.MessageRepository;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ee.word.cloud.worker.common.Constant.*;

@Component
public class MessageReceiver {
    private final MessageRepository messageRepository;

    public MessageReceiver(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Bean
    public Queue queue() {
        return  new Queue(QUEUE);
    }

    @RabbitListener(queues = QUEUE)
    public void receive(String response) {
        String[] words = response.split(REGEX_SPLIT_MESSAGE);
        List<String> wordsList = Arrays.stream(words).map(String::toLowerCase).toList();
        Set<Occurrence> occurrences = wordsList.stream()
                .filter(word -> !REDUNDANT_WORDS.contains(word))
                .map(word -> {
                    int count = Collections.frequency(wordsList, word);
                    return new Occurrence(word, count);
                }).collect(Collectors.toSet());
        Message message = new Message(response, occurrences);
        messageRepository.save(message);
    }
}
