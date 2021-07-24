package com.hc.weathermail.schedule;

import cn.hutool.extra.mail.MailUtil;
import com.hc.weathermail.entity.WeatherVo;
import com.hc.weathermail.utils.ConfigUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

/**
 * @author: houcheng
 * @date: 2021/7/22 14:34
 * @version: V1.0
 * @description:
 * @modify:
 */
@Component
@EnableScheduling
@Slf4j
public class StaticScheduleTask {

    /**
     * 每天12点和18点执行
     */
    @Scheduled(cron = "0 0 12,18 * * ?")
    public void sendWeatherMail() {
        Configuration weatherConfig = ConfigUtil.getWeatherConfig();
        if (weatherConfig != null) {
            String to = weatherConfig.getString("to");
            // 获取天气信息
            WeatherVo weatherInfo = getWeatherInfo(weatherConfig);
            if (weatherInfo != null) {
                log.info("获取天气信息：" + weatherInfo.toString());
                // 发送邮件
                StringBuilder sb = new StringBuilder();
                sb.append("当前城市：").append(weatherInfo.getCity()).append("\n")
                        .append("当前天气：").append(weatherInfo.getWea()).append("\n")
                        .append("最近更新时间：").append(weatherInfo.getDate()).append("\n")
                        .append("当前温度：").append(weatherInfo.getTem()).append("\n")
                        .append("最高温：").append(weatherInfo.getTem1()).append("\n")
                        .append("最低温：").append(weatherInfo.getTem2()).append("\n")
                        .append("风速:").append(weatherInfo.getWin_speed()).append("\n")
                        .append("温馨提示：").append(weatherInfo.getAir_tips());
                log.info("邮件内容：" + sb.toString());
                MailUtil.send(to, "天气情况", sb.toString(), false);
            } else {
                MailUtil.send(to, "获取天气情况失败", "应该是接口出问题了", false);
            }
        }
    }

    /**
     * 获取天气信息
     * @param weatherConfig 配置信息
     * @return WeatherVo
     */
    private WeatherVo getWeatherInfo(Configuration weatherConfig) {
        String url = weatherConfig.getString("url");
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
        return res.getBody();
    }

}
