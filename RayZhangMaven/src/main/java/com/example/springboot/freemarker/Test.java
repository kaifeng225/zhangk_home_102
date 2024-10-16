package com.example.springboot.freemarker;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Lists;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.micrometer.core.instrument.util.StringEscapeUtils;

/**
 * @author Robyn Liu
 */
public class Test {
    public static final String FIELD_QUOTE_DETAILS = "quoteDetails";
    public static final String FIELD_PAYMENT_TYPE_DETAILS = "paymentTypeDetails";

    private static void escapeJson() {
        String s = "<#ftl output_format=\\\"XML\\\"><#assign posItem=parseJson(request.vendorItem.sourceData!\\\"\\\")><?xml version=\\\"1.0\\\"?><POSItemParams>    <POSItemParam>        <ItemKey>${posItem.ItemKey}</ItemKey>        <PromptAnswers>            <#if posItem.PromptInfos.PromptInfo?has_content>                <#assign answers=request.answers>                <#assign answerMap={}>                <#list answers as answer>                    <#assign answerMap=answerMap + {answer.formEntryLabel:answer.answer!\\\"\\\"}>                </#list>        <#list posItem.PromptInfos.PromptInfo?is_enumerable?then(posItem.PromptInfos.PromptInfo,[posItem.PromptInfos.PromptInfo]) as prompt>            <PromptAnswer>                <#assign answer = answerMap[prompt.Message]!\\\"\\\">                <#if prompt.PromptType == \\\"5\\\" && answer?has_content>                    <#assign answer = (answer?datetime.iso)?date?iso_utc>                </#if>                <Answer>${answer}</Answer>            </PromptAnswer>        </#list>            </#if>        </PromptAnswers>    </POSItemParam></POSItemParams>";
        System.out.println(StringEscapeUtils.escapeJson(s));
    }

    private static void freemaker() throws IOException {
        Reader reader = new FileReader(new File("C:/temp/vendorItemResp.ftl"));

        Template template = new Template("test", reader, null, "utf-8");

        Map<String, Object> params = new HashMap<>();
        Map<String, Object> request = new HashMap<>();
        request.put("amountDue", "15522.333");
        request.put("dateDue", "");
        params.put(FIELD_QUOTE_DETAILS, "");
        params.put(FIELD_PAYMENT_TYPE_DETAILS, "");
        params.put("amount", 99999.99);
        params.put("taxAmount", 99999.99);
        params.put("statusID", 0);
        params.put("request", request);
        params.put("statusTest", "~!@#$%^&*()_+{}|\\\":?><,./;'[]\\\\=-`1");
        params.put("cartId", "48861");
        Map<String, Object> item1 = new HashMap<String, Object>();
        item1.put("ItemName", "Business Licesnse Renewal");
        item1.put("IsManySPFlag", "False");
        item1.put("Amount", "0");
        item1.put("ItemKey", "101");
        item1.put("Tax", "0");
        item1.put("ItemCounter", "1");
        Map<String, Object> item2 = new HashMap<String, Object>();
        item2.put("ItemName", "ADMINISTRATIVE FEE");
        item2.put("IsManySPFlag", "True");
        item2.put("Amount", "0.99");
        item2.put("ItemKey", "1994");
        item2.put("Tax", "0");
        item2.put("ItemCounter", "1");
        Map<String, Object> item3 = new HashMap<String, Object>();
        item3.put("ItemName", "ADMIN COMMERCIAL");
        item3.put("IsManySPFlag", "True");
        item3.put("Amount", "5.99");
        item3.put("ItemKey", "1884");
        item3.put("Tax", "0");
        item3.put("ItemCounter", "1");
        Map<String, Object> item4 = new HashMap<String, Object>();
        item4.put("ItemName", "AR Invoice Online");
        item4.put("IsManySPFlag", "False");
        item4.put("Amount", "9.99");
        item4.put("ItemKey", "1884");
        item4.put("Tax", "0");
        item4.put("ItemCounter", "1");
        params.put("items", Lists.newArrayList(item1, item2, item3, item4));
        Writer writer = new PrintWriter(System.out);

        try {
            template.process(params, writer);
        } catch (TemplateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            writer.flush();
            writer.close();
            reader.close();
        }
    }

    public static void main(String[] args) {
        try {
            freemaker();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
