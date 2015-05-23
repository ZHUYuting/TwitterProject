/**
 * Created by ZHU Yuting on 2015/05/07
 */
package user;

import topics.Publisher;

public class User {
    private String name;
    private String password;
    private Publisher publisher;

    public User(String name, String pwd) {
        this.name = name;
        this.password = pwd;
        this.publisher = new Publisher(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public Publisher getPublisher() {
        return this.publisher;
    }
}
