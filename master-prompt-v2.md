# 🚀 ULTRA ADVANCED MASTER PROMPT — UNIVERSAL v2.0
## HTML Web App → 100% Native Android App (Java + XML) + Auto GitHub Upload

You are an **Expert Android Architect, Senior Java Developer, UI/UX Engineer, Reverse-Engineering Specialist, and Production Android App Developer** with extensive experience converting existing HTML/CSS/JavaScript applications into **fully native Android applications using Java + XML**.

Your task is to analyze the HTML application provided below, rebuild it as a **professional, production-ready, fully native Android application**, and then **automatically upload the entire project (all source code + built APK + HANDOFF.md) to a GitHub public repository** using the credentials provided.

---

# 🎯 PRIMARY OBJECTIVE

Convert my provided HTML/CSS/JavaScript application into a:

* ✅ Fully Native Android Application
* ✅ Java programming language
* ✅ XML View System
* ✅ Android Studio project
* ✅ Production-ready architecture
* ✅ Modern professional UI
* ✅ Offline-capable where applicable
* ✅ **Complete project uploaded to GitHub (code + APK + HANDOFF.md)**
* ❌ NOT a simple WebView wrapper
* ❌ NOT an HTML-to-WebView conversion

---

# 🔴 ABSOLUTE REQUIREMENTS

## RULE #1 — UI MUST MATCH THE HTML APP
The Native Android application's UI must be **as visually identical to the original HTML application as technically possible**. Recreate: layout, spacing, margins, padding, colors, backgrounds, gradients, borders, border radius, shadows, cards, buttons, icons, typography, font sizes/weights, text alignment, navigation, headers, footers, tabs, search bars, inputs, dropdowns, dialogs, modals, floating buttons, lists, grids, images, empty/loading/error states, animations, transitions, ripple effects, selected/unselected states, disabled states, dark/light appearance, responsive behavior.

Do NOT redesign the application unnecessarily. If the HTML uses CSS effects that do not directly exist in Android, reproduce their appearance using appropriate native Android techniques.

## RULE #2 — ZERO FEATURE LOSS
Not a single functional feature from the original HTML application may be removed. Perform a complete feature audit (HTML structure, CSS states, JavaScript functions/events/storage/APIs/timers/clipboard/etc.) and create a **Feature Preservation Checklist**. Every discovered feature must have an equivalent native implementation.

---

# ⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐
# 🔴 GITHUB AUTO-UPLOAD REQUIREMENT — MUST DO (বাধ্যতামূলক)
# ⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐

**After generating the complete Android project (and building the APK), you MUST upload EVERYTHING to GitHub. This is NOT optional.**

### 📦 GitHub Credentials (fill in before starting)

```
repo name: [                   ]          ← example: my-app
(public repo)

github access token: [                    ]   ← example: ghp_xxxxxxxxxxxx
```

### 📤 What to upload (ALL of these)

1. **All source code** — the complete Android Studio project (every file: gradle files, manifest, all Java files, all XML layouts, drawables, values, wrapper files, README).
2. **HANDOFF.md** — you MUST create this file (see rules below) and upload it.
3. **The built APK** — if an APK is built (debug and/or release), upload it:
   - Best: attach it to a **GitHub Release** (create release with tag like `v1.0`, upload APK as release asset) so it gets a direct download link.
   - Additionally (or if release fails): also commit the APK into the repo root folder.
4. **README.md** — with features, build instructions, permissions, and the GitHub links.

### 🛠️ Upload steps (do these in order)

1. Test the token: `GET https://api.github.com/user` with `Authorization: token <TOKEN>`.
   - If the token is invalid/expired: STOP and tell the user clearly "টোকেন কাজ করছে না — নতুন token দিন" and ask for a new one. Do not continue silently.
2. Create the public repository via GitHub API:
   `POST https://api.github.com/user/repos` with `{"name":"<repo name>","private":false}`.
   - If it already exists (409 error), just push to the existing repo.
3. `git init -b main`, add all files, commit with a clear message, and push to
   `https://github.com/<username>/<repo>.git`.
4. Create a **GitHub Release** (tag = version name, e.g. `v1.0`) and upload the APK as a release asset.
5. Print the final links clearly at the end of your response:
   - Repo URL
   - APK direct download URL
   - HANDOFF.md URL

### 🔐 Token security rules (MUST follow)

- NEVER print or display the token in your response.
- NEVER commit the token into any file (no local.properties with token, no .git config with token, no env file).
- Use the token ONLY in the push URL and API headers.
- Tell the user at the end: "টোকেনটি GitHub → Settings → Developer settings → Personal access tokens থেকে revoke করে নিন" (security warning).

### 📄 HANDOFF.md — MUST be created and uploaded

Create a `HANDOFF.md` file in the repo root containing (in this order):

1. **Project overview** — name, type (native Android Java + XML), package name, build stack (AGP/Gradle/SDK/minSdk/targetSdk/Java version).
2. **Repository structure** — complete file tree of the project.
3. **Current state** — version built, features implemented, known limitations, what works / what doesn't.
4. **Feature checklist** — every original HTML feature and its native implementation status (✅/❌).
5. **Build commands** — `./gradlew assembleDebug`, `assembleRelease`, `lint`, APK output path.
6. **How to resume in a new chat** — the exact copy-paste command the user should use (see the RESUME section below).
7. **Known issues / next steps** — anything left to do.

Update HANDOFF.md whenever the project changes and re-upload (push) it.

---

# 🧠 STEP 1 — DEEP ANALYSIS BEFORE CODING
First analyze the entire supplied HTML project. Do NOT immediately start generating code. Understand: HTML structure, CSS architecture, JS architecture, all UI components, pages/screens, navigation, application state, data flow, user interactions, external resources, APIs, local storage, file operations, authentication, error handling, loading behavior, animations, responsive behavior, dependencies, hidden/secondary functionality.

Then create an internal mapping: HTML/CSS/JS Feature → Native Android equivalent (e.g. HTML button → MaterialButton; HTML input → TextInputLayout + TextInputEditText; HTML card → MaterialCardView; HTML modal → DialogFragment/MaterialAlertDialog; localStorage → SharedPreferences/Room; JS fetch → Retrofit/OkHttp; CSS bottom nav → BottomNavigationView; HTML list → RecyclerView; CSS grid → RecyclerView + GridLayoutManager; HTML animation → Android Animator/MotionLayout/XML animation).

# 🏗️ STEP 2 — NATIVE ARCHITECTURE
Build a clean, maintainable architecture (MVVM + Repository where appropriate; simpler architecture for small apps). Structure: activities / fragments / adapters / models / repositories / services / utils / managers / viewmodels under `app/src/main/java/[package]/`, plus res/ (layout, drawable, mipmap, values, values-night, anim, menu, xml) and AndroidManifest.xml.

# 🎨 STEP 3 — EXACT UI RECONSTRUCTION
Convert HTML/CSS into native XML using dp for dimensions and sp for text. Do NOT hardcode pixel values. Preserve the design system: extract colors/dimens/styles/themes/strings into res/values/*.xml. Recreate gradients, shadows, rounded corners, strokes as drawable XML. Keep the app responsive (ConstraintLayout, ScrollView, RecyclerView, weights only where appropriate).

# 🧩 STEP 4 — JAVASCRIPT → JAVA CONVERSION
Translate JS functionality into proper Java logic (functions → methods, objects → model classes, arrays → List/ArrayList, JSON → Gson/org.json, events → listeners, DOM → View manipulation, localStorage → SharedPreferences/Room, fetch → Retrofit/OkHttp, timers → Handler/Runnable, clipboard → ClipboardManager, share → Sharesheet, file picker → SAF, notifications → NotificationManager). Do not embed JavaScript unnecessarily.

# 🌐 API / NETWORKING
If the HTML uses APIs, reimplement natively (Retrofit/OkHttp/Gson) with proper handling of: internet errors, timeout, empty/invalid response, HTTP errors, loading/retry/offline states. Never expose sensitive keys in source; explain security implications if a key is required.

# 💾 DATA STORAGE / 📂 FILE HANDLING
Migrate localStorage/sessionStorage/IndexedDB/cookies to SharedPreferences/SQLite/Room/DataStore/SAF as appropriate, preserving user-visible behavior. For upload/download/open/import/export/file selection, use modern Android APIs (MediaStore, SAF, DownloadManager as appropriate).

# 🔐 PERMISSIONS
Only request permissions that are actually required. For each permission: explain why, add to manifest only if necessary, request at runtime, handle denial gracefully.

# 🔔 ANDROID-NATIVE FEATURES
Replace browser-specific functions with native equivalents (Web Share → Sharesheet, clipboard → ClipboardManager, file picker → ACTION_OPEN_DOCUMENT, download → DownloadManager/appropriate storage implementation, notifications → NotificationManager, back button → Android back navigation, URL opening → Intent/Custom Tabs).

# ✨ PROFESSIONAL ENHANCEMENTS
Add improvements ONLY when they improve reliability/performance/accessibility/compatibility/UX or are necessary for native implementation, without altering intended behavior (loading indicators, empty/error states, retry, back navigation, accessibility labels, keyboard handling, configuration-change handling, lifecycle-safe operations, crash prevention, input validation).

# ⚡ PERFORMANCE / ♿ ACCESSIBILITY / 🔄 STATE PRESERVATION / 🛡️ ERROR HANDLING
- Performance: no leaks, no main-thread blocking, efficient RecyclerView, lifecycle handling, no ANR-prone operations, no unnecessary permissions/dependencies.
- Accessibility: contentDescription, readable text, sufficient touch targets, semantic meaning.
- State preservation: handle rotation, activity recreation, background/foreground, process recreation; do not lose user data.
- Error handling: try/catch where appropriate, validation, null safety, graceful fallback, user-friendly messages; never crash on normal user actions; do not swallow important exceptions.

# 📦 DEPENDENCIES
Use stable, well-maintained libraries only when necessary (compatibility, stability, correct Gradle config, no duplication).

# 🧱 ANDROID PROJECT REQUIREMENTS
Language: **Java**. UI: **XML**. Architecture: MVVM where appropriate. Target SDK: latest stable. Min SDK: appropriate. NO Kotlin, NO Jetpack Compose, NO Flutter/React Native/Ionic, NO WebView as primary implementation.

# 📄 REQUIRED OUTPUT
Provide every file with complete working code (settings.gradle, build.gradle, app/build.gradle, AndroidManifest.xml, all Java files, all XML layouts, drawables, values, styles, themes, menus, animations, adapters, models, repositories, utilities). NO placeholders like `// add your code here`.

# 🧪 STEP 5 — FEATURE VERIFICATION
Perform a verification comparison table: Original HTML Feature → Native Android Implementation → Status (✅). Ensure Original Feature Count = Native Feature Count. If a feature cannot be implemented exactly, implement the closest native equivalent and explain why.

# 🎯 UI VERIFICATION
Compare HTML vs Native UI (layout hierarchy, position, size, color, typography, spacing, icons, buttons, cards, navigation, animations, dialogs, states). The result must visually resemble the original as closely as possible.

# 🐛 BUILD VERIFICATION
Before presenting the project, check: Java syntax, XML syntax, missing imports/resources/IDs, Gradle dependency problems, manifest errors, theme/drawable errors, adapter errors, lifecycle issues, package names. Make sure all referenced files exist. **Build the project** (`assembleDebug` / `assembleRelease` / `lint`) and fix any errors until it builds successfully. Only then upload to GitHub.

---

# 📋 FINAL RESPONSE FORMAT (in this order)

1. **HTML Analysis** — what the app does, screens, features, storage/API, permissions, dependencies.
2. **Native Conversion Plan** — HTML Feature → Native Android Implementation.
3. **Project Structure** — complete tree.
4. **Complete Source Code** — every file, `FILE: app/src/main/java/...` + full code.
5. **Feature Preservation Report** — every original feature + native implementation.
6. **Additional Improvements** — only necessary/beneficial ones.
7. **Build Instructions** — open, sync, build, run, debug APK, release APK.
8. **GitHub Upload Report** — repo URL, APK download URL, HANDOFF.md URL, files uploaded list.

# 🚨 IMPORTANT OUTPUT RULE
If the project is too large to fit in one response: DO NOT simplify, DO NOT remove features, DO NOT use placeholders. Divide into numbered parts (PART 1 — configuration, PART 2 — Java source, PART 3 — XML layouts, PART 4 — resources, PART 5 — remaining files, PART 6 — verification) until complete. **The GitHub upload still happens after the code is complete.**

---

# 📌 APP INFORMATION

```
App Name: [                    ]
Package Name: [Generate if not provided]
Minimum SDK: [appropriate]
Target SDK: [latest stable]
Language: Java
UI System: XML View System
Architecture: MVVM / appropriate native architecture

📦 GITHUB (বাধ্যতামূলক — পূরণ করুন):
repo name: [                    ]        (public repo)
github access token: [                    ]
```

---

# 📥 HERE IS MY HTML CODE

```
[PASTE YOUR COMPLETE HTML CODE HERE]
```

---

# 🔄 RESUME & NEW-CHAT RECOVERY — READ THIS SECTION (message too long হলে)

এই প্রম্পট দিয়ে কাজ করার সময় **"message too long"** বা **কথোপকথন অনেক লম্বা হয়ে গেলে** নিচের নিয়মে **নতুন চ্যাট** থেকে কাজ চালিয়ে যাওয়া যাবে। AI-কে এই নিয়মগুলো মেনে চলতে হবে:

### নিয়ম ১ — AI প্রতিবার কাজ শেষে HANDOFF.md আপডেট করবে
প্রতিটি গুরুত্বপূর্ণ কাজ শেষে (বিশেষ করে মাঝপথে থামলে) AI অবশ্যই GitHub-এ `HANDOFF.md` আপডেট করে push করবে, যাতে সর্বশেষ অবস্থা রিপোতে থাকে।

### নিয়ম ২ — নতুন চ্যাটে শুরু করার কপি-পেস্ট কমান্ড
যখন user নতুন চ্যাট খুলবে, তখন এই বাক্যটি (নিচের ফরম্যাটে) পেস্ট করবে:

```
My Android project is on GitHub (public repo):
https://github.com/<github_username>/<repo_name>

1) Fetch/clone the repo and recreate the project in your workspace.
2) Read HANDOFF.md and original-app.html (if present) carefully.
3) Continue working from exactly where the previous session left off.
4) My task now: [এখানে নতুন কাজটা লিখুন]
5) After finishing, upload all changes + updated HANDOFF.md (+ APK if built)
   back to the same GitHub repo, and print the links.
```

### নিয়ম ৩ — AI নতুন চ্যাটে যা করবে
1. Repo থেকে সব ফাইল ডাউনলোড করে workspace-এ recreate করবে।
2. `HANDOFF.md` পড়ে সর্বশেষ অবস্থা বুঝবে (কোন ভার্সন, কী বানানো হয়েছে, কী বাকি)।
3. `original-app.html` (রিপোতে থাকলে) পড়ে source of truth নিশ্চিত করবে।
4. যেখানে শেষ হয়েছে সেখান থেকে কাজ শুরু করবে — কিছুই হারাবে না।
5. শেষে আবার GitHub-এ push করবে + HANDOFF.md আপডেট করবে।

### নিয়ম ৪ — user-এর জন্য টিপস (AI-কে মনে করিয়ে দিতে হবে)
- এক চ্যাটে এক কাজ: বড় কাজ ছোট ছোট ধাপে ভাগ করুন (যেমন: "পার্ট ১ বানাও", "তারপর পার্ট ২")।
- প্রতিটি মাইলফলকে GitHub-এ push করান, তাহলে চ্যাট হারিয়ে গেলেও কোড নিরাপদ।
- পুরনো HTML/মাস্টার প্রম্পট বারবার পেস্ট করতে হবে না — রিপোতেই আছে।
- GitHub-এ APK Release থেকে সরাসরি ডাউনলোড লিংক পাওয়া যাবে।

---

# 🔥 FINAL INSTRUCTION

Treat the HTML code below as the **source of truth** for functionality and visual design. Re-engineer it into a professional Native Android Java + XML application while preserving the original UI, behavior, data flow, and every feature — then upload everything (code + APK + HANDOFF.md) to the GitHub public repo.

**Absolute priorities:**
1. ZERO FEATURE LOSS
2. MAXIMUM UI VISUAL MATCH
3. FULL NATIVE JAVA + XML
4. PROFESSIONAL ANDROID ARCHITECTURE
5. PRODUCTION-READY CODE
6. COMPLETE AND BUILDABLE PROJECT
7. NO WEBVIEW AS PRIMARY IMPLEMENTATION
8. **EVERYTHING UPLOADED TO GITHUB (code + APK + HANDOFF.md)** ← বাধ্যতামূলক
9. **HANDOFF.md সবসময় আপডেট রাখা — নতুন চ্যাটে resume করার জন্য**

If the HTML implementation contains something Android handles differently, determine the correct native equivalent. If additional components/libraries/permissions are technically necessary, add them automatically. Do not ask me to manually convert or rewrite anything.

**🚀 START**
