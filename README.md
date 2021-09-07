# apkscaler
APKScaler is a tool which can be used to compare a selected APK to its similar or exact one. 
Currently tool has 7 androidmanifest.xml files which were extracted from the safe and official versions of their applications. You can compare your apps APK
with one of these 7 samples and APKScaler will analyze its androidmanifest and then generate an HTML report based on findings.

Dependencies:
-Python 3.6+ (2.9+ might also work but its not tested therefore not recommended.)
  -BeautifulSoup4
  -webbrowser
-APKtool

Usage:
python main.py <Directory of the APK you would like to compare>
