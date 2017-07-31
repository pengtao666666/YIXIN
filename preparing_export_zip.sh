#!/bin/bash

checkFuncBack(){
if [ $? -eq 0 ]; then
        echo "$1 success"
else
        echo "$1 error stop build"
        exit 1
fi
}

file_counter=0
function count_dir_file(){
	file_counter=0
	for files in $1/*
	do
		if [ -f "$files" ]; then
			file_counter=`expr ${file_counter} + 1`
		fi
	done
}

# clean old export
rm -rf export

# setting out apk module
PREPARING_MODULE="app"
count_dir_file ${PREPARING_MODULE}/build/outputs/apk/
exp_build_apk=
if [ ${file_counter} -eq 3 ]; then
	exp_build_apk=${PREPARING_MODULE}/build/outputs/apk/*-release-unsigned.apk
	echo "this channel has one-so-apk path: ${exp_build_apk}"
else
	exp_build_apk=${PREPARING_MODULE}/build/outputs/apk/${PREPARING_MODULE}-universal-release-unsigned.apk
	echo "this channel has mutli-SO-apk path: ${exp_build_apk}"
fi

# if APKTOOL decompile error at ${APKTOOL_HOME} try with ${APKTOOL_2_0_1} and must set -t ${APKTOOL_2_0_1}
# More version ask administrator
echo "use apktool at: ${APKTOOL_HOME}"
# if brut.androlib.err.UndefinedResObject resource spec: 0x01010490 let install framework-res or tell administrator
# java -jar ${APKTOOL_HOME} if /home/server/opt/android-framework-res/base/framework-res.apk
java -jar ${APKTOOL_2_0_1}/apktool.jar d ${exp_build_apk} -o decompile -p ${APKTOOL_2_0_1}/ -t 1
#java -jar ${APKTOOL_HOME} d ${exp_build_apk} -o decompile
checkFuncBack "APKTOOL decompile apk"

mkdir export

# Copy resource assets libs and Rename
cp -R decompile/lib export/ForLibs
checkFuncBack "cp decompile/lib"
cp -R decompile/assets export/ForAssets
checkFuncBack "cp decompile/assets"
cp -R decompile/res export/ForRes
checkFuncBack "cp decompile/res"
if [ -d "decompile/unknown" ]; then
	echo "this channel has unknown!"
    cp -R decompile/unknown export/ForUnknown
	checkFuncBack "cp decompile/ForUnknown"
fi
rm export/ForAssets/developer.properties
checkFuncBack "rm developer.properties"

# copy dex
mkdir -p decompile/out_dex/for_zip
cp ${exp_build_apk} decompile/out_dex/for_zip/release.zip
checkFuncBack "copy dex if error see http://10.8.230.251/as_sdk_team/as_yixin/blob/master/API/KFSOSSplits.md"
unzip -o decompile/out_dex/for_zip/release.zip -d decompile/out_dex
cp decompile/out_dex/classes.dex export/classes.dex
checkFuncBack "copy class.dex"

# copy package resource
cp -R resources/* export/
checkFuncBack "copy package resource"

# remove resource
for i in "`find export -type f -name '*.xml'`"; do sed -i '/<string name=\"app_name\">.*<\/string>/{d}' $i; echo 'fix app_name ' $1; done
for i in "`find export -type f -name '*.xml'`"; do sed -i '/<public type=\"string\" name=\"app_name\".*\/>/{d}' $i; echo 'fix string app_name' $1; done
for i in "`find export -type f -name '*.xml'`"; do sed -i '/<public type=\"layout\" name=\"activity_main\".*\/>/{d}' $i; echo 'fix layout activity_main' $1; done
for i in "`find export -type f -name '*.xml'`"; do sed -i '/<public type=\"drawable\" name=\"activity_demo_main\".*\/>/{d}' $i; echo 'fix drawable activity_demo_main' $1; done
for i in "`find export -type f -name '*.xml'`"; do sed -i '/<public type=\"layout\" name=\"activity_demo_main\".*\/>/{d}' $i; echo 'fix layout activity_demo_main' $1; done
for i in "`find export -type f -name '*.xml'`"; do sed -i '/<public type=\"drawable\" name=\"ic_launcher\".*\/>/{d}' $i; echo 'fix drawable ic_launcher' $1; done
for i in "`find export -type f -name '*.xml'`"; do sed -i '/<public type=\"mipmap\" name=\"ic_launcher\".*\/>/{d}' $i; echo 'fix mipmap ic_launcher' $1; done
find export -type f -name "activity_main.xml"| xargs rm -rf
find export -type f -name "activity_demo_main.xml"| xargs rm -rf
find export -type f -name "ic_launcher.png"| xargs rm -rf
checkFuncBack "remove resource"

if [ -d "export/ForRes/values-v23" ]; then
	rm -rf export/ForRes/values-v23/
	checkFuncBack "remove export/ForRes/values-v23"
	sleep 1
	for i in "`find export -type f -name '*.xml'`"; do sed -i '/<public type=\"style\" name=\"Base\.V23\.Theme.*\/>/{d}' $i; echo 'fix file ' $1; done
	sleep 1
#	grep -c "/<public type="style" name=\"Base\.V23\.Theme.*\/" export/ForRes/values/public.xml | export
#	checkFuncBack "Remove style Base.V23.Theme"
	echo "Delete export/ForRes/values-v23 success"
fi

# zip preparing file
cd export

if [ -f "script.py" ]; then
	if [ -f "script.pyc" ]; then
		rm "script.pyc"
		sleep 1
		checkFuncBack "remove old script.pyc file"
	else
		echo -e "this script.py not compile"
	fi
    /usr/local/bin/python --version
    checkFuncBack "show python --version"
    /usr/local/bin/python -m compileall script.py
    checkFuncBack "compileall script.py"
    sleep 4
    if [ -f "script.pyc" ]; then
        echo -e "just find new script pyc, compile script.py success"
    else
    	echo -e "Error! stop build...\nCan not find new compile script.pyc file, please check!"
        exit 1
    fi
    sleep 1
fi

zip -r ../export.zip *
cd ..
rm -rf build/
sleep 1
rm -rf .gradle/
sleep 1
# rm -rf decompile/
# sleep 1

echo "all the preparing finish!"
