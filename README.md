# RareBox
**一个完全开源免费的WearOS手表和Android调试工具箱。麻雀虽小五脏俱全！**

![RareBox](https://github.com/Genouka/RareBox/assets/142009020/90a9719c-9f8e-4d96-a1a2-4c9b7902841b)


很抱歉我把许可证和贡献提到在前面，但如果我不这么做，没有人会去认真看。

如果你要查看其他内容，请往下多翻一下。

# 许可证与贡献
以下提到的"作者"指代的均为Genouka，而非"贡献者"。

本项目采用三种不同的授权方式授权：

1.仅当遵守以下要求时，你被允许根据AGPL3.0获得许可（除非作者更改许可证，否则不允许使用后续版本）：

  - (1). 当你把项目作为整体使用时，你不能获得授权。（包括但不限于：你只是克隆仓库并修改许可证；你将本项目整体不做改动或略做改动便公开发布）
  
  - (2). 禁止将你编译的产物（如apk、aab等，包括修改后的，例如将应用程序名称重命名为“TerBox”）分发到任何应用商店（包括但不限于 Google Play store、F-Droid、亚马逊应用商店、酷安、小问商店、小趣商店等）。

  - (3). 当你被授权后违反以上要求时，你立即失去授权。 **请注意：本项要求的优先级高于AGPL3.0。**
  
  - (4). `app/src/main/res/drawable/ic_launcher.webp`图标不属于授权范围，版权和所有权归GenOuka所有！

2.商用授权，通过联系作者获得关于书面授权的详细信息。

3.作者直接取得授权，作者始终持有所有权和所有一切关于本源码和其产物的权利。

无论采用哪种方式授权，本软件都不含任何保证。

当你为本项目做任何贡献时必须同意：

   (1). 你永久并且不可撤销地授权：任何人都可以通过以上三种不同的授权方式中任意一种获得授权。

   (2). 你永久并且不可撤销地放弃你贡献的代码的专利。

   (3). 你永久并且不可撤销地授权作者持有你所贡献的内容的所有权和处理权。

以上是根据RareBox原来是一个闭源软件而做出的综合考量，如果你对以上条件感到不满，请知悉：

   (1). 没有什么软件开发起来是容易的。

   (2). 没有什么理由是让一个软件必须开源的。

   (3). 作者没有义务处理你的问题，贡献者也没有。

   (4). 本项目纯粹用爱发电，爱用不用，不管用不用你都没有理由骂人。
   
# 功能
在手表上自调试：

- [x] adb终端
- [x] 安装应用
- [x] 修改dpi、显示范围

用Android设备调试手表：

- [x] adb终端
- [x] 安装应用
- [x] 修改dpi、显示范围
- [x] 传输文件

用手表调试另一手表：

与用Android设备调试设备是一回事。

用手表/Android设备调试另一Android设备：

没考虑过，目前调试Android8以下设备应该没有问题。

# 需要的贡献
如果你有Android开发经验，请帮忙修改本项目！

现在正在努力解决以下问题，欢迎PR：

- [ ] WearOS3/4上的配对调试
- [ ] 更好的选择文件界面
- [ ] 用户自定义扩展功能
- [ ] 华为手表调试功能
- [ ] UI设计美化
- [ ] 支持从文件管理器唤起安装
- [ ] 更多支持的终端命令
