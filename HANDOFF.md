# HANDOFF — Resume Guide (নতুন পেজ/চ্যাট থেকে আবার শুরু করার গাইড)

এই ফাইলটি পড়লেই আপনি বা একটি **নতুন AI চ্যাট সেশন** এই প্রজেক্টের পুরো অবস্থা বুঝতে পারবে
এবং যেখানে শেষ হয়েছে সেখান থেকে চালিয়ে যেতে পারবে।

## Project Overview

- **Name:** Google Drive Tool Suite
- **Type:** 100% native Android app — Java + XML (NO WebView)
- **Package name:** `com.drivetoolsuite`
- **Source of truth:** `original-app.html` (আসল HTML অ্যাপ — UI ও ফিচারের উৎস; এটা নতুন সেশনে দিলে কোনো কিছু হারাবে না)
- **Build stack:** AGP 8.7.3, Gradle 8.9 (wrapper included), compileSdk 35, targetSdk 35, minSdk 23, Java 17
- **App language (UI):** Bengali (বাংলা) + English buttons

## Repository Structure

```
auto.dw/
├── README.md                      — full docs (build steps, permissions)
├── HANDOFF.md                     — this file
├── master-prompt-v2.md            — the user's Universal Master Prompt (v2):
│                                    HTML→Native Android conversion + auto GitHub
│                                    upload (code + APK + HANDOFF.md) + resume guide.
│                                    New chats should follow it.
├── original-app.html              — the original HTML app (source of truth)
├── DriveToolSuite-debug.apk       — prebuilt v1.0 APK
├── build.gradle / settings.gradle / gradle.properties / gradlew / gradle/wrapper/
└── app/
    ├── build.gradle
    ├── proguard-rules.pro / lint.xml
    └── src/main/
        ├── AndroidManifest.xml
        ├── java/com/drivetoolsuite/
        │   ├── MainActivity.java              — container card + TabLayout + ViewPager2
        │   ├── adapter/MainPagerAdapter.java  — two-tab pager
        │   ├── fragments/LinkCreatorFragment.java
        │   ├── fragments/BulkDownloaderFragment.java
        │   └── util/DriveLinkParser.java      — regex ports of the JS patterns
        └── res/
            ├── layout/ (activity_main, fragment_link_creator, fragment_bulk_downloader)
            ├── drawable/ (bg_container, bg_input_selector, bg_note, bg_tab_layout, ic_launcher)
            ├── color/ (color_action_button, color_copy_button, tab_text_color)
            └── values/ (colors, dimens, strings, styles, themes)
```

## Current State (main branch = v1.0)

- **v1.0** — প্রথম ভার্সন, এখন রিপোতে আছে:
  - Link Creator: Drive file ID extraction (original regex) → `Lecture:NN` + converted links → clipboard
  - Bulk Downloader: urlRegex extraction → sequential **DownloadManager** triggers (1.2s cadence)
  - No permissions at all; APK in repo root + GitHub Release `v1.0`
- **Known limitation of v1.0:** DownloadManager silently fails on some devices for Google Drive links.

### Later versions (chat-session only, NOT yet in this repo)

| Version | What changed |
|---|---|
| v1.1 | In-app downloads: `DriveLinkResolver` (handles Drive redirects + virus-scan token) + `DriveDownloader` (HttpURLConnection → Downloads via MediaStore). Added INTERNET + WRITE_EXTERNAL_STORAGE(maxSdk 28). Progress %, real filename from Content-Disposition. |
| v2.0 | SAF folder picker: first Start click → file manager folder pick → `takePersistableUriPermission` (remembered forever) → downloads go to chosen folder; folder row UI + change button; dedupe names. |
| v2.1 | Crash fixes: executor lifecycle (no shutdown in onDestroyView), catch-all in download loop, no requireContext() on background thread, folder-picker graceful fallback, global crash logger (`App.java` → files/crash.log). |

If the user wants to resume with the folder-picker version, the new session should
rebuild the **v2.1** feature set (or re-apply the saved zip from the last chat).

## How to Resume in a New Chat (নতুন পেজে শুরু করার নিয়ম)

1. নতুন চ্যাট খুলুন।
2. এই বাক্যটি বলুন:
   > "Fetch my project from https://github.com/viphasibul792/auto.dw (public repo).
   > Read HANDOFF.md and original-app.html, then we continue from there. Task: ..."
3. AI তখন রিপো থেকে সব ফাইল ওয়ার্কস্পেসে তৈরি করে নেবে এবং কাজ চালিয়ে যাবে।

Alternatively: download the project zip / last APK from the repo and attach it to the new chat.

## Feature Checklist (original HTML app — ZERO feature loss)

- [x] Two navigation tabs (Link Creator / Bulk Downloader) with active underline + colors
- [x] Link Creator: paste text → regex extract file IDs → `Lecture:NN` + `https://drive.google.com/u/0/uc?id=...&export=download` → Copy Links (clipboard) + alert messages
- [x] Bulk Downloader: paste labeled links → extract all URLs → sequential trigger (1.2s) → status texts (ডাউনলোড শুরু হচ্ছে..., ডাউনলোড হচ্ছে: X / Y, ✅ সবকটি ফাইলের ডাউনলোড প্রক্রিয়া সম্পন্ন হয়েছে!) → disabled button while running
- [x] Tip note box (টিপস: ... Allow multiple downloads ...) with bold/italic spans
- [x] Visual fidelity: body #f4f6f9, white card (650dp, radius 10, shadow), h2 #1a73e8, tabs #dadce0 2px border + 3px active underline, textareas #dadce0 1px border radius 6 focus #1a73e8, buttons #1a73e8/#1557b0 hover/#ccc disabled, copy button #34a853, note #e8f0fe + 4px #1a73e8 left border

## Build

```bash
./gradlew assembleDebug        # debug APK → app/build/outputs/apk/debug/app-debug.apk
./gradlew assembleRelease      # unsigned release APK
./gradlew lint                 # static analysis (0 issues on v1.0)
```

Requires: JDK 17, Android SDK Platform 35 + Build Tools 35.0.0 (Android Studio Ladybug+ recommended).

## Known Issues / Notes

- v1.0's DownloadManager may fail silently for Drive files on some devices → use v1.1+ approach for reliable downloads.
- Large Google Drive files show a virus-scan confirmation page; v1.1+ resolver handles the token automatically; v1.0 needs the file confirmed in a browser once.
- Keystore is NOT committed (user generates their own for release signing).
