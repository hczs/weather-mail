# weather-mail

#### 介绍
由于最近北京天气变化太快，下班也时长忘记看天气，所以使用springboot定时任务，每天下班（12:00、18:00）给我的邮箱发送天气信息，防止出现下班之后不带伞被雨淋的情况

#### 主要涉及技术点

1. 自定义注解 + aop，实现自定义日志注解，在方法上加上@PrintLog注解，即可在控制台打印方法执行的日志，如方法执行时间，执行了哪个方法，方法执行完毕结束时间
2. springboot定时任务
3. 邮件发送，使用hutool工具包封装的邮件发送
4. 使用restTemplate进行http调用
5. properties配置文件的读取


#### 安装使用教程

1.  拉取项目到本地
2.  使用idea，open打开，等待pom中依赖包的下载
3.  打开https://www.tianqiapi.com/index/doc?version=v6，本次天气调用使用的是此天气实况接口，使用需要邮箱注册
4.  注册完成之后，进入个人中心，把appid和appsecret，填写到配置文件weathermail.properties中
5.  打开https://gitee.com/wangjins/weather_api，city.json，搜索你的城市id，填写到weathermail.properties中
6.  weathermail.properties中的”to“配置为你的接收天气邮件信息的邮箱
7.  在resources的config目录下，配置mail.setting文件，这个邮箱是你发送天气邮件信息的邮箱，需要配置邮箱地址和授权码，授权码获取教程自行Google
8.  启动WeatherMailApplication，访问http://localhost:8050/hello/11，返回hello, world!代表启动完成
9.  可以在test/java/com/hc/weathermail包下找到测试类，自行测试配置文件和获取天气api是否可以正常使用
9.  【推荐】本地测试无问题后，将项目打jar包，放在服务器上，然后可以安心工作，下班就会有邮件提醒到邮箱了！


#### 参与贡献

1.  Fork 本仓库
2.  新建 feater-你的英文功能名 分支
3.  提交代码
4.  新建 Pull Request