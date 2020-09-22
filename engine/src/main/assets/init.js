var global = this;


(function () {
    //重定向importClass使得其支持字符串参数
    global.importClass =
        (function () {
            var __importClass__ = importClass;
            return function (pack) {
                if (typeof (pack) == "string") {
                    __importClass__(Packages[pack]);
                } else {
                    __importClass__(pack);
                }
            }
        })();

    //内部函数

     // 初始化基础模块

     //初始化不依赖环境的模块

})();


