package com.example.springboot.freemarker.templateengine;

/**
 * Error when processing the template, may caused by missing required property
 */
public class TemplateProcessingException extends RuntimeException {
	private final transient String template;
	private final transient Object templateProperties;

	public TemplateProcessingException(String message) {
		super(message);
		this.template = "template";
		templateProperties = null;
	}

	public TemplateProcessingException(String message, Throwable cause) {
		super(message, cause);
		this.template = "template";
		templateProperties = null;
	}

	public TemplateProcessingException(String message, String template, Object templateProperties, Throwable cause) {
		super(message, cause);
		this.template = template;
		this.templateProperties = templateProperties;
	}

	public String getTemplate() {
		return template;
	}

	public Object getTemplateProperties() {
		return templateProperties;
	}

	@Override
	public String toString() {
		return "TemplateProcessingException: " + getMessage() + "{" + "templateString='" + template + '\''
				+ ", templateProperties=" + templateProperties + '}';
	}
}
