package configs;

import graph.Agent;
import graph.Message;
import graph.Topic;
import graph.TopicManagerSingleton;

import java.util.function.BinaryOperator;

public class BinOpAgent implements Agent {

    public Agent agent;

    public String agentName;
    public String inputTopicName1;
    public String inputTopicName2;
    public String outputTopicName;
    public BinaryOperator<Double> operator;

    public BinOpAgent(String agentName, String inputTopicName1, String inputTopicName2, String outputTopicName, BinaryOperator<Double> operator) {

        this.agentName = agentName;
        this.inputTopicName1 = inputTopicName1;
        this.inputTopicName2 = inputTopicName2;
        this.outputTopicName = outputTopicName;
        this.operator = operator;

        TopicManagerSingleton.TopicManager tm = TopicManagerSingleton.get();

        Topic inputTopic1 = tm.getTopic(inputTopicName1);
        Topic inputTopic2 = tm.getTopic(inputTopicName2);
        Topic outputTopic = tm.getTopic(outputTopicName);

        if (inputTopic1 == null || inputTopic2 == null || outputTopic == null) {
            throw new IllegalArgumentException("Input topic or topic is null!");
        }


        this.agent = new Agent() {
            @Override
            public String getName() {
                return agentName;
            }

            @Override
            public void reset() {
                inputTopic1.publish(new Message(Double.NaN));
                inputTopic2.publish(new Message(Double.NaN));
            }

            @Override
            public void callback(String topic, Message msg) {

                //System.out.println("Callback received: " + topic + " -> " + msg.asDouble);
                if (topic.equals(inputTopicName1)) {
                    // Message came from the first topic
                    double x = msg.asDouble;
                    double y = (inputTopic2.getMsg() != null) ? inputTopic2.getMsg().asDouble : 0;
                    double result = operator.apply(x, y);
                    outputTopic.publish(new Message(result));
                } else if (topic.equals(inputTopicName2)) {
                    // Message came from the second topic
                    double y = msg.asDouble;
                    double x = (inputTopic1.getMsg() != null) ? inputTopic1.getMsg().asDouble : 0;
                    double result = operator.apply(x, y);
                    outputTopic.publish(new Message(result));
                }
            }

            @Override
            public void close() {
                System.out.println("Agent " + agentName + " closed");
            }


        };
        inputTopic1.subscribe(this.agent);
        inputTopic2.subscribe(this.agent);
        outputTopic.addPublisher(this.agent);
    }

    public void reset(TopicManagerSingleton.TopicManager tm) {
        System.out.println("Resetting topics.");
        tm.getTopic(this.inputTopicName1).publish(new Message(0.0));
        tm.getTopic(this.inputTopicName2).publish(new Message(0.0));
    }

    // Close the agent and unsubscribe from topics
    public void closeAgent() {
        System.out.println("Unsubscribing agent from topics.");
        TopicManagerSingleton.TopicManager tm = TopicManagerSingleton.get();
        tm.getTopic(this.inputTopicName1).unsubscribe(agent);
        tm.getTopic(this.inputTopicName2).unsubscribe(agent);
        tm.getTopic(this.outputTopicName).removePublisher(agent);
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public void reset() {

    }

    @Override
    public void callback(String topic, Message msg) {

    }

    @Override
    public void close() {

    }
}
