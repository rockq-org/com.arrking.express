## 微信4.2版导航栏效果
## 1.效果
![TabHost](http://fangjie-wordpress.stor.sinaapp.com/github/wechattab.gif "效果")
## 2.讲解
* ViewPager
主界面内容显示区域采用ViewPager+Fragment实现，采用FragmentPageAdapter，adapter的内容是Fragment

* BadgeView
显示消息提醒数目标记采用的是开源项目[BadgeView](https://github.com/stefanjauker/BadgeView)

* 导航栏下面的指示器根据滑动距离发生变化
原理就是通过改变指示器与左边的margin值来达到移动的效果  

```java
LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) mLine.getLayoutParams();
lp.leftMargin=position*ScreenWidth+(int)(positionOffset*ScreenWidth);
mLine.setLayoutParams(lp);
```

### 3. 启动程序
* 安装工具
gradle 1.12+, Android Studio 1.0.1+, Java SDK 7+, Ant 1.8+

* 下载源码
```
git clone git@github.com:JayFang1993/AndroidUtil.git 
```

* 下载依赖, 打包APK
```
cd AndroidUtil/WechatTab
gradle wrapper
./gradlew clean assembleRelease
```
现在，检查 ```./build/outputs/apk/WechatTab-release-unsigned.apk``` .

* 声称 IntelliJ IDEA || Android Studio Project
```
./gradlew idea
```
然后，打开IDE，添加项目即可。

* 其他命令
```
./gradlew task
```

* 声称
详细介绍：http://fangjie.info/
### 作者:by[@方杰_Jay](http://weibo.com/ncuitstudent) 
