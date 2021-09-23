import base64
import glob
import json
import math
import os
import re
import time
import webbrowser
from datetime import datetime

import sys
from xml.dom.minidom import parseString
from pathlib import Path
from types import SimpleNamespace
from vtapi3 import VirusTotalAPIIPAddresses, VirusTotalAPIError, VirusTotalAPIUrls
from fpdf import FPDF
from multipledispatch import dispatch

class pdfColors:
    currindex=-1
    colors={'red':[220,20,60],
            'blue':[0,191,255],
            'black':[0,0,0]}
    def getColor(self,color):
        self.currindex+=1
        return self.colors.get(color)[self.currindex%3]

class PDF(FPDF):
    def footer(self):
        self.set_y(-15)
        self.set_font('Arial', 'I', 12)
        self.cell(0, 10, '%s' % self.page_no(), 0, 0, 'C')

class allResponses:
    allResponses = []

    def pushToList(self, response):
        self.allResponses.append(response)

    def getList(self):
        return self.allResponses


class response:

    def __init__(self, last_analysis_stats, url, message):
        self.last_analysis_stats = last_analysis_stats
        self.url = url
        self.message = message


class request:

    @dispatch(VirusTotalAPIUrls, str)
    def request_and_parse(vt_urls, address):

        vt_urls = vt_urls

        try:
            result = vt_urls.get_report(base64.urlsafe_b64encode(address.encode()).decode().strip("="))
            result = json.loads(result, object_hook=lambda d: SimpleNamespace(**d))

        except VirusTotalAPIError as err:
            push = response(None, address, "API Error. Message:" + str(err))
            return push
        else:
            if vt_urls.get_last_http_error() == vt_urls.HTTP_OK:
                push = response(result.data.attributes.last_analysis_stats, result.data.attributes.url,
                                "Request fulfilled.")
                return push
            else:

                push = response(None, address, "Request failed. Code: " + str(vt_urls.get_last_http_error()))
                return push

    @dispatch(VirusTotalAPIIPAddresses, str)
    def request_and_parse(vt_api_ip, address):


        vt_api_ip = vt_api_ip

        try:

            result = vt_api_ip.get_report("address")
            result = json.loads(result, object_hook=lambda d: SimpleNamespace(**d))

        except VirusTotalAPIError as err:
            push = response(None, address, "API Error. Error: " + str(err))
            return push
        else:
            if vt_api_ip.get_last_http_error() == vt_api_ip.HTTP_OK:

                push = response(result.data.attributes.last_analysis_stats, address, "Request fulfilled.")
                return push
            else:

                push = response(None, address, "Request failed. Code:" + str(vt_api_ip.get_last_http_error()))
                return push


def decompile_and_dispose():
    directory = sys.argv[1]  # python main.py directory

    cmd = "apktool d -f  " + directory + " -o temp"
    try:
        os.system(cmd)  # temp içerisine decompile edildi
    except:
        print("apktool was unable to decompile your app.")
    cmd = "mv temp/AndroidManifest.xml ./fake.xml && rm -r temp"  # Android manifest ayıklandı ve kalan dosyalara ihtiyaç olmadığı için silindi.
    os.system(cmd)

    return os.path.basename(directory)


def analyze_addresses():
    # decompile

    apiFT = "ce982fc75e3417a47ad5a6aa9e95afcc4a1207d07f8144e0a3004e019d61efe6"
    api19 = "3a0d01adbef89c562cd3d341309f9af15104ed9bb4cde8c7c1ca9a6735098425"
    directory = sys.argv[1]

    apkName = Path(directory).stem

    cmd = "jadx  " + directory  # Tool'lar için exception
    try:
        os.system(cmd)
    except:
        print("Jadx was unable to decompile your app to source code.")
        sys.exit()

    ipAddresses = []
    webAddresses = []

    # detect ip and web addresses
    patternIp = re.compile('''((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)''')
    patternWeb = re.compile("http[s]?://(?:[a-zA-Z]|[0-9]|[$-_@.&+]|[!*\(\),]|(?:%[0-9a-fA-F][0-9a-fA-F]))+")

    rootdir = os.getcwd()
    print("Parsing web and IP adresses from .xml and .java files...")
    for folder, dirs, files in os.walk(rootdir):
        for file in files:
            if file.endswith('.java') or file.endswith('.xml'):
                fullpath = os.path.join(folder, file)
                with open(fullpath, 'r') as f:
                    for line in f:
                        line = line.rstrip()
                        ip = patternIp.search(line)  # Bu işlemi daha kısa sürede nasıl yaparız ?
                        web = patternWeb.search(line)
                        if ip:
                            ipAddresses.append(patternIp.search(line).group(0))
                        if web:
                            webAddresses.append(patternWeb.search(line).group(0))

    webAddresses = list(set(webAddresses))
    ipAddresses = list(set(ipAddresses))

    fileName = apkName + ".txt"

    responseList = allResponses()

    print("\n Analyzing IP addresses...\n")
    for ip in ipAddresses:
        responseList.pushToList(request.request_and_parse(VirusTotalAPIIPAddresses(api19), ip))
        time.sleep(15)
    print("\n Analyzing Web addresses...\n")
    for address in webAddresses:
        responseList.pushToList(request.request_and_parse(VirusTotalAPIUrls(api19), address))
        time.sleep(15)

    print_to_text_file(fileName,responseList.getList())
    generate_pdf_report(apkName,responseList.getList())


def generate_pdf_report(apkName,responses):

    report=PDF()
    getClr = pdfColors()
    report.add_page()
    report.set_font("Arial", size=15)
    report.alias_nb_pages()
    report.set_text_color(getClr.getColor("blue"), getClr.getColor("blue"), getClr.getColor("blue"))
    report.cell(200, 10, txt="Web And IP Address Analysis Results", ln=1, align='C')
    report.set_text_color(0, 0, 0)
    for resp in responses:
        if resp.message=="Request fulfilled.":
            report.set_text_color(getClr.getColor("black"),getClr.getColor("black"),getClr.getColor("black"))
            report.set_x(10)
            report.cell(200, 10, txt="Address: "+resp.url,ln=11, align='left')
            report.set_x(20)
            report.cell(200, 10, txt="Harmless: "+str(resp.last_analysis_stats.harmless), ln=12, align='left')
            if resp.last_analysis_stats.malicious > 0:
                report.set_font("Arial","B",size=15)
                report.set_text_color(getClr.getColor("red"),getClr.getColor("red"),getClr.getColor("red"))
                report.cell(200, 10, txt="Malicious: "+str(resp.last_analysis_stats.malicious), ln=13, align='left')
                report.set_text_color(0, 0, 0)
                report.set_font("Arial",size=15)
            else:
                report.cell(200, 10, txt="Malicious: "+str(resp.last_analysis_stats.malicious), ln=13, align='left')
            report.cell(200, 10, txt="Suspicious: "+str(resp.last_analysis_stats.suspicious), ln=14, align='left')
            report.cell(200, 10, txt="Undetected: "+str(resp.last_analysis_stats.undetected), ln=15, align='left')
            report.set_x(10)

        else:
            report.set_text_color(getClr.getColor("red"),getClr.getColor("red"),getClr.getColor("red"))
            report.set_x(10)
            report.cell(200, 10, txt="Address: " + resp.url, ln=11, align='left')
            report.set_x(20)
            report.cell(200, 10, txt="Couldn't fetch adress stats. ", ln=12, align='left')
            report.cell(200, 10, txt="Error: " + resp.message, ln=13, align='left')
            report.set_x(10)
            report.set_text_color(0, 0, 0)
        report.set_text_color(getClr.getColor("blue"),getClr.getColor("blue"),getClr.getColor("blue"),)
        report.cell(200, 10, txt="---------------------------------------------------------------------------",
                        ln=9,
                        align='left')


    dir=os.getcwd()+"/AddressAnalysis/"+apkName+".pdf"
    report.output(dir)


def print_to_text_file(filename,list):
    with open(filename+"JSON",'w') as txt:
        for resp in list:

            if resp.message == "Request fulfilled.":
                txt.write("Address: "+str(resp.url))
                txt.write("\n\t Harmless: "+str(resp.last_analysis_stats.harmless))
                txt.write("\n\t Malicious: "+str(resp.last_analysis_stats.malicious))
                txt.write("\n\t Suspicious: "+str(resp.last_analysis_stats.suspicious))
                txt.write("\n\t Undetected: "+str(resp.last_analysis_stats.undetected))
            else:
                txt.write("Address: "+str(resp.url))
                txt.write("\n\t Couldnt fetch address stats.")
                txt.write("\n\t Error: "+str(resp.message))
            txt.write("\n--------------------\n")


def clean_up():
    cmd = " rm fake.xml && rm -r *-dex2jar*"

    os.system(cmd)


def check_db_exists():
    if not os.path.isdir("database"):
        print("Couldnt locate database folder. Make sure you have cloned all the files from our repository!")


def start_up():
    if len(sys.argv) < 2:
        print("Insufficient amount of inputs. \n    Usage: main.py <apk_directory>")
        sys.exit()
    if not os.path.isfile(sys.argv[1]):
        print("No such file in specified directory.")
        sys.exit()
    if not sys.argv[1].endswith(".apk"):
        print("Specified file is not an APK.")
        sys.exit()
    else:
        print("All inputs satisfied. Commencing...")


def select_compared_from_db():
    xmlSamples = {}  # boş bir dictionary oluşturduk
    counter = 1  # dictionry key'lerinin value'larını bu counter yardımıyla belirtiyoruz
    for xml in glob.glob(os.getcwd() + "/database/*.xml"):  # database klasöründeki her xml dosyası için çalışıyor
        xmlSamples[xml] = counter  # key: dosya path'i, value: index şeklinde kayıt açıyoruz her xml için
        counter += 1

    for key, value in xmlSamples.items():  # her dictionary kaydı için çalışıyor
        print(
            str(value) + "-" + os.path.basename(key))  # her xml dosyasının sadece adını yazdırıyor, path'ini kırpıyor.

    print("Enter the ID of the xml file you would like to compare your apk with: ")
    flag = True

    while flag:

        selectedManifest = input()

        try:
            int(selectedManifest)  # kullanıcı string inputu girerse cast error üretilecek ve except bloku çalıştırılıcak.
            if int(selectedManifest) <= len(xmlSamples) and int(
                    selectedManifest) > 0:  # negatif veya dosya miktarından büyük bir sayı girilirse döngüden çıkılmaması sağlanıyor.
                flag = False
            else:
                print("Invalid input.")
                print("Enter the ID of the xml file you would like to compare your apk with: ")
        except ValueError:  # kullanıcı string girerse çalışıcak
            print("Enter numerical values only! ")
            print("Invalid input.")
            print("Enter the ID of the xml file you would like to compare your apk with: ")
    choice = int(
        selectedManifest) - 1  # şartlar sağlanırsa array indise ihtiyacımız olacağı için choice değişkenine, girilen değerin 1 eksiğini atıyoruz.
    return list(xmlSamples.keys())[int(choice)]  # dosya adlarını bir listeye alıp, seçilen indistekini döndürüyoruz.


def parse_users_permissions():
    permissionList = []
    with open('fake.xml', 'r') as f:
        data = f.read()
    dom = parseString(data)

    permissions = dom.getElementsByTagName('uses-permission')
    permissions += dom.getElementsByTagName('android:uses-permission')

    for node in permissions:
        permissionList.append(node.getAttribute("android:name"))
        permissionList.append(node.getAttribute("name"))
    permissionList = list(filter(None, permissionList))
    return permissionList


def parse_users_services():
    servicesList = []
    with open('fake.xml', 'r') as f:
        data = f.read()
    dom = parseString(data)

    services = dom.getElementsByTagName('service')
    # Iterate over all the uses-permission nodes
    for service in services:
        servicesList.append(service.getAttribute("android:name"))

    return servicesList


def parse_services_to_compare(toBeCompared):
    xmlPath = toBeCompared
    serviceList = []
    with open(xmlPath) as file:
        data = file.read()
    dom = parseString(data)
    services = dom.getElementsByTagName('service')

    for service in services:
        serviceList.append(service.getAttribute("android:name"))

    return serviceList


def parse_permissions_to_compare(toBeCompared):
    xmlPath = toBeCompared
    permissionList = []
    with open(xmlPath) as file:
        data = file.read()
    dom = parseString(data)
    permissions = dom.getElementsByTagName('uses-permission')
    permissions += dom.getElementsByTagName('android:uses-permission')

    for node in permissions:
        permissionList.append(node.getAttribute("android:name"))
        permissionList.append(node.getAttribute("name"))
    permissionList = list(filter(None, permissionList))
    return permissionList


def parse_users_intent_filters():
    intentList = []
    with open('fake.xml', 'r') as f:
        data = f.read()
    dom = parseString(data)

    intents = dom.getElementsByTagName('action')

    for node in intents:
        intentList.append(node.getAttribute("android:name"))
        intentList.append(node.getAttribute("name"))
    intentList = list(filter(None, intentList))
    intentList = list(set(intentList))  # duplicate'leri eledik

    return intentList


def parse_intents_to_compare(toBeCompared):
    intentList = []
    with open(toBeCompared) as file:
        data = file.read()
    dom = parseString(data)

    intents = dom.getElementsByTagName('action')

    for node in intents:
        intentList.append(node.getAttribute("android:name"))
        intentList.append(node.getAttribute("name"))
    intentList = list(filter(None, intentList))
    intentList = list(set(intentList))  # duplicate'leri eledik
    return intentList


def parse_users_debuggable_allowedbackup():
    isDebuggable = ""
    isAllowedBackup = ""
    with open('fake.xml', 'r') as f:
        data = f.read()
    dom = parseString(data)

    application = dom.getElementsByTagName('application')

    for node in application:
        isDebuggable = node.getAttribute("android:debuggable")
        isAllowedBackup = node.getAttribute("android:allowBackup")

    return isDebuggable, isAllowedBackup


def parse_compared_debuggable_allowedbackup(toBeCompared):
    isDebuggable = ""
    isAllowedBackup = ""
    with open(toBeCompared) as f:
        data = f.read()
    dom = parseString(data)

    application = dom.getElementsByTagName('application')

    for node in application:
        isDebuggable = node.getAttribute("android:debuggable")
        isAllowedBackup = node.getAttribute("android:allowBackup")

    return isDebuggable, isAllowedBackup


def filter_list(userList, comparedList):
    return [x for x in userList if x not in comparedList]


def parse_lists(list):
    newlist = []
    for member in list:
        newlist.append(member.rpartition('.')[-1])
    return newlist


def get_perm_info(list):
    dic = {}
    with open('permissioninfo.xml', 'r') as f:
        data = f.read()
    dom = parseString(data)

    permissions = dom.getElementsByTagName('permission')

    # Iterate over all the uses-permission nodes
    for permission in list:
        for node in permissions:
            if node.getAttribute("android:name") == permission:
                dic[permission] = node.getAttribute("android:protectionLevel")
    return dic


# Score
def rate_apk(comparedPermissions, usersPermissions, usersUnfilteredPermissions, intents):
    filteredDangerRate = calculateDangerRate(usersPermissions)
    comparedPermissions = get_perm_info(comparedPermissions)  # Database'den seçilen apk'nın izinlerinin tehlike düzeylerini almamız gerekli.

    comparedDangerRate = calculateDangerRate(comparedPermissions)

    comparedPermCount = len(comparedPermissions)
    usersPermCount = len(usersUnfilteredPermissions)

    tolerance_rate = 0.05  # Tolerans değerimiz ne kadar düşükse o kadar az toleransımız var. 0-1 aralığında değer alabilir.
    permRisk = True

    if filteredDangerRate > comparedDangerRate and usersPermCount < comparedPermCount:  # Ekstra izinlerdeki tehlikeli iiznlerin oranı orijinal apk oranından fazla ve şüpheli izin sayısı orijinal APK izin sayısından büyükse kesinlikle şüphelidir.
        permRisk = False

    if filteredDangerRate > comparedDangerRate:  # Ekstra izinlerdeki riskli izinlerin oranı normal aplikasyondaki orandan göre daha mı fazla? Bütün izin oranlarını karşılaştır.
        differential = filteredDangerRate - comparedDangerRate  # Riskli izin oranlarının farkı 0.25-0.20 diyelim. Orijinal apk total izin sayısı 100, şüpheli apk için total izin sayısı 110 olsun
        differential = differential * 100  # %5 oranını belli bir tolerans değeri ile yakalamaya çalışacağız

        stubRate = comparedPermCount + (
                comparedPermCount / differential)  # 100+(100/5)=120. Eğer şüpheli APK'mızın izin sayısı 120 civarı ise bu riskli izinlerdeki artışı tolere edebiliriz.

        if not math.isclose(usersPermCount / stubRate, 1,
                            abs_tol=tolerance_rate):  # Eğer şüpheli APK'nın istediği izin miktarı, güvenli APK'nın aynı izin miktarına sahi olsaydı isteyeceği riskli izin değeri ile tolere edilen değer kadar yakın değilse APK risklidir.
            permRisk = False

    malwarePopularIntents = ["BOOT_COMPLETED", "SENDTO", "DIAL", "SCREEN_OFF", "TEXT", "SEND", "USER_PRESENT",
                             "PACKAGE_ADDED", "SCREEN_ON", "CALL", ]
    riskyIntents = []
    intentRisk = True

    for intent in intents:
        for riskyIntent in malwarePopularIntents:
            if riskyIntent == intent:
                riskyIntents.append(riskyIntent)

    if len(riskyIntents) > 0:
        intentRisk = False



    if not permRisk:
        if not intentRisk:
            conclusion = "Dangerous"
        else:  # İzinler şüpheli && Intent'ler şüpheli -> Malware
            conclusion = "Moderate"  # İzinler şüpheli && Intent'ler normal  -> Moderate
    else:  # İzinler normal && Intent'ler normal -> Safe
        conclusion = "Might Be Safe"

    return conclusion, riskyIntents


def calculateDangerRate(permissions):
    dangerous = 0
    signature = 0
    normal = 0
    unknown = 0
    for perm in permissions:
        if str(permissions[perm]) == "dangerous":
            dangerous += 3
        elif str(permissions[perm]).__contains__("signature"):                                                          # KAtsayılı-Ağırlıklı puanlama.
            signature += 2
        elif str(permissions[perm]) == "normal":
            normal += 1
        else:
            unknown += 1
    try:
        return (dangerous / (unknown + signature + dangerous + normal))
    except:
        return 0


# HTML Output
def create_report(permissions, intents, usermnf, tag, comparedmnf):
    username = os.path.splitext(usermnf.get_name())[0]
    cmpname = os.path.splitext(comparedmnf.get_name())[0]

    now = datetime.now()
    dateTime = str(now.strftime("%d:%m:%Y\ %H:%M\ "))

    fileName = dateTime + "-" + username + "\-\ " + cmpname + ".html"


    # intro ve puanlama

    titles = '<p style="text-align: center;"><span style="color: #0000ff;"><strong>APKScaler</strong></span></p><p style="text-align: center;"><span style="color: #0000ff;"><strong>Analysis Conclusion:'
    explanations = '<p style="text-align: justify;"><p style="text-align: center;"><span style="color: #00ff00;"><strong><span style="color: #0000ff;">Report Date: </span> <span style="color: #0000ff;">' + dateTime + '</span></strong></span></p>' \
                                                                                                                                                                                                                         '<p style="text-align: center;"><span style="color: #0000ff;"><strong>' + cmpname + ' </strong></span><strong> - </strong><span style="color: #0000ff;"><strong>' + username + ' </strong></span></p><p style="text-align: center;">&nbsp;</p>' \
                                                                                                                                                                                                                                                                                                                                                                                                        '<span style="color: #0000ff; background-color: #ffffff;"><strong><span style="color: #000000;">Your app has been reviewed by 3 categories:</span></strong></span>' \
                                                                                                                                                                                                                                                                                                                                                                                                        '</p><ol style="margin:5px;"><li style="margin:5px;text-align: justify; "><span style="color: #0000ff; background-color: #ffffff;"><strong><span style="color: #000000;">' \
                                                                                                                                                                                                                                                                                                                                                                                                        'Permissions: These are <span class="ILfuVd"><span class="hgKElc">special privileges that your app must ask for your permission to use when it needs or wants to access the data on your phone.</span></span></span></strong></span>' \
                                                                                                                                                                                                                                                                                                                                                                                                        ' </li><li style="margin:5px;text-align: justify;"><span style=" color: #0000ff; background-color: #ffffff;"><strong><span style="color: #000000;">' \
                                                                                                                                                                                                                                                                                                                                                                                                        'Intents: You can think these as ports which the app can use to interact with itself, system or other apps. As like all everything, these can be abused as well.</span></strong></span></li><li style="margin:5px;text-align: justify;"><span style="color: #0000ff; background-color: #ffffff;"><strong><span style="color: #000000;">' \
                                                                                                                                                                                                                                                                                                                                                                                                        'Flags: We have checked 2 flags in your apps manifest file. While these flags may not be directly related to malware, setting these may jepardise your data reliability and cause data leaks and other problems. ' \
                                                                                                                                                                                                                                                                                                                                                                                                        'If you are the author off the app you have analyzed, you may want to check these.</span></strong></span><ul><li style="margin:5px;text-align: justify;"><span style="color: #0000ff; background-color: #ffffff;"><strong><span style="color: #000000;">isDebuggable: This states wether the app is debuggable in user mode.</span></strong></span></li>' \
                                                                                                                                                                                                                                                                                                                                                                                                        '<li style="margin:5px;text-align: justify;"><span style="color: #0000ff; background-color: #ffffff;"><strong><span style="color: #000000;">allowBackup: Applications that store sensitive data should set this to false because these data might be exposed to an attacker through adb.</span></strong></span></li></ul></li></ol>'
    permissionTable = '<div style="text-align: center; width: 50%;  top:0;bottom: 0;left: 0;right: 0;margin: auto;">' \
                      '<span style="color: #ff0000;"><strong>Excessive Permissions</strong></span>' \
                      '<table style="text-align:center;width: 100%; border-collapse: collapse; border-style: solid; border-color: blue; ' 'height: 36px;" border="1" cellspacing="2" cellpadding="2">' \
                      '<tbody>' \
                      '<tr style="height: 18px;">' \
                      '<td  id="name" style="width: 33.3333%; text-align: center; height: 18px;"><strong>Permission</strong></td>' \
                      '<td style="width: 33.3333%; text-align: center; height: 18px;"><strong>Protection Level</strong></td>' \
                      '</tr>'
    intentTable = '<div style=" text-align: center; width: 25%;  top:0;bottom: 0;left: 0;right: 0;margin-left: auto; margin-right:auto;margin-top:25px;">' \
                  '<span style="color: #ff0000;"><strong>Intents Your App And Other Malware Uses In Common</strong></span>' \
                  '<table style="text-align:center;width: 100%; border-collapse: collapse; border-style: solid; border-color: blue; ' 'height: 36px;" border="1" cellspacing="2" cellpadding="2">' \
                  '<tbody>' \
                  '<tr style="height: 18px;">' \
                  '<td  id="name" style="width: 33.3333%; text-align: center; height: 18px;"><strong>Intent </strong></td>' \
                  '</tr>'



    with open("index.html", "w") as fp:
        fp.write(str(titles))
        if str(tag) == "Dangerous":
            fp.write(
                '<span style="background-color: #ff0000; color: #000000;">Dangerous</span></strong>&nbsp;</span></p>')
        elif str(tag) == "Moderate":
            fp.write(
                '<span style="background-color: #ff6600; color: #000000;">Moderate</span></strong>&nbsp;</span></p>')
        else:
            fp.write(
                '<span style="background-color: #808080; color: #000000;">Might Be Safe</span></strong>&nbsp;</span></p>')
        fp.write(str(explanations))

        # permissions
        fp.write(str(permissionTable))
        for key in permissions:
            if str(permissions[key]).__contains__("dangerous"):
                fp.write(
                    str('<tr ><td >' + key + '</td><td style="background-color: #eb4034">' + str(
                        permissions[key]) + '</tr>'))
            elif str(permissions[key]).__contains__("signature"):
                fp.write(
                    str('<tr ><td >' + key + '</td><td style="background-color: #b1eb34">' + str(
                        permissions[key]) + '</tr>'))
            elif str(permissions[key]) == "normal":
                fp.write(
                    str('<tr ><td >' + key + '</td><td style="background-color: #ccc6ee">' + str(
                        permissions[key]) + '</tr>'))
            else:
                fp.write(
                    str('<tr><td>' + key + '</td><td >' + str(permissions[key]) + '</tr>')
                )
        fp.write(str("</tbody></table></div>"))

        # intents
        fp.write(intentTable)
        for intent in intents:
            fp.write(str('<tr><td>' + intent + '</td></tr>'))
        fp.write(str("</tbody></table></div>"))

        # flagler
        flagTable = '<div style="text-align: center; width: 50%;  top:0;bottom: 0;left: 0;right: 0;margin: auto;">' \
                          '<span style="color: #ff0000;"><strong>Flag Status</strong></span>' \
                          '<table style="text-align:center;width: 100%; border-collapse: collapse; border-style: solid; border-color: blue; ' 'height: 36px;" border="1" cellspacing="2" cellpadding="2">' \
                          '<tbody>' \
                          '<tr style="height: 18px;">' \
                          '<td style="width: 33.3333%; text-align: center; height: 18px;"><strong>Flag Name</strong></td>' \
                          '<td  id="name" style="width: 33.3333%; text-align: center; height: 18px;"><strong>'+comparedmnf.get_name()+'</strong></td>' \
                          '<td style="width: 33.3333%; text-align: center; height: 18px;"><strong>'+usermnf.get_name()+'</strong></td>' \
                          '</tr>'

        fp.write(flagTable)
        #fp.write('<p style="text-align: center;"><span style="color: #ff0000;"><strong>Flags</strong></span></p>')

        if comparedmnf.get_is_debuggable()=="" or comparedmnf.get_is_debuggable()==None:
            comparedmnf.set_is_debuggable("false")
        if comparedmnf.get_is_backup_allowed() == "" or comparedmnf.get_is_backup_allowed == None:
            comparedmnf.set_is_backup_allowed("false")

        if usermnf.get_is_debuggable() == "" or usermnf.get_is_debuggable() == None:
            usermnf.set_is_debuggable("true")
        if usermnf.get_is_backup_allowed() == "" or usermnf.get_is_backup_allowed == None:
            usermnf.set_is_backup_allowed("true")

        fp.write(
            str('<tr ><td>Debuggable:</td><td ><strong>' + str(
                comparedmnf.get_is_debuggable()).upper() + '</strong></td><td><strong>' + str(
                usermnf.get_is_debuggable()).upper() + '</strong></tr>') +
            str('<tr ><td>Backup Allowed:</td><td><strong>' + str(
                comparedmnf.get_is_backup_allowed()).upper() + '</strong></td><td><strong>' + str(
                usermnf.get_is_backup_allowed()).upper() + '</strong></tr>'))

    fileName.replace(" ", "\ ")

    cmd = " cp index.html ./reports/" + fileName

    os.system(cmd)

    webbrowser.open("file://" + os.path.realpath("index.html"))
