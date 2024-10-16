package active.webservice;

import javax.jws.WebService;

/**
 * 编写SEI实现类，此类作为webservice提供服务类
 */
@WebService     //@WebService表示该类是一个服务类，需要发布其中的public的方法
public class WeatherInterfaceImpl implements WeatherInterface {

    @Override
    public String queryWeather(String cityName) {
        System.out.println("获取城市名" + cityName);
        String weather = "暴雨";
        return weather;
    }

}