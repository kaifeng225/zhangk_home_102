package com.example.springboot.json;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME, property = "EventType", include = JsonTypeInfo.As.EXISTING_PROPERTY, visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(value = InstanceEvent.class, names = {"process"}),
  @JsonSubTypes.Type(value = ReqArrayInstanceEvent.class, names = {"start"})
})
@Data
public class BaseInstanceEvent {

//	@JsonProperty("EeventType")
    private InstanceEventType eventType;

    @JsonSetter("EventType")
    public void setEventType(String eventType) {
    	System.out.println("======2======");
        this.eventType = InstanceEventType.valueOf(eventType.toUpperCase());
    }
}
