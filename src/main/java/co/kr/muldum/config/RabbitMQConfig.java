package co.kr.muldum.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String TEAM_QUERY_QUEUE = "team.query.queue";
    public static final String TEAM_QUERY_EXCHANGE = "team.query.exchange";
    public static final String TEAM_QUERY_ROUTING_KEY = "team.query.routing.key";
    public static final String TEAM_QUERY_RESPONSE_QUEUE = "team.query.queue.response";
    public static final String TEAM_QUERY_RESPONSE_ROUTING_KEY = "team.query.response.routing.key";

    @Bean
    public Queue teamQueryQueue() {
        return new Queue(TEAM_QUERY_QUEUE, true);
    }

    @Bean
    public Queue teamQueryResponseQueue() {
        return new Queue(TEAM_QUERY_RESPONSE_QUEUE, true);
    }

    @Bean
    public DirectExchange teamQueryExchange() {
        return new DirectExchange(TEAM_QUERY_EXCHANGE);
    }

    @Bean
    public Binding teamQueryBinding(Queue teamQueryQueue, DirectExchange teamQueryExchange) {
        return BindingBuilder.bind(teamQueryQueue)
                .to(teamQueryExchange)
                .with(TEAM_QUERY_ROUTING_KEY);
    }

    @Bean
    public Binding teamQueryResponseBinding(Queue teamQueryResponseQueue, DirectExchange teamQueryExchange) {
        return BindingBuilder.bind(teamQueryResponseQueue)
                .to(teamQueryExchange)
                .with(TEAM_QUERY_RESPONSE_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }
}