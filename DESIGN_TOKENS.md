# Design Tokens - Spacing & Dimensions

## 📏 Files Created:

### 1. `Spacing.kt` - Padding & Margins
### 2. `Dimensions.kt` - Component Sizes

---

## 🎨 How to Use:

### **Import:**
```kotlin
import com.yousrasdn.businesscardgenerator.ui.theme.Spacing
import com.yousrasdn.businesscardgenerator.ui.theme.Dimensions
```

### **In Your Composables:**

```kotlin
@Composable
fun MyScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = Spacing.screenHorizontal,  // 16.dp
                vertical = Spacing.screenVertical        // 16.dp
            )
    ) {
        Text("Title")
        
        Spacer(modifier = Modifier.height(Spacing.medium))  // 16.dp
        
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimensions.buttonHeight)  // 56.dp
        ) {
            Text("Click Me")
        }
    }
}
```

---

## 📐 Spacing Values:

| Name | Value | Use Case |
|------|-------|----------|
| `extraSmall` | 4.dp | Tight spacing, icon padding |
| `small` | 8.dp | Between related items |
| `medium` | 16.dp | Default spacing |
| `large` | 24.dp | Section spacing |
| `extraLarge` | 32.dp | Large gaps |
| `huge` | 48.dp | Major sections |
| `screenHorizontal` | 16.dp | Screen edge padding |
| `screenVertical` | 16.dp | Top/bottom padding |
| `cardPadding` | 16.dp | Inside cards |

---

## 📏 Dimensions Values:

### **Buttons:**
- `buttonHeight` - 56.dp (Standard)
- `buttonHeightSmall` - 48.dp (Compact)

### **Text Fields:**
- `textFieldHeight` - 56.dp

### **Icons:**
- `iconSizeSmall` - 16.dp
- `iconSizeMedium` - 24.dp (Default)
- `iconSizeLarge` - 32.dp
- `iconSizeExtraLarge` - 48.dp

### **Profile Images:**
- `profileImageSmall` - 80.dp
- `profileImageMedium` - 120.dp (Default)
- `profileImageLarge` - 160.dp

### **Cards:**
- `cardElevation` - 4.dp
- `cardCornerRadius` - 16.dp

### **Other:**
- `bottomSheetCornerRadius` - 28.dp
- `dividerThickness` - 1.dp
- `borderWidth` - 2.dp

---

## 💡 Examples:

### **Screen Layout:**
```kotlin
Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = Spacing.screenHorizontal)
) {
    // Content
}
```

### **Card:**
```kotlin
Card(
    modifier = Modifier
        .fillMaxWidth()
        .padding(Spacing.medium),
    shape = RoundedCornerShape(Dimensions.cardCornerRadius)
) {
    Column(modifier = Modifier.padding(Spacing.cardPadding)) {
        // Card content
    }
}
```

### **Button:**
```kotlin
Button(
    onClick = {},
    modifier = Modifier
        .fillMaxWidth()
        .height(Dimensions.buttonHeight)
) {
    Icon(
        imageVector = Icons.Default.Add,
        contentDescription = null,
        modifier = Modifier.size(Dimensions.iconSizeMedium)
    )
    Spacer(modifier = Modifier.width(Spacing.small))
    Text("Add Card")
}
```

### **Profile Image:**
```kotlin
AsyncImage(
    model = profileImageUrl,
    contentDescription = "Profile",
    modifier = Modifier
        .size(Dimensions.profileImageMedium)
        .clip(CircleShape)
)
```

### **Spacing Between Elements:**
```kotlin
Column {
    Text("Title")
    Spacer(modifier = Modifier.height(Spacing.small))
    Text("Subtitle")
    Spacer(modifier = Modifier.height(Spacing.large))
    Button(onClick = {}) { Text("Action") }
}
```

---

## ✅ Benefits:

1. **Consistency** - Same spacing across the app
2. **Easy Updates** - Change once, updates everywhere
3. **Readable Code** - `Spacing.medium` vs `16.dp`
4. **Design System** - Follows Material Design principles
5. **Maintainable** - Easy to adjust for different screen sizes

---

## 🎯 Best Practices:

### **DO:**
- ✅ Use `Spacing.medium` for default spacing
- ✅ Use `Spacing.screenHorizontal` for screen edges
- ✅ Use `Dimensions.buttonHeight` for all buttons
- ✅ Be consistent across screens

### **DON'T:**
- ❌ Use hardcoded values like `16.dp` directly
- ❌ Create custom spacing values inline
- ❌ Mix spacing systems

---

## 🔄 When to Add New Values:

If you find yourself using the same custom spacing multiple times:
1. Add it to `Spacing.kt` or `Dimensions.kt`
2. Give it a meaningful name
3. Use it throughout the app

Example:
```kotlin
// If you keep using 12.dp for something specific
val stepIndicatorSpacing: Dp = 12.dp
```

---

## 📱 Responsive Design (Future):

For different screen sizes, you can create variants:
```kotlin
object Spacing {
    val screenHorizontal: Dp
        get() = if (isTablet) 24.dp else 16.dp
}
```

But for MVP, fixed values are fine!
