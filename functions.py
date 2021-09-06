import glob
import os
import re
from bs4 import BeautifulSoup
import sys
from xml.dom.minidom import parseString


def decompile_and_dispose():
    directory = sys.argv[1]  # python main.py directory

    cmd = "apktool d -f  " + directory + " -o temp"
    os.system(cmd)  # temp içerisine decompile edildi

    cmd = "mv temp/AndroidManifest.xml ./fake.xml && rm -r temp"  # Android manifest ayıklandı ve kalan dosyalara ihtiyaç olmadığı için silindi.
    os.system(cmd)


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
        selectedManifest) - 1  # şartlar sağlanırsa array indise ihtiyacımız olacağı için choice değşikenine girilen değerin 1 eksiğini atıyoruz.
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



