package com.pluralsight.practicaljms;

/**
 * Created by Grant Little grant@grantlittle.me
 */
public class Email {

    private String emailAddress;
    private String subject;
    private String text;

    public Email() {
    }

    public Email(String emailAddress, String subject, String text) {
        this.emailAddress = emailAddress;
        this.subject = subject;
        this.text = text;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email email = (Email) o;

        if (emailAddress != null ? !emailAddress.equals(email.emailAddress) : email.emailAddress != null) return false;
        if (subject != null ? !subject.equals(email.subject) : email.subject != null) return false;
        return text != null ? text.equals(email.text) : email.text == null;
    }

    @Override
    public int hashCode() {
        int result = emailAddress != null ? emailAddress.hashCode() : 0;
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Email{");
        sb.append("emailAddress='").append(emailAddress).append('\'');
        sb.append(", subject='").append(subject).append('\'');
        sb.append(", text='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
