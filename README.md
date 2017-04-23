Download
--------
Use Gradle:

```gradle
dependencies {
    // RoundedFrameLayout
    compile 'com.github.QuarkWorks:RoundedFrameLayout-Android:0.3.4'
}
```
How do I use?
-------------------
Configure radius in layout xml file:
```xml
    <com.quarkworks.roundedframelayout.RoundedFrameLayout
        android:id="@+id/refresh_button_container"
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="1"
        android:layout_margin="4dp"

        app:cornerRadiusTopLeft="10dp"
        app:cornerRadiusTopRight="20dp"
        app:cornerRadiusBottomLeft="20dp"
        app:cornerRadiusBottomRight="10dp"
        app:borderWidth="2dp"
        app:clippedBackgroundColor="#00ff00"
        app:borderColor="#ff0000"
        app:softBorderColor="#ffffff">

        <!--sub views-->
        <ImageView
            android:id="@+id/refresh_button"
            android:layout_width="match_parent"
            android:layout_height="match_parent"

            android:src="@drawable/image_1"/>

    </com.quarkworks.roundedframelayout.RoundedFrameLayout>
```

Or configure radius in code:
```java
    // setCornerRadius() will override four corners
    // refreshButtonContainer.setCornerRadius(20);

    refreshButtonContainer.setCornerRadiusTopLeft(10);
    refreshButtonContainer.setCornerRadiusTopRight(20);
    refreshButtonContainer.setCornerRadiusBottomLeft(20);
    refreshButtonContainer.setCornerRadiusBottomRight(10);

    refreshButtonContainer.setClippedBackgroundColor(Color.RED);
    refreshButtonContainer.setBorderColor(Color.BLACK);
    refreshButtonContainer.setBorderWidth(4);

    // Smooth drawn bound of RoundedFrameLayout when below LOLLIPOP
    // Should be close to or same as background color
    refreshButtonContainer.setSoftBorderColor(Color.WHITE);

    refreshButtonContainer.requestLayout();
```
