/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package active;

import active.webservice.WeatherInterfaceImpl;

import javax.xml.ws.Endpoint;

public class App {
    public String getGreeting() {
        return "Hello World!1";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
        Endpoint.publish("http://127.0.0.1:12345/weather", new WeatherInterfaceImpl());
    }
}
