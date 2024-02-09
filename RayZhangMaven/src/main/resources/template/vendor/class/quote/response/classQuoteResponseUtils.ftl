<#macro secondaryInfoProcessor prompts cartXML>
    <#assign allPromptAnswers=[]>
    <#list getItems(cartXML) as item>
        <#if item.PromptAnswers?has_content && item.PromptAnswers.PromptAnswer?has_content>
            <#list item.PromptAnswers.PromptAnswer?is_enumerable?then(item.PromptAnswers.PromptAnswer,[item.PromptAnswers.PromptAnswer]) as promptAnswer>
                <#assign allPromptAnswers += [promptAnswer]>
            </#list>
        </#if>
    </#list>

    <#if prompts?has_content>
    	<#list prompts?is_enumerable?then(prompts,[prompts]) as prompt>
    		<#if prompt?index lt allPromptAnswers?size>
    			<#assign secondaryValues=[]>
    			<#assign returnString=(allPromptAnswers[prompt?index].ReturnString!"")?trim>
    			<#if returnString?has_content>
    				<#list splitLines(returnString) as secondaryValue>
    					<#if secondaryValue?trim?has_content>
    						<#assign secondaryValues += [secondaryValue?trim]>
    					</#if>
    				</#list>    
    			</#if> 
    			"${prompt.Message}": [
    				<#if secondaryValues?has_content>
    				<#list secondaryValues as secondaryValue>
    					<#if secondaryValue?has_content>
    						"${secondaryValue?json_string}"
    					    <#sep>, </#sep>
    					</#if>
    				</#list>
    				</#if>
    			]
            <#else>
                "${prompt.Message}": []
            </#if> 
            <#sep>, </#sep>
    	</#list>
    </#if>
</#macro>

<#function getItems cartXML>
    <#assign items=cartXML.ItemXML![]>
    <#return items?is_enumerable?then(items,[items])>
</#function>

<#function splitLines targetStr>
    <#if !targetStr?has_content || !targetStr?is_string>
        <#return []>
    </#if>

    <#return targetStr?split(getLineSeparator(targetStr))>
</#function>

<#function replaceLineBreaker targetStr>
    <#if !targetStr?has_content || !targetStr?is_string>
        <#return targetStr>
    </#if>

    <#return targetStr?replace(getLineSeparator(targetStr), '\n')>
</#function>

<#function getLineSeparator targetStr>
    <#assign separator = "\n">
    <#if targetStr?contains("\r\n")>
        <#assign separator = "\r\n">
    <#elseif targetStr?contains("&vbcftl")>
        <#assign separator = "&vbcftl">
    <#elseif targetStr?contains("<br>")>
        <#assign separator = "<br>">
    <#elseif targetStr?contains("<BR>")>
        <#assign separator = "<BR>">
    </#if>
	<#return separator>	
</#function>

<#function getItemPrompts item>
    <#assign prompts=[]>
    <#assign promptAnswers=[]>
    <#if item.PromptAnswers?has_content && item.PromptAnswers.PromptAnswer?has_content>
        <#list item.PromptAnswers.PromptAnswer?is_enumerable?then(item.PromptAnswers.PromptAnswer,[item.PromptAnswers.PromptAnswer]) as promptAnswer>
            <#assign promptAnswers += [promptAnswer]>
        </#list>
    </#if>
    <#if item.PromptInfos?has_content && item.PromptInfos.PromptInfo?has_content>
        <#list item.PromptInfos.PromptInfo?is_enumerable?then(item.PromptInfos.PromptInfo,[item.PromptInfos.PromptInfo]) as promptInfo>
			<#assign prompt = { "question": promptInfo}>
        	<#if promptInfo?index lt promptAnswers?size>
        		<#assign prompt += {"answer": promptAnswers[promptInfo?index]}>	
        	</#if>
        	<#assign prompts += [prompt]>
        </#list>
    </#if>

    <#return prompts>
</#function>

<#function getPromptDescription item>
	<#assign result = "">
	<#assign validPrompts = []>
	<#list getItemPrompts(item) as prompt>	
		<#if prompt.question?has_content && prompt.question.Message?has_content && prompt.answer?has_content && prompt.answer.Answer?has_content>
			<#assign validPrompts += [prompt]>
		</#if>
	</#list>
	<#list validPrompts as prompt>
		<#assign result += prompt.question.Message + ": " + prompt.answer.Answer>
		<#if prompt?has_next>
			<#assign result += "\n">			
		</#if>
	</#list>

	<#return result>
</#function>

<#function getValidItemPrompts item>
	<#assign validPrompts = []>
	<#list getItemPrompts(item) as prompt>
		<#if prompt.question?has_content && prompt.question.Message?has_content && prompt.answer?has_content && prompt.answer.Answer?has_content>
			<#assign validPrompts += [prompt]>
		</#if>
	</#list>

	<#return validPrompts>
</#function>