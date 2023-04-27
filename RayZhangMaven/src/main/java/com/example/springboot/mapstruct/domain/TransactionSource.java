package com.example.springboot.mapstruct.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TransactionSource {
    FRONT_DESK("FRONT_DESK", false),
    CITIZEN_UI("CITIZEN_UI", true),
    SMS("SMS", true),
    IVR("IVR", true),

    POS("POS-Offline", false);

    private static final Map<String, TransactionSource> OMS_SALES_CHANNEL_MAP = new HashMap<>();

    static {
        for (TransactionSource source : values()) {
            OMS_SALES_CHANNEL_MAP.put(source.getOmsSalesChannel(), source);
        }
    }

    /**
     * Since POS-service use 'POS-Offline'  as the sales channel pass to oms, 'POS-Offline' can't be a valid name in java enum,
     * so we need this property to mapping the value
     */
    private String omsSalesChannel;
    private boolean online;


    public static TransactionSource getByOmsSalesChannel(String omsSalesChannel) {
        return OMS_SALES_CHANNEL_MAP.get(omsSalesChannel);
    }
}
