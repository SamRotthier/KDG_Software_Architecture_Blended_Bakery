package be.kdg.sa.bakery.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTopology {

    public final static String ORDER_INGREDIENT_QUEUE = "order-ingredient-queue";
    public final static String PRODUCT_STATE_QUEUE = "product-state-queue";
    public final static String NEW_PRODUCT_WAREHOUSE_QUEUE = "new-product-warehouse-queue";
    public final static String NEW_PRODUCT_CLIENT_QUEUE = "new-product-client-queue";
    public final static String DELIVER_QUEUE = "deliver-queue";
    public final static String ORDER_PRODUCT_QUEUE = "order-product-queue";

    public final static String CONFIRM_ORDER_INGREDIENT_QUEUE = "confirm-order-ingredient-queue";

    public static final String TOPIC_EXCHANGE = "bakery-exchange";


    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    //this is a new deliver queue
    @Bean
    public Queue newOrderIngredientQueue() {
        return new Queue(ORDER_INGREDIENT_QUEUE, false);
    }

    @Bean
    public Binding topicNewOrderIngredientBinding() {
        return BindingBuilder.bind(newOrderIngredientQueue()).to(topicExchange()).with(ORDER_INGREDIENT_QUEUE);
    }

    @Bean
    public Queue newProductStateQueue() {
        return new Queue(PRODUCT_STATE_QUEUE, false);
    }

    @Bean
    public Binding topicProductStateBinding() {
        return BindingBuilder.bind(newProductStateQueue()).to(topicExchange()).with(PRODUCT_STATE_QUEUE);
    }

    @Bean
    public Queue newProductWarehouseQueue() {
        return new Queue(NEW_PRODUCT_WAREHOUSE_QUEUE, false);
    }

    @Bean
    public Binding topicNewProductWarehouseBinding() {
        return BindingBuilder.bind(newProductWarehouseQueue()).to(topicExchange()).with(NEW_PRODUCT_WAREHOUSE_QUEUE);
    }

    @Bean
    public Queue newProductClientQueue() {
        return new Queue(NEW_PRODUCT_CLIENT_QUEUE, false);
    }

    @Bean
    public Binding topicNewProductClientBinding() {
        return BindingBuilder.bind(newProductClientQueue()).to(topicExchange()).with(NEW_PRODUCT_CLIENT_QUEUE);
    }

    @Bean
    public Queue newDeliverQueue() {
        return new Queue(DELIVER_QUEUE, false);
    }

    @Bean
    public Binding topicNewDeliverBinding() {
        return BindingBuilder.bind(newDeliverQueue()).to(topicExchange()).with(DELIVER_QUEUE);
    }

    @Bean
    public Queue newOrderProductQueue() {
        return new Queue(ORDER_PRODUCT_QUEUE, false);
    }


    @Bean
    public Binding topicOrderProductBinding() {
        return BindingBuilder.bind(newOrderProductQueue()).to(topicExchange()).with(ORDER_PRODUCT_QUEUE);
    }

    @Bean
    public Queue newConfirmOrderIngredientQueue() {
        return new Queue(CONFIRM_ORDER_INGREDIENT_QUEUE, false);
    }

    @Bean
    public Binding topicConfirmOrderIngredientBinding() {
        return BindingBuilder.bind(newConfirmOrderIngredientQueue()).to(topicExchange()).with(CONFIRM_ORDER_INGREDIENT_QUEUE);
    }


    @Bean
    RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());

        return rabbitTemplate;
    }

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}
