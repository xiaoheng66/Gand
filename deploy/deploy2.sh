#!/bin/bash
echo "publish----------"

process_id=`ps -ef | grep yuheng-api-proj-0.0.1-SNAPSHOT.jar | grep -v grep |awk '{print $2}'`
if [ $process_id ] ; then
sudo kill -9 $process_id
fi

source /etc/profile
nohup java -jar ~/apiproj/yuheng-api-proj-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &

echo "end publish"
