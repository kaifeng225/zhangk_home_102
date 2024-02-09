<#assign textPromptTypes=["1","2","6","7","9","10","11"]>
<#assign multiOptionPromptTypes=["3","4"]>
<#assign dateTimePromptTypes=["5"]>
<#assign currencyPromptTypes=["12"]>
<#macro promptLineProcessor prompt>
    {
    <#if textPromptTypes?seq_contains(prompt.PromptType)>
        "controlType": "TEXT_QUESTION",
        <#if prompt.PromptType == "6" || prompt.PromptType == "12">
        <#-- this is numeric prompt -->
            "maxLength": "20",
            "numericOnly": "true",
            "minimumValue": "${convertToInt(prompt.Minimum)}",
            "maximumValue": "${(prompt.Maximum == "0")?then("",convertToInt(prompt.Maximum))}",
        <#else>
            "maxLength": "${(prompt.Maximum == "0")?then("2000", convertToInt(prompt.Maximum))}",
            "numericOnly": "false",
        </#if>
    <#elseif multiOptionPromptTypes?seq_contains(prompt.PromptType)>
        "controlType": "MULTI_OPTION_QUESTION",
        <#if prompt.PromptType == "3">
        <#-- this is yes/no prompt -->
            "multiOptionType":"RADIO_BUTTON",
            "responseList": [
            {"response":"Yes"},
            {"response":"No"}
            ],
        <#elseif prompt.PromptType == "4">
        <#-- this is table prompt -->
            "multiOptionType":"DROPDOWN",
            "responseList": [
            <#assign answers=prompt.TableChoices?is_hash?then(prompt.TableChoices.TableChoice,[])>
            <#list answers?is_enumerable?then(answers,[answers]) as answer>
                {"response": "${answer.Choice?truncate(100)?json_string}"}
                <#sep>, </#sep>
            </#list>
            ],
        </#if>
    <#elseif dateTimePromptTypes?seq_contains(prompt.PromptType)>
        "controlType": "DATETIME_QUESTION",
        "defaultDateStr": "${prompt.DefaultAnswer}",
        "showDate":"true",
    <#elseif currencyPromptTypes?seq_contains(prompt.PromptType)>
        "controlType": "CURRENCY_QUESTION",
        <#if prompt.DefaultAnswer?has_content>
            "defaultValue": "${prompt.DefaultAnswer}",
        </#if>
        "currencyCode": "USD",
        "precision": "2",
        "minimumValue": "${convertToLong(prompt.Minimum?number?floor)}",
        "maximumValue": "${(prompt.Maximum == "0")?then("",convertToLong(prompt.Maximum?number?ceiling))}",
        "currencyCodeType": "SYMBOL",
        "currencyCodePosition": "LEFT",
    </#if>
    "defaultValue": "${prompt.DefaultAnswer?truncate(100)?json_string}",
    "instructionalText": "${prompt.StatusLine?truncate(255)?json_string}",
    "label": "${prompt.Message?truncate(100)?json_string}",
    "name": "${prompt.Message?truncate(30)?json_string}",
    "position": "${prompt.DisplayOrder}",
    "required": "${prompt.Required}"
    }
</#macro>

<#macro itemProcesser item>
    {
    "billItem": {
    "name": "${item.ItemName?truncate(100)?json_string}",
    "billType": "OTHER",
    "customizedBillType": "OTHER",
    "description": "${item.ItemName?truncate(500)?json_string}",
    "linkFutureLocations": true,
    "isDisplayCui": true
    },
    "prompts": [
    <#assign promptInfos=item.PromptInfos?is_hash?then(item.PromptInfos.PromptInfo,[])>
    <#assign prompts=promptInfos?is_enumerable?then(promptInfos,[promptInfos])?filter(prompt -> prompt.Active == "True")>
    <#list prompts?filter(prompt-> prompt.OneOrMore == "True") as prompt>
        {
        "name": "prompt of ${item.ItemKey?truncate(20)}",
        "respView":"${request.vendorMethod.respView?json_string}",
        "promptLines": [
        <#list prompts?filter(prompt-> prompt.OneOrMore != "True") as prompt>
            <@promptLineProcessor prompt=prompt/>
            ,
        </#list>
        <@promptLineProcessor prompt=prompt/>
        ]
        }
        <#sep>, </#sep>
    <#else>
        <#if prompts?has_content>
            {
            "name": "prompt of ${item.ItemKey?truncate(20)}",
            "respView":"${request.vendorMethod.respView?json_string}",
            "promptLines": [
            <#list prompts as prompt>
                <@promptLineProcessor prompt=prompt/>
                <#sep>, </#sep>
            </#list>
            ]
            }
        </#if>
    </#list>
    ],
    "vendorItemIdentifier": "${item.ItemKey}",
    "sourceData": "${toQuotedJson(item)}"
    }
</#macro>

[
<#if response.itemsXML?has_content>
    <#assign items=parseXML(response.itemsXML).ItemXML![]>
    <#list items?is_enumerable?then(items,[items]) as item>
        <@itemProcesser item=item/>
        <#sep>, </#sep>
    </#list>
</#if>
]
