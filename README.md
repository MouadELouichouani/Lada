# Lada

Lada is a native Android recipe app built with Kotlin and Jetpack Compose. It gives users a place to sign in, browse recipes, view profile information, and work with recipe data stored in Firebase.

The project currently includes authentication, home feed, profile, saved, and notification navigation flows, with the data layer organized around repositories and domain models.

## Features

- Splash, sign-in, and sign-up screens
- Email/password authentication with Firebase Auth
- Google sign-in support through Google Play Services
- Recipe feed backed by Cloud Firestore
- User profile data backed by Cloud Firestore
- Saved, notifications, and profile destinations in the main navigation
- Custom bottom navigation for the main app area
- Clean layered structure with data, domain, presentation, and dependency container packages

## Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- Android Architecture Components
- ViewModel
- Kotlin Coroutines and Flow
- Navigation Compose
- Firebase Auth
- Cloud Firestore
- Firebase Analytics
- Google Sign-In
- Coil for image loading
- Exyte Animated Navigation Bar

## Requirements

- Android Studio with recent Android Gradle Plugin support
- JDK 11 or newer
- Android SDK 36
- A Firebase project configured for Android

## Getting Started

1. Clone the repository.

   ```bash
   git clone <repository-url>
   cd Lada
   ```

2. Open the project in Android Studio.

3. Add your Firebase configuration file:

   - Create or open a Firebase project.
   - Add an Android app with package name `project.ma.lada`.
   - Download `google-services.json`.
   - Place it in the `app/` directory.

4. Enable the Firebase products used by the app:

   - Authentication
   - Cloud Firestore
   - Analytics, if desired for your Firebase setup

5. Build and run the app from Android Studio, or use Gradle:

   ```bash
   ./gradlew assembleDebug
   ```

   On Windows:

   ```powershell
   .\gradlew.bat assembleDebug
   ```

## Firebase Data

The app currently reads and writes these Firestore collections:

- `recipes`
- `users`

Recipe documents map to the `Recipe` domain model:

- `id`
- `title`
- `description`
- `ingredients`
- `steps`
- `imageUrl`
- `userId`
- `rating`
- `time`
- `category`
- `authorName`
- `authorImageUrl`
- `timestamp`

## Project Structure

```text
app/src/main/java/project/ma/lada/
|-- core/common/              # Shared utility types such as Resource
|-- data/
|   |-- repository/           # Firebase-backed repository implementations
|   `-- source/               # Local/simple data sources
|-- di/                       # AppContainer dependency wiring
|-- domain/
|   |-- model/                # Domain models
|   |-- repository/           # Repository interfaces
|   `-- usecase/              # Use cases
|-- presentation/
|   |-- components/           # Reusable Compose UI components
|   |-- navigation/           # Screen routes
|   |-- state/                # UI state models
|   |-- ui/                   # App screens
|   `-- viewmodel/            # ViewModels
`-- ui/theme/                 # Compose theme, colors, and typography
```

## Useful Commands

```bash
./gradlew assembleDebug
./gradlew test
./gradlew connectedAndroidTest
```

On Windows, replace `./gradlew` with `.\gradlew.bat`.

## Notes

- Firebase setup requires `app/google-services.json`. Keep this file out of version control if it contains private project credentials.
- Saved recipes and notifications are already connected to navigation, but their screens currently use placeholder content.
- Recipe creation from the bottom navigation add action is planned but not implemented yet.
