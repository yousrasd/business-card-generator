# Fix Gradle/Hilt Build Issue

## ✅ What I Fixed:

### 1. Added Hilt plugins to project-level build.gradle.kts:
```kotlin
alias(libs.plugins.hilt.android) apply false
alias(libs.plugins.ksp) apply false
```

### 2. Added JavaPoet dependency:
```kotlin
implementation("com.squareup:javapoet:1.13.0")
```

---

## 🔧 Steps to Fix (Try in Order):

### **Step 1: Clean & Rebuild**
```bash
# In terminal, run:
./gradlew clean
./gradlew build
```

Or in Android Studio:
- **Build** → **Clean Project**
- **Build** → **Rebuild Project**

---

### **Step 2: Invalidate Caches**

In Android Studio:
1. **File** → **Invalidate Caches**
2. Check **"Clear file system cache and Local History"**
3. Check **"Clear downloaded shared indexes"**
4. Click **"Invalidate and Restart"**

---

### **Step 3: Delete Gradle Cache (If Still Failing)**

```bash
# Stop Gradle daemon
./gradlew --stop

# Delete Gradle cache folders
rm -rf ~/.gradle/caches/
rm -rf .gradle/

# Sync again
./gradlew clean build
```

---

### **Step 4: Sync Gradle**

In Android Studio:
- Click **"Sync Now"** or
- **File** → **Sync Project with Gradle Files**

---

## 🎯 Quick Fix (Most Common):

Just run these commands:
```bash
./gradlew --stop
./gradlew clean
./gradlew build
```

Then sync Gradle in Android Studio.

---

## ✅ After Fix:

The project should compile successfully. The JavaPoet dependency resolves the version conflict between Hilt and other annotation processors.

---

## 🚨 If Still Not Working:

1. Check your internet connection (Gradle needs to download dependencies)
2. Make sure you're using Java 11 or higher
3. Try restarting Android Studio completely
4. Delete the `.idea` folder and re-import the project

---

## 📝 What Caused This:

Hilt uses JavaPoet internally, and there was a version mismatch. By explicitly declaring JavaPoet 1.13.0, we ensure compatibility with Hilt 2.51.1.
