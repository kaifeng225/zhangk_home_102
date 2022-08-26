package active.webservice;

/**
 * 编写SEI(Service Endpoint Interface)，SEI在webservice中称为portType，在java中就是普通接口
 */
public interface WeatherInterface {
    public String queryWeather(String cityName);
}