# 基于 BTrace 实现 Velocity 渲染 XSS 探测

* (welcome.vm)[src/main/resources/templates/welcome.vm] - Velocity 模板, 包含了常用的 Case
* (WelcomeController.java)[src/main/java/sample/web/velocity/WelcomeController.java] - 请求处理逻辑, 可调整 model 数据
* (XSS.java)[/src/main/btrace/XSS.java] - BTrace 探测脚本, 可增加检测规则

## 启动服务

```
mvn clean spring-boot:run
```

## 点击 <http://localhost:8080/>

观察日志输出, 会有脚本探测到的渲染内容:

```
Render $head -> Change me
Render $bean.text -> hey
Render $i -> 1
Render $k -> sh
Render $k -> sh
Render $bean.map.get($k) -> it
```


## References

* <https://github.com/btraceio/btrace>