package br.com.techfullit.mycluby.common.models;

public class MailConfig {

	private String subject;

	private String to;

	private String bodyContent;

	private boolean htmlBody;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getBodyContent() {
		return bodyContent;
	}

	public void setBodyContent(String bodyContent) {
		this.bodyContent = bodyContent;
	}

	public boolean isHtmlBody() {
		return htmlBody;
	}

	public void setHtmlBody(boolean htmlBody) {
		this.htmlBody = htmlBody;
	}

}
