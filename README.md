# Schneckengetriebe Rechner (Worm Gear Calculator)

An Android application for calculating worm gear (Schneckengetriebe) parameters based on input specifications.

## Features

- **Input Tab**: Enter worm gear parameters including:
  - Normal module (Normalmodul m_n)
  - Center lead angle (Mittensteigungswinkel γ)
  - Number of threads (Gangzahl Z₁)
  - Number of teeth (Zähnezahl Z₂)
  - Center distance (Achsabstand a)
  - And more...

- **Results Tab**: Automatically calculates and displays 20+ results including:
  - Axial module (Axialmodul m_x)
  - Pitch diameter (Teilkreisdurchmesser d_2)
  - Profile shift factor (Profilverschiebungsfaktor x_f)
  - Various diameters, pitches, and angles

- **Info Tab**: Shows formulas, usage hints, and copyright information

## Technology Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material Design 3
- **Architecture**: MVVM (Model-View-ViewModel)
- **State Management**: Kotlin StateFlow
- **Minimum SDK**: Android 13 (API 33)
- **Target SDK**: Android 14 (API 36)

## Building the App

### Prerequisites

- Android Studio (latest version recommended)
- Java 11 or higher

### Build Debug APK

```bash
./gradlew assembleDebug
```

APK location: `app/build/outputs/apk/debug/app-debug.apk`

### Build and Install on Device/Emulator

```bash
./gradlew installDebug
```

### Build Release APK

```bash
./gradlew assembleRelease
```

## Project Structure

```text
Walze/
├── app/
│   ├── src/main/java/net/fritz/walze/
│   │   ├── MainActivity.kt              # Main entry point
│   │   ├── WormGearCalculator.kt        # ViewModel with calculation logic
│   │   └── ui/screens/
│   │       ├── InputScreen.kt           # Input parameters screen
│   │       ├── ResultsScreen.kt         # Results display screen
│   │       └── InfoScreen.kt            # Information and formulas
│   └── src/main/res/                    # Resources (strings, colors, etc)
├── gradle/                               # Gradle wrapper
└── build.gradle.kts                      # Build configuration
```

## Calculations

All worm gear calculations are based on DIN standards for worm gears (Schneckengetriebe). The app performs mathematical computations for:

- Axial and normal pitch
- Profile shift factors
- Tooth heights and diameters
- Lead angles at different diameters
- Contact ratio (Überdeckungsgrad)

## Conversion Note

This app was converted from an iOS SwiftUI application to Android using:

- Kotlin instead of Swift
- Jetpack Compose instead of SwiftUI
- Android Material Design 3 instead of iOS design system

All calculation logic and formulas remain identical to the original iOS version.

## Author

© 2025 Andreas Fritz

## License

All rights reserved.
