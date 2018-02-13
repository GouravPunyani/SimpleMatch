# Signing an Android Build

While we develop, I created a temporary keystore so that we can build the app in release mode.

I've checked this keystore into source control but for the *real* version, I recommend not doing that.

### One Time Setup

I generated the keystore with this:

`keytool -genkey -v -keystore app/dev.keystore -alias matchco -keyalg RSA -keysize 2048 -validity 10000`

The password for both the keystore and key are both `password`.

### Building on your machine

Edit your `~/.gradle/gradle.properties` to include this:

```
MATCHCO_STORE_FILE=dev.keystore
MATCHCO_KEY_ALIAS=matchco
MATCHCO_STORE_PASSWORD=password
MATCHCO_KEY_PASSWORD=password
```

# Directory Structure Overview

### android

The android codebase.

### android/app/src/main/java/co/getmatch/ScanApp

This contains the native Android scan code.

* `ReactScanPackage.java` is a declaration to React that we have a native code extension.
* `ReactScanModule.java` serves an the interface that javascript will be calling to interact with the native code.
* `ReactScanViewManager.java` is unused, but I left this in. If you ever decide to integrate the scan code at a view-level (instead of an activity-level), you can start here. Don't hesitate in deleting this file if you're happy with what you have.

### flow-typed

Contains the type-definitions for 3rd party libraries we're using (if they're available).

### ignite

Contains some generators for screens & components. You can access these generators by typing `ignite generate`.

### ios

The ios codebase. You'll be dropping your iOS scan code in here when it's ready.

### schema

Unused. I had used this at the start of the project to help wrap my head around scope. Feel free to delete.

### src

This is the majority of your React Native application.

### src/app

Serves as the entry point of the app. `index.ios.js` and `index.android.js` call this file. It bootstraps your React Native environment by creating the top level data stores and loading the navigation component (which is the visual root component).

### src/entities

These are the simple data structs of your app. The nouns. For example, a `testimonial` or a `user` or a `product`. They contain fields and validation code, but perform no actions or side-effects.

### src/lib

Sort of a miscellaneous pile. `scan.js` contains the javascript-side to the Native scan code.

### src/services

These represents mostly external IO portals.

* `firebase.js` allows you to talk to firebase to get/set data. 
* `opener.js` allows you to open external links like email, browser, and phone calls
* `reactotron.js` is a debugging tool used in dev only (https://github.com/infinitered/reactotron)
* `translate/langauges/*.json` are your language translation files
* `translate/languages/english.json` is your main fallback file. everything translatable lives here.
* `translate/languages/jp.json` is an example of a translation file with some overrides.
* `translate/translate-provider.js` is a provider that makes a `translate` function available to all React components.
* `translate/translate-setup.js` is called at startup to hookup the i18n library.
* `translate/translator.js` is a higher order component that you can use to wrap your own components to access the `translate` function made available by the Provider. See `src/ui/basics/text/text.js` for an example.

### src/stores

Stores hold your entities and provide actions to change to them. If you're familiar with MVC, this is the C (controller). They serve as a traffic cop between your ui, entities, and services.

* `contacts-store.js` holds the contacts entity.
* `navigation-store.js` manages the `react-navigation` state (ie. the data powering which screen you see on the screen)
* `products-store.js` holds products (1) and testimonials (4)... i typed the data in here for that as well, in the future, it'll come from firebase.
* `scan-store.js` holds the latest scan data from the user. In the future, you'll be uploading this to firebase.
* `user-store.js` manages the user. you can see it provides the login/logout workflow.

### ui

This is your user interface. They're divided into a few groups (directories):

* `basics`
* `components`
* `layouts`
* `navigation`
* `screens`
* `theme`

The `index.js` file serves as a table-of-contents which exports every component except for the navigation.

### ui/basics

These are dependency-free components. They are foundations of which other components can be built upon. These components are very portable to your platform component and can be used outside this app.

For example, `text.js` serves as the base component for all `<Text>` in the system. It provides both properties and behaviours common to all text:

* `<Text text='hello' />` - write literal text... not recommend because....
* `<Text tx='some.i18n.key' />` - looks up the text in your language files based on the translation key
* `<Text small />` - a smaller version used for less-important info
* `<Text title />` - used for headings
* `<Text fancy />` - switches the font to be the scripty-italic one you guys use
* `<Text brand />` - colors it with your golden color
* `<Text lighter />` - a lighter version of your standard text color
* `<Text white />` - straight up white text
* `<Text center />` - centered text.

All these are usuable (some will clobber others -- for example colors) together:  

```jsx
<Text tx='loginScreen.title' title fancy center brand />
```

### ui/components

These are shared components in the traditional sense. They're made up of React Native components, your `basics` and other `components` in this directory. The intention is that they are portable, just like `basics`. The majority of components in your system will be here. They are pure components and are handed the props. They know nothing about state or entities.

Let's take a look at `ui/components/button`.

First, here's an example usage:

```jsx
<Button
  tx='login.submitButton'
  onPress={this.handleSubmit}
  disabled={isDisabled} 
/>
```

It is made up a `TouchableOpacity` containing a `Text`.

Both of these sub-components are styled with the `glamorous-native` library. It provides a simple way to extend components both statically and dynamically. For example, buttons always have a shadow radius of 1 (static), but their background color will change based on their `disabled` state (dynamic).

Buttons also have **variants**. These represent presets. They're buttons, but styled slightly different.

```jsx
<Button.Facebook /> /** Used to draw a facebook-based button (like login or signup) **/
<Button.Clear /> /** Used to draw a clear-version of buttons used in a few places in your app **/
```

They're just presets that didn't warrant their own 1st class component & file.

### ui/layouts

These are component containers which get re-used.  There are 2 used for screens.  You have some screens that scroll and some that don't.  They start here.

### ui/navigation

These components control your navigation flow.

`drawer-navigator` is the heart of your navigation.  Your app lives entirely in a drawer that can be swiped in from the left.  Each item in the drawer (`ui/navigation/drawer-navigator/drawer-content.js`) represents an individual stack of screens (`ui/navigation/drawer-navigator/drawer-navigator.js`).

In each of the 9 stacks, there will be a starting screen.  For example, the `login` stack, starts with a login screen, but you can navigate to `signup` within that stack.

### ui/screens

These are the 28 screens that make up your app.

### ui/theme

These are various data structures which represent the styles of your app.

* `colors.js` defines both the color palette and the roles of colors in your app
* `font-family.js` defines the fonts you use (check out `ui/basics/text/text.js`)
* `spacing.js` defines a common spacing scale used for consistent layouts for padding and margins


