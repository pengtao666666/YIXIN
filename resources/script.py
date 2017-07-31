__author__ = 'Administrator'

import os
import xml.etree.ElementTree as ET
import shutil
from xml.etree.ElementTree import ElementTree,Element

ANDROID_NAMESPACE = 'http://schemas.android.com/apk/res/android'

def script(workspace_sdk_dir, extract_dir, channel_sdk_info, new_game_channel_info, game_info):
    update_yxpay(workspace_sdk_dir,extract_dir)
	
   
    sdk_channel_logo = os.path.join(extract_dir,"res/mipmap-hdpi-v4/sdk_channel_logo.png");
    if os.path.exists(sdk_channel_logo):
	    os.remove(sdk_channel_logo)



def get_package_name(path):
    path = os.path.join(path,'AndroidManifest.xml')
    if(os.path.isfile(path)):
        ET.register_namespace('android', ANDROID_NAMESPACE)
        tree = ET.parse(path)
        root = tree.getroot()
        return root.attrib['package']

def update_yxpay(channel_paht,game_path):
    package_name = str(get_package_name(game_path))
    sTempChannelPath = channel_paht

    toBeModifyFiles = ['YXEntryActivity$1$1.smali', 'YXEntryActivity.smali', 'YXEntryActivity$1.smali', 'YXEntryActivity$2.smali','AppRegister.smali']

    for sTempSmali in toBeModifyFiles:
        channel_paht = os.path.join(sTempChannelPath, sTempSmali)
        if(os.path.isfile(channel_paht)):
            f = open(channel_paht)
            lines = f.readlines()
            lines_write = []
            for line in lines:
                if(line.find('L[]/yxapi/') != -1):
                     line = line.replace('[]',package_name.replace('.', '/'))
                lines_write.append(line)
            f.close()

            f_handle = open(channel_paht,'w+')
            f_handle.truncate()
            f_handle.writelines(lines_write)
            f_handle.close()
            copy_to_package_path(channel_paht, package_name,game_path)


def copy_to_package_path(channel_paht, package_name, game_path):
    game_path = os.path.join(game_path,'smali')
    package_path =  os.path.join(game_path, package_name.replace('.', '/'), 'yxapi')
    if(not os.path.exists(package_path)):
        os.makedirs(package_path)
    shutil.copy(channel_paht, package_path)


if __name__ == '__main__':
    # get_package_name('C:\\Users\\Administrator\\dizun_yayawan_V1.0.8')
    #update_yxpay('D:\\python_debug\\Channels\\yixin','D:\\python_debug\\GameLocations\\HJR_yixin')
	sdk_channel_logo = os.path.join('C:/Users/Administrator/Desktop/app-debug',"res/mipmap-hdpi-v4/sdk_channel_logo.png");
	if os.path.exists(sdk_channel_logo):
		os.remove(sdk_channel_logo)
