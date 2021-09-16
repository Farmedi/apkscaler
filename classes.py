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

class response200:
    def __init__(self,url,last_analysis_stats):
        self.url=url
        self.last_analysis_stats=last_analysis_stats