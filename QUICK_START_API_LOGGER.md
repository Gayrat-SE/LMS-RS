# 🚀 API Logger - Tezkor Boshlash

## 1️⃣ Appni Ishga Tushirish

```bash
flutter run
```

## 2️⃣ WebView Ochilishini Kutish

App ochilganda avtomatik ravishda:
- ✅ FCM Token inject qilinadi
- ✅ API Request Logger inject qilinadi

Console'da quyidagi log'larni ko'rishingiz kerak:

```
[Flutter] FCM Token injected into WebView
[Flutter] API Request Logger injected into WebView
[WebView Console] 🚀 API Request Logger initialized
[WebView Console] ✅ API Request Logger ready
```

## 3️⃣ Login Qilish

1. WebView ichida login sahifasiga o'ting
2. Username va password kiriting
3. Login tugmasini bosing

## 4️⃣ Log'larni Ko'rish

### Variant 1: Flutter App'da Ko'rish (Recommended)

1. Ekranning o'ng pastki burchagida **bug icon** (🐛) tugmasini bosing
2. API Logger Viewer ochiladi
3. 4 ta tab mavjud:
   - **Logs** - Barcha request'lar
   - **Endpoints** - Unique endpoint'lar
   - **Methods** - HTTP method statistikasi
   - **Stats** - Umumiy statistika

### Variant 2: Chrome DevTools'da Ko'rish (Android)

1. Chrome'da oching: `chrome://inspect/#devices`
2. Qurilmangizni tanlang va "inspect" bosing
3. Console tab'da log'larni ko'ring

### Variant 3: Safari Web Inspector'da Ko'rish (iOS)

1. Safari > Develop > [Your Device] > [Your App]
2. Console tab'da log'larni ko'ring

## 5️⃣ Endpoint'larni Aniqlash

### Flutter App'da:

1. Bug icon (🐛) tugmasini bosing
2. **Endpoints** tab'ni tanlang
3. Barcha endpoint'larni ko'ring
4. Endpoint'ga bosing va clipboard'ga copy qiling

### Chrome DevTools Console'da:

```javascript
// Barcha endpoint'larni ko'rish
console.log(window.getUniqueEndpoints());

// Natija:
// [
//   "https://lms.rahimovschool.uz/api/login",
//   "https://lms.rahimovschool.uz/api/user/profile",
//   "https://lms.rahimovschool.uz/api/courses"
// ]
```

## 6️⃣ Login Endpoint'ni Topish

### Flutter App'da:

1. Bug icon (🐛) tugmasini bosing
2. **Logs** tab'da login request'ni toping
3. Request'ga bosing va details'ni ko'ring:
   - URL
   - Method (POST)
   - Headers (X-FCM-Token, FCM-Token, etc.)
   - Body (username, password)

### Chrome DevTools Console'da:

```javascript
// Login request'ni topish
const loginRequest = window.getAPIRequestLogs()
  .find(log => log.url.includes('login'));

console.log('Login Endpoint:', loginRequest.url);
console.log('Method:', loginRequest.method);
console.log('Headers:', loginRequest.headers);
console.log('Body:', loginRequest.body);
```

## 7️⃣ Log'larni Export Qilish

### Flutter App'da:

1. Bug icon (🐛) tugmasini bosing
2. O'ng yuqori burchakda **copy icon** (📋) tugmasini bosing
3. Log'lar clipboard'ga copy qilinadi
4. Text editor'da paste qiling

### Chrome DevTools Console'da:

```javascript
// JSON formatda export qilish
const jsonLogs = window.exportAPIRequestLogs();
console.log(jsonLogs);

// Copy qilish uchun
copy(jsonLogs);
```

## 8️⃣ Statistikani Ko'rish

### Flutter App'da:

1. Bug icon (🐛) tugmasini bosing
2. **Stats** tab'ni tanlang
3. Ko'ring:
   - Total Requests
   - Unique Endpoints
   - HTTP Methods

### Chrome DevTools Console'da:

```javascript
// Method statistikasi
console.log(window.getRequestStatsByMethod());
// Natija: { GET: 10, POST: 5, PUT: 2, DELETE: 1 }

// Endpoint statistikasi
console.log(window.getRequestStatsByEndpoint());
// Natija: { "https://lms.rahimovschool.uz/api/login": 3, ... }
```

## 🎯 Tez-tez So'raladigan Savollar

### Q: Bug icon ko'rinmayapti?
**A:** WebView initialized bo'lishini kutib turing. Bir necha soniya keyin paydo bo'ladi.

### Q: Log'lar bo'sh?
**A:** Biror request yuborilganligini tekshiring. Login qiling yoki sahifani refresh qiling.

### Q: Endpoint'lar topilmayapti?
**A:** Console'da `window.getAPIRequestLogs()` ni bajaring va natijani tekshiring.

### Q: FCM Token header'da ko'rinmayapti?
**A:** Console'da `window.fcmToken` ni tekshiring. Agar null bo'lsa, Firebase sozlamalarini tekshiring.

## 🐛 Debugging

### Log'larni Tekshirish

```bash
# Flutter logs
flutter logs | grep -E "API Request Logger|FCM Token"

# Android logs
adb logcat | grep -E "WebView Console|API Request"
```

### Console'da Tekshirish

```javascript
// Logger initialized bo'lganligini tekshirish
console.log('Logger functions:', {
  getAPIRequestLogs: typeof window.getAPIRequestLogs,
  getUniqueEndpoints: typeof window.getUniqueEndpoints,
  clearAPIRequestLogs: typeof window.clearAPIRequestLogs
});

// Log'lar sonini tekshirish
console.log('Total logs:', window.getAPIRequestLogs().length);

// Endpoint'lar sonini tekshirish
console.log('Total endpoints:', window.getUniqueEndpoints().length);
```

## 📊 Natija Misoli

### Console Output:

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

### Endpoints List:

```javascript
[
  "https://lms.rahimovschool.uz/api/login",
  "https://lms.rahimovschool.uz/api/user/profile",
  "https://lms.rahimovschool.uz/api/courses",
  "https://lms.rahimovschool.uz/api/lessons",
  "https://lms.rahimovschool.uz/api/assignments",
  "https://lms.rahimovschool.uz/api/notifications"
]
```

## ✅ Checklist

- [ ] App ishga tushdi
- [ ] WebView yuklandi
- [ ] FCM Token inject qilindi
- [ ] API Logger inject qilindi
- [ ] Login qilindi
- [ ] Bug icon (🐛) ko'rindi
- [ ] Log'lar ko'rindi
- [ ] Endpoint'lar aniqlandi
- [ ] FCM Token header'da mavjud

## 🎉 Tayyor!

Endi siz:
- ✅ Barcha API requestlarni ko'rishingiz mumkin
- ✅ Endpoint'larni aniqlashingiz mumkin
- ✅ Request details'ni tahlil qilishingiz mumkin
- ✅ FCM Token header'da yuborilayotganini ko'rishingiz mumkin

## 📚 Qo'shimcha Ma'lumot

Batafsil ma'lumot uchun:
- **API_LOGGER_GUIDE.md** - To'liq qo'llanma
- **FCM_TOKEN_INTEGRATION.md** - FCM token integration
- **HOW_TO_TEST.md** - Test qilish qo'llanmasi

