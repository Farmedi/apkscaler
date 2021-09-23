#! /usr/bin/env python
import os
import sys

import functions as fnc
class manifest:
    def __init__(self):
        self.permissionList = []
        self.intentFilterList = []
        self.servicesList = []
        self.isBackupAllowed = ""
        self.isDebuggable = ""
        self.name= ""

    def set_servicesList(self, _list):
        self.servicesList = _list

    def get_services_list(self, ):
        return self.servicesList

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

    def set_name(self,name):
        self.name=name

    def get_name(self):
        return self.name




users_mnf = manifest()
compared_mnf = manifest()  # Objeler oluşturuldu


fnc.start_up()  # Basit kontroller
fnc.check_db_exists()  # DB klasörü var mı


toBeCompared = fnc.select_compared_from_db()  # Kıyaslanacak dosyayı aldık.
compared_mnf.set_name(os.path.basename(toBeCompared))

users_mnf.set_name(fnc.decompile_and_dispose())

users_mnf.set_permission_list(fnc.parse_users_permissions())  # Elde edilen manifestteki izinler parse'landı
compared_mnf.set_permission_list(fnc.parse_permissions_to_compare(toBeCompared))  # Kıyaslanacak manifestteki izinler parse'landı


users_mnf.set_intent_filter_list(fnc.parse_users_intent_filters())  # Elde edilen manifestteki intent'ler parse'landı
compared_mnf.set_intent_filter_list(fnc.parse_intents_to_compare(toBeCompared))  # Kıyaslanacak olan manifestteki intent'ler parse'landı


# users_mnf.set_servicesList(fnc.parse_users_services())
# compared_mnf.set_servicesList(fnc.parse_services_to_compare(toBeCompared))


# filteredServicesList = fnc.filter_list(users_mnf.get_services_list(), compared_mnf.get_services_list())
filteredPermissionList = fnc.filter_list(users_mnf.get_permission_list(), compared_mnf.get_permission_list()) # test ettiğimiz apk, orijinal apk'dan farklı olarak hangi izinleri ve intentleri istiyor
filteredIntentList = fnc.filter_list(users_mnf.get_intent_filter_list(),compared_mnf.get_intent_filter_list())


debuggable, allowedBackup = fnc.parse_users_debuggable_allowedbackup()
users_mnf.set_is_debuggable(debuggable)
users_mnf.set_is_backup_allowed(allowedBackup)  # debuggable ve allowedbackup attribute larını aldık


debuggable, allowedBackup = fnc.parse_compared_debuggable_allowedbackup(toBeCompared)
compared_mnf.set_is_debuggable(debuggable)
compared_mnf.set_is_backup_allowed(
    allowedBackup)  # compared manifest için debuggable ve allowedbackup attribute larını aldık


# debuggablesus = False
# allowedBackupsus = False
# if users_mnf.get_is_debuggable() == "true" and compared_mnf.get_is_debuggable() != "true":
#     debuggablesus = True
# if users_mnf.get_is_backup_allowed() == "true" and compared_mnf.get_is_backup_allowed() != "true":
#     allowedBackupsus = True  # bu flagler kullanıcının APK'sında var, kıyaslanacak apk'da yok ise şüpheli bayrağı true yapıyoruz. (KULLANILMADI.)

# Skorlama için gerekli servis, intent, izin ve flag bilgilerine sahibiz.
# ------------------------SKOR-----------------------
filteredIntentList = fnc.parse_lists(filteredIntentList)  # İzin ve intentlerin başındaki com.android. gibi kısımları filtreliyoruz
filteredPermissionList = fnc.get_perm_info(filteredPermissionList)
filteredServicesList = fnc.filter_list(fnc.parse_users_services(), fnc.parse_services_to_compare(toBeCompared))


tag,intentList=fnc.rate_apk(compared_mnf.get_permission_list(),filteredPermissionList,users_mnf.get_permission_list(),filteredIntentList)

fnc.create_report(filteredPermissionList,intentList,users_mnf,tag,compared_mnf)

fnc.analyze_addresses()



fnc.clean_up()
