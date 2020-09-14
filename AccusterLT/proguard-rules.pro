# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/appideas-user19/Android/Sdk/tools/proguard/proguard-android.txt
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

-ignorewarnings

#Use 5 step of optimization
-optimizationpasses 5

-dontwarn **CompatHoneycomb
-dontwarn android.support.**

-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepattributes *Annotation*
-printmapping mapping.txt
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.preference.Preference
-keep public class * extends android.os.AsyncTask
-keep class com.accusterltapp.model.** {*;}
-keep class com.base.model.** {*;}

#add these to remove warning
-dontwarn org.apache.**
-dontwarn org.junit.**
-dontwarn com.opencsv.**
-dontwarn com.squareup.**
-dontwarn okhttp3.**
-dontwarn retrofit2.**

# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

-dontwarn okio.**
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# remove itext warning
-keep class org.spongycastle.** { *; }
-dontwarn org.spongycastle.**
-keep class com.itextpdf.text.** { *; }
-dontwarn com.itextpdf.text.**


# Also you must note that if you are using GSON for conversion from JSON to POJO representation, you must ignore those POJO classes from being obfuscated.
# Here include the POJO's that have you have created for mapping JSON response to POJO for example.


-keepclassmembernames class * {
    java.lang.Class class$(java.lang.String);
    java.lang.Class class$(java.lang.String, boolean);
}
# Gson specific classes
-keep class sun.misc.Unsafe {
    <fields>;
    <methods>;
}
# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.** {
    <fields>;
    <methods>;
}
-keep class android.support.v4.** {
    <fields>;
    <methods>;
}
-keep class android.support.v7.** {
    <fields>;
    <methods>;
}



# Google play services proguard enable

-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}
-keep class com.google.android.gms.** { *; }
-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.MapActivity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.content.Context {
   public void *(android.view.View);
   public void *(android.view.MenuItem);
}

-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}

-keepattributes InnerClasses

-keep class **.R
-keep class *.R$ {
    <fields>;
}
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}


-keepattributes InnerClasses
-dontoptimize