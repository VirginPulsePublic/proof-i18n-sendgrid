package com.vieques.proofs;

public class TemplateVersion {
    private String id;

    private Long user_id;

    private String template_id;

    private Boolean active;

    private String name;

    private String html_content;

    private String plain_content;

    private String subject;

    private String updated_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHtml_content() {
        return html_content;
    }

    public void setHtml_content(String html_content) {
        this.html_content = html_content;
    }

    public String getPlain_content() {
        return plain_content;
    }

    public void setPlain_content(String plain_content) {
        this.plain_content = plain_content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "TemplateVersion{" +
                "id='" + id + '\'' +
                ", user_id=" + user_id +
                ", template_id='" + template_id + '\'' +
                ", active=" + active +
                ", name='" + name + '\'' +
                ", html_content='" + html_content + '\'' +
                ", plain_content='" + plain_content + '\'' +
                ", subject='" + subject + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
