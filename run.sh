./gradlew assembleDebug &&
adb install -r app/build/outputs/apk/debug/app-debug.apk &&
adb shell am start -n com.chandrapal.manage_college/com.chandrapal.manage_college.MainActivity
