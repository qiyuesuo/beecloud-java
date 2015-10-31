## BeeCloud Java SDK (Open Source)

![pass](https://img.shields.io/badge/Build-pass-green.svg) ![license](https://img.shields.io/badge/license-MIT-brightgreen.svg) ![v2.3.0](https://img.shields.io/badge/Version-v2.3.0-blue.svg) 

## 简介

本项目的官方GitHub地址是 [https://github.com/beecloud/beecloud-java](https://github.com/beecloud/beecloud-java)

本SDK的是根据[BeeCloud Rest API](https://github.com/beecloud/beecloud-rest-api)开发的Java SDK，适用于JRE 1.6及以上平台。可以作为调用BeeCloud Rest API的示例或者直接用于生产。

## 安装

1.从[github](https://github.com/beecloud/beecloud-java/releases)下载带依赖的jar文件,然后导入到自己的工程依赖包中


2.若是工程采用maven进行依赖配置，可在自己工程的pom.xml文件里加入以下配置

```xml
<dependency>   
    <groupId>cn.beecloud</groupId>
    <artifactId>beecloud-java-sdk</artifactId>
    <version>2.3.0</version>
</dependency>
```
工程名以及版本号需要保持更新。（更新可参考本项目的pom.xml，文件最顶端）


## 注册

三个步骤，2分钟轻松搞定： 

1. 注册开发者：猛击这里注册成为[BeeCloud](https://beecloud.cn/register/)开发者

2. 注册应用：使用注册的账号登陆[控制台](https://beecloud.cn/login/)后，点击"+创建App"创建新应用

3. 在代码中注册：

  BeeCloud.registerApp(appid, appsecret);


## 使用方法

具体使用请参考本目录下的demo项目


### <a name="payment">支付</a>

支付接口接收BCPayParameter参数对象，该对象封装了发起支付所需的各个具体参数。BCPayParameter类提供了一个4个必填的具体支付参数作为参数的构造函数：
```java
public BCPayParameter(PAY_CHANNEL channel, Integer totalFee, String billNo, String title)
```
发起支付将返回BCPayResult对象，BCPayResult对象包含两种状态，正确状态和错误状态，正确状态的BCPayResult的type类型字符串为OK； 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErrDetail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。
#### <a name="wx_native">微信扫码调用</a>
正确状态调用getCodeUrl()方法返回二维码字符串，返回code url的格式为：weixin://wxpay/bizpayurl?sr=XXXXX。
请商户调用第三方库将返回的code url生成二维码图片。
该模式链接较短，生成的二维码打印到结账小票上的识别率较高。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.WX_NATIVE, 1, billNo, title);  
param.setBillTimeout(120);

BCPayResult bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	System.out.println(bcPayResult.getCodeUrl());
} else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```
#### <a name="wx_jsapi">微信公众号调用</a>
正确状态调用getWxJSAPIMap()方法返回jsapi map对象。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.WX_JSAPI, 1, billNo, title);
param.setOpenId(openId);

BCPayResult bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	System.out.println(bcPayResult.getWxJSAPIMap());
} else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="un_web">银联网页调用</a>
正确状态调用getHtml()方法，如将html输出至页面，即可开始银联网页支付。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.UN_WEB, 1, billNo, title);
param.setReturnUrl(unReturnUrl);
param.setBillTimeout(180);

bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	out.println(bcPayResult.getHtml());
} else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="ali_web">阿里网页调用</a>
正确状态调用getHtml()方法或者getUrl()方法，getHtml()方法返回html,如将html输出至页面，即可开始支付宝网页支付。getUrl()方法返回支付宝跳转url,推荐使用html。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.ALI_WEB, 1, billNo, title);
param.setReturnUrl(aliReturnUrl);
param.setBillTimeout(120);
param.setOptional(optional);
			
bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	out.println(bcPayResult.getHtml());
	response.sendRedirect(bcPayResult.getUrl());
} else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="ali_qrcode">阿里扫码调用</a>
正确状态调用getHtml()方法或者getUrl()方法，getHtml()方法返回html,如将html输出至页面，即可开始扫描支付。getUrl()方法返回支付宝内嵌二维码地址。需使用```<iframe>```加载此url
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.ALI_QRCODE, 1, billNo, title);
param.setReturnUrl(aliReturnUrl);
param.setQrPayMode(QR_PAY_MODE.MODE_FRONT);

bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
    //使用html示例
	out.println(bcPayResult.getHtml());
	//使用url示例
	out.println("<html><iframe width='420' height='330' name='url' frameborder='0' src='" + bcPayResult.getUrl() + "'></iframe</html>");
} else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="ali_wap">阿里移动网页调用</a>
正确状态调用getHtml()方法或者getUrl()方法，getHtml()方法返回html,如将html输出至页面，即可开始支付。getUrl()方法返回支付宝跳转url,推荐使用html。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.ALI_WAP, 1, billNo, title);

bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	out.println(bcPayResult.getHtml());
}
else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="kuaiqian_web">快钱网页调用</a>
正确状态调用getHtml()方法，getHtml()方法返回html,如将html输出至页面，即可开始快钱网页支付。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.KUAIQIAN_WEB, 1, billNo, title);
			
bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	out.println(bcPayResult.getHtml());
} else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="kuaiqian_wap">快钱移动网页调用</a>
正确状态调用getHtml()方法，getHtml()方法返回html,如将html输出至页面，即可开始快钱移动网页支付。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.KUAIQIAN_WAP, 1, billNo, title);
			
bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	out.println(bcPayResult.getHtml());
} else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="jd_web">京东网页调用</a>
正确状态调用getHtml()方法，getHtml()方法返回html,如将html输出至页面，即可开始京东网页支付。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.JD_WEB, 1, billNo, title);
param.setReturnUrl(jdReturnUrl);
			
bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	out.println(bcPayResult.getHtml());
} else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="jd_wap">京东移动网页调用</a>
正确状态调用getHtml()方法，getHtml()方法返回html,如将html输出至页面，即可开始京东移动网页支付。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.JD_WAP, 1, billNo, title);
param.setReturnUrl(jdReturnUrl);
			
bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	out.println(bcPayResult.getHtml());
} else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="yee_web">易宝网页调用</a>
正确状态调用getHtml()方法或者getUrl()方法，getHtml()方法返回html,如将html输出至页面，即可开始支付。getUrl()方法返回跳转url,推荐使用html。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.YEE_WEB, 1, billNo, title);
param.setReturnUrl(yeeWebReturnUrl);
param.setBillTimeout(180);

bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	out.println(bcPayResult.getHtml());
	response.sendRedirect(bcPayResult.getUrl());
}
else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="yee_wap">易宝移动网页调用</a>
正确状态调用getUrl()方法，getUrl()方法返回跳转url,如跳转至此url页面，即可开始支付。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.YEE_WAP, 1, billNo, title);
param.setBillTimeout(180);

bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	response.sendRedirect(bcPayResult.getUrl());
}
else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="yee_nobankcard">易宝点卡支付调用</a>
getSucessMsg()方法，getSucessMsg()方法显示支付已经成功。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.YEE_NOBANKCARD, 10, billNo, title);
param.setCardNo(cardNo);
param.setCardPwd(cardPwd);
param.setFrqid(frqid);

bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	out.println(bcPayResult.getObjectId());
	Thread.sleep(5000);
	out.println(bcPayResult.getSucessMsg());
}
else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="bd_wap">百度移动网页调用</a>
正确状态调用getUrl()方法，getUrl()方法返回跳转url,如跳转至此url页面，即可开始支付。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.BD_WAP, 1, billNo, title);
param.setReturnUrl(bdReturnUrl);
param.setBillTimeout(180);

bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	response.sendRedirect(bcPayResult.getUrl());
}
else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

#### <a name="bd_web">百度网页调用</a>
正确状态调用getUrl()方法，getUrl()方法返回跳转url,如跳转至此url页面，即可开始支付。
```java
BCPayParameter param = new BCPayParameter(PAY_CHANNEL.BD_WEB, 1, billNo, title);
param.setReturnUrl(bdReturnUrl);
param.setBillTimeout(180);

bcPayResult = BCPay.startBCPay(param);
if (bcPayResult.getType().ordinal() == 0) {
	response.sendRedirect(bcPayResult.getUrl());
}
else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```



代码中的参数对象BCPayParameter封装字段显示如下：

key | 说明
---- | -----
channel | 渠道类型， 根据不同场景选择不同的支付方式，包含：<br>WX_NATIVE 微信公众号二维码支付<br/>WX_JSAPI 微信公众号支付<br/>ALI_WEB 支付宝网页支付<br/>ALI_QRCODE 支付宝内嵌二维码支付<br>ALI_WAP 支付宝移动网页支付 <br/>UN_WEB 银联网页支付<br>JD_WEB 京东网页支付<br/> JD_WAP 京东移动网页支付<br/> YEE_WEB 易宝网页支付<br/> YEE_WAP 易宝移动网页支付<br/> YEE_NOBANKCARD 易宝点卡支付<br> KUAIQIAN_WEB 快钱网页支付<br/> KUAIQIAN_WAP 快钱移动网页支付<br/>BD_WEB 百度网页支付<br>BD_WAP 百度移动网页支付（必填）
totalFee | 订单总金额， 只能为整数，单位为分，例如 1，（必填）
billNo | 商户订单号, 8到32个字符内，数字和/或字母组合，确保在商户系统中唯一, 例如(201506101035040000001),（必填）
title | 订单标题， 32个字节内，最长支持16个汉字，（必填）
optional | 附加数据， 用户自定义的参数，将会在webhook通知中原样返回，该字段主要用于商户携带订单的自定义数据，（选填）
returnUrl | 同步返回页面	， 支付渠道处理完请求后,当前页面自动跳转到商户网站里指定页面的http路径。当 channel 参数为 ALI_WEB 或 ALI_QRCODE 或 UN_WEB 或 JD_WEB 或 JD_WAP时为必填，（选填）
openId | 微信公众号支付(WX_JSAPI)必填，（选填）
showUrl | 商品展示地址，需以http://开头的完整路径，例如：http://www.商户网址.com/myorder，（选填）
qrPayMode | 二维码类型，二维码类型含义MODE_BRIEF_FRONT： 订单码-简约前置模式, 对应 iframe 宽度不能小于 600px, 高度不能小于 300px<br>MODE_FRONT： 订单码-前置模式, 对应 iframe 宽度不能小于 300px, 高度不能小于 600px<br>MODE_MINI_FRONT： 订单码-迷你前置模式, 对应 iframe 宽度不能小于 75px, 高度不能小于 75px ，（选填）
billTimeoutValue | 订单失效时间，单位秒，非零正整数，快钱不支持此参数。例如：120（选填）
cardNo | 点卡卡号，每种卡的要求不一样，例如易宝支持的QQ币卡号是9位的，江苏省内部的QQ币卡号是15位，易宝不支付，当channel 参数为YEE_NOBANKCARD时必填，（选填）
cardPwd | 点卡密码，简称卡密当channel 参数为YEE_NOBANKCARD时必填，（选填）
frqid | 点卡类型编码：<br>骏网一卡通(JUNNET)<br>盛大卡(SNDACARD)<br>神州行(SZX)<br>征途卡(ZHENGTU)<br>Q币卡(QQCARD)<br>联通卡(UNICOM)<br>久游卡(JIUYOU)<br>易充卡(YICHONGCARD)<br>网易卡(NETEASE)<br>完美卡(WANMEI)<br>搜狐卡(SOHU)<br>电信卡(TELECOM)<br>纵游一卡通(ZONGYOU)<br>天下一卡通(TIANXIA)<br>天宏一卡通(TIANHONG)<br>32 一卡通(THIRTYTWOCARD)<br>当channel 参数为YEE_NOBANKCARD时必填，（选填）
return   |  BCPayResult对象， 根据type决定返回内容


### <a name="transfer">批量打款</a>
调用以下接口发起批量退款并将得到BCPayResult对象，BCPayResult对象包含两种状态，正确状态和错误状态，正确状态的BCPayResult的type类型字符串为OK, 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErrDetail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。正确状态调用getUrl()方法，getUrl()方法返回跳转url,如跳转至此url页面，即可开始支付。

```java
List<TransferData> list = new ArrayList<TransferData>();
TransferData data1 = new TransferData("transfertest11223", "13584809743", "袁某某", 1, "赏赐");
TransferData data2 = new TransferData("transfertest11224", "13584809742", "张某某", 1, "赏赐");
list.add(data1);
list.add(data2);


bcPayResult = BCPay.startTransfer(PAY_CHANNEL.ALI, billNo, "苏州比可网络科技有限公司", list);
if (bcPayResult.getType().ordinal() == 0) {
	response.sendRedirect(bcPayResult.getUrl());
}
else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErrDetail());
}
```

代码中的各个参数含义如下：

key | 说明
---- | -----
channel | 渠道类型， 暂时只支持ALI，（必填）
batchNo | 批量付款批号， 此次批量付款的唯一标示，11-32位数字字母组合，（必填）
accountName | 付款方的支付宝账户名, 支付宝账户名称,例如:毛毛，（必填）  
transferData |  付款的详细数据 {TransferData} 的 List集合，（必填）  
return | BCPayResult, 根据type决定返回内容


### <a name="refund">退款</a>
退款接口接收BCRefundParameter参数对象，该对象封装了发起退款所需的各个具体参数。BCRefundParameter类提供了一个3个必填的具体退款参数作为参数的构造函数：
```java
public BCRefundParameter(String billNo, String refundNo, Integer refundFee)
```
发起退款将得到BCPayResult对象。BCPayResult对象包含两种状态，正确状态和错误状态，正确状态的BCPayResult的type类型字符串为OK, 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErrDetail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。 如果是ALI退款，则需要调用getUrl()方法并跳转至该url，输入支付密码完成退款。

```java
BCRefundParameter param = new BCRefundParameter(billNo, refundNo, 1);
param.setOptional(optional);

BCPayResult result = BCPay.startBCRefund(param);
if (result.getType().ordinal() == 0) {
	if (result.getUrl() != null) {
		//阿里退款，跳转至退款url并输入支付密码完成退款
		response.sendRedirect(result.getUrl());
	} else {
		//其他渠道退款，返回"退款成功！" 
		out.println(result.getSucessMsg());
	}
} else {
	//handle the error message as you wish！
	out.println(result.getErrMsg());
	out.println(result.getErrDetail());
}
```

代码中的参数对象BCRefundParameter封装字段显示如下：

key | 说明
---- | -----
channel | 渠道类型， 根据不同场景选择不同的支付方式，包含：<br>WX  微信<br>ALI 支付宝<br>UN 银联<br>JD 京东<br>KUAIQIAN 快钱<br>YEE 易宝<br>BD 百度，（选填，可为NULL）
refundNo | 商户退款单号	， 格式为:退款日期(8位) + 流水号(3~24 位)。不可重复，且退款日期必须是当天日期。流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”，例如：201506101035040000001	（必填）
billNo | 商户订单号， 32个字符内，数字和/或字母组合，确保在商户系统中唯一，（必填）  
refundFee | 退款金额，只能为整数，单位为分，例如1，（必填）  
optional   |  附加数据 用户自定义的参数，将会在webhook通知中原样返回，该字段主要用于商户携带订单的自定义数据，例如{"key1":"value1","key2":"value2",...}, （选填）
needApproval | 标识该笔是预退款还是直接退款，true为预退款，false或者 null为直接退款，（选填）  
return | BCPayResult, 根据type决定返回内容

### <a name="billQuery">订单查询</a>

订单查询接口接收BCQueryParameter对象，该对象提供了一个无参的构造函数。
```java
BCQueryParameter param = new BCQueryParameter();
```
发起订单查询后返回BCQueryResult对象，BCQueryResult对象包含两种状态，正确状态和错误状态，正确状态的BCQueryResult的type类型字符串为OK, 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErrDetail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。

正确状态调用bcQueryResult.getBcOrders()方法返回订单(BCOrderBean)的list集合。调用者可任意遍历，显示这个订单的list对象。

```java
BCQueryParameter param = new BCQueryParameter();
param.setChannel(PAY_CHANNEL.ALI);

bcQueryResult = BCPay.startQueryBill(param);
if (bcQueryResult.getType().ordinal() == 0) {
    //handle the order list as you wish.
	pageContext.setAttribute("bills", bcQueryResult.getBcOrders());
} else {
	out.println(bcQueryResult.getErrMsg());
	out.println(bcQueryResult.getErrDetail());
}
```

代码中的参数对象BCQueryParameter封装字段含义如下：

key | 说明
---- | -----
channel | 渠道类型， 根据不同场景选择不同的支付方式，包含：<br>WX<br>WX_APP 微信手机APP支付<br>WX_NATIVE 微信公众号二维码支付<br>WX_JSAPI 微信公众号支付<br>ALI<br>ALI_APP 支付宝APP支付<br>ALI_WEB 支付宝网页支付<br>ALI_QRCODE<br>ALI_WAP 支付宝移动网页支付 支付宝内嵌二维码支付<br>UN<br>UN_APP 银联APP支付<br>UN_WEB 银联网页支付<br>KUAIQIAN<br>KUAIQIAN_WEB 快钱网页支付<br>KUAIQIAN_WAP 快钱移动网页支付<br>YEE<br>YEE_WEB 易宝网页支付<br>YEE_WAP 易宝移动网页支付<br>YEE_NOBANKCARD 易宝点卡支付<br>JD<br>JD_WEB 京东网页支付<br>JD_WAP 京东移动网页支付<br>PAYPAL<br>PAYPAL_SANDBOX<br>PAYPAL_LIVE<br>BD<br>BD_WEB 百度网页支付<br>BD_APP 百度APP支付<br>BD_WAP 百度移动网页支付,（选填）
billNo | 商户订单号，String类型，（选填）
startTime | 起始时间， Date类型，（选填）  
endTime | 结束时间， Date类型， （选填）  
skip   |  查询起始位置	 默认为0。设置为10，表示忽略满足条件的前10条数据， （选填）
limit |  查询的条数， 默认为10，最大为50。设置为10，表示只查询满足条件的10条数据，（选填）	
return | BCQueryResult, 根据type决定返回内容

### <a name="refundQuery">退款查询</a>
退款查询接口接收BCRefundQueryParameter对象，该对象提供了一个无参的构造函数。
```java
BCRefundQueryParameter param = new BCRefundQueryParameter();
```
发起退款查询将得到BCQueryResult对象，BCQueryResult对象包含两种状态，正确状态和错误状态，正确状态的BCQueryResult的type类型字符串为OK, 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErrDetail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。

正确状态调用bcQueryResult.getBcRefundList()方法返回退款记录(BCRefundBean)的list集合。调用者可任意遍历，显示这个退款记录的list对象。

```java
BCRefundQueryParameter param = new BCRefundQueryParameter();
param.setChannel(PAY_CHANNEL.ALI);

bcQueryResult = BCPay.startQueryRefund(param);
if (bcQueryResult.getType().ordinal() == 0) {
	pageContext.setAttribute("refundList", bcQueryResult.getBcRefundList());
} else {
	out.println(bcQueryResult.getErrMsg());
	out.println(bcQueryResult.getErrDetail());
}
```

代码中的参数对象BCRefundQueryParameter封装字段含义如下：

key | 说明
---- | -----
channel | 渠道类型， 根据不同场景选择不同的支付方式，包含：<br>WX<br>WX_APP 微信手机APP支付<br>WX_NATIVE 微信公众号二维码支付<br>WX_JSAPI 微信公众号支付<br>ALI<br>ALI_APP 支付宝APP支付<br>ALI_WEB 支付宝网页支付<br>ALI_QRCODE<br>ALI_WAP 支付宝移动网页支付 支付宝内嵌二维码支付<br>UN<br>UN_APP 银联APP支付<br>UN_WEB 银联网页支付<br>KUAIQIAN<br>KUAIQIAN_WEB 快钱网页支付<br>KUAIQIAN_WAP 快钱移动网页支付<br>YEE<br>YEE_WEB 易宝网页支付<br>YEE_WAP 易宝移动网页支付<br>JD<br>JD_WEB 京东网页支付<br>JD_WAP<br>BD<br>BD_WEB 百度网页支付<br>BD_APP 百度APP支付<br>BD_WAP 京东移动网页支付，（选填）
billNo | 商户订单号， 32个字符内，数字和/或字母组合，确保在商户系统中唯一, （选填）
refundNo | 商户退款单号， 格式为:退款日期(8位) + 流水号(3~24 位)。不可重复，且退款日期必须是当天日期。流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”	，（选填）
startTime | 起始时间， Date类型，（选填）  
endTime | 结束时间， Date类型， （选填）  
skip   |  查询起始位置	 默认为0。设置为10，表示忽略满足条件的前10条数据, （选填）
limit |  查询的条数， 默认为10，最大为50。设置为10，表示只查询满足条件的10条数据， （选填）	
return | BCQueryResult, 根据type决定返回内容



### <a name="RefundStatusQuery">退款状态查询</a>
调用以下接口发起退款状态查询并将得到BCQueryStatusResult对象，BCQueryStatusResult对象包含两种状态，正确状态和错误状态，正确状态的BCQueryStatusResult的type类型字符串为OK, 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErrDetail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。调用参数中，channel参数包含以下取值：
WX、YEE、KUAIQIAN、BD。

正确状态调用getRefundStatus()方法返回退款状态(Success, Processing, Fail ...)。调用者可任意处理这个值。

```java
BCQueryStatusResult result = BCPay.startRefundUpdate(PAY_CHANNEL.YEE, refund_no);
if (result.getType().ordinal() == 0 ) {
	out.println(result.getRefundStatus());
} else {
	out.println(result.getErrMsg());
	out.println(result.getErrDetail());
}
```
代码中的各个参数含义如下：

key | 说明
---- | -----
refundNo | 商户退款单号， 格式为:退款日期(8位) + 流水号(3~24 位)。不可重复，且退款日期必须是退款发起当日日期。流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”。，（必填）
channel | 渠道类型， 包含WX、YEE、KUAIQIAN和BD（必填）
return | BCQueryStatusResult, 根据type决定返回内容



### <a name="billQueryById">支付订单查询(指定ID)</a>
调用以下接口发起支付订单查询（指定ID）并将得到BCQueryResult对象，BCQueryResult对象包含两种状态，正确状态和错误状态，正确状态的BCQueryResult的type类型字符串为OK, 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErrDetail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。

正确状态调用getOrder()方法返回该笔订单对象。

```java
BCQueryResult result = BCPay.startQueryBillById(id);
	if (result.getType().ordinal() == 0) {
		pageContext.setAttribute("bill", result.getOrder());
	}else {
		out.println(result.getErrMsg());
		out.println(result.getErrDetail());
	}
```
代码中的各个参数含义如下：

key | 说明
---- | -----
id | 待查询订单记录的唯一标识符，（必填）
return | BCQueryResult, 根据type决定返回内容



### <a name="refundQueryById">退款订单查询(指定ID)</a>
调用以下接口发起支付订单查询（指定ID）并将得到BCQueryResult对象，BCQueryResult对象包含两种状态，正确状态和错误状态，正确状态的BCQueryResult的type类型字符串为OK, 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErrDetail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。

正确状态调用getRefund()方法返回该笔退款记录对象。

```java
BCQueryResult result = BCPay.startQueryRefundById(id);
	if (result.getType().ordinal() == 0) {
		pageContext.setAttribute("refund", result.getRefund());
	}else {
		out.println(result.getErrMsg());
		out.println(result.getErrDetail());
	}
```
代码中的各个参数含义如下：

key | 说明
---- | -----
id | 待查询订单记录的唯一标识符，（必填）
return | BCQueryResult, 根据type决定返回内容


## Demo
项目文件夹demo为我们的样例项目，详细展示如何使用java sdk.  
•关于支付宝的return_url  
请参考demo中的 aliReturnUrl.jsp 

•关于银联的return_url  
请参考demo中的 unReturnUrl.jsp

•关于京东PC网页的return_url  
请参考demo中的 jdWebReturnUrl.jsp

•关于京东移动网页的return_url  
请参考demo中的 jdWapReturnUrl.jsp

•关于快钱的return_url  
请参考demo中的 kqReturnUrl.jsp

•关于易宝PC网页的return_url  
请参考demo中的 yeeWebReturnUrl.jsp

•关于易宝移动网页的return_url  
请参考demo中的 yeeWapReturnUrl.jsp

•关于百度钱包的return_url  
请参考demo中的 bdReturnUrl.jsp

•关于weekhook的接收  
请参考demo中的 notifyUrl.jsp  文档请阅读 [webhook](https://github.com/beecloud/beecloud-webhook)

## 测试
TODO

## 常见问题
- 根据app_id找不到对应的APP/keyspace或者app_sign不正确,或者timestamp不是当前UTC，可能的原因：系统时间不准确 app_id和secret填写不正确，请以此排查如下：<br>
1.appid和appSecret填写是否一致<br>
2.校准系统时间
- 支付宝吊起支付返回调试错误，请回到请求来源地，重新发起请求。错误代码ILLEGAL_PARTNER，可能的原因：使用了测试账号test@beecloud.cn的支付宝支付参数。请使用自己申请的支付账号。



## 代码贡献
我们非常欢迎大家来贡献代码，我们会向贡献者致以最诚挚的敬意。

一般可以通过在Github上提交[Pull Request](https://github.com/beecloud/beecloud-java)来贡献代码。

Pull Request要求

•代码规范 


•代码格式化 


•必须添加测试！ - 如果没有测试（单元测试、集成测试都可以），那么提交的补丁是不会通过的。


•记得更新文档 - 保证 README.md 以及其他相关文档及时更新，和代码的变更保持一致性。


•创建feature分支 - 最好不要从你的master分支提交 pull request。


•一个feature提交一个pull请求 - 如果你的代码变更了多个操作，那就提交多个pull请求吧。


•清晰的commit历史 - 保证你的pull请求的每次commit操作都是有意义的。如果你开发中需要执行多次的即时commit操作，那么请把它们放到一起再提交pull请求。


## 联系我们
•如果有什么问题，可以到 321545822 BeeCloud开发者大联盟QQ群提问

•更详细的文档，见源代码的注释以及[官方文档](https://beecloud.cn/doc/?index=4)

•如果发现了bug，欢迎提交[issue](https://github.com/beecloud/beecloud-java/issues)

•如果有新的需求，欢x迎提交[issue](https://github.com/beecloud/beecloud-java/issues)

## 代码许可
The MIT License (MIT).
