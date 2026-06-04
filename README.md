# Map By Step

## Explanation

Map By Step is a gamified geography learning app in which you make progress by walking in real life.
Go for a walk, enjoy nature, and explore the world. When you are finished with your walk, open the
app and find out which destinations you have visited virtually.

### How to Play

The app requires permission to read from Android Health Connect or Apple Health and will ask you to
grant these permissions on the Profile screen. After doing so, all steps registered to those
platforms from that point on are automatically synced to the game when opening it. You can find
statistics of your step data on the same Profile screen.

### Maps and Destinations

Currently, only one map is available to progress in, which is Countries of the World. Every
destination you visit is an independent country on Earth. Trivia facts such as capitals and
population are provided for each destination, and the destination is displayed on an interactive
map.

Destinations have different rarity tiers, based on the population. For Countries of the World, these
are the numbers:

| Rarity    | Population Range         | Odds of Visiting |
|-----------|--------------------------|------------------|
| Common    | Less than 5,000,000      | 81.1%            |
| Uncommon  | 5,000,000 - 24,999,999   | 15%              |
| Rare      | 25,000,000 - 74,999,999  | 3%               |
| Epic      | 75,000,000 - 249,999,999 | 0.65%            |
| Legendary | 250,000,000 or more      | 0.25%            |

### Directions

If you are struggling to reach destinations of higher tiers, you can spend Map Points to get
directions to an undiscovered destination of a specific rarity. Map Points are earned by visiting
destinations you already discovered before.

| Rarity    | Map Points Awarded | Direction Price |
|-----------|--------------------|-----------------|
| Common    | 5                  | 50              |
| Uncommon  | 20                 | 200             |
| Rare      | 50                 | 500             |
| Epic      | 100                | 1,000           |
| Legendary | 250                | 2,500           |

### Leveling up

Once you have visited all destinations of a map, you will level up. Leveling up increases the amount
of steps that are required to visit a destination for that map by 10% of the base distance per
level compared to the distance of the previous level. For example:

| Level | Distance            | Difference            |
|-------|---------------------|-----------------------|
| 1     | 500 (base distance) | -                     |
| 2     | 550                 | +50 (1x 10% of base)  |
| 3     | 650                 | +100 (2x 10% of base) |
| 4     | 800                 | +150 (3x 10% of base) |
| 5     | 1,000               | +200 (4x 10% of base) |
| ...   | ...                 | ...                   |

Your Map Points will be reset and all destinations will be reset to undiscovered. After leveling up
the first time, you will still be able to check out all the destinations' information of a map, even
if you have not discovered them yet for the new level.

### Settings

If you want to be able to complete a map quicker or slower, you can change the distance multiplier.
This changes how many steps are required to visit a destination. Feel free to set this to whatever
suits your lifestyle and your body's ability best.

## Technical Information

### Architecture

The app follows Clean Architecture and uses an MVI state management approach.

### Use of AI

This project was mainly created as a way for me to learn about KMP, and I firmly believe writing
code yourself is the best way to learn. That is why no AI agent was used during the development of
this project. AI assistants were only used for brainstorming, reviewing, and code suggestions. All
AI generated code has been thoroughly reviewed by me before manually implementing it myself.

### Tools & Libraries

- Android Health
  API: [Health Connect](https://developer.android.com/training/wearables/health-connect)
- Dependency Injection: [Koin](https://insert-koin.io/)
- Database: [Room](https://developer.android.com/kotlin/multiplatform/room)
- Image loading: [Coil](https://github.com/coil-kt/coil)
- Map (interactive and static image): [MapBox](https://www.mapbox.com/)
- Charts: [Vico](https://github.com/patrykandpatrick/vico)
- UI design (by
  me): [Figma](https://www.figma.com/design/xKbS1UpJltSdgTHozo4Vj3/MBS?m=auto&t=XchPLaMGu1iF9dlY-1)

### Data Sources

- Countries of the World metadata: [REST Countries API](https://restcountries.com/)

### Compose Multiplatform

This is a Compose Multiplatform project. While both Android and iOS are targeted, iOS is currently
not implemented and only targeted for future implementation.

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform
  applications.
  It contains several subfolders:
    - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
    - Other folders are for Kotlin code that will be compiled for only the platform indicated in the
      folder name.
      For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
      the [iosMain](./composeApp/src/iosMain/kotlin) folder would be the right place for such calls.

* [/iosApp](./iosApp/iosApp) contains iOS applications. Even if you’re sharing your UI with Compose
  Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for
  your project.

### Build and Run Android Application

To build and run the development version of the Android app, use the run configuration from the run
widget
in your IDE’s toolbar or build it directly from the terminal:

- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Build and Run iOS Application

To build and run the development version of the iOS app, use the run configuration from the run
widget
in your IDE’s toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.

---

Learn more
about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

## Roadmap

Because this is in the end just a passion project, it can take some time to get to these. Items are
not in any particular order.

- More maps
- Exporting and importing progress
- Database migrations
- Visual improvements (especially for leveling up)
- Unit tests
- Analytics
- Crash reporting
- Play Store release
- iOS implementation
- Realtime step tracking

# License

This project is licensed under the PolyForm Strict License 1.0.0.
You are free to view, learn from, and contribute to this code. However, you are strictly prohibited
from using this software or its source code for any commercial purposes.
See the [LICENSE](LICENSE.md) file for the full license text.