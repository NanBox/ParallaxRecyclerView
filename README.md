[![](https://api.bintray.com/packages/southernbox/maven/ParallaxRecyclerView/images/download.svg)](https://bintray.com/southernbox/maven/ParallaxRecyclerView/_latestVersion)
[![](https://img.shields.io/badge/Android%20Arsenal-ParallaxRecyclerView-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/6399)
[![](https://img.shields.io/badge/API-15+-green.svg?style=flat)](https://android-arsenal.com/api?level=15)

# ParallaxRecyclerView

A RecyclerView with parallax folding effect.

![](/images/ParallaxRecyclerView.gif)

# Usage

**Add the dependencies to your gradle file:**

```javascript
dependencies {
    implementation 'com.nanbox:ParallaxRecyclerView:1.1.0'
}
```
**In the layout file, replace the RecyclerView with ParallaxRecyclerView:**

```xml
<com.nanbox.parallaxrecyclerview.ParallaxRecyclerView
    android:id="@+id/rv"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```