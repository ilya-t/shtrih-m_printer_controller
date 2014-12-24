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
-dontwarn **

-keep public class com.shtrih.fiscalprinter.**
-keepclassmembers class com.shtrih.fiscalprinter.**{
    public *;
}

-keep public class com.printerhelper.common.**
-keepclassmembers class com.printerhelper.common.**{
    public *;
}

-keep public class com.printerhelper.shtrih.PrintError
-keepclassmembers class com.printerhelper.shtrih.PrintError{
    public *;
}
-keep public class com.printerhelper.shtrih.ShtrihDeviceSettings
-keepclassmembers class com.printerhelper.shtrih.ShtrihDeviceSettings{
    public *;
}
-keep public class com.printerhelper.shtrih.ShtrihPaymentTypeParser
-keepclassmembers class com.printerhelper.shtrih.ShtrihPaymentTypeParser{
    public *;
}
-keep public class com.printerhelper.shtrih.ShtrihPrinter
-keepclassmembers class com.printerhelper.shtrih.ShtrihPrinter{
    public *;
}