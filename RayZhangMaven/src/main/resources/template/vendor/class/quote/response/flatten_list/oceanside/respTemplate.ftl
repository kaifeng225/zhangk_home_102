<#ftl output_format="JSON">
<#import "/template/vendor/class/quote/response/classQuoteResponseUtils.ftl" as utils>
{
"statusID":"${response.statusID?string}",
"statusText":"${utils.replaceLineBreaker(response.statusText)?json_string}",
"dataGeneral": {
"viewType": "flattenList",
<#assign posItem=parseJson(request.vendorItem.sourceData!"")>
<#assign promptTypeMap={}>
<#if posItem.PromptInfos.PromptInfo?has_content>
    <#list posItem.PromptInfos.PromptInfo?is_enumerable?then(posItem.PromptInfos.PromptInfo,[posItem.PromptInfos.PromptInfo]) as prompt>
        <#assign promptTypeMap=promptTypeMap + {prompt.Message:prompt.PromptType}>
    </#list>
</#if>
<#if response.itemsXML?has_content>
    <#assign cartXML=parseXML(response.itemsXML)>
</#if>
    "primary": {
        <#assign answers=request.answers>
        <#list answers as answer>
            <#assign answerString = answer.answer!"">
            <#if promptTypeMap[answer.formEntryLabel] == "5" && answerString?has_content>
                <#assign answerString = (answerString?datetime.iso)?date?string["MM/dd/yyyy"]>
            </#if>
            <#if promptTypeMap[answer.formEntryLabel] == "12" && answerString?has_content>
                <#assign answerString = answerString?number?string.currency>
            </#if>
            "${answer.formEntryLabel}":"${answerString?json_string}"
            <#sep>, </#sep>
        </#list>
    },
    "secondary": {
        <#if response.itemsXML?has_content>
            <#assign promptInfos=posItem.PromptInfos?is_hash?then(posItem.PromptInfos.PromptInfo,[])>
            <#assign prompts=promptInfos?is_enumerable?then(promptInfos,[promptInfos])?filter(prompt -> prompt.Active == "True")>
            <@utils.secondaryInfoProcessor prompts=prompts cartXML=cartXML/>
        </#if>
    }
},
"dataList": [
<#if response.itemsXML?has_content>
    <#assign cartId=cartXML.CartID!"">
    <#assign items=utils.getItems(cartXML)>
    {
    <#assign amount=0>
    <#assign taxAmount=0>
    <#list items as item>
        <#assign amount=item.Amount?number + amount>
        <#assign taxAmount=item.Tax?number + taxAmount>
    </#list>
    "amount":"${amount?string.currency}",
    "taxAmount":"${taxAmount?string.currency}",
    "totalAmount": "${(amount + taxAmount)?string.currency}",
    "itemName": "${items[0].ItemName}",
    "itemKey": "${items[0].ItemKey}",
    "quoteDetailType":"${(items[0].ItemType == "Department")?then("USER_DEFINABLE","FIXED_PRICE")}",
    "flattenData":[
    <#list items as item>
        {
        "item":"${item.ItemName}",
        "amount": "${item.Amount?number?string.currency}",
        "taxAmount": "${item.Tax?number?string.currency}",
        "totalAmount": "${(item.Amount?number + item.Tax?number)?string.currency}",
        "itemKey": "${item.ItemKey}",
        "ver1": "${item.Ver1}",
        "ver2": "${item.Ver2}",
        "ver3": "${item.Ver3}",
        "isMainItem": ${(item.IsManySPFlag == "True")?then("false","true")},
        "prompts": [
            <#if item.IsManySPFlag == "True">
            	<#list utils.getValidItemPrompts(item) as validItemPrompt>
            	    {
            	    <#if validItemPrompt.question.Required == "True">
            	        "question":"${validItemPrompt.question.Message}(Required)",
            	    <#else>
            	        "question":"${validItemPrompt.question.Message}(Optional)",
            	    </#if>
            	     "required":"${validItemPrompt.question.Required}",
            	     "answer":"${validItemPrompt.answer.Answer}"
            	    }
                    <#sep>, </#sep>
                </#list>
            </#if>
        ]
        }
        <#sep>, </#sep>
    </#list>
    ],
    "items":[
    <#list items as item>
        {
        "itemName":"${item.ItemName}",
        "cartId":"${cartId}",
        "isMainItem": ${(item.IsManySPFlag == "True")?then("false","true")},
        "amount": "${item.Amount}",
        "itemKey": "${item.ItemKey}",
        "taxAmount": "${item.Tax}",
        "itemCounter": "${item.ItemCounter}"
        }
        <#sep>, </#sep>
    </#list>
    ]
    }
</#if>
]
}