/**
 * Created by ilkayaktas on 12.07.2021 at 14:11.
 */
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerApplication {

    private volatile boolean keepConsuming = true;
    private Consumer<String, String> consumer;

    public KafkaConsumerApplication(final Consumer<String, String> consumer) {
        this.consumer = consumer;
    }

    public void runConsume(final Properties consumerProps) {
        try {
            String topic = consumerProps.getProperty("input.topic.name");
            System.out.println("subscrbied "+topic);
            consumer.subscribe(Collections.singletonList(topic));
            long t1 = System.currentTimeMillis();
            while (keepConsuming) {
                final ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(10));
                long t2 = System.currentTimeMillis();
                if (consumerRecords.count() > 0){
                    System.out.println("New records are consumed! " + (t2-t1) + " " + consumerRecords.count());
                    consumerRecords.forEach(record -> {
                        System.out.println(record.topic() + " " + record.key() + " " + record.value() + " ");
                    });
                }
                t1 = t2;
            }
        } finally {
            consumer.close();
        }
    }

    public void shutdown() {
        keepConsuming = false;
    }

    public static Properties loadProperties(String fileName) throws IOException {
        final Properties props = new Properties();
        final FileInputStream input = new FileInputStream(fileName);
        props.load(input);
        input.close();
        return props;
    }

    public static void main(String[] args) throws Exception {

        if (args.length < 1) {
            throw new IllegalArgumentException(
                    "This program takes one argument: the path to an environment configuration file.");
        }

        final Properties consumerAppProps = KafkaConsumerApplication.loadProperties(args[0]);
        final Consumer<String, String> consumer = new KafkaConsumer<>(consumerAppProps);
        final KafkaConsumerApplication consumerApplication = new KafkaConsumerApplication(consumer);

        Runtime.getRuntime().addShutdownHook(new Thread(consumerApplication::shutdown));

        consumerApplication.runConsume(consumerAppProps);
    }

}