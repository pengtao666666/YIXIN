# coding=utf-8

__author__ = 'sinlov'

import os
import platform
import re
import shutil
import stat
import sys
import time
import optparse

reload(sys)
sys.setdefaultencoding('utf-8')

is_verbose = False
is_force = False


def change_files_write(path=str):
    for root, dirs, files in os.walk(path):
        for name in files:
            os.chmod(os.path.join(root, name), stat.S_IWRITE)
    print ('change change_files_write success')


def del_files(path=str):
    try:
        for root, dirs, files in os.walk(path):
            for name in files:
                if name.startswith("."):
                    os.remove(os.path.join(root, name))
        print ("delete path " + path + " success!")
    except Exception, e:
        print e


def is_path_idea_project(p_path=str):
    for root, dirs, files in os.walk(p_path):
        for name in files:
            if name.endswith(".iml"):
                return True
    return False


def clean_idea_useless_code():
    global is_force, is_verbose
    parser = optparse.OptionParser("Usage%prog " + "-f <force> -v <verbose>")
    parser.add_option('-v', dest='v_verbose', action="store_true", help="see info of clean", default=False)
    parser.add_option('-f', dest='f_force', action="store_true", help="force clean", default=False)
    (options, args) = parser.parse_args()
    if options.v_verbose:
        is_verbose = True
    if options.f_force:
        is_force = True

    for root, dirs, files in os.walk(os.getcwd()):
        for name in dirs:
            if name.endswith(".idea"):
                find_folder_idea = "Find .idea/ " + str(root) + "/" + str(name) + "/"
                verbose_print(find_folder_idea)
                shutil.rmtree(name, is_force)
                time.sleep(1)
            if name.endswith(".gradle"):
                find_folder_idea = "Find .idea/ " + str(root) + "/" + str(name) + "/"
                verbose_print(find_folder_idea)
                shutil.rmtree(name, is_force)
                time.sleep(1)
            if name.endswith("build"):
                find_folder_build = "Find build/ " + str(root) + "/" + str(name) + "/"
                verbose_print(find_folder_build)
                shutil.rmtree((str(root) + "/" + str(name)), is_force)
                time.sleep(1)
        for name in files:
            if name.endswith(".iml"):
                find_file_iml = "Find iml at: " + str(root) + "/" + str(name)
                verbose_print(find_file_iml)
                os.remove(str(root) + "/" + str(name))


def verbose_print(info=str):
    if is_verbose:
        print info


if __name__ == '__main__':
    # if len(sys.argv) != 2:
    #     print("your input is error please check!")
    #     exit(1)
    if is_path_idea_project(os.getcwd()):
        path_start_clean = "Start clean project at " + str(os.getcwd())
        print  path_start_clean
        clean_idea_useless_code()
        path_end_clean = "End clean project at " + str(os.getcwd())
        print path_end_clean
        exit(0)
    else:
        path_error_str = "Can not find *.iml with this project at" + str(os.getcwd())
        print path_error_str
        exit(1)
