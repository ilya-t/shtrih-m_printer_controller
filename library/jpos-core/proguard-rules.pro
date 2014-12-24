# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Applications/DevApps/android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontobfuscate
-dontpreverify
-dontwarn **


-keep class jpos.JposConst
-keepclassmembers class jpos.JposConst{
    public protected *;
}

-keep class jpos.JposException
-keepclassmembers class jpos.JposException{
    public protected *;
}

-keep class com.shtrih.fiscalprinter.ShtrihFiscalPrinter
-keepclassmembers class com.shtrih.fiscalprinter.ShtrihFiscalPrinter{
    public protected *;
}

-keep class com.shtrih.tinyjavapostester.ConfigureLog4J
-keepclassmembers class com.shtrih.tinyjavapostester.ConfigureLog4J{
    public protected *;
}

-keep class com.shtrih.tinyjavapostester.DeviceListActivity
-keepclassmembers class com.shtrih.tinyjavapostester.DeviceListActivity{
    public protected *;
}

-keep class com.shtrih.tinyjavapostester.JposConfig
-keepclassmembers class com.shtrih.tinyjavapostester.JposConfig{
    public protected *;
}

-keep class com.shtrih.util.StaticContext
-keepclassmembers class com.shtrih.util.StaticContext{
    public protected *;
}


-keep class jpos.FiscalPrinter
-keepclassmembers class jpos.FiscalPrinter{
    public protected *;
}

-keep class jpos.FiscalPrinterConst
-keepclassmembers class jpos.FiscalPrinterConst{
    public protected *;
}

-keep class com.shtrih.jpos.JposDeviceStatistics
-keepclassmembers class com.shtrih.jpos.JposDeviceStatistics{
    public protected *;
}

-keep class com.shtrih.util.SysUtils
-keepclassmembers class com.shtrih.util.SysUtils{
    public *;
}

-keep class jpos.config.simple.xml.SimpleXmlRegPopulator
-keepclassmembers class jpos.config.simple.xml.SimpleXmlRegPopulator{
    public *;
}


#------
-keep class mf.org.apache.xerces.parsers.XIncludeAwareParserConfiguration
-keepclassmembers class mf.org.apache.xerces.parsers.XIncludeAwareParserConfiguration{
    public *;
}

-keep class mf.org.apache.xerces.impl.dv.dtd.DTDDVFactoryImpl
-keepclassmembers class mf.org.apache.xerces.impl.dv.dtd.DTDDVFactoryImpl{
    public *;
}

-keep class com.shtrih.**
-keepclassmembers class com.shtrih.**{
    public *;
}