# FCM Token Integration - Test Qilish Bo'yicha Qo'llanma

## 1. Tezkor Test (Recommended)

### Android uchun:

1. **Appni ishga tushiring:**
```bash
flutter run
```

2. **Loglarni kuzating:**
```bash
# Terminal 1 - Flutter logs
flutter run

# Terminal 2 - Android logs
adb logcat | grep -E "FCM Token|WebView Console"
```

3. **Kutilayotgan loglar:**
```
Initial FCM Token: [your-token-here]
FCM Token obtained: [your-token-here]
WebView finished loading: https://lms.rahimovschool.uz
FCM Token injected into WebView
WebView Console: FCM Token stored globally: [your-token-here]
WebView Console: Universal FCM Token interceptors initialized
```

### iOS uchun:

1. **Appni ishga tushiring:**
```bash
flutter run
```

2. **Safari Web Inspector orqali console'ni oching:**
   - Safari > Preferences > Advanced > Show Develop menu
   - Develop > [Your Device] > [Your App]
   - Console tab'ni oching

3. **Console'da quyidagilarni ko'ring:**
```javascript
FCM Token stored globally: [your-token-here]
Universal FCM Token interceptors initialized
```

## 2. Chrome DevTools orqali Test (Android)

1. **Chrome'da quyidagi URL'ni oching:**
```
chrome://inspect/#devices
```

2. **Qurilmangizni tanlang va "inspect" tugmasini bosing**

3. **Console tab'da quyidagi kodni bajaring:**
```javascript
// FCM tokenni tekshirish
console.log('FCM Token:', window.fcmToken);
console.log('localStorage:', localStorage.getItem('fcm_token'));
console.log('sessionStorage:', sessionStorage.getItem('fcm_token'));
```

4. **Network tab'ni oching va login qiling**

5. **Login request'ni tanlang va Headers tab'ni oching**

6. **Request Headers'da quyidagilarni toping:**
```
X-FCM-Token: [your-token-here]
FCM-Token: [your-token-here]
```

## 3. Test HTML Sahifasi orqali Test

### Lokal test uchun:

1. **Test HTML faylini web serverda ishga tushiring:**
```bash
# Python 3
cd /path/to/project
python3 -m http.server 8000

# yoki Node.js
npx http-server -p 8000
```

2. **WebView URL'ni o'zgartiring:**

`lib/pages/home_page/home_page_widget.dart` faylida:
```dart
content: 'http://10.0.2.2:8000/test_fcm_token.html', // Android emulator
// yoki
content: 'http://localhost:8000/test_fcm_token.html', // iOS simulator
// yoki
content: 'http://YOUR_LOCAL_IP:8000/test_fcm_token.html', // Real device
```

3. **Appni qayta ishga tushiring va test tugmalarini bosing**

## 4. Real Backend bilan Test

### Backend'da FCM tokenni qabul qilish:

#### Node.js (Express):
```javascript
const express = require('express');
const app = express();

app.use(express.json());

app.post('/api/login', (req, res) => {
  const fcmToken = req.headers['x-fcm-token'] || req.headers['fcm-token'];
  
  console.log('=== Login Request ===');
  console.log('FCM Token:', fcmToken);
  console.log('Body:', req.body);
  console.log('All Headers:', req.headers);
  
  if (fcmToken) {
    // Database'ga saqlash
    // saveTokenToDatabase(req.body.userId, fcmToken);
    
    res.json({
      success: true,
      message: 'FCM Token received',
      token: fcmToken
    });
  } else {
    res.status(400).json({
      success: false,
      message: 'FCM Token not found in headers'
    });
  }
});

app.listen(3000, () => {
  console.log('Server running on port 3000');
});
```

#### PHP:
```php
<?php
header('Content-Type: application/json');

$fcmToken = $_SERVER['HTTP_X_FCM_TOKEN'] ?? $_SERVER['HTTP_FCM_TOKEN'] ?? null;

error_log('=== Login Request ===');
error_log('FCM Token: ' . $fcmToken);
error_log('POST Data: ' . print_r($_POST, true));
error_log('All Headers: ' . print_r(getallheaders(), true));

if ($fcmToken) {
    // Database'ga saqlash
    // saveTokenToDatabase($_POST['userId'], $fcmToken);
    
    echo json_encode([
        'success' => true,
        'message' => 'FCM Token received',
        'token' => $fcmToken
    ]);
} else {
    http_response_code(400);
    echo json_encode([
        'success' => false,
        'message' => 'FCM Token not found in headers'
    ]);
}
?>
```

## 5. Muammolarni Aniqlash

### Token null bo'lsa:

1. **Firebase sozlamalarini tekshiring:**
```bash
# Android
cat android/app/google-services.json

# iOS
cat ios/Runner/GoogleService-Info.plist
```

2. **Firebase Console'da app'ni tekshiring:**
   - https://console.firebase.google.com/
   - Project Settings > General
   - Your apps > [Your App]

3. **Permissions'ni tekshiring:**
```bash
# Android
adb shell dumpsys package com.mycompany.lmsrs | grep permission

# iOS - Xcode'da
Product > Scheme > Edit Scheme > Run > Arguments > Environment Variables
```

### Token inject bo'lmasa:

1. **WebView console'ni tekshiring:**
```javascript
// Chrome DevTools Console'da
console.log('Interceptors:', {
  xhr: XMLHttpRequest.prototype.send.toString().includes('FCM'),
  fetch: window.fetch.toString().includes('FCM')
});
```

2. **JavaScript injection'ni manual test qiling:**
```dart
// home_page_widget.dart'da
await _controller?.evaluateJavascript(source: '''
  console.log('Manual test:', window.fcmToken);
  alert('FCM Token: ' + window.fcmToken);
''');
```

### Headers'da token ko'rinmasa:

1. **Network request'ni to'liq tekshiring:**
```javascript
// Chrome DevTools Console'da
const originalFetch = window.fetch;
window.fetch = function(...args) {
  console.log('Fetch called:', args);
  return originalFetch.apply(this, args);
};
```

2. **Backend CORS sozlamalarini tekshiring:**
```javascript
// Node.js
app.use((req, res, next) => {
  console.log('Request Headers:', req.headers);
  next();
});
```

## 6. Production'da Test

### Release build:

```bash
# Android
flutter build apk --release
flutter install

# iOS
flutter build ios --release
# Xcode orqali install qiling
```

### Loglarni ko'rish:

```bash
# Android
adb logcat -s flutter

# iOS
# Xcode > Window > Devices and Simulators > [Your Device] > Open Console
```

## 7. Automated Test

### Integration test yozish:

```dart
// test/integration_test/fcm_token_test.dart
import 'package:flutter_test/flutter_test.dart';
import 'package:integration_test/integration_test.dart';
import 'package:lms_rs/main.dart' as app;
import 'package:lms_rs/flutter_flow/fcm_token_helper.dart';

void main() {
  IntegrationTestWidgetsFlutterBinding.ensureInitialized();

  testWidgets('FCM Token should be available', (WidgetTester tester) async {
    app.main();
    await tester.pumpAndSettle();

    // FCM tokenni olish
    final token = await FCMTokenHelper.getFCMToken();
    
    expect(token, isNotNull);
    expect(token, isNotEmpty);
    
    print('FCM Token: $token');
  });
}
```

### Test'ni ishga tushirish:

```bash
flutter test integration_test/fcm_token_test.dart
```

## 8. Monitoring va Analytics

### Firebase Analytics bilan integratsiya:

```dart
// FCM token olinganda event yuborish
import 'package:firebase_analytics/firebase_analytics.dart';

final analytics = FirebaseAnalytics.instance;

final token = await FCMTokenHelper.getFCMToken();
if (token != null) {
  await analytics.logEvent(
    name: 'fcm_token_obtained',
    parameters: {
      'token_length': token.length,
      'timestamp': DateTime.now().toIso8601String(),
    },
  );
}
```

## 9. Troubleshooting Checklist

- [ ] Firebase initialized correctly
- [ ] FCM token obtained (check logs)
- [ ] WebView loaded successfully
- [ ] JavaScript injection successful
- [ ] Token visible in window.fcmToken
- [ ] Token visible in localStorage
- [ ] Interceptors initialized
- [ ] Login request sent
- [ ] Headers include X-FCM-Token
- [ ] Backend receives token
- [ ] Token saved to database

## 10. Qo'shimcha Resurslar

- [Firebase Cloud Messaging Documentation](https://firebase.google.com/docs/cloud-messaging)
- [Flutter InAppWebView Documentation](https://inappwebview.dev/)
- [Chrome DevTools Documentation](https://developer.chrome.com/docs/devtools/)
- [Safari Web Inspector Guide](https://developer.apple.com/safari/tools/)

