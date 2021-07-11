package academy.learn.programming.events;

import java.time.LocalDateTime;

public class EventData {

    private String email;
    private LocalDateTime loginTime;

    public EventData() {
    }

    public EventData(String email, LocalDateTime loginTime) {
        this.email = email;
        this.loginTime = loginTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }
}
