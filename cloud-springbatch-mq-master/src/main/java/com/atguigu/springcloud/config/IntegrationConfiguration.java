package com.atguigu.springcloud.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.amqp.inbound.AmqpInboundChannelAdapter;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.NullChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.PollableChannel;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 远程分区通讯
 *
 * @author huyang
 * @date 2020/7/13 16:28
 */
//注入配置文件属性
@Configuration
@ConfigurationProperties(prefix = "spring.rabbit")
public class IntegrationConfiguration {
    private String host;
    private Integer port=5672;
    private String username;
    private String password;
    private String virtualHost;
    private int connRecvThreads=5;
    private int channelCacheSize=10;

    /**
     * 配置连接的MQ服务器，采用连接池
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(connRecvThreads);
        executor.initialize();
        connectionFactory.setExecutor(executor);
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setChannelCacheSize(channelCacheSize);
        return connectionFactory;
    }

    /**
     * integrate用来发送和接收消息的，
     * 原生的jms使用复杂spring已经简化，
     * 这里设置了默认的信道，所以分片完成后会自动发送到DirectChannel信道中
     */
    @Bean
    public MessagingTemplate messageTemplate() {
        MessagingTemplate messagingTemplate = new MessagingTemplate(outboundRequests());
        messagingTemplate.setReceiveTimeout(60000000l);
        return messagingTemplate;
    }

    /**
     * integrate支持点对点，和发布订阅,
     * 此处采用点对点，发送消息的信道
     */
    @Bean
    public DirectChannel outboundRequests() {
        return new DirectChannel();
    }

    /**
     * 1、分区后的数据由PartitionHandler发送到信道里面
     *
     * 2、所有发送到outboundRequests的消息将会被此方法拦截，
     * AMQP提供的AmqpTemplate接口定义了发送和接收消息的基本操作，
     * 扮演者关键的角色（此处会注入RabbitTemplate）
     *
     * 3、inboundRequests,进过此方法处理后将会发给QueueChannel
     */
    @Bean
    @ServiceActivator(inputChannel = "outboundRequests")
    public AmqpOutboundEndpoint amqpOutboundEndpoint(AmqpTemplate template) {
        AmqpOutboundEndpoint endpoint = new AmqpOutboundEndpoint(template);
        // setExpectReply请求/回复,设置为true时将进行发送并会期待接受回复，
        // setRoutingKey因为设置了接受回复，设置了partition.requests这里默认绑定到name为partition.requests的队列
        endpoint.setExpectReply(true);
        endpoint.setOutputChannel(inboundRequests());
        endpoint.setRoutingKey("partition.requests");
        return endpoint;
    }

    /**
     * channel只是用来与队列交互的一个东西，不能直接操作队列。
     *
     * 接收是在channel上订阅指定的队列消息
     * 发送一般是通过channel带上routingKey发送到指定的exchange,exchange上根据routingKey绑定queue来决定发送到什么队列
     * 不显式声明交换机时并且发送消息不指定交换机，则默认使用Direct，并且声明队列时，
     * 不显式绑定队列与交换机，则队列以队列名为routing-key绑定到默认的direct交换机，发送消息不指定交换机时，则将消息发到默认的direct交换机
     */
    @Bean
    public Queue requestQueue() {
        return new Queue("partition.requests", false);
    }

    @Bean
    @Profile({"slave","mixed"})
    public AmqpInboundChannelAdapter inbound(SimpleMessageListenerContainer listenerContainer) {
        AmqpInboundChannelAdapter adapter = new AmqpInboundChannelAdapter(listenerContainer);
        adapter.setOutputChannel(inboundRequests());
        adapter.afterPropertiesSet();
        return adapter;
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames("partition.requests");
        container.setAutoStartup(false);

        return container;
    }

    @Bean
    public PollableChannel outboundStaging() {
        return new NullChannel();
    }

    /**
     * QueueChannel，允许消息接收者轮询获得消息，
     * 用一个队列(queue)接收消息，队列的容量大小可配置
     */
    @Bean
    public QueueChannel inboundRequests() {
        return new QueueChannel();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public int getConnRecvThreads() {
        return connRecvThreads;
    }

    public void setConnRecvThreads(int connRecvThreads) {
        this.connRecvThreads = connRecvThreads;
    }

    public int getChannelCacheSize() {
        return channelCacheSize;
    }

    public void setChannelCacheSize(int channelCacheSize) {
        this.channelCacheSize = channelCacheSize;
    }
}

