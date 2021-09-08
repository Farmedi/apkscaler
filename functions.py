import glob
import math
import os
import re
import webbrowser
from datetime import datetime
from bs4 import BeautifulSoup
import sys
from xml.dom.minidom import parseString
from pathlib import Path

def decompile_and_dispose():
    directory = sys.argv[1]  # python main.py directory

    cmd = "apktool d -f  " + directory + " -o temp"
    os.system(cmd)  # temp içerisine decompile edildi

    cmd = "mv temp/AndroidManifest.xml ./fake.xml && rm -r temp"  # Android manifest ayıklandı ve kalan dosyalara ihtiyaç olmadığı için silindi.
    os.system(cmd)
    return os.path.basename(directory)

def clean_up():
    cmd = " rm fake.xml"
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
    # Iterate over all the uses-permission nodes
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
    intentList = list(set(intentList)) #duplicate'leri eledik

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
    intentList = list(set(intentList)) #duplicate'leri eledik
    return intentList


def parse_users_debuggable_allowedbackup():
    isDebuggable=""
    isAllowedBackup=""
    with open('fake.xml', 'r') as f:
        data = f.read()
    dom = parseString(data)

    application = dom.getElementsByTagName('application')

    for node in application:
        isDebuggable=node.getAttribute("android:debuggable")
        isAllowedBackup=node.getAttribute("android:allowBackup")

    return isDebuggable,isAllowedBackup


def parse_compared_debuggable_allowedbackup(toBeCompared):
    isDebuggable=""
    isAllowedBackup=""
    with open(toBeCompared) as f:
        data = f.read()
    dom = parseString(data)

    application = dom.getElementsByTagName('application')

    for node in application:
        isDebuggable=node.getAttribute("android:debuggable")
        isAllowedBackup=node.getAttribute("android:allowBackup")

    return isDebuggable,isAllowedBackup


def filter_list(userList,comparedList):
    return [x for x in userList if x not in comparedList]


def parse_lists(list):
    newlist=[]
    for member in list:
         newlist.append(member.rpartition('.')[-1])
    return newlist


def get_perm_info(list):
    dic={}
    with open('permissioninfo.xml', 'r') as f:
        data = f.read()
    dom = parseString(data)

    permissions = dom.getElementsByTagName('permission')

    # Iterate over all the uses-permission nodes
    for permission in list:
        for node in permissions:
            if node.getAttribute("android:name")==permission:
                dic[permission]=node.getAttribute("android:protectionLevel")
    return dic


#Score
def rate_apk(comparedPermissions,usersPermissions,usersUnfilteredPermissions,intents):
    filteredDangerRate=calculateDangerRate(usersPermissions)
    comparedPermissions=get_perm_info(comparedPermissions)      #Database'den seçilen apk'nın izinlerinin tehlike düzeylerini almamız gerekli.
    comparedDangerRate = calculateDangerRate(comparedPermissions)
    comparedPermCount = len(comparedPermissions)
    usersPermCount= len(usersUnfilteredPermissions)


    tolerance_rate=0.05 #Tolerans değerimiz ne kadar yüksekse o kadar az toleransımız var. 0-1 aralığında değer alabilir.
    permRisk=True

    if filteredDangerRate>comparedDangerRate and usersPermCount<comparedPermCount: #Ekstra izinlerdeki tehlikeli iiznlerin oranı orijinal apk oranından fazla ve şüpheli izin sayısı orijinal APK izin sayısından büyükse kesinlikle şüphelidir.
        permRisk=False

    if filteredDangerRate>comparedDangerRate:       #Ekstra izinlerdeki riskli izinlerin oranı normal aplikasyondaki orandan göre daha mı fazla? Bütün izin oranlarını karşılaştır.
        differential=filteredDangerRate-comparedDangerRate      #Riskli izin oranlarının farkı 0.25-0.20 diyelim. Orijinal apk total izin sayısı 100, şüpheli apk için total izin sayısı 110 olsun
        differential=differential*100   #%5 oranını belli bir tolerans değeri ile yakalamaya çalışacağız

        stubRate=comparedPermCount+(comparedPermCount/differential) #100+(100/5)=120. Eğer şüpheli APK'mızın izin sayısı 120 civarı ise bu riskli izinlerdeki artışı tolere edebiliriz.

        if not math.isclose(usersPermCount/stubRate,1,abs_tol=tolerance_rate): #Eğer şüpheli APK'nın istediği izin miktarı, güvenli APK'nın aynı izin miktarına sahi olsaydı isteyeceği riskli izin değeri ile tolere edilen değer kadar yakın değilse APK risklidir.
            permRisk=False


    malwarePopularIntents = ["BOOT_COMPLETED","SENDTO","DIAL","SCREEN_OFF","TEXT","SEND","USER_PRESENT","PACKAGE_ADDED","SCREEN_ON","CALL",]
    riskyIntents=[]
    intentRisk=True

    for intent in intents:
        for riskyIntent in malwarePopularIntents:
            if riskyIntent==intent:
                riskyIntents.append(riskyIntent)

    if len(riskyIntents)>0:
        intentRisk=False


    conclusion=""

    if not permRisk:
        if not intentRisk:
            conclusion="Dangerous"
        else:                                           # İzinler şüpheli && Intent'ler şüpheli -> Malware
            conclusion="Moderate"                       # İzinler şüpheli && Intent'ler normal  -> Moderate
    else:                                               # İzinler normal && Intent'ler normal -> Safe
        conclusion="Might Be Safe"

    return conclusion,riskyIntents


def calculateDangerRate(permissions):
    dangerous = 0
    signature = 0
    normal = 0
    unknown = 0
    for perm in permissions:
        if str(permissions[perm]) == "dangerous":
            dangerous += 1
        elif str(permissions[perm]).__contains__("signature"):
            signature += 1
        elif str(permissions[perm]) == "normal":
            normal += 1
        else:
            unknown += 1
    try:
        return (dangerous / (unknown + signature + dangerous + normal))
    except:
        return 0


#HTML Output
def create_report(permissions,intents,fbackup,fdebug,tag,username,cmpname):

    username=os.path.splitext(username)[0]
    cmpname=os.path.splitext(cmpname)[0]


    now = datetime.now()
    dateTime = str(now.strftime("%d:%m:%Y\ %H:%M\ "))

    fileName=dateTime+"-"+username+"\-\ "+cmpname+".html"
    print("filename:",fileName)


    #intro ve puanlama

    titles = '<p style="text-align: center;"><span style="color: #0000ff;"><strong>APKScaler</strong></span></p><p style="text-align: center;"><span style="color: #0000ff;"><strong>Analysis Conclusion:'
    explanations='<p style="text-align: justify;"><p style="text-align: center;"><span style="color: #00ff00;"><strong><span style="color: #0000ff;">Report Date: </span> <span style="color: #0000ff;">'+dateTime+'</span></strong></span></p>' \
                 '<p style="text-align: center;"><span style="color: #0000ff;"><strong>'+cmpname+' </strong></span><strong> - </strong><span style="color: #0000ff;"><strong>'+username+' </strong></span></p><p style="text-align: center;">&nbsp;</p>' \
                 '<span style="color: #0000ff; background-color: #ffffff;"><strong><span style="color: #000000;">Your app has been reviewed by 3 categories:</span></strong></span>'\
                        '</p><ol style="margin:5px;"><li style="margin:5px;text-align: justify; "><span style="color: #0000ff; background-color: #ffffff;"><strong><span style="color: #000000;">'\
                        'Permissions: These are <span class="ILfuVd"><span class="hgKElc">special privileges that your app must ask for your permission to use when it needs or wants to access the data on your phone.</span></span></span></strong></span>'\
                        ' </li><li style="margin:5px;text-align: justify;"><span style=" color: #0000ff; background-color: #ffffff;"><strong><span style="color: #000000;">'\
                        'Intents: You can think these as ports which the app can use to interact with itself, system or other apps. As like all everything, these can be abused as well.</span></strong></span></li><li style="margin:5px;text-align: justify;"><span style="color: #0000ff; background-color: #ffffff;"><strong><span style="color: #000000;">'\
                        'Flags: We have checked 2 flags in your apps manifest file. While these flags may not be directly related to malware, setting these may jepardise your data reliability and cause data leaks and other problems. '\
                        'If you are the author off the app you have analyzed, you may want to check these.</span></strong></span><ul><li style="margin:5px;text-align: justify;"><span style="color: #0000ff; background-color: #ffffff;"><strong><span style="color: #000000;">isDebuggable: This states wether the app is debuggable in user mode.</span></strong></span></li>'\
                        '<li style="margin:5px;text-align: justify;"><span style="color: #0000ff; background-color: #ffffff;"><strong><span style="color: #000000;">allowBackup: Applications that store sensitive data should set this to false because these data might be exposed to an attacker through adb.</span></strong></span></li></ul></li></ol>'

    permissionTable = '<div style="text-align: center; width: 50%;  top:0;bottom: 0;left: 0;right: 0;margin: auto;">' \
                      '<span style="color: #ff0000;"><strong>Excessive Permissions</strong></span>' \
                      '<table style="text-align:center;width: 100%; border-collapse: collapse; border-style: solid; border-color: blue; ' 'height: 36px;" border="1" cellspacing="2" cellpadding="2">' \
                      '<tbody>' \
                      '<tr style="height: 18px;">' \
                      '<td  id="name" style="width: 33.3333%; text-align: center; height: 18px;"><strong>Permission</strong></td>' \
                      '<td style="width: 33.3333%; text-align: center; height: 18px;"><strong>Protection Level</strong></td>' \
                      '</tr>'
    intentTable= '<div style=" text-align: center; width: 25%;  top:0;bottom: 0;left: 0;right: 0;margin-left: auto; margin-right:auto;margin-top:25px;">' \
                      '<span style="color: #ff0000;"><strong>Intents Your App And Other Malware Uses In Common</strong></span>' \
                      '<table style="text-align:center;width: 100%; border-collapse: collapse; border-style: solid; border-color: blue; ' 'height: 36px;" border="1" cellspacing="2" cellpadding="2">' \
                      '<tbody>' \
                      '<tr style="height: 18px;">' \
                      '<td  id="name" style="width: 33.3333%; text-align: center; height: 18px;"><strong>Intent </strong></td>' \
                      '</tr>'

    with open("index.html") as fp:
        soup = BeautifulSoup(fp, 'html.parser')
    soup = BeautifulSoup(titles, 'html.parser')

    with open("index.html", "w") as fp:
        fp.write(str(titles))
        if str(tag)=="Dangerous":
            fp.write('<span style="background-color: #ff0000; color: #000000;">Dangerous</span></strong>&nbsp;</span></p>')
        elif str(tag)=="Moderate":
            fp.write('<span style="background-color: #ff6600; color: #000000;">Moderate</span></strong>&nbsp;</span></p>')
        else:
            fp.write('<span style="background-color: #808080; color: #000000;">Might Be Safe</span></strong>&nbsp;</span></p>')
        fp.write(str(explanations))

        #permissions
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

        #intents
        fp.write(intentTable)
        for intent in intents:
            fp.write(str('<tr><td>' + intent + '</td></tr>' ))
        fp.write(str("</tbody></table></div>"))

        # flagler
        fp.write('<p style="text-align: center;"><span style="color: #ff0000;"><strong>Flags</strong></span></p>')
        if str(fdebug)=="True" or str(fdebug)=="true":
            fp.write('<p style="text-align: center;"><span style="color: #000000;"><strong>debuggable: '
                     '<span style="background-color: #ff0000;">ENABLED</span></strong></span></p>')
        else:
            fp.write('<p style="text-align: center;"><span style="color: #000000;"><strong>debuggable: '
                     '<span style="background-color: #00ff00;">DISABLED</span></strong></span></p>')
        if str(fbackup)=="True" or str(fbackup)=="true":
            fp.write('<p style="text-align: center;"><span style="color: #000000;"><strong>allowBackup: '
                     '<span style="background-color: #ff0000;">ENABLED</span></strong></span></p>')
        elif str(fbackup)=="False" or str(fbackup)=="false":
            fp.write('<p style="text-align: center;"><span style="color: #000000;"><strong>allowBackup: '
                     '<span style="background-color: #00ff00;">DISABLED</span></strong></span></p>')
        else:
            fp.write('<p style="text-align: center;"><span style="color: #000000;"><strong>allowBackup: '
                     '<span style="background-color: #ff0000;">ENABLED (BY DEFAULT)</span></strong></span></p>')

    fileName.replace(" ", "\ ")

    cmd = " cp index.html ./reports/"+fileName


    os.system(cmd)


    webbrowser.open("file://" + os.path.realpath("index.html"))






