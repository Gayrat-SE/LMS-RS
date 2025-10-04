# 🔍 API Request Logger - Qo'llanma

## 📝 Umumiy Ma'lumot

API Request Logger webview ichidagi barcha HTTP requestlarni avtomatik ravishda log qiladi va endpoint'larni aniqlashga yordam beradi.

## ✨ Xususiyatlar

- ✅ Barcha HTTP requestlarni log qilish (XHR, Fetch, Axios, jQuery)
- ✅ Request details: URL, method, headers, body
- ✅ Unique endpoint'larni aniqlash
- ✅ Method bo'yicha statistika (GET, POST, PUT, DELETE, etc.)
- ✅ Endpoint bo'yicha statistika
- ✅ Log'larni export qilish (clipboard'ga copy)
- ✅ Real-time monitoring
- ✅ Visual UI bilan ko'rish

## 🚀 Qanday Ishlatish

### 1. Appni Ishga Tushirish

```bash
flutter run
```

### 2. WebView Ochilganda

App ochilganda avtomatik ravishda API Request Logger inject qilinadi va barcha requestlarni log qila boshlaydi.

### 3. Log'larni Ko'rish

Ekranning o'ng pastki burchagida **bug icon** (🐛) tugmasi paydo bo'ladi. Uni bosing va API Logger Viewer ochiladi.

### 4. Logger Viewer Tabs

#### 📋 Logs Tab
- Barcha API requestlarning to'liq ma'lumotlari
- Har bir request uchun:
  - HTTP Method (GET, POST, PUT, DELETE, etc.)
  - Full URL
  - Request Headers
  - Request Body (agar mavjud bo'lsa)
  - Timestamp

#### 🔗 Endpoints Tab
- Unique endpoint'larning ro'yxati
- Har bir endpoint uchun request soni
- Endpoint'ni clipboard'ga copy qilish

#### 📊 Methods Tab
- HTTP method'lar bo'yicha statistika
- Har bir method uchun request soni
- Color-coded display

#### 📈 Stats Tab
- Umumiy statistika:
  - Total Requests
  - Unique Endpoints
  - HTTP Methods count

## 🎯 Console'da Log'larni Ko'rish

### Chrome DevTools (Android)

1. Chrome'da oching: `chrome://inspect/#devices`
2. Qurilmangizni tanlang va "inspect" bosing
3. Console tab'da quyidagi log'larni ko'rasiz:

```
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
📡 API Request [Fetch]
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
🔹 Method: POST
🔹 URL: https://lms.rahimovschool.uz/api/login
🔹 Headers: {
  "Content-Type": "application/json",
  "X-FCM-Token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...",
  "FCM-Token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9..."
}
🔹 Body: {
  "username": "user@example.com",
  "password": "password123"
}
🔹 Time: 10:30:45 AM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
```

### Safari Web Inspector (iOS)

1. Safari > Preferences > Advanced > Show Develop menu
2. Develop > [Your Device] > [Your App]
3. Console tab'da yuqoridagi kabi log'larni ko'rasiz

## 💻 JavaScript Console Commands

WebView ichida quyidagi JavaScript funksiyalarni ishlatishingiz mumkin:

### Log'larni Olish
```javascript
// Barcha log'larni olish
const logs = window.getAPIRequestLogs();
console.log(logs);

// Unique endpoint'larni olish
const endpoints = window.getUniqueEndpoints();
console.log(endpoints);

// Method statistikasini olish
const methodStats = window.getRequestStatsByMethod();
console.log(methodStats);

// Endpoint statistikasini olish
const endpointStats = window.getRequestStatsByEndpoint();
console.log(endpointStats);
```

### Log'larni Tozalash
```javascript
window.clearAPIRequestLogs();
```

### Log'larni Export Qilish
```javascript
const jsonLogs = window.exportAPIRequestLogs();
console.log(jsonLogs);
```

## 📊 Endpoint'larni Aniqlash

### Avtomatik Aniqlash

Logger avtomatik ravishda barcha unique endpoint'larni aniqlaydi:

```javascript
// Misol natija:
[
  "https://lms.rahimovschool.uz/api/login",
  "https://lms.rahimovschool.uz/api/user/profile",
  "https://lms.rahimovschool.uz/api/courses",
  "https://lms.rahimovschool.uz/api/lessons",
  "https://lms.rahimovschool.uz/api/assignments"
]
```

### Manual Aniqlash

Chrome DevTools Console'da:

```javascript
// Barcha log'larni ko'rish
window.getAPIRequestLogs().forEach(log => {
  console.log(`${log.method} ${log.url}`);
});

// Faqat POST requestlarni ko'rish
window.getAPIRequestLogs()
  .filter(log => log.method === 'POST')
  .forEach(log => console.log(log.url));

// Login bilan bog'liq endpoint'larni topish
window.getAPIRequestLogs()
  .filter(log => log.url.includes('login'))
  .forEach(log => console.log(log.url));
```

## 🔧 Logger Viewer Funksiyalari

### Refresh Button (🔄)
Log'larni yangilash va eng so'nggi ma'lumotlarni olish

### Export Button (📋)
Barcha log'larni JSON formatda clipboard'ga copy qilish

### Clear Button (🗑️)
Barcha log'larni tozalash

## 📱 Foydalanish Misoli

### 1. Login Endpoint'ni Aniqlash

1. Appni oching
2. Login sahifasiga o'ting
3. Login qiling
4. Bug icon (🐛) tugmasini bosing
5. "Logs" tab'da login request'ni toping
6. Request details'ni ko'ring:
   - URL: `https://lms.rahimovschool.uz/api/login`
   - Method: `POST`
   - Headers: Content-Type, X-FCM-Token, etc.
   - Body: username, password

### 2. Barcha Endpoint'larni Ko'rish

1. Bug icon (🐛) tugmasini bosing
2. "Endpoints" tab'ni tanlang
3. Barcha unique endpoint'larni ko'ring
4. Endpoint'ga bosing va clipboard'ga copy qiling

### 3. Statistikani Ko'rish

1. Bug icon (🐛) tugmasini bosing
2. "Stats" tab'ni tanlang
3. Umumiy statistikani ko'ring:
   - Jami request'lar soni
   - Unique endpoint'lar soni
   - HTTP method'lar soni

## 🐛 Debugging

### Log'lar Ko'rinmasa

1. **WebView initialized bo'lganligini tekshiring:**
```dart
debugPrint("WebView initialized: $_isControllerInitialized");
```

2. **Console'da tekshiring:**
```javascript
console.log('Logger initialized:', typeof window.getAPIRequestLogs);
```

3. **Flutter log'larni ko'ring:**
```bash
flutter logs | grep "API Request Logger"
```

### Endpoint'lar Topilmasa

1. **Request yuborilganligini tekshiring:**
```javascript
console.log('Total requests:', window.getAPIRequestLogs().length);
```

2. **URL format'ini tekshiring:**
```javascript
window.getAPIRequestLogs().forEach(log => {
  console.log('URL:', log.url);
});
```

## 📋 Log Format

Har bir log quyidagi ma'lumotlarni o'z ichiga oladi:

```json
{
  "type": "Fetch",
  "method": "POST",
  "url": "https://lms.rahimovschool.uz/api/login",
  "headers": {
    "Content-Type": "application/json",
    "X-FCM-Token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...",
    "FCM-Token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9..."
  },
  "body": {
    "username": "user@example.com",
    "password": "password123"
  },
  "timestamp": 1704067845000,
  "datetime": "2024-01-01T10:30:45.000Z"
}
```

## 🎨 UI Features

### Color Coding

- 🟢 **GET** - Green
- 🔵 **POST** - Blue
- 🟠 **PUT** - Orange
- 🔴 **DELETE** - Red
- 🟣 **PATCH** - Purple

### Icons

- 📥 **GET** - Download icon
- 📤 **POST** - Upload icon
- ✏️ **PUT** - Edit icon
- 🗑️ **DELETE** - Delete icon
- 🔄 **PATCH** - Update icon

## 💡 Maslahatlar

### 1. Endpoint'larni Tezda Topish

Login qiling va darhol bug icon'ni bosing. Eng so'nggi request'lar ro'yxatning yuqorisida bo'ladi.

### 2. Log'larni Export Qilish

Export button'ni bosing va log'larni text editor'da tahlil qiling.

### 3. Statistikadan Foydalanish

"Stats" tab'da eng ko'p ishlatiladigan endpoint'larni aniqlang.

### 4. Console'dan Foydalanish

Chrome DevTools'da JavaScript console'dan foydalanib, log'larni filter qiling va tahlil qiling.

## 🔐 Xavfsizlik

⚠️ **Muhim:** Log'larda sensitive ma'lumotlar (password, token, etc.) bo'lishi mumkin. Production build'da logger'ni o'chiring yoki faqat debug mode'da ishlating.

### Production'da O'chirish

```dart
// lib/flutter_flow/flutter_flow_inapp_web_view.dart
onLoadStop: (controller, url) async {
  // ...
  
  // Faqat debug mode'da logger'ni inject qilish
  if (kDebugMode) {
    await controller.evaluateJavascript(
      source: APIRequestLogger.getRequestLoggerJS(),
    );
    debugPrint("API Request Logger injected into WebView");
  }
  
  // ...
}
```

## 📚 Qo'shimcha Resurslar

- **FCM_TOKEN_INTEGRATION.md** - FCM token integration hujjati
- **HOW_TO_TEST.md** - Test qilish qo'llanmasi
- **IMPLEMENTATION_SUMMARY.md** - Amalga oshirish xulosasi

## 🎉 Xulosa

API Request Logger yordamida:
- ✅ Barcha API requestlarni ko'rishingiz mumkin
- ✅ Endpoint'larni tezda aniqlashingiz mumkin
- ✅ Request details'ni tahlil qilishingiz mumkin
- ✅ Statistikani ko'rishingiz mumkin
- ✅ Log'larni export qilishingiz mumkin

**Keyingi qadam:** Appni ishga tushiring va bug icon'ni bosing! 🐛

