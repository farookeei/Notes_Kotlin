# Kotlin Multiplatform (KMP) Migration Guide: Comprehensive Steps & Reasoning

## Overview & Architecture Goal
This document provides a detailed step-by-step breakdown and the underlying reasoning for migrating the Notes application from a standard Android-only project into a Kotlin Multiplatform (KMP) architecture.

**The Goal:** We wanted to share the core Business Logic (Room Database, Koin Dependency Injection, and Data Models) across both Android and iOS, while retaining a **Native UI architecture** (Jetpack Compose for Android, SwiftUI for iOS). This ensures maximum code reuse without sacrificing the native look, feel, and performance of the UI on each platform.

---

## 1. Project Restructuring & Gradle Configuration

### Step: Creating the Shared Module
We created a new `shared` module containing three primary source sets: `commonMain` (for shared Kotlin code), `androidMain` (for Android-specific Kotlin code), and `iosMain` (for iOS-specific Kotlin code).
**Reasoning:** KMP relies on this structure to know which code can be compiled universally, and which code relies on platform-specific APIs (like `Context` on Android or `NSFoundation` on iOS).

### Step: Gradle Version Catalogs (`libs.versions.toml`)
We moved dependency declarations out of individual `build.gradle.kts` files and into a centralized `libs.versions.toml` file.
**Reasoning:** In a multi-module KMP project, dependency versions can easily drift out of sync between the Android app and the shared module. A version catalog ensures that both modules use the exact same versions of Koin, Room, and Coroutines, preventing bizarre runtime crashes.

### Step: Configuring iOS Targets
In `shared/build.gradle.kts`, we configured `iosX64()`, `iosArm64()`, and `iosSimulatorArm64()`.
**Reasoning:** iOS devices use ARM64 architecture, while older Macs use Intel (X64) and newer Mac simulators use Apple Silicon (SimulatorArm64). By declaring these targets, we instruct the Kotlin compiler to translate our Kotlin code into an LLVM Apple Framework (`shared.framework`) that can run on any of these Apple architectures.

---

## 2. Room Database KMP Migration

Migrating Android's Room database to KMP was the most complex code task, primarily due to the limitations of Kotlin/Native on iOS.

### Step: Bypassing Java Reflection on iOS
- **The Issue:** On Android, Room heavily relies on Java Reflection (the ability for code to dynamically inspect and instantiate classes at runtime) to find and create the generated database implementation (`NoteDatabase_Impl`). However, iOS natively compiled code (Kotlin/Native) **does not support reflection**.
- **The Fix:** We annotated our `NoteDatabase` class with `@ConstructedBy(NoteDatabaseConstructor::class)`.
- **Reasoning:** This annotation tells the Room compiler: "Do not attempt to use reflection on iOS. Instead, use this specific constructor object to instantiate the database."

### Step: The `expect/actual` Constructor Trick
- **The Issue:** We had to define `expect object NoteDatabaseConstructor : RoomDatabaseConstructor<NoteDatabase> { override fun initialize(): NoteDatabase }` in `commonMain`.
- **The Fix & Reasoning:** Because the actual database implementation doesn't exist until compile-time, we created an `expect` placeholder. Normally, Kotlin forces you to manually write the `actual` implementation for Android and iOS. However, Room acts as a "ghostwriter" during the build process and generates the `actual` code for us. We added `@Suppress("NO_ACTUAL_FOR_EXPECT")` to stop Android Studio from showing a syntax error, since the IDE cannot see the code that Room hasn't generated yet.

### Step: iOS Database Builder Configuration
- **The Fix:** In `iosMain/noteDb.kt`, we created an iOS-specific builder using `NSHomeDirectory() + "/note_database.db"`.
- **Reasoning:** Android uses a `Context` to figure out where it has permission to save a database file. iOS does not have a `Context`. Instead, iOS uses sandboxed directories. We used Apple's native `NSHomeDirectory()` API (available in Kotlin via Kotlin/Native interoperability) to get the correct save path for the iOS database. 

### Step: Eliminating JVM-Only APIs (`System.currentTimeMillis()`)
- **The Issue:** The `Note` entity used `System.currentTimeMillis()` as a default timestamp. `System` is a Java Virtual Machine (JVM) class and does not exist on iOS. The compilation failed.
- **The Fix:** We created an `expect fun getCurrentTimeMillis(): Long` in `commonMain`.
- **Reasoning:** We delegated the responsibility of fetching time to the native platforms. In `androidMain`, the `actual` function returns `System.currentTimeMillis()`. In `iosMain`, the `actual` function uses Apple's native `(NSDate().timeIntervalSince1970 * 1000).toLong()`. 

---

## 3. Dependency Injection (Koin) Setup

### Step: Platform-Specific Modules
- **The Fix:** We split the Koin database module into platform-specific `PlatformModule.kt` files using `expect/actual`.
- **Reasoning:** The Android Room database requires a `Context` to be injected into its builder, while the iOS database builder requires zero parameters. Splitting the DI module allowed us to fulfill these platform-specific requirements while keeping the rest of the DI graph shared.

### Step: Bootstrapping Koin on iOS
- **The Fix:** We created `KoinInitializer.kt` in `iosMain` containing a `doInitKoin()` function.
- **Reasoning:** On Android, Koin is automatically started in the `Application` class when the app boots. iOS has no concept of an Android `Application` class. By creating `doInitKoin()`, we exposed a direct bridge to Swift, allowing us to manually call `KoinInitializerKt.doInitKoin()` inside the `init()` block of the main `iosAppApp.swift` file.

---

## 4. Xcode & Build System Integration

Integrating the compiled Kotlin framework into Xcode required resolving several deep environment and security issues.

### Step: Fixing the Missing Java Runtime (`PhaseScriptExecution` Error)
- **The Issue:** Xcode uses a "Run Script Build Phase" to execute `./gradlew embedAndSignAppleFrameworkForXcode` before compiling the iOS app. This ensures the Kotlin framework is always up-to-date. However, Mac terminal environments do not inherently know where Java is installed (`JAVA_HOME` is missing), causing the Gradle script to crash with a nonzero exit code.
- **The Fix:** We added `export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"` to the top of the Xcode build script.
- **Reasoning:** Instead of forcing developers to install a system-wide JDK, we temporarily point the Xcode terminal to the secret, bundled version of Java that lives inside the Android Studio application folder. This guarantees Gradle will always run successfully.

### Step: Disabling User Script Sandboxing
- **The Issue:** The build failed with `User Script Sandboxing Enabled in Xcode Project`, resulting in a `No such module 'shared'` error in Swift.
- **The Fix:** We changed "User Script Sandboxing" to **No** in the Xcode Build Settings.
- **Reasoning:** Apple introduced strict sandboxing in Xcode 15 that prevents terminal scripts from modifying files in the project directory. Because sandboxing was turned on, Xcode actively blocked our Gradle script from creating the `xcode-frameworks` output folder. Disabling it allowed Gradle to safely place the compiled framework where Xcode expected it.

### Step: Target Architecture ("My Mac" vs "iOS Simulator")
- **The Issue:** Attempting to build the app while "My Mac" was selected in Xcode failed.
- **Reasoning:** "My Mac" attempts to compile the app using the macOS SDK (`macosx`). Because our Kotlin `build.gradle.kts` only specified iOS architectures (`iosX64`, `iosArm64`), the Gradle script had no idea how to build a macOS framework. Switching the target to an iPhone Simulator instructed Xcode to use the iOS SDK, which matched our Kotlin configurations perfectly.
