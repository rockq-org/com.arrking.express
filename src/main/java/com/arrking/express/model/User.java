package com.arrking.express.model;

/**
 * Created by hain on 06/01/2015.
 */
//{
//"id": "abby",
// "firstName": "Abby",
// "lastName": "Clinton",
// "url": "http://bizflow.mybluemix.net/service/identity/users/abby",
// "email": "Abby@arrking.com"
// }
public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String url;
    private String email;
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
