fastlane documentation
----

# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```sh
xcode-select --install
```

For _fastlane_ installation instructions, see [Installing _fastlane_](https://docs.fastlane.tools/#installing-fastlane)

# Available Actions

## Android

### android cf_release

```sh
[bundle exec] fastlane android cf_release
```

CF (Alpha) release: bump patch, build release APK, and upload via Firebase CLI

### android beta_release

```sh
[bundle exec] fastlane android beta_release
```

Beta release: bump patch, build release APK, and distribute to beta testers

### android dev_build

```sh
[bundle exec] fastlane android dev_build
```

Dev build: timestamped debug APK on each commit

### android bump_patch

```sh
[bundle exec] fastlane android bump_patch
```

Bump patch version (x.y.Z → x.y.Z+1)

### android bump_minor

```sh
[bundle exec] fastlane android bump_minor
```

Bump minor version (x.y.Z → x.(y+1).0)

### android bump_major

```sh
[bundle exec] fastlane android bump_major
```

Bump major version (x.y.Z → (x+1).0.0)

### android test

```sh
[bundle exec] fastlane android test
```

Runs all the tests

### android beta

```sh
[bundle exec] fastlane android beta
```

Submit a new Beta Build to Crashlytics Beta

### android deploy

```sh
[bundle exec] fastlane android deploy
```

Deploy a new version to the Google Play

----

This README.md is auto-generated and will be re-generated every time [_fastlane_](https://fastlane.tools) is run.

More information about _fastlane_ can be found on [fastlane.tools](https://fastlane.tools).

The documentation of _fastlane_ can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
