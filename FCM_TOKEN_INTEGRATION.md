# FCM Token WebView Integration

## Umumiy ma'lumot

Bu integratsiya Firebase Cloud Messaging (FCM) tokenni webview ichidagi login requestlarga avtomatik ravishda qo'shadi.

## Qanday ishlaydi?

### 1. FCM Token olish
- App ochilganda `main.dart` faylida FCM token olinadi
- Token cache'da saqlanadi va kerak bo'lganda qayta ishlatiladi
- Token yangilanganda avtomatik ravishda yangi token olinadi

### 2. WebView ichiga token inject qilish
- WebView sahifa yuklanganda (`onLoadStop` event) FCM token JavaScript orqali inject qilinadi
- Token quyidagi joylarda saqlanadi:
  - `window.fcmToken` - global o'zgaruvchi
  - `localStorage.setItem('fcm_token', token)` - localStorage
  - `sessionStorage.setItem('fcm_token', token)` - sessionStorage

### 3. HTTP Request Interceptor
JavaScript interceptor'lar quyidagi API'larni qo'llab-quvvatlaydi:

#### XMLHttpRequest (XHR)
```javascript
// Barcha XHR requestlarga avtomatik FCM token qo'shiladi
const xhr = new XMLHttpRequest();
xhr.open('POST', '/api/login');
xhr.send(data);
// Headers: X-FCM-Token va FCM-Token avtomatik qo'shiladi
```

#### Fetch API
```javascript
// Barcha fetch requestlarga avtomatik FCM token qo'shiladi
fetch('/api/login', {
  method: 'POST',
  body: JSON.stringify(data)
});
// Headers: X-FCM-Token va FCM-Token avtomatik qo'shiladi
```

#### Axios (agar ishlatilsa)
```javascript
// Barcha axios requestlarga avtomatik FCM token qo'shiladi
axios.post('/api/login', data);
// Headers: X-FCM-Token va FCM-Token avtomatik qo'shiladi
```

## Header nomlari

FCM token quyidagi header nomlari bilan yuboriladi:
- `X-FCM-Token` - asosiy header
- `FCM-Token` - alternative header

Backend'da ikkala header'ni ham qo'llab-quvvatlash tavsiya etiladi.

## Backend'da qabul qilish

### Node.js (Express) misoli:
```javascript
app.post('/api/login', (req, res) => {
  const fcmToken = req.headers['x-fcm-token'] || req.headers['fcm-token'];
  console.log('FCM Token:', fcmToken);
  
  // Login logic...
  // FCM tokenni database'ga saqlash
});
```

### PHP misoli:
```php
$fcmToken = $_SERVER['HTTP_X_FCM_TOKEN'] ?? $_SERVER['HTTP_FCM_TOKEN'] ?? null;
if ($fcmToken) {
    // FCM tokenni database'ga saqlash
    echo "FCM Token: " . $fcmToken;
}
```

### Python (Django) misoli:
```python
def login_view(request):
    fcm_token = request.META.get('HTTP_X_FCM_TOKEN') or request.META.get('HTTP_FCM_TOKEN')
    if fcm_token:
        # FCM tokenni database'ga saqlash
        print(f"FCM Token: {fcm_token}")
```

### Python (Flask) misoli:
```python
@app.route('/api/login', methods=['POST'])
def login():
    fcm_token = request.headers.get('X-FCM-Token') or request.headers.get('FCM-Token')
    if fcm_token:
        # FCM tokenni database'ga saqlash
        print(f"FCM Token: {fcm_token}")
```

## Foydalanilgan fayllar

### 1. `lib/flutter_flow/fcm_token_helper.dart`
- FCM token bilan ishlash uchun helper class
- Token olish, cache qilish va JavaScript injection funksiyalari

### 2. `lib/flutter_flow/flutter_flow_inapp_web_view.dart`
- WebView widget
- Token injection logic qo'shilgan

### 3. `lib/main.dart`
- App initialization
- FCM token refresh listener sozlangan

## Debugging

### Console log'larni ko'rish

#### Android:
```bash
# Chrome DevTools orqali
chrome://inspect/#devices

# yoki adb logcat orqali
adb logcat | grep "WebView Console"
```

#### iOS:
```bash
# Safari Web Inspector orqali
Safari > Develop > [Your Device] > [Your App]
```

### Log messages:
- `FCM Token obtained: [token]` - Token muvaffaqiyatli olindi
- `FCM Token injected into WebView` - Token webview'ga inject qilindi
- `FCM Token added to XHR request: [method] [url]` - Token XHR requestga qo'shildi
- `FCM Token added to fetch request: [url]` - Token fetch requestga qo'shildi

## Xavfsizlik

⚠️ **Muhim:** FCM token sensitive ma'lumot hisoblanadi. Quyidagilarga e'tibor bering:

1. **HTTPS ishlatish** - Barcha API requestlar HTTPS orqali bo'lishi kerak
2. **Token validation** - Backend'da tokenni validate qiling
3. **Token storage** - Database'da tokenni xavfsiz saqlang
4. **Token expiration** - Eski tokenlarni o'chiring

## Muammolarni hal qilish

### Token null bo'lsa:
1. Firebase configuration to'g'ri sozlanganligini tekshiring
2. `google-services.json` (Android) va `GoogleService-Info.plist` (iOS) fayllarini tekshiring
3. Internet ulanishini tekshiring
4. Firebase Console'da app ro'yxatdan o'tganligini tekshiring

### Token header'da ko'rinmasa:
1. Browser console'da log'larni tekshiring
2. Network tab'da request headers'ni ko'ring
3. JavaScript injection muvaffaqiyatli bo'lganligini tekshiring

### Backend'da token qabul qilinmasa:
1. Header nomlarini to'g'ri yozganligingizni tekshiring (case-sensitive)
2. CORS sozlamalarini tekshiring
3. Backend log'larini ko'ring

## Qo'shimcha sozlamalar

### Faqat login requestlarga token qo'shish

Agar barcha requestlarga emas, faqat login requestlarga token qo'shmoqchi bo'lsangiz, `fcm_token_helper.dart` faylida `getTokenInjectionJS()` metodini ishlating:

```dart
// flutter_flow_inapp_web_view.dart faylida
await controller.evaluateJavascript(
  source: FCMTokenHelper.getTokenInjectionJS(fcmToken), // getUniversalTokenInjectionJS o'rniga
);
```

Bu faqat URL'da `/login`, `/auth`, yoki `/signin` bo'lgan requestlarga token qo'shadi.

## Test qilish

1. Appni ishga tushiring
2. Console log'larni kuzating
3. Login sahifasiga o'ting
4. Browser DevTools'da Network tab'ni oching
5. Login request'ni tanlang va Headers'ni ko'ring
6. `X-FCM-Token` yoki `FCM-Token` header'ni toping

## Qo'llab-quvvatlash

Muammo yuzaga kelsa:
1. Console log'larni tekshiring
2. Network request headers'ni ko'ring
3. Firebase Console'da tokenni tekshiring
4. Backend log'larini ko'ring

