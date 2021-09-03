#! /usr/bin/env python
import functions as fnc


class manifest:
    def __init__(self):
        self.permissionList = []
        self.intentFilterList = []
        self.isBackupAllowed = ""
        self.isDebuggable = ""

    def set_permission_list(self, _list):
        self.permissionList = _list

    def get_permission_list(self):
        return self.permissionList

    def set_intent_filter_list(self, _list):
        self.intentFilterList = _list

    def get_intent_filter_list(self):
        return self.intentFilterList

    def set_is_backup_allowed(self, flag):
        self.isBackupAllowed = flag

    def get_is_backup_allowed(self):
        return self.isBackupAllowed

    def set_is_debuggable(self, flag):
        self.isDebuggable = flag

    def get_is_debuggable(self):
        return self.isDebuggable


users_mnf = manifest()
compared_mnf = manifest()  # Objeler oluşturuldu

fnc.start_up()  # Basit kontroller
fnc.check_db_exists()  # DB klasörü var mı

toBeCompared = fnc.select_compared_from_db()  # Kıyaslanacak dosyayı aldık.
fnc.decompile_and_dispose()  # Parametre olarak verilen APK'nın manifest'inin elde edilip kalan dosyaların silinmesi

users_mnf.set_permission_list(fnc.parse_users_permissions())  # Elde edilen manifestteki izinler parse'landı
compared_mnf.set_permission_list( fnc.parse_permissions_to_compare(toBeCompared))  # Kıyaslanacak manifestteki izinler parse'landı

users_mnf.set_intent_filter_list(fnc.parse_users_intent_filters())  #Elde edilen manifestteki intent'ler parse'landı
compared_mnf.set_intent_filter_list(fnc.parse_intents_to_compare(toBeCompared)) #Kıyaslanacak olan manifestteki intent'ler parse'landı


debuggable,allowedBackup=fnc.parse_users_debuggable_allowedbackup()
users_mnf.set_is_debuggable(debuggable)
users_mnf.set_is_backup_allowed(allowedBackup)                  #debuggable ve allowedbackup attribute larını aldık

debuggable,allowedBackup=fnc.parse_compared_debuggable_allowedbackup(toBeCompared)
compared_mnf.set_is_debuggable(debuggable)
compared_mnf.set_is_backup_allowed(allowedBackup)               #compared manifest için debuggable ve allowedbackup attribute larını aldık

filteredPermissionList=fnc.filter_list(users_mnf.get_permission_list(),compared_mnf.get_permission_list())
filteredIntentList=fnc.filter_list(users_mnf.get_intent_filter_list(),compared_mnf.get_intent_filter_list())       #test ettiğimiz apk, orijinal apk'dan farklı olarak hangi izinleri ve intentleri istiyor

debuggablesus=False
allowedBackupsus=False

if users_mnf.get_is_debuggable()=="true" and compared_mnf.get_is_debuggable()!="true":
    debuggablesus=True
if users_mnf.get_is_backup_allowed()=="true" and compared_mnf.get_is_backup_allowed()!="true":
    allowedBackupsus=True                                                                           #bu flagler kullanıcının APK'sında var, kıyaslanacak apk'da yok ise şüpheli bayrağı true yapıyoruz.

# print(filteredPermissionList)
# print("\n")
# print(filteredIntentList)
# print("\n")
# print("debuggablesus=",debuggablesus,", allowedbackupsus=",allowedBackupsus)
# print("\n")
# print(fnc.parse_lists(filteredPermissionList))
filteredPermissionList=fnc.parse_lists(filteredPermissionList)
filteredIntentList=fnc.parse_lists(filteredIntentList)              #İzin ve intentlerin başındaki com.android. gibi kısımları filtreliyoruz




fnc.clean_up()

# with open('fake.xml', 'r') as f:
#     data = f.read()
#
# Bs_data = BeautifulSoup(data, "xml")
# # Finding all instances of tag
# # `unique`
# b_unique = Bs_data.find_all('uses-permission')
# for str in b_unique
#     str.replace("android:maxSdkVersion==")
# output = str(b_unique)
# print(output)
# output = re.findall('"([^"]*)"',output)
#
# noutput=""
# for st in output:
#     noutput+=st.replace('android.permission.', '')+ "\n"
#
