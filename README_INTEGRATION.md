# 🚀 FCM Token & API Logger Integration - README

## 📋 Qisqacha Ma'lumot

Bu loyihada 2 ta asosiy funksiya amalga oshirildi:

1. **FCM Token Integration** - Login request'ga Firebase Cloud Messaging tokenni avtomatik qo'shish
2. **API Request Logger** - WebView ichidagi barcha HTTP requestlarni log qilish va endpoint'larni aniqlash

## ✨ Xususiyatlar

### FCM Token Integration
- ✅ Firebase Messaging'dan token olish
- ✅ Token cache qilish va refresh
- ✅ Login endpoint'ga avtomatik token qo'shish
- ✅ Headers: `X-FCM-Token` va `FCM-Token`
- ✅ Console logging

### API Request Logger
- ✅ Barcha HTTP requestlarni log qilish (XHR, Fetch, Axios)
- ✅ Endpoint'larni avtomatik aniqlash
- ✅ Method va endpoint statistikasi
- ✅ Visual UI viewer (4 tabs)
- ✅ Export va clear funksiyalari
- ✅ Real-time monitoring

## 🚀 Tezkor Boshlash

### 1. Appni Ishga Tushirish

```bash
flutter run
```

### 2. Login Qilish

1. WebView ichida login sahifasiga o'ting
2. Username va password kiriting
3. Login tugmasini bosing

### 3. Log'larni Ko'rish

**Console'da:**
```
✅ FCM Token injected into WebView for LOGIN endpoint
📱 Token: eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...
🔐 FCM Token added to LOGIN request
```

**API Logger Viewer'da:**
1. Bug icon (🐛) tugmasini bosing
2. Logs tab'da login request'ni toping
3. Headers'da `X-FCM-Token` va `FCM-Token` ni ko'ring

## 📁 Fayl Strukturasi

```
lib/
├── flutter_flow/
│   ├── fcm_token_helper.dart          # FCM token helper
│   ├── api_request_logger.dart        # API logger
│   ├── api_logger_viewer.dart         # Visual UI viewer
│   ├── flutter_flow_inapp_web_view.dart  # WebView (modified)
│   └── ...
├── pages/
│   └── home_page/
│       └── home_page_widget.dart      # Home page (modified)
├── main.dart                           # App entry (modified)
└── ...

Hujjatlar/
├── FCM_TOKEN_INTEGRATION.md           # FCM token hujjati
├── FCM_TOKEN_README.md                # FCM qisqacha qo'llanma
├── FCM_LOGIN_TEST_GUIDE.md            # Login test qo'llanmasi
├── API_LOGGER_GUIDE.md                # API logger hujjati
├── QUICK_START_API_LOGGER.md          # API logger tezkor boshlash
├── API_LOGGER_SUMMARY.md              # API logger xulosasi
├── HOW_TO_TEST.md                     # Test qilish qo'llanmasi
├── IMPLEMENTATION_SUMMARY.md          # Amalga oshirish xulosasi
├── FINAL_SUMMARY.md                   # Yakuniy xulosa
└── README_INTEGRATION.md              # Bu fayl
```

## 🎯 Login Endpoint

Sizning log'laringizdan aniqlangan:

```
Endpoint: https://api.rahimovschool.uz/api/v1/auth/login/
Method: POST
Headers:
  - Content-Type: application/json
  - X-FCM-Token: [Firebase token]
  - FCM-Token: [Firebase token]
Body:
  - username
  - password
```

## 📊 Request Headers

Login request quyidagi headers bilan yuboriladi:

```http
POST /api/v1/auth/login/ HTTP/1.1
Host: api.rahimovschool.uz
Content-Type: application/json
X-FCM-Token: eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...
FCM-Token: eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...

{
  "username": "user@example.com",
  "password": "password123"
}
```

## 🔍 API Logger Viewer

### Bug Icon (🐛)
Ekranning o'ng pastki burchagida floating button

### 4 ta Tab:

1. **Logs** - Barcha request'lar
   - Method, URL, Headers, Body
   - Expandable details
   - Color-coded by method

2. **Endpoints** - Unique endpoint'lar
   - Request count
   - Tap to copy

3. **Methods** - HTTP method statistikasi
   - GET, POST, PUT, DELETE, etc.
   - Request count per method

4. **Stats** - Umumiy statistika
   - Total Requests
   - Unique Endpoints
   - HTTP Methods

### Actions:
- 🔄 **Refresh** - Log'larni yangilash
- 📋 **Export** - Clipboard'ga copy qilish
- 🗑️ **Clear** - Log'larni tozalash

## 💻 Chrome DevTools

### Console Commands:

```javascript
// FCM tokenni tekshirish
console.log('FCM Token:', window.fcmToken);

// Barcha log'larni olish
console.log(window.getAPIRequestLogs());

// Unique endpoint'larni olish
console.log(window.getUniqueEndpoints());

// Method statistikasi
console.log(window.getRequestStatsByMethod());

// Endpoint statistikasi
console.log(window.getRequestStatsByEndpoint());

// Log'larni tozalash
window.clearAPIRequestLogs();

// JSON formatda export
console.log(window.exportAPIRequestLogs());
```

## 🔧 Backend Integration

### Node.js (Express)

```javascript
app.post('/api/v1/auth/login/', async (req, res) => {
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

## 🐛 Debugging

### Console Log'lar

```bash
# Flutter logs
flutter logs | grep -E "FCM Token|API Request Logger"

# Android logs
adb logcat | grep -E "WebView Console|FCM Token"
```

### Chrome DevTools

```
chrome://inspect/#devices
```

### Safari Web Inspector

```
Safari > Develop > [Your Device] > [Your App]
```

## 📚 Hujjatlar

### Asosiy Hujjatlar:
- **FINAL_SUMMARY.md** - Yakuniy xulosa va barcha ma'lumotlar
- **FCM_LOGIN_TEST_GUIDE.md** - Login test qo'llanmasi
- **QUICK_START_API_LOGGER.md** - API logger tezkor boshlash

### Batafsil Hujjatlar:
- **FCM_TOKEN_INTEGRATION.md** - FCM token to'liq hujjat
- **API_LOGGER_GUIDE.md** - API logger to'liq hujjat
- **HOW_TO_TEST.md** - Test qilish qo'llanmasi
- **IMPLEMENTATION_SUMMARY.md** - Amalga oshirish xulosasi

## ✅ Test Checklist

- [ ] App ishga tushdi
- [ ] WebView yuklandi
- [ ] FCM Token olinadi
- [ ] Login qilindi
- [ ] Console'da log'lar ko'rinadi
- [ ] Bug icon (🐛) ko'rinadi
- [ ] API Logger Viewer ochiladi
- [ ] Login request'da FCM Token mavjud
- [ ] Backend FCM tokenni qabul qildi

## 🔐 Xavfsizlik

⚠️ **Muhim:** Production build'da logger'ni o'chiring!

```dart
import 'package:flutter/foundation.dart';

// Faqat debug mode'da
if (kDebugMode) {
  // FCM Token injection
  // API Logger injection
}
```

## 🎉 Natija

### ✅ Muvaffaqiyatli:
- FCM Token login request'ga qo'shiladi
- Barcha API requestlar log qilinadi
- Endpoint'lar avtomatik aniqlanadi
- Visual UI bilan ko'rish mumkin

### 📊 Statistika:
- Yaratilgan fayllar: 14
- O'zgartirilgan fayllar: 3
- Qo'llanmalar: 10
- Code lines: 2000+

## 🚀 Keyingi Qadamlar

1. **Test qiling** - Login qiling va log'larni tekshiring
2. **Backend integration** - FCM tokenni qabul qiling
3. **Database** - Token saqlash uchun table yarating
4. **Push notification** - FCM orqali notification yuborish
5. **Production** - Logger'ni o'chiring va build qiling

## 💡 Maslahatlar

1. **Chrome DevTools** - Network tab'da headers'ni ko'ring
2. **Console** - JavaScript console'dan foydalaning
3. **API Logger Viewer** - Visual UI'dan foydalaning
4. **Backend logs** - Backend'da ham log'larni tekshiring
5. **Test thoroughly** - Barcha funksiyalarni test qiling

## 📞 Yordam

Muammo yuzaga kelsa:

1. **Console log'larni tekshiring**
2. **Chrome DevTools'dan foydalaning**
3. **Hujjatlarni o'qing**
4. **Backend log'larini ko'ring**

## 🎊 Xulosa

Barcha funksiyalar tayyor va test qilishga tayyor!

**Endi:** Appni ishga tushiring va login qiling! 🚀

---

**Muallif:** Augment AI  
**Sana:** 2025-09-30  
**Versiya:** 2.0.0  
**Status:** ✅ Ready for Testing

**Keyingi qadam:** `flutter run` va login qiling!

