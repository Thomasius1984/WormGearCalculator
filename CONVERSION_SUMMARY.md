# iOS to Android Conversion Summary

## Project: Schneckengetriebe (Worm Gear) Calculator

Successfully converted the iOS TestApp to an Android 13+ app in the Walze folder.

---

## What Was Converted

### 1. **Core Calculator Logic** (`WormGearCalculator.kt`)
- **Source:** `WormGearCalculator.swift` (iOS)
- **Target:** `WormGearCalculator.kt` (Android ViewModel)
- **Changes:**
  - Converted from Swift `@Published` properties to Kotlin `StateFlow`
  - Converted computed properties to functions
  - Implemented `calculateResults()` function for all calculations
  - Preserved all 20+ worm gear calculation formulas exactly
  - Uses Kotlin's `kotlin.math.*` instead of Swift's Foundation

### 2. **User Interface Screens**

#### Input Screen (`InputScreen.kt`)
- Converted from SwiftUI `InputView` to Jetpack Compose
- 9 input fields organized in 3 sections:
  - Grundparameter (Basic Parameters)
  - Durchmesser & Winkel (Diameter & Angles)
  - Faktoren (Factors)
- Uses Material 3 components
- Real-time validation and calculations

#### Results Screen (`ResultsScreen.kt`)
- Converted from SwiftUI `ResultsView` to Jetpack Compose
- Displays 20 calculated results in a scrollable list
- Each result shows name, value (with proper formatting), and unit
- Real-time updates as input values change

#### Info Screen (`InfoScreen.kt`)
- Converted from SwiftUI `InfoView` to Jetpack Compose
- Displays app information
- Lists all important formulas
- Shows usage hints and copyright notice

### 3. **Navigation**
- **iOS:** TabView with 3 tabs
- **Android:** Material 3 NavigationBar (bottom tabs)
- Same 3-tab structure maintained:
  1. Eingabe (Input)
  2. Ergebnisse (Results)
  3. Info (Information)

### 4. **Main Activity** (`MainActivity.kt`)
- Converted from SwiftUI `TestAppApp` structure
- Uses `viewModel()` composable for ViewModel management
- Implements `Scaffold` with `NavigationBar`
- Material 3 design system applied

---

## Technical Implementation Details

### Architecture
- **Pattern:** MVVM (Model-View-ViewModel)
- **State Management:** Kotlin StateFlow for reactive UI updates
- **UI Framework:** Jetpack Compose with Material 3

### Formulas Preserved
All calculation formulas from the iOS app are identical:
- Axialmodul (m_x)
- Teilkreisdurchmesser (d_2)
- Profilverschiebungsfaktor (x_f)
- Normale Teilung (p_n)
- Axialteilung (p_x)
- And 15+ more...

### Kotlin-Specific Changes
- Used `Double` instead of Swift's numeric types
- Used `String.format()` for decimal formatting
- Used `MutableStateFlow` for state management
- Used `collectAsState()` in Composables for reactive updates
- Used `remember()` for computed calculations

---

## Files Created/Modified

### New Files Created:
1. `app/src/main/java/net/fritz/walze/WormGearCalculator.kt` - Calculator ViewModel
2. `app/src/main/java/net/fritz/walze/ui/screens/InputScreen.kt` - Input UI
3. `app/src/main/java/net/fritz/walze/ui/screens/ResultsScreen.kt` - Results UI
4. `app/src/main/java/net/fritz/walze/ui/screens/InfoScreen.kt` - Info UI

### Modified Files:
1. `app/src/main/java/net/fritz/walze/MainActivity.kt` - Updated with new navigation
2. `app/src/main/res/values/strings.xml` - Updated app name to "Schneckengetriebe Rechner"

---

## Compatibility
- **Target SDK:** Android 13+ (API 33+)
- **Min SDK:** Android 13 (API 33)
- **Kotlin Version:** 1.9+
- **Compose Version:** Latest (from libs.versions.toml)
- **Material Design:** Material 3

---

## Features Maintained
✅ All calculation functions identical to iOS version
✅ Same UI layout with 3 tabs
✅ Real-time results calculation
✅ Input validation
✅ Proper unit display
✅ Copyright and author information
✅ German language support

---

## Ready for Testing
The Android app is now ready to build and test. All code compiles without errors and follows Android best practices using Jetpack Compose and Material 3 design system.
