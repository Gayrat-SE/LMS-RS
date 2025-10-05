#!/bin/bash

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${BLUE}🔧 LMS-RS Microphone Permission Fix${NC}"
echo -e "${BLUE}=====================================${NC}"
echo ""

# Step 1: Clean Flutter
echo -e "${YELLOW}📦 Step 1: Cleaning Flutter...${NC}"
flutter clean
if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ Flutter clean completed${NC}"
else
    echo -e "${RED}❌ Flutter clean failed${NC}"
    exit 1
fi
echo ""

# Step 2: Remove Pods
echo -e "${YELLOW}🗑️  Step 2: Removing Pods...${NC}"
cd ios
rm -rf Pods
rm -rf Podfile.lock
rm -rf .symlinks
if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ Pods removed${NC}"
else
    echo -e "${RED}❌ Failed to remove Pods${NC}"
    exit 1
fi
echo ""

# Step 3: Update Podfile iOS version
echo -e "${YELLOW}📝 Step 3: Updating Podfile iOS version...${NC}"
sed -i '' "s/platform :ios, '15.0'/platform :ios, '15.0.0'/" Podfile
if grep -q "platform :ios, '15.0.0'" Podfile; then
    echo -e "${GREEN}✅ Podfile updated to iOS 15.0.0${NC}"
else
    echo -e "${YELLOW}⚠️  Podfile already has correct version or update failed${NC}"
fi
echo ""

# Step 4: Install Pods
echo -e "${YELLOW}📦 Step 4: Installing Pods...${NC}"
pod install --repo-update
if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ Pods installed successfully${NC}"
else
    echo -e "${RED}❌ Pod install failed${NC}"
    exit 1
fi
echo ""

# Step 5: Go back to root
cd ..

# Step 6: Flutter pub get
echo -e "${YELLOW}📦 Step 5: Running flutter pub get...${NC}"
flutter pub get
if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ Flutter pub get completed${NC}"
else
    echo -e "${RED}❌ Flutter pub get failed${NC}"
    exit 1
fi
echo ""

# Step 7: Verify configuration
echo -e "${BLUE}🔍 Step 6: Verifying configuration...${NC}"
echo ""

# Check Info.plist
if grep -q "NSMicrophoneUsageDescription" ios/Runner/Info.plist; then
    echo -e "${GREEN}✅ NSMicrophoneUsageDescription found in Info.plist${NC}"
else
    echo -e "${RED}❌ NSMicrophoneUsageDescription NOT found in Info.plist${NC}"
fi

# Check Podfile
if grep -q "PERMISSION_MICROPHONE=1" ios/Podfile; then
    echo -e "${GREEN}✅ PERMISSION_MICROPHONE=1 found in Podfile${NC}"
else
    echo -e "${RED}❌ PERMISSION_MICROPHONE=1 NOT found in Podfile${NC}"
fi

# Check permission_request_helper.dart
if [ -f "lib/flutter_flow/permission_request_helper.dart" ]; then
    echo -e "${GREEN}✅ permission_request_helper.dart exists${NC}"
else
    echo -e "${RED}❌ permission_request_helper.dart NOT found${NC}"
fi

# Check home_page_widget.dart
if grep -q "PermissionRequestHelper.requestMicrophonePermission" lib/pages/home_page/home_page_widget.dart; then
    echo -e "${GREEN}✅ Permission request found in HomePage${NC}"
else
    echo -e "${RED}❌ Permission request NOT found in HomePage${NC}"
fi

echo ""
echo -e "${BLUE}=====================================${NC}"
echo -e "${GREEN}✅ Fix completed!${NC}"
echo ""
echo -e "${YELLOW}📱 Next steps:${NC}"
echo -e "1. ${BLUE}Xcode'da Derived Data'ni tozalang:${NC}"
echo -e "   Xcode → Product → Clean Build Folder (Shift+Cmd+K)"
echo -e "   Xcode → Preferences → Locations → Derived Data → Delete"
echo ""
echo -e "2. ${BLUE}Device'dan ilovani to'liq o'chiring:${NC}"
echo -e "   - iPhone'da LMS-RS ikonini bosib turing"
echo -e "   - 'Remove App' → 'Delete App' ni tanlang"
echo ""
echo -e "3. ${BLUE}Qayta build qiling:${NC}"
echo -e "   flutter run --release"
echo ""
echo -e "4. ${BLUE}Birinchi ochilganda permission dialog ko'rinishi kerak!${NC}"
echo ""

