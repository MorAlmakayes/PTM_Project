package graph;
import java.util.ArrayList;
import java.util.List;

public class Topic {
    public final String name;
    protected ArrayList<Agent> subs;
    protected ArrayList<Agent> pubs;
    protected Message msg;

    Topic(String name){
        this.name=name;
        this.subs=new ArrayList<>();
        this.pubs=new ArrayList<>();
    }

    public Topic getTopic(String name){
        return this;
    }

    public void subscribe(Agent a){
        subs.add(a);
    }
    public void unsubscribe(Agent a){
        subs.remove(a);
    }

    public void publish(Message m){
        msg=m;
        for(Agent a : subs){
            a.callback(this.name,m);
        }
    }

    public void addPublisher(Agent a){
        pubs.add(a);
    }

    public void removePublisher(Agent a){
        pubs.remove(a);
    }

    public Message getMsg(){
        return msg;
    }

    public List<Agent> getSubs() {
        return subs;
    }
    public List<Agent> getPubs() {
        return pubs;
    }
}
