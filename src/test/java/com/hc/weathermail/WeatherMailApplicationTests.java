package com.hc.weathermail;

import com.hc.weathermail.entity.WeatherVo;
import com.hc.weathermail.utils.ConfigUtil;
import org.apache.commons.configuration.Configuration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


@SpringBootTest
class WeatherMailApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * 测试获取配置信息
     */
    @Test
    public void testGetConfig() {
        Configuration weatherConfig = ConfigUtil.getWeatherConfig();
        if (weatherConfig != null) {
            System.out.println(weatherConfig.getString("url"));
            System.out.println(weatherConfig.getString("to"));
            System.out.println(weatherConfig.getString("appid"));
            System.out.println(weatherConfig.getString("appsecret"));
            System.out.println(weatherConfig.getString("cityid"));
        }
    }

    /**
     * 测试获取天气信息
     */
    @Test
    public void testGetWeather() {
        Configuration weatherConfig = ConfigUtil.getWeatherConfig();
        if (weatherConfig != null) {
            String url = weatherConfig.getString("url");
            String to = weatherConfig.getString("to");
            String appid = weatherConfig.getString("appid");
            String appsecret = weatherConfig.getString("appsecret");
            String cityid = weatherConfig.getString("cityid");
            // 使用restTemplate发送请求
            RestTemplate restTemplate = new RestTemplate();
            // 准备参数
            String resUrl = url + "&" + "appid=" + appid +
                    "&" + "appsecret=" + appsecret +
                    "&" + "cityid=" + cityid;
            ResponseEntity<WeatherVo> res = restTemplate.getForEntity(resUrl, WeatherVo.class);
            WeatherVo weatherVo = res.getBody();
            System.out.println(weatherVo);
        }

    }
}
