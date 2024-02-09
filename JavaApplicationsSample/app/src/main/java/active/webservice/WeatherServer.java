package active.webservice;

import javax.xml.ws.Endpoint;

/**
 * 第三步：发布服务，Endpoint类发布服务，publish方法，两个参数：1.服务地址；2.服务实现类
 */
public class WeatherServer {
    public static void main(String[] args) {
        Endpoint.publish("http://127.0.0.1:12345/weather", new WeatherInterfaceImpl());
    }
    /**
     * 测试服务是否发布成功，通过阅读wsdl，确定客户端调用的接口、方法、参数和返回值存在，证明服务发布成功
     * 我们在浏览器输入 http://127.0.0.1:12345/weather?wsdl 来获取wsdl文件进行阅读
     * wsdl,是以XML文件形式来描述WebService的”说明书”,有了说明书,我们才可以知道如何使用或是调用这个服务.
     * 只要能获取到，就能确定WebService服务发布成功
     */
    
    /**
     * 客户端调用服务有很多种方法，我们先用工具生成客户端代码，后面会详解
     * wsimport是jdk自带的webservice客户端工具,可以根据wsdl文档生成客户端调用代码(java代码).当然,无论服务器端的WebService是用什么语言写的,都可以生成调用webservice的客户端代码。
     * 
     * 创建一个客户端空项目，cmd命令行进入此项目的src目录使用以下命令生成客户端代码  
     * wsimport -s . http://127.0.0.1:12345/weather?wsdl
     * -s是指编译出源代码文件,后面的.(点)指將代码放到当前目录下. 最后面的http….是指获取wsdl说明书的地址
     */
}