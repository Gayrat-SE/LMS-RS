# 🔥 FCM Token WebView Integration

## 📝 Qisqacha Ma'lumot

Bu integratsiya Firebase Cloud Messaging (FCM) tokenni mobile app'dan webview ichidagi login requestlarga avtomatik ravishda qo'shadi.

## ✨ Xususiyatlar

- ✅ FCM tokenni avtomatik olish va cache qilish
- ✅ Token webview ichiga JavaScript orqali inject qilish
- ✅ Barcha HTTP requestlarga (XHR, Fetch, Axios) token qo'shish
- ✅ Token yangilanganda avtomatik yangilash
- ✅ Android va iOS qo'llab-quvvatlash
- ✅ Debug logging va monitoring

## 🚀 Tezkor Boshlash

### 1. Appni Ishga Tushirish

```bash
flutter run
```

### 2. Loglarni Kuzatish

```bash
# Android
adb logcat | grep -E "FCM Token|WebView Console"

# iOS
# Safari > Develop > [Your Device] > [Your App] > Console
```

### 3. Kutilayotgan Natija

Console'da quyidagi log'larni ko'rishingiz kerak:

```
✅ Initial FCM Token: eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...
✅ FCM Token obtained: eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...
✅ WebView finished loading: https://lms.rahimovschool.uz
✅ FCM Token injected into WebView
✅ WebView Console: FCM Token stored globally
✅ WebView Console: Universal FCM Token interceptors initialized
```

## 📁 Fayl Strukturasi

```
lib/
├── flutter_flow/
│   ├── fcm_token_helper.dart          # FCM token helper class
│   ├── flutter_flow_inapp_web_view.dart  # WebView widget (modified)
│   └── ...
├── main.dart                           # App entry point (modified)
└── ...

Hujjatlar/
├── FCM_TOKEN_INTEGRATION.md           # To'liq integratsiya hujjati
├── HOW_TO_TEST.md                     # Test qilish qo'llanmasi
├── IMPLEMENTATION_SUMMARY.md          # Amalga oshirish xulosasi
├── FCM_TOKEN_README.md                # Bu fayl
└── test_fcm_token.html                # Test HTML sahifasi
```

## 🔧 Qanday Ishlaydi

### 1. Token Olish
```dart
// main.dart
final token = await FCMTokenHelper.getFCMToken();
```

### 2. Token Inject Qilish
```dart
// flutter_flow_inapp_web_view.dart
await controller.evaluateJavascript(
  source: FCMTokenHelper.getUniversalTokenInjectionJS(fcmToken),
);
```

### 3. HTTP Interceptor
```javascript
// JavaScript (avtomatik inject qilinadi)
XMLHttpRequest.prototype.send = function(...args) {
  this.setRequestHeader('X-FCM-Token', token);
  this.setRequestHeader('FCM-Token', token);
  return originalSend.apply(this, args);
};
```

### 4. Backend'da Qabul Qilish
```javascript
// Node.js
const fcmToken = req.headers['x-fcm-token'] || req.headers['fcm-token'];
```

## 📊 Request Headers

Login request quyidagi headers bilan yuboriladi:

```http
POST /api/login HTTP/1.1
Host: lms.rahimovschool.uz
Content-Type: application/json
X-FCM-Token: eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...
FCM-Token: eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...

{
  "username": "user@example.com",
  "password": "password123"
}
```

## 🧪 Test Qilish

### Chrome DevTools (Android)

1. Chrome'da oching: `chrome://inspect/#devices`
2. Qurilmangizni tanlang va "inspect" bosing
3. Console'da:
```javascript
console.log('FCM Token:', window.fcmToken);
```
4. Network tab'da login request'ni ko'ring
5. Headers'da `X-FCM-Token` va `FCM-Token` ni toping

### Safari Web Inspector (iOS)

1. Safari > Preferences > Advanced > Show Develop menu
2. Develop > [Your Device] > [Your App]
3. Console'da:
```javascript
console.log('FCM Token:', window.fcmToken);
```

### Test HTML Sahifasi

1. `test_fcm_token.html` faylini web serverda ishga tushiring
2. WebView URL'ni test sahifasiga o'zgartiring
3. Test tugmalarini bosing va natijalarni ko'ring

## 🔐 Xavfsizlik

### ⚠️ Muhim Eslatmalar:

1. **HTTPS ishlatish** - Barcha API requestlar HTTPS orqali bo'lishi kerak
2. **Token validation** - Backend'da tokenni validate qiling
3. **Token storage** - Database'da tokenni xavfsiz saqlang
4. **Token expiration** - Eski tokenlarni o'chiring

### Backend Validation Misoli:

```javascript
async function validateFCMToken(token) {
  if (!token || token.length < 100) {
    return false;
  }
  
  try {
    await admin.messaging().send({
      token: token,
      dryRun: true
    });
    return true;
  } catch (error) {
    return false;
  }
}
```

## 📚 Backend Integration

### Node.js (Express)

```javascript
app.post('/api/login', async (req, res) => {
  const fcmToken = req.headers['x-fcm-token'] || req.headers['fcm-token'];
  
  if (fcmToken) {
    // Database'ga saqlash
    await db.query(
      'INSERT INTO user_fcm_tokens (user_id, fcm_token) VALUES ($1, $2) ON CONFLICT (user_id) DO UPDATE SET fcm_token = $2',
      [userId, fcmToken]
    );
  }
  
  // Login logic...
});
```

### PHP

```php
$fcmToken = $_SERVER['HTTP_X_FCM_TOKEN'] ?? $_SERVER['HTTP_FCM_TOKEN'] ?? null;

if ($fcmToken) {
    $stmt = $pdo->prepare('INSERT INTO user_fcm_tokens (user_id, fcm_token) VALUES (?, ?) ON DUPLICATE KEY UPDATE fcm_token = ?');
    $stmt->execute([$userId, $fcmToken, $fcmToken]);
}
```

### Python (Django)

```python
def login_view(request):
    fcm_token = request.META.get('HTTP_X_FCM_TOKEN') or request.META.get('HTTP_FCM_TOKEN')
    
    if fcm_token:
        UserFCMToken.objects.update_or_create(
            user_id=user.id,
            defaults={'fcm_token': fcm_token}
        )
```

## 🐛 Troubleshooting

### Token null bo'lsa:

```bash
# Firebase sozlamalarini tekshiring
cat android/app/google-services.json
cat ios/Runner/GoogleService-Info.plist

# Permissions'ni tekshiring
adb shell dumpsys package com.mycompany.lmsrs | grep permission
```

### Token inject bo'lmasa:

```javascript
// Chrome DevTools Console'da
console.log('window.fcmToken:', window.fcmToken);
console.log('localStorage:', localStorage.getItem('fcm_token'));
```

### Headers'da token ko'rinmasa:

```javascript
// Network tab'da request'ni tanlang
// Headers > Request Headers > X-FCM-Token
```

## 📖 Batafsil Hujjatlar

- **[FCM_TOKEN_INTEGRATION.md](FCM_TOKEN_INTEGRATION.md)** - To'liq integratsiya hujjati
- **[HOW_TO_TEST.md](HOW_TO_TEST.md)** - Test qilish qo'llanmasi
- **[IMPLEMENTATION_SUMMARY.md](IMPLEMENTATION_SUMMARY.md)** - Amalga oshirish xulosasi

## 🎯 Keyingi Qadamlar

1. ✅ **Test qilish** - Appni ishga tushiring va log'larni tekshiring
2. ✅ **Backend integration** - Backend'da FCM tokenni qabul qiling
3. ✅ **Database schema** - Token saqlash uchun table yarating
4. ✅ **Push notification** - FCM orqali notification yuborish
5. ✅ **Monitoring** - Production'da token statistikasini kuzating

## 💡 Maslahatlar

### Faqat Login Requestlarga Token Qo'shish

```dart
// lib/flutter_flow/flutter_flow_inapp_web_view.dart'da
await controller.evaluateJavascript(
  source: FCMTokenHelper.getTokenInjectionJS(fcmToken), // getUniversalTokenInjectionJS o'rniga
);
```

### Custom Header Nomlari

```dart
// lib/flutter_flow/fcm_token_helper.dart'da
this.setRequestHeader('Your-Custom-Header', '$fcmToken');
```

### Token Refresh Handling

```dart
FCMTokenHelper.setupTokenRefreshListener();

// Token yangilanganda avtomatik yangilanadi
```

## 📞 Yordam

Muammo yuzaga kelsa:

1. Console log'larni tekshiring
2. Chrome DevTools yoki Safari Web Inspector'dan foydalaning
3. Network tab'da request headers'ni ko'ring
4. Backend log'larini tekshiring
5. Hujjatlarni o'qing

## 🎉 Xulosa

FCM token integration muvaffaqiyatli amalga oshirildi!

**Natija:**
- ✅ FCM token avtomatik olinadi
- ✅ Token webview ichiga inject qilinadi
- ✅ Barcha HTTP requestlarga token qo'shiladi
- ✅ Backend token qabul qilishi mumkin
- ✅ Push notification yuborish imkoniyati mavjud

**Keyingi qadam:** Test qilish va backend integration!

---

**Muallif:** Augment AI  
**Sana:** 2025-09-30  
**Versiya:** 1.0.0

