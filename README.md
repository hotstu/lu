Lu-Another
======================

[Lu-Another]是一款我用业余时间开发的的

基于Virtual DOM的原生UI渲染框架

类似[Weex]、[ReactNative],

但不是对他们的复制，而是独立开发从底层写起

设计开发理念是实现一个极简的, 界面渲染各种业务逻辑全部在JS中, 实现了App的动态化。


虽然无法和大公司的成熟框架相比，但他内部的原理是相通的

研究学习探索的过程也是不断自我成长的过程

在这个这个项目开发过程中不仅实现了virtualDom的渲染引擎

还实现了基于web的原生界面编辑器(可以在浏览器中编辑界面在手机中实时看到渲染效果)

封装了Rhino Js引擎

掌握了Android中动态加载动态Class的技术

基于AIDL的模块化技术


# demo使用方式

手机、PC连接在同一wifi下， 启动App， PC访问`http://手机ip:8080/`

(如果手机为模拟器， 需要先执行`adb -e forward tcp:8080 tcp:8080`, 然后访问`http://127.0.0.1:8080/`)


# 运行效果

<img src="./demo.png" width="400">



## more about me

|简书| 掘金|JCenter | dockerHub|
| ------------- |------------- |------------- |------------- |
| [简书](https://www.jianshu.com/u/ca2207af2001) | [掘金](https://juejin.im/user/5bee320651882516be2ebbfe) |[JCenter ](https://bintray.com/hglf/maven)      | [dockerHub](https://hub.docker.com/u/hglf)|





