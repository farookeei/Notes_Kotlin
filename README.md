# Notes App

A modern, robust Android Notes application built to demonstrate expertise in state-of-the-art
Android development practices and to serve as a foundational step toward Kotlin Multiplatform (KMP)
architecture.

## Tech Stack & Architecture

### Core Technologies

* **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) - For building a modern,
  declarative, and responsive user interface.
* **Concurrency:
  ** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) -
  For asynchronous programming and reactive data streams.
* **Local Storage:** [Room Database](https://developer.android.com/training/data-storage/room) - For
  robust, offline-first SQLite data persistence.
* **Dependency Injection:** **[Koin](https://insert-koin.io/)** - A pragmatic, lightweight
  dependency injection framework built purely in Kotlin.

### Architecture

The app follows **Clean Architecture** principles combined with the **MVVM (Model-View-ViewModel)**
or **MVI (Model-View-Intent)** pattern to ensure separation of concerns, testability, and
scalability.

## Why Koin for Dependency Injection?

For this specific project, **Koin** is the recommended DI framework over Hilt or Dagger. Here is
why:

1. **KMP Readiness:** The ultimate goal is to architect solutions for Kotlin Multiplatform. Koin is
   natively supported and widely adopted in the KMP ecosystem (alongside Ktor and SQLDelight). Using
   Koin now builds the exact muscle memory needed for shared KMP codebases.
2. **Kotlin Idiomatic:** Koin uses a pure Kotlin DSL, making it extremely readable and intuitive
   without relying on heavy annotations or code generation (unlike Dagger/Hilt).
3. **Compose Integration:** Koin provides excellent, out-of-the-box extensions for Jetpack Compose (
   `koin-androidx-compose`), making ViewModel injection seamless.

## Getting Started

### Prerequisites

* Android Studio (Latest stable or Koala/Ladybug depending on Compose versions)
* JDK 17+
* Kotlin 1.9+ / 2.0+

### Installation

1. Clone the repository.
2. Open the project in Android Studio.
3. Sync Gradle dependencies.
4. Run the `app` configuration on an emulator or physical device.

## Roadmap & Future Scope

- [ ] Implement core CRUD operations for Notes using Room.
- [ ] Build UI with Jetpack Compose (Material 3).
- [ ] Integrate Koin for dependency injection.
- [ ] Add unit tests using JUnit and MockK.
- [ ] **Phase 2:** Refactor the core business logic and database (migrating to SQLDelight) into a
  KMP shared module to support an iOS target via Compose Multiplatform or SwiftUI.
