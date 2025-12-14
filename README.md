# Irrigo
An Android application for smart irrigation system monitoring & control. Monitor soil moisture and control irrigation devices with manual controls, automation settings, notifications, and activity logs.

## ✨ Features

- 🌱 **Soil Moisture Monitoring** - Track real-time soil moisture levels and review historical data over time.

- 💧 **Manual Device Control** - Manually trigger watering whenever needed.

- ⚙️ **Automatic Irrigation Mode** - Enable or disable auto-watering and set the minimum soil moisture threshold to trigger irrigation.

- 🔔 **Smart Notifications & Reminders** - Get reminders for watering and alerts when the device’s water capacity is low.

- 📊 **Activity & Sensor Logs** - View detailed logs of soil moisture readings, watering events, and water capacity changes.

## 📥 Installation
### 🔹 Requirements
- Android Studio
- Android device running **Android 8.0 (Oreo) or higher**

### 📄 Set up `local.properties`
Inside `<project root>/local.properties` file, add the following properties:
```properties
SERVER_URL=<backend base url>
SERVER_URL_WS=<backend base websocket url>
```

### 🔹 Debug Build
1. Clone the repository

    ```bash
    git clone https://github.com/kite1412/android-irrigo.git
    ```
2. Open the downloaded repository in Android Studio
3. Simply click run (▶️) button at the top of the Android Studio to install and launch the app.

### 🔹 Release Build (preferred for smaller apk size)
1. Clone the repository

    ```bash
    git clone https://github.com/kite1412/android-irrigo.git
    ```
2. Open the downloaded repository in Android Studio
3. Navigate to **Build > Generate Signed APP Bundle / APK**
4. Select **APK** to build signed APK
5. Choose an existing `.jks` file or create a new one to sign the APK
6. Select **release** as Build Variants then **Create**, wait for the APK file generation to complete
7. In project's root directory, run  following command:

    ```bash
    adb install app/release/app-release.apk
    ```

## 🔗 Related Projects
- [Backend Repository](https://github.com/kite1412/irrigo-be)

