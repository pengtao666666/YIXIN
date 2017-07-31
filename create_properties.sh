#!/bin/bash
#

LOCAL_PRO="local.properties"
## open it to create gradle.properties
#GRADLE_PRO="gradle.properties"


if [ -f "$LOCAL_PRO" ]; then
    echo ">> File $LOCAL_PRO is exist"
    else
        cat > $LOCAL_PRO << EOF
# Please set your config path after this
sdk.dir=/home/server/opt/android-sdk-linux
#ndk.dir=/Users/sinlov/opt/android-ndk/android-ndk-r10e
EOF
echo ">> create $LOCAL_PRO finish"
fi

if [ ! -n "$GRADLE_PRO" ]; then
    echo ">> You are note set gradle.properties please set at this shell line 5."
        elif [ -f "$GRADLE_PRO" ]; then
            echo ">> File gradle.properties is exist"
        else
            cat > $GRADLE_PRO << EOF
# Please set your config path after this
#systemProp.http.proxyHost=
#systemProp.http.proxyPort=
#systemProp.https.proxyHost=
#systemProp.https.proxyPort=
EOF
echo ">> create $GRADLE_PRO finish"
fi