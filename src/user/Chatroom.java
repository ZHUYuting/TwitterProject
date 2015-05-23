package user;

import javax.jms.JMSException;

import topics.Subscriber;

public class Chatroom {
    private Subscriber subscriber;
    private String topics[];

    public Chatroom() {
        subscriber = new Subscriber();
        lancer();
    }

    private void lancer() {
        try {
            subscriber.lancerLecture(this.topics);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
