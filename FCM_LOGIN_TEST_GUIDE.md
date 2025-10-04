# 🔐 FCM Token Login Integration - Test Qo'llanmasi

## 📋 Nima Qilindi

Login endpoint'ga FCM Token qo'shildi:
- ✅ Endpoint: `https://api.rahimovschool.uz/api/v1/auth/login/`
- ✅ Headers: `X-FCM-Token` va `FCM-Token`
- ✅ Token: Firebase Messaging'dan olingan real token

## 🚀 Test Qilish

### 1. Appni Ishga Tushirish

```bash
flutter run
```

### 2. Kutilayotgan Log'lar

App ochilganda console'da quyidagi log'larni ko'rishingiz kerak:

```
[Flutter] Initial FCM Token: eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...
[Flutter] FCM Token obtained: eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...
[Flutter] WebView finished loading: https://lms.rahimovschool.uz
[Flutter] ✅ FCM Token injected into WebView for LOGIN endpoint
[Flutter] 📱 Token: eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...
[Flutter] ✅ API Request Logger injected into WebView
[WebView Console] ✅ FCM Token injected into WebView: eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...
[WebView Console] ✅ FCM Token interceptors initialized for LOGIN endpoint
[WebView Console] 🚀 API Request Logger initialized
[WebView Console] ✅ API Request Logger ready
```

### 3. Login Qilish

1. WebView ichida login sahifasiga o'ting
2. Username va password kiriting
3. Login tugmasini bosing

### 4. Login Request Log'larini Ko'rish

Console'da quyidagi log'larni ko'rishingiz kerak:

```
[WebView Console] ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
[WebView Console] 📡 API Request [XMLHttpRequest]
[WebView Console] ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
[WebView Console] 🔹 Method: POST
[WebView Console] 🔹 URL: https://api.rahimovschool.uz/api/v1/auth/login/
[WebView Console] 🔹 Headers: {
  "Content-Type": "application/json",
  "X-FCM-Token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...",
  "FCM-Token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9..."
}
[WebView Console] 🔐 FCM Token Header: eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...
[WebView Console] 🔹 Body: {
  "username": "user@example.com",
  "password": "password123"
}
[WebView Console] 🔹 Time: 12:52:28
[WebView Console] ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
[WebView Console] 🔐 FCM Token added to LOGIN request: https://api.rahimovschool.uz/api/v1/auth/login/
[WebView Console] 📱 Token: eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...
```

### 5. API Logger Viewer'da Ko'rish

1. Bug icon (🐛) tugmasini bosing
2. **Logs** tab'da login request'ni toping
3. Request'ga bosing va details'ni ko'ring
4. Headers'da `X-FCM-Token` va `FCM-Token` ni ko'ring

## 🔍 Chrome DevTools'da Tekshirish

### 1. Chrome DevTools'ni Ochish

```
chrome://inspect/#devices
```

### 2. Console'da Tekshirish

```javascript
// FCM tokenni tekshirish
console.log('FCM Token:', window.fcmToken);

// Login request'ni topish
const loginRequest = window.getAPIRequestLogs()
  .find(log => log.url.includes('/auth/login'));

console.log('Login Request:', loginRequest);
console.log('Headers:', loginRequest.headers);
console.log('X-FCM-Token:', loginRequest.headers['X-FCM-Token']);
console.log('FCM-Token:', loginRequest.headers['FCM-Token']);
```

### 3. Network Tab'da Tekshirish

1. Network tab'ni oching
2. Login qiling
3. `auth/login` request'ni toping
4. Headers tab'ni oching
5. Request Headers'da quyidagilarni toping:
   - `X-FCM-Token: eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...`
   - `FCM-Token: eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...`

## 📊 Backend'da Tekshirish

### Node.js (Express)

```javascript
app.post('/api/v1/auth/login/', (req, res) => {
  const fcmToken = req.headers['x-fcm-token'] || req.headers['fcm-token'];
  
  console.log('=== Login Request ===');
  console.log('FCM Token:', fcmToken);
  console.log('Body:', req.body);
  console.log('All Headers:', req.headers);
  
  if (fcmToken) {
    console.log('✅ FCM Token received:', fcmToken);
    // Database'ga saqlash
    // await saveUserFCMToken(userId, fcmToken);
  } else {
    console.log('❌ FCM Token NOT found in headers');
  }
  
  // Login logic...
});
```

### PHP

```php
<?php
$fcmToken = $_SERVER['HTTP_X_FCM_TOKEN'] ?? $_SERVER['HTTP_FCM_TOKEN'] ?? null;

error_log('=== Login Request ===');
error_log('FCM Token: ' . $fcmToken);
error_log('POST Data: ' . print_r($_POST, true));
error_log('All Headers: ' . print_r(getallheaders(), true));

if ($fcmToken) {
    error_log('✅ FCM Token received: ' . $fcmToken);
    // Database'ga saqlash
} else {
    error_log('❌ FCM Token NOT found in headers');
}

// Login logic...
?>
```

### Python (Django)

```python
def login_view(request):
    fcm_token = request.META.get('HTTP_X_FCM_TOKEN') or request.META.get('HTTP_FCM_TOKEN')
    
    print('=== Login Request ===')
    print(f'FCM Token: {fcm_token}')
    print(f'POST Data: {request.POST}')
    print(f'All Headers: {request.META}')
    
    if fcm_token:
        print(f'✅ FCM Token received: {fcm_token}')
        # Database'ga saqlash
    else:
        print('❌ FCM Token NOT found in headers')
    
    # Login logic...
```

## ✅ Checklist

### App Side
- [ ] App ishga tushdi
- [ ] WebView yuklandi
- [ ] FCM Token olinadi
- [ ] FCM Token inject qilindi
- [ ] API Logger inject qilindi
- [ ] Login qilindi
- [ ] Login request yuborildi

### Console Logs
- [ ] `✅ FCM Token injected into WebView for LOGIN endpoint`
- [ ] `📱 Token: [token]`
- [ ] `✅ API Request Logger injected into WebView`
- [ ] `🔐 FCM Token added to LOGIN request`
- [ ] `🔐 FCM Token Header: [token]`

### Headers
- [ ] `X-FCM-Token` header mavjud
- [ ] `FCM-Token` header mavjud
- [ ] Token qiymati to'g'ri
- [ ] Token Firebase'dan olingan

### Backend
- [ ] Backend FCM tokenni qabul qildi
- [ ] Token log'larda ko'rinadi
- [ ] Token database'ga saqlandi

## 🐛 Muammolarni Hal Qilish

### Token null bo'lsa:

```bash
# Firebase sozlamalarini tekshiring
cat android/app/google-services.json
cat ios/Runner/GoogleService-Info.plist

# Permissions'ni tekshiring
adb shell dumpsys package com.mycompany.lmsrs | grep permission
```

### Token header'da ko'rinmasa:

```javascript
// Chrome DevTools Console'da
console.log('FCM Token:', window.fcmToken);
console.log('Interceptor:', typeof XMLHttpRequest.prototype.send);

// Login request'ni manual test qilish
const xhr = new XMLHttpRequest();
xhr.open('POST', 'https://api.rahimovschool.uz/api/v1/auth/login/');
xhr.setRequestHeader('Content-Type', 'application/json');
xhr.send(JSON.stringify({username: 'test', password: 'test'}));
```

### Backend'da token qabul qilinmasa:

1. **Header nomlarini tekshiring:**
   - `X-FCM-Token` (case-sensitive)
   - `FCM-Token` (case-sensitive)

2. **CORS sozlamalarini tekshiring:**
```javascript
// Node.js
app.use((req, res, next) => {
  res.header('Access-Control-Allow-Headers', 'X-FCM-Token, FCM-Token, Content-Type');
  next();
});
```

3. **Backend log'larini ko'ring:**
```bash
# Node.js
console.log('All headers:', req.headers);

# PHP
error_log(print_r(getallheaders(), true));

# Python
print(request.META)
```

## 📱 Real Device'da Test

### Android

```bash
# App'ni install qiling
flutter run --release

# Log'larni ko'ring
adb logcat | grep -E "FCM Token|WebView Console"
```

### iOS

```bash
# App'ni install qiling
flutter run --release

# Xcode'da log'larni ko'ring
# Xcode > Window > Devices and Simulators > [Your Device] > Open Console
```

## 🎯 Kutilayotgan Natija

### Console Output:

```
✅ FCM Token injected into WebView for LOGIN endpoint
📱 Token: eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...
✅ API Request Logger injected into WebView
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
📡 API Request [XMLHttpRequest]
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
🔹 Method: POST
🔹 URL: https://api.rahimovschool.uz/api/v1/auth/login/
🔹 Headers: {
  "Content-Type": "application/json",
  "X-FCM-Token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...",
  "FCM-Token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9..."
}
🔐 FCM Token Header: eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...
🔹 Body: {"username": "user@example.com", "password": "password123"}
🔹 Time: 12:52:28
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
🔐 FCM Token added to LOGIN request: https://api.rahimovschool.uz/api/v1/auth/login/
📱 Token: eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Network Request:

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

## 🎉 Muvaffaqiyat!

Agar barcha checklist'lar ✅ bo'lsa, FCM Token muvaffaqiyatli login request'ga qo'shildi!

**Keyingi qadam:** Backend'da tokenni qabul qiling va database'ga saqlang.

