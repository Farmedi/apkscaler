#! /usr/bin/env python
import glob
import os
import re
from bs4 import BeautifulSoup
import sys
from xml.dom.minidom import parseString

def decompileAndDispose(directory):
    cmd = "apktool d -f  "+directory+" -o temp"
    os.system(cmd)                                #temp içerisine decompile edildi

    cmd = "mv temp/AndroidManifest.xml ./fake.xml && rm -r temp" #Android manifest ayıklandı ve kalan dosyalara ihtiyaç olmadığı için silindi.
    os.system(cmd)

def cleanUp():
    cmd = " rm fake.xml"
    os.system(cmd)

def check_db_exists():
    if not os.path.isdir("temp"):
        print("Couldnt locate database folder. Make sure you have cloned all the files from our repository!")


directory = sys.argv[1] #python main.py directory
decompileAndDispose(directory)
check_db_exists()



data = ''
with open('fake.xml','r') as f:
    data = f.read()
dom = parseString(data)
permissions = dom.getElementsByTagName('uses-permission')
permissions+= dom.getElementsByTagName('android:uses-permission')
# Iterate over all the uses-permission nodes
#for node in permissions:
    #print(node.getAttribute("android:name"))


features = dom.getElementsByTagName('uses-feature')
#for node in features:
      #print(node)

cleanUp()













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