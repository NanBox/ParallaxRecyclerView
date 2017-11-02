[![](https://travis-ci.org/SouthernBox/ParallaxRecyclerView.svg?branch=master)](https://travis-ci.org/SouthernBox/ParallaxRecyclerView)
[![](https://api.bintray.com/packages/southernbox/maven/ParallaxRecyclerView/images/download.svg)](https://bintray.com/southernbox/maven/ParallaxRecyclerView/_latestVersion)
![](https://img.shields.io/badge/platform-android-green.svg)
[![](https://img.shields.io/badge/API-15+-green.svg?style=flat)](https://android-arsenal.com/api?level=15)
![](https://img.shields.io/badge/language-java-orange.svg)
[![](https://badge.juejin.im/entry/58dca9212f301e0062f8cca3/likes.svg?style=flat)](https://juejin.im/entry/58dca9212f301e0062f8cca3/detail)

# ParallaxRecyclerView

![](/images/ParallaxRecyclerView.gif)

总结一下这个列表的特点，就是以下三点：
1. 重叠效果；
2. 层次感；
3. 首项的差动。

下面我们来一个个解决。

我们新建一个 ParallaxRecyclerView，让它继承 RecyclerView，并使用LinearLayoutManager作为布局管理器。

## 重叠效果

其实就是每一项都搭一部分在它前面那项而已。我们知道，RecyclerView 可以通过设置 ItemDecoration 来实现列表的间隔效果，有没有想过要是把间隔设为负数会怎么样？比如：

```java
addItemDecoration(new ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = -dp2px(context, 10);
            }
        });
```

没错，这就实现了我们的重叠效果。

## 层次感

在 Material Design 里是有Z轴这个概念的，我们可以给控件设置垂直于屏幕的高度，让不在同一高度的控件看起来有层次感。当然，我们要用 Material Design 的控件才有这个属性，这里我用的是 CardView。

我们给 ParallaxRecyclerView 增加一个滑动监听，在 onScrolled 方法里面做如下设置：

```java
LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
int firstPosition = layoutManager.findFirstVisibleItemPosition();
int lastPosition = layoutManager.findLastVisibleItemPosition();
int visibleCount = lastPosition - firstPosition;
//重置控件的高度
int elevation = 1;
for (int i = firstPosition - 1; i <= (firstPosition + visibleCount) + 1; i++) {
    View view = layoutManager.findViewByPosition(i);
    if (view != null) {
        if (view instanceof CardView) {
            ((CardView) view).setCardElevation(dp2px(context, elevation));
            elevation += 5;
        }
       
    }
}
```

其中，setCardElevation 方法就是用来给 CardView 设置高度的，这里让每一项的高度比它的上一项高 5dp。

## 首项的差动

最后，我们想给第一项增加一个差动效果，这个同样在 onScrolled 方法里面做处理就好了：

```java
View firstView = layoutManager.findViewByPosition(firstPosition);
float firstViewTop = firstView.getTop();
firstView.setTranslationY(-firstViewTop / 2.0f);
```

这样相当于第一项的滑动速度变成原来的一半。但这也会导致一个问题， 由于改变了控件的位置，当这个控件被复用时，会出现位置不正确的情况。所以我们在设置高度的时候，可以顺便把控件的位置复原了：

```java
 float translationY = view.getTranslationY();
if (i > firstPosition && translationY != 0) {
    view.setTranslationY(0);
}
```

这样就完成了一个带有简单折叠效果的 RecyclerView 了，妥妥的。
