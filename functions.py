import glob
import os
import re
import webbrowser

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


#Score
def rate_apk(comparedPermissions,usersPermissions,usersUnfilteredPermissions,intents,services,fbackup,fdebug):
    filteredDangerRate=calculateDangerRate(usersPermissions)
    comparedDangerRate = calculateDangerRate(comparedPermissions)
    comparedPermCount = len(comparedPermissions)
    usersPermCount= len(usersUnfilteredPermissions)

    tolerance_rate=0.05 #Tolerans değerimiz ne kadar yüksekse o kadar az toleransımız var. 0-1 aralığında değer alabilir.
    permRisk=False

    if filteredDangerRate<comparedDangerRate and usersPermCount<comparedPermCount: #Ekstra izinlerdeki tehlikeli iiznlerin oranı orijinal apk oranından fazla ve şüpheli izin sayısı orijinal APK izin sayısından büyükse kesinlikle şüphelidir.
        permRisk=True

    elif filteredDangerRate>comparedDangerRate:       #Ekstra izinlerdeki riskli izinlerin oranı normal aplikasyondaki orandan göre daha mı fazla? Bütün izin oranlarını karşılaştır.
        differential=filteredDangerRate-comparedDangerRate      #Riskli izin oranlarının farkı 0.25-0.20 diyelim. Orijinal apk total izin sayısı 100, şüpheli apk için total izin sayısı 110 olsun
        differential=differential*100   #%5 oranını belli bir tolerans değeri ile yakalamaya çalışacağız

        stubRate=comparedPermCount+(comparedPermCount/differential) #100+(100/5)=120. Eğer şüpheli APK'mızın izin sayısı 120 civarı ise bu riskli izinlerdeki artışı tolere edebiliriz.

        if usersPermCount/stubRate > 1-tolerance_rate and usersPermCount/stubRate > 1+tolerance_rate:
            permRisk=False





def calculateDangerRate(permissions):
    dangerous = 0
    signature = 0
    normal = 0
    unknown = 0
    for perm in permissions:
        if permissions[perm] == "dangerous":
            dangerous += 1
        elif permissions[perm].__contains__("signature"):
            signature += 1
        elif permissions[perm] == "normal":
            normal += 1
        else:
            unknown += 1

    return (dangerous / (unknown + signature + dangerous + normal))

#HTML Output
def create_report(permissions,intents,services,fbackup,fdebug):


    #intro ve puanlama
    # html_code = '<div style="text-align: center; width: 50%;  top:0;bottom: 0;left: 0;right: 0;margin: auto;">' \
    #             '<span style="color: #ff0000;"><strong>Excessive Permissions</strong></span>' \
    #             '<table style="text-align:center;width: 100%; border-collapse: collapse; border-style: solid; border-color: blue; ' 'height: 36px;" border="1" cellspacing="2" cellpadding="2">' \
    #             '<tbody>' \
    #             '<tr style="height: 18px;">' \
    #             '<td  id="name" style="width: 33.3333%; text-align: center; height: 18px;"><strong>Permission</strong></td>' \
    #             '<td id = "pl "style="width: 33.3333%; text-align: center; height: 18px;"><strong>Protection Level</strong></td>' \
    #             '</tr>'
    #
    # with open("index.html") as fp:
    #     soup = BeautifulSoup(fp, 'html.parser')
    #
    # soup = BeautifulSoup(html_code, 'html.parser')
    #
    # with open("index.html", "w") as fp:
    #     fp.write(str(html_code))
    #     for key in dic:
    #         if str(dic[key]) == "dangerous":
    #             fp.write(
    #                 str('<tr ><td >' + key + '</td><td style="background-color: #eb4034">' + str(dic[key]) + '</tr>'))
    #         elif str(dic[key]).__contains__("signature"):
    #             fp.write(
    #                 str('<tr ><td >' + key + '</td><td style="background-color: #b1eb34">' + str(dic[key]) + '</tr>'))
    #         elif str(dic[key]) == "normal":
    #             fp.write(
    #                 str('<tr ><td >' + key + '</td><td style="background-color: #2a2a2b">' + str(dic[key]) + '</tr>'))
    #
    #     fp.write(str("</tbody></table></div>"))
    # webbrowser.open("file://" + os.path.realpath("index.html"))



    #permission
    html_code = '<div style="text-align: center; width: 50%;  top:0;bottom: 0;left: 0;right: 0;margin: auto;">' \
                '<span style="color: #ff0000;"><strong>Excessive Permissions</strong></span>' \
                '<table style="text-align:center;width: 100%; border-collapse: collapse; border-style: solid; border-color: blue; ' 'height: 36px;" border="1" cellspacing="2" cellpadding="2">' \
                '<tbody>' \
                '<tr style="height: 18px;">' \
                '<td  id="name" style="width: 33.3333%; text-align: center; height: 18px;"><strong>Permission</strong></td>' \
                '<td id = "pl "style="width: 33.3333%; text-align: center; height: 18px;"><strong>Protection Level</strong></td>' \
                '</tr>'

    with open("index.html") as fp:
        soup = BeautifulSoup(fp, 'html.parser')

    soup = BeautifulSoup(html_code, 'html.parser')

    dangerous=0
    signature=0
    normal=0
    unknown=0

    with open("index.html", "w") as fp:
        fp.write(str(html_code))
        for key in permissions:
            if str(permissions[key]) == "dangerous":
                dangerous+=1
                fp.write(
                    str('<tr ><td >' + key + '</td><td style="background-color: #eb4034">' + str(permissions[key]) + '</tr>'))
            elif str(permissions[key]).__contains__("signature"):
                signature+=1
                fp.write(
                    str('<tr ><td >' + key + '</td><td style="background-color: #b1eb34">' + str(permissions[key]) + '</tr>'))
            elif str(permissions[key]) == "normal":
                normal+=1
                fp.write(
                    str('<tr ><td >' + key + '</td><td style="background-color: #ccc6ee">' + str(permissions[key]) + '</tr>'))
            else:
                unknown+=1
                fp.write(
                    str('<tr><td>'+key+'</td><td >'+str(permissions[key])+'</tr>')
                )
        fp.write(str("</tbody></table></div>"))
    webbrowser.open("file://" + os.path.realpath("index.html"))


    # intent

    # html_code = '<div style="text-align: center; width: 50%;  top:0;bottom: 0;left: 0;right: 0;margin: auto;">' \
    #             '<span style="color: #ff0000;"><strong>Excessive Permissions</strong></span>' \
    #             '<table style="text-align:center;width: 100%; border-collapse: collapse; border-style: solid; border-color: blue; ' 'height: 36px;" border="1" cellspacing="2" cellpadding="2">' \
    #             '<tbody>' \
    #             '<tr style="height: 18px;">' \
    #             '<td  id="name" style="width: 33.3333%; text-align: center; height: 18px;"><strong>Permission</strong></td>' \
    #             '<td id = "pl "style="width: 33.3333%; text-align: center; height: 18px;"><strong>Protection Level</strong></td>' \
    #             '</tr>'
    #
    # with open("index.html") as fp:
    #     soup = BeautifulSoup(fp, 'html.parser')
    #
    # soup = BeautifulSoup(html_code, 'html.parser')
    #
    # with open("index.html", "w") as fp:
    #     fp.write(str(html_code))
    #     for key in intents:
    #         if str(intents[key]) == "dangerous":
    #             fp.write(
    #                 str('<tr ><td >' + key + '</td><td style="background-color: #eb4034">' + str(intents[key]) + '</tr>'))
    #         elif str(intents[key]).__contains__("signature"):
    #             fp.write(
    #                 str('<tr ><td >' + key + '</td><td style="background-color: #b1eb34">' + str(intents[key]) + '</tr>'))
    #         elif str(intents[key]) == "normal":
    #             fp.write(
    #                 str('<tr ><td >' + key + '</td><td style="background-color: #ccc6ee">' + str(intents[key]) + '</tr>'))
    #
    #     fp.write(str("</tbody></table></div>"))
    # webbrowser.open("file://" + os.path.realpath("index.html"))


    #services

    # html_code = '<div style="text-align: center; width: 50%;  top:0;bottom: 0;left: 0;right: 0;margin: auto;">' \
    #             '<span style="color: #ff0000;"><strong>Excessive Permissions</strong></span>' \
    #             '<table style="text-align:center;width: 100%; border-collapse: collapse; border-style: solid; border-color: blue; ' 'height: 36px;" border="1" cellspacing="2" cellpadding="2">' \
    #             '<tbody>' \
    #             '<tr style="height: 18px;">' \
    #             '<td  id="name" style="width: 33.3333%; text-align: center; height: 18px;"><strong>Permission</strong></td>' \
    #             '<td id = "pl "style="width: 33.3333%; text-align: center; height: 18px;"><strong>Protection Level</strong></td>' \
    #             '</tr>'
    #
    # with open("index.html") as fp:
    #     soup = BeautifulSoup(fp, 'html.parser')
    #
    # soup = BeautifulSoup(html_code, 'html.parser')
    #
    # with open("index.html", "w") as fp:
    #     fp.write(str(html_code))
    #     for key in services:
    #         if str(services[key]) == "dangerous":
    #             fp.write(
    #                 str('<tr ><td >' + key + '</td><td style="background-color: #eb4034">' + str(services[key]) + '</tr>'))
    #         elif str(services[key]).__contains__("signature"):
    #             fp.write(
    #                 str('<tr ><td >' + key + '</td><td style="background-color: #b1eb34">' + str(services[key]) + '</tr>'))
    #         elif str(services[key]) == "normal":
    #             fp.write(
    #                 str('<tr ><td >' + key + '</td><td style="background-color: #2a2a2b">' + str(services[key]) + '</tr>'))
    #
    #     fp.write(str("</tbody></table></div>"))
    # webbrowser.open("file://" + os.path.realpath("index.html"))

    #flagler


