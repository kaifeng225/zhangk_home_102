package com.example.springboot.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InstanceEvent extends BaseInstanceEvent {

    @JsonProperty("Request")
    private InstanceReq request;
}
