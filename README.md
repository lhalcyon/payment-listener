## payment-monitor

__payment-monitor__ 是一款Android原生插件,对手机通知栏监听,针对支付类型通知进行回调.

目前支持

- 支付宝
- 收钱吧
- 工商银行
- 云闪付
- 微信 / 微信店员

### 0.0.3

这里更新了文档为uni的引入方式,历史版本写的是weex引入方法可能有所误导.

```javascript

<script>
	// 云端插件依赖后,代码引入
	const notifyListenerPlugin = uni.requireNativePlugin('lhalcyon-payment-monitor');

	export default {
		data() {
			return {
				title: 'Hello'
			}
		},
		methods: {
		  // 测试调用Android原生toast
			onToast(){
				notifyListenerPlugin.show('heheda');
			},
			// 获取是否有通知栏权限
			getNotification(){
				const isEnable = notifyListenerPlugin.isNotificationListening();
				notifyListenerPlugin.show('通知栏权限是否开启:'+isEnable);
			},
			// 跳转原生通知栏设置页, 需要Android >=5.1
			onJumpToSettings(){
				notifyListenerPlugin.jumpToNotificationSettingPage();
			},
			// 初始化通知监听服务,并设置监听回调
      // params 是string的map  ,主要key有 type,time,title,money,content,transferor 等
      // 具体可以参考 https://github.com/lhalcyon/payment-listener/tree/master/payment-listener/src/main/java/com/lhalcyon/pl/handler 查看各通知类型具体字段
			initNotificationCallback(){
				notifyListenerPlugin.show('init invoked');
				notifyListenerPlugin.initNotificationCallback(function (params) {
				    notifyListenerPlugin.show('notification callback:'+params);
				});
			},
      // 这里是将服务前台化,方便用户感知监听,以及提升服务存活性,但是需要 Android O及以上版本才能使用,否则和 startService() 方法一样处理
			startForegroundService(){
				notifyListenerPlugin.show('startForegroundService');
        // 这里的两个参数分别为通知栏的标题和描述
				notifyListenerPlugin.startForegroundService('plugin test title','working in the foreground');
			},
      // 停止这里调用 stopService() 是一样的效果
			stopForegroundService(){
				notifyListenerPlugin.show('stopForegroundService');
				notifyListenerPlugin.stopForegroundService();
			},
		}
	}
</script>

```

前台服务演示:

![](https://i.loli.net/2019/07/31/5d4131497aa0c72064.png)

回调中的params字段实例:

![](https://i.loli.net/2019/07/24/5d38155eaccad17075.png)



---






