package com.example.springboot.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReqArrayInstanceEvent extends BaseInstanceEvent {

    @JsonProperty("Request")
    private List<InstanceReq> request;
}
