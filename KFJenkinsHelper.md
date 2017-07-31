[TOC]

# 说明

此文档适用于Jenkins服务器 10.8.230.251:8088

**配置构建前，请检查您的工程在Gradle构建下的警告，某些警告是不会让编译通过的**

注意，module的名称不得使用 `app` `build` `gradle` `resource` `export` `decompile` `channel` 这些已经占用的文件夹名字

# 配置前准备

请按照模板工程的项目结构来提交工程

[模板工程链接](http://10.8.230.251/as_sdk_team/as_demo/tree/master)

* 拷贝模板工程中的 `.gitignore`，内容可以调整，建议只做自定义忽略部分
* 拷贝 `create_properties.sh` 和 `preparing_export_zip.sh` 到你的工程根目录
* 将准备好的 `resources` 文件 整个文件夹放置到工程根目录
* 获取工程的链接 `git@git.techcenter.com:......`

## 构建加速

构建服务器本地包含一个本地镜像缓存需要在 `工程根目录 build.gradle` 中设置

```gradle
allprojects {
    repositories {
        // 构建加速地址 - 只能在10.8.230.251 jenkins服务器上使用
        maven { url "file:////home/server/tmp/repo/kfsdk" }
    }
}
```

## 强制拉取最新依赖

```sh
gradlew clean --refresh-dependencies build --info
```

- 如果是Android Studio使用,需要配置在 `Setting -> Build,Ex... -> Compiler`

中 `Command-line Options` 加入 `--refresh-dependencies --info`

# 配置Jenkins

## 创建

* 新建一个Jenkins项目，选择 `构建一个自由风格的软件项目`
* 填写`项目名称` `描述`

## 配置源

选择 `git` 代码源 填写
* `Repository`git仓库地址
* `Credentials` 选择 `wayne(test)`
* `Branch Specifier` 填写 `*/master` 如果有分支要求的，写对应的分支名称
* 在选项 `Additional Behaviours` 选择 `add` 选择 `Wipe out repository & force clone`

## 配置构建

选择 `增加构建步骤`添加条目，后面的一样

* 添加`Execute shell`在`Command`中填写内容`chmod +x create_properties.sh && source ./create_properties.sh`
* 添加`Invoke Gradle script`在其中，选择`Gradle Version`版本`gradle-2.9` 在`Switches`中填写`clean build --info`
* 添加`Execute shell`在`Command`中填写内容`chmod +x preparing_export_zip.sh && source ./preparing_export_zip.sh`

> 如果做构建调试，`Switches`中填写`clean build --info --debug --stacktrace`

## 去除渠道不需要的资源功能 

* `Invoke Gradle script`在其中，选择`Gradle Version`版本`gradle-2.9` 在`Switches`中填写 `clean lintClean build --info`

`注意这个功能会导致资源文件删除! 用一定要测试!`

测试过程请每次删除 `app module` 下的 `build` 文件夹来清空缓存 

详细文档 https://github.com/marcoRS/lint-cleaner-plugin 

lintClean 插件使用参考

```gradle
lintCleaner {
    // 忽略不需要检查的文件,比如某些文件不能忽略,就填写进去
    exclude = ['com_crashlytics_export_strings.xml','config.xml']

    // 忽略所有的资源文件,默认不忽略,不建议开启
    ignoreResFiles = true

    // 设置忽略输出路径,一般不需要添加
    lintXmlFilePath = 'path/to/lint-results.xml'
}
```

> 定制构建后shell脚本，请在`preparing_export_zip.sh`中写入，不要在Jenkins中填写

发布配置 `构建后操作` 选择 `Archive the artifacts` 填入 `app/build/outputs/apk/*debug.apk,export/*.dex,*.zip`

填写完毕后点击屏幕下方 `保存`

# 测试构建

点击左上方`立即构建` 等待构建

# 构建错误调试

点击`构建历史条目中的左侧圆点` 或者 `进入构建历史条目后`点击`Console Output`

根据其中的日志内容进行调试

