# Google Drive Tool Suite — Native Android App (Java + XML)

A 100% native Android port of the "Google Drive Tool Suite" HTML application.
No WebView — every feature is rebuilt with Java and XML views.

## Features

- **Link Creator** — pastes any text/messy links, extracts Google Drive file IDs
  with the original regex, and writes `Lecture:NN` + converted download links
  (`https://drive.google.com/u/0/uc?id=…&export=download`) into the output box.
  Copy the result to the clipboard with one tap.
- **Bulk Downloader** — pastes a list of links (lines may contain labels such as
  `Lecture:01`), extracts every http(s) URL, then triggers each download one by
  one (1.2 s interval, like the original app) through the system
  **DownloadManager**. Live status: `ডাউনলোড শুরু হচ্ছে…`, `ডাউনলোড হচ্ছে: X / Y`,
  `✅ সবকটি ফাইলের ডাউনলোড প্রক্রিয়া সম্পন্ন হয়েছে!`. The system download UI
  shows progress/notifications for each file.

## Requirements

- Android Studio Ladybug (2024.2.1) or newer
- JDK 17 (bundled with Android Studio)
- Android SDK Platform 35 + Build Tools 35.0.0 (auto-installed on first sync)

## How to build

1. **Open** — Android Studio → `Open` → select the project folder.
2. **Sync** — wait for Gradle sync (AGP 8.7.3 + Gradle 8.9 will be downloaded
   automatically via the wrapper).
3. **Build** — `Build` → `Make Project`, or run:

   ```bash
   ./gradlew assembleDebug
   ```

4. **Run** — connect a device/emulator and press Run ▶, or:

   ```bash
   ./gradlew installDebug
   ```

5. **APK output** — debug APK is at:

   ```
   app/build/outputs/apk/debug/app-debug.apk
   ```

6. **Release APK** — create a keystore, then:

   ```bash
   keytool -genkey -v -keystore release.keystore -alias drive -keyalg RSA \
           -keysize 2048 -validity 10000
   ./gradlew assembleRelease
   ```

   Sign the unsigned release APK:

   ```bash
   apksigner sign --ks release.keystore --out app-release-signed.apk \
       app/build/outputs/apk/release/app-release-unsigned.apk
   ```

   (or configure `signingConfigs` in `app/build.gradle` for one-step signed builds)

## Permissions

**None.** DownloadManager is a system service — enqueuing downloads requires no
app permission, and the system writes files into the public Downloads folder and
shows its own notifications. The clipboard uses the system ClipboardManager.

## Notes on the Bulk Downloader

The original HTML triggered downloads with hidden iframes. The native equivalent
is the Android DownloadManager, which performs real file downloads with system
notifications. For very large Google Drive files Google may show its
"virus scan" confirmation page; in that case, open that file's link in a browser
once and confirm, then re-run the bulk download — the same caveat that applies
to the original web app.

## Project structure

```
app/src/main/java/com/drivetoolsuite/
├── MainActivity.java              — container card + tabs + ViewPager2
├── adapter/MainPagerAdapter.java  — two-tab pager adapter
├── fragments/
│   ├── LinkCreatorFragment.java   — Tab 1 (regex extraction + clipboard)
│   └── BulkDownloaderFragment.java— Tab 2 (sequential DownloadManager triggers)
└── util/DriveLinkParser.java      — regex ports of the original JS patterns
```
