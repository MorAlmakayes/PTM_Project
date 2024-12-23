package graph;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class TopicManagerSingleton {



    public static class TopicManager{

        public static final TopicManager instance = new TopicManager();
        public static final ConcurrentHashMap<String, Topic> topics = new ConcurrentHashMap<>();

        public static Topic getTopic(String topicName){
            return topics.computeIfAbsent(topicName,Topic:: new);
        }

        public Collection<Topic> getTopics(){
            return topics.values();
        }

        public void clear(){
            topics.clear();
        }
    }



    public static TopicManager get(){

        return TopicManager.instance;
    }
}
